from dronekit import connect, VehicleMode, LocationGlobalRelative, LocationGlobal, Command
import time
from pymavlink import mavutil
from utils.utils import Patrol, Photo, get_distance_metres, update_kml_file
from utils.SocketIstic import SocketIstic


# library help : http://python.dronekit.io/automodule.html
class NotreDrone():

    stopRequested = False
    droneStopped = False;

    # Construtor with specific IP, targeted altitude
    def __init__(self, sIP, simulation):
        # START du drone
        self.connection_string = sIP
        self.sitl = None
        # Start SITL if no connection string specified
        if simulation:
            import dronekit_sitl
            self.sitl = dronekit_sitl.start_default()
            self.connection_string = self.sitl.connection_string()

        # Connect to the Vehicle
        print("Connecting to vehicle on: %", self.connection_string)
        self.vehicle = connect(self.connection_string, wait_ready=True)
        self.patrol = 0

    def setIntervention(self,interventionId):
        self.interventionId = interventionId

    def setAltitude(self, altitude):
        self.takeoffAltitude = altitude

    def turnoff(self, value):
        self.stopRequested = value

    # Arms vehicle and fly to aTargetAltitude.
    def arm_and_takeoff(self, aTargetAltitude):

        print("Basic pre-arm checks")
        # Don't let the user try to arm until autopilot is ready
        while not self.vehicle.is_armable:
            print(" Waiting for vehicle to initialise...")
            time.sleep(1)

        print("Arming motors")
        # Copter should arm in GUIDED mode
        self.vehicle.mode = VehicleMode("GUIDED")
        self.vehicle.armed = True

        while not self.vehicle.armed:
            print(" Waiting for arming...")
            time.sleep(1)

        print("Taking off!")
        self.vehicle.simple_takeoff(aTargetAltitude)  # Take off to target altitude

    # Wait until the vehicle reaches a safe height before processing the goto (otherwise the command
    #  after Vehicle.simple_takeoff will execute immediately).
    def is_takeoff(self, aTargetAltitude):
        return self.vehicle.location.global_relative_frame.alt >= aTargetAltitude * 0.95  # Trigger just below target alt.

    # Put mission's point for a mission
    def change_mission(self, liste_position):
        # Put mission's point to reach to the dronekit vehicle for automatic mode
        self.mission_location = liste_position

    # Update the drone with all new positions to follow
    def charge_commands(self, liste, head_to_north):

        # Clear Vehicle.commands and flush.
        self.vehicle.commands.clear()
        self.vehicle.flush()

        # Reset the Vehicle.commands from the vehicle.
        self.vehicle.commands.download()
        self.vehicle.commands.wait_ready()

        # MAV_CMD_DO_DIGICAM_CONTROL : start/stop the camera recorder
        # MAV_CMD_DO_SET_ROI : the head of the drone watch a specific Region Of Interest

        print(" Define/add new commands.")
        # Add new commands. The meaning/order of the parameters is documented in the Command class.
        for point in liste:
            # self.vehicle.commands.add(
            #     Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT,
            #             mavutil.mavlink.MAV_CMD_NAV_WAYPOINT, 0, 0, 0,
            #             0, 0, 0, point.lat, point.lon, point.alt))

            if head_to_north:
                self.vehicle.commands.add(
                    Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT,
                            mavutil.mavlink.MAV_CMD_DO_SET_ROI, 0, 0, 0,
                            0, 0, 0, 90, point.lon, point.alt))
                self.vehicle.commands.add(
                    Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT,
                            mavutil.mavlink.MAV_CMD_NAV_LOITER_TIME, 0, 0, 2,
                            0, 0, 0, 0, 0, 0))

            self.vehicle.commands.add(
                Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT,
                        mavutil.mavlink.MAV_CMD_NAV_WAYPOINT, 0, 0, 0,
                        0, 0, 0, point.lat, point.lon, point.alt))

        # point fictif
        self.vehicle.commands.add(
                Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT,
                    mavutil.mavlink.MAV_CMD_NAV_WAYPOINT, 0, 0, 0,
                    0, 0, 0, point.lat, point.lon, point.alt))

        print(" Upload new mission to vehicle")
        self.vehicle.commands.upload()
        self.vehicle.commands.wait_ready()
        # self.vehicle.flush()

    # Put mission's point for a patrol mission
    def change_patrol_mission(self, liste_position, patrol):
        # We need to change the mission
        self.new_patrol_mission = True
        # list of the patrol mission
        self.patrol_mission_location = liste_position
        # Type of patrol we must execute
        self.patrol = patrol

    # Arm the drone and take-off
    def start(self):
        # This method take-off the drone, reach each point of the mission, Return To Launch and take-On

        SocketIstic.get_socket().send_position(self.vehicle.location.global_frame, self.vehicle.battery.level, self.interventionId)
        # Take-off
        self.arm_and_takeoff(self.takeoffAltitude)

        # Asked Altitude is reached
        while not self.is_takeoff(self.takeoffAltitude):
            update_kml_file(self.vehicle.location.global_frame)

            time.sleep(1)

    # the drone will reach the launch position and land
    def stop(self):
        print('Return to launch')
        self.vehicle.mode = VehicleMode("RTL")

        # Floor is reached
        while self.is_takeoff(1):
            update_kml_file(self.vehicle.location.global_frame)
            location = self.vehicle.location.global_relative_frame
            SocketIstic.get_socket().send_position(location, self.vehicle.battery.level, self.interventionId)
            time.sleep(1)

        # Close vehicle object before exiting script
        print("Close vehicle object")
        self.vehicle.close()

        # Shut down simulator if it was started.
        if self.sitl is not None:
            self.sitl.stop()

    # The drone will start the patrol according the patrol_mission_location and patrol type
    # normal patrol, reach point 1,2,3,1,2,3,1....
    # go and back patrol : 1,2,3,2,1,2,3,...
    def start_patrol(self):

        print(" *** PATROL MODE *** ")
        liste = self.patrol_mission_location
        liste_back = []

        while (self.vehicle.battery.level > Patrol.BATTERY_LOW) and not self.stopRequested :
            self.execute(liste, True)

            if self.patrol == Patrol.PATROL_GO_AND_BACK:
                if len(liste_back) == 0:
                    nb = len(liste)
                    i = nb - 2
                    while i >= 0:
                        liste_back.append(liste[i])
                        i = i - 1
                self.execute(liste_back, True)

    def is_position_reached(self,targetLocation):
        remaining_distance=get_distance_metres(self.vehicle.location.global_relative_frame, targetLocation)
        print("Distance to target: ", remaining_distance)
        return (remaining_distance <= 5) #Just below target, in case of undershoot.

    def simple_goto(self,targetLocation):
        self.vehicle.simple_goto(targetLocation)
        while not self.is_position_reached(targetLocation):
            time.sleep(0.5)

    def auto_goto(self,targetLocation):
        self.vehicle.commands.clear()
        self.vehicle.commands.add(Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT,
                        mavutil.mavlink.MAV_CMD_NAV_WAYPOINT, 0, 0, 0,
                        0, 0, 0, targetLocation.lat, targetLocation.lon, targetLocation.alt))
        while not self.is_position_reached(targetLocation):
            time.sleep(0.5)

    # The drone will follow some positions in order to reach the patrol first position
    def goto_patrol_zone(self):
        # Arms vehicle and fly to aTargetAltitude.

        print(" *** GO TO PATROL ZONE *** ")
        liste_position = self.mission_location
        self.execute(liste_position, False)

    # Normal cycle for a drone to reach different points
    def execute(self, liste, isPatrol):

        self.charge_commands(liste, isPatrol)

        # Set mode to AUTO to start mission
        self.vehicle.mode = VehicleMode("AUTO")

        # Reset mission set to first (0) waypoint
        self.vehicle.commands.next = 0

        # we take a photo only one time per 201 command
        photo_ok = False

        while (self.vehicle.battery.level > Patrol.BATTERY_LOW) and not self.stopRequested:
            nextwaypoint = self.vehicle.commands.next

            location = self.vehicle.location.global_frame

            # live update for Google Earth view
            update_kml_file(location)

            # live update for drone position on the android map
            SocketIstic.get_socket().send_position(location, self.vehicle.battery.level, self.interventionId)

            # if we detect a command 19, we take a photo and send it to the server
            if self.vehicle.commands.next - 1 >= 0:
                # print photo_ok
                if self.vehicle.commands[self.vehicle.commands.next - 1].command == 19:
                    if not photo_ok:
                        (photo_ok, image) = Photo.take_photo(location)
                        SocketIstic.get_socket().send_photo(location, image, self.interventionId)
                else:
                    photo_ok = False

            # End of requested way
            if nextwaypoint == self.vehicle.commands.count:
                print("Final point reached : ", self.vehicle.commands.count)
                break
            time.sleep(1)
