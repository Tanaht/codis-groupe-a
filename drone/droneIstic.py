from dronekit import connect, VehicleMode, LocationGlobalRelative, LocationGlobal, Command
import time
from pymavlink import mavutil
from utils import Patrol, get_distance_metres


class NotreDrone:

    # Construtor with specific IP, targeted altitude
    def __init__(self, sIP, simulation, altitude):
        # START du drone
        self.connection_string = sIP
        self.sitl = None
        self.takeoffAltitude = altitude
        # Start SITL if no connection string specified
        if simulation:
            import dronekit_sitl
            self.sitl = dronekit_sitl.start_default()
            self.connection_string = self.sitl.connection_string()

        # Connect to the Vehicle
        print("Connecting to vehicle on: %", self.connection_string)
        self.vehicle = connect(self.connection_string, wait_ready=True)
        self.patrol = 0

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
    def isTakeoff(self, aTargetAltitude):
        return self.vehicle.location.global_relative_frame.alt >= aTargetAltitude * 0.95  # Trigger just below target alt.

    # Put mission's point for a mission
    def change_mission(self, maListe):
        # Put mission's point to reach to the dronekit vehicle for automatic mode
        self.mission_location = maListe

    # Update the drone with all new positions to follow
    def charge_commands(self,liste):

        # Clear Vehicle.commands and flush.
        self.vehicle.commands.clear()
        self.vehicle.flush()

        # Reset the Vehicle.commands from the vehicle.
        self.vehicle.commands.download()
        self.vehicle.commands.wait_ready()
        print(" APRES CLEAR et DOWNLOAD : nb commandes ", self.vehicle.commands.count)

        print(" Define/add new commands.")
        # Add new commands. The meaning/order of the parameters is documented in the Command class.
        for point in liste:
            print(" Point :{}:{}:{}" .format(point.lat, point.lon, point.alt))
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
    def change_patrol_mission(self, maListe, patrol):
        # We need to change the mission
        self.new_patrol_mission = True
        # list of the patrol mission
        self.patrol_mission_location = maListe
        # Type of patrol we must execute
        self.patrol = patrol

    # Create a file for a live view in Google Earth
    def updateKmlFile(self):
        #   Create KML file in order to update the google earth view

        lat = self.vehicle.location.global_frame.lat
        lon = self.vehicle.location.global_frame.lon
        alt = self.vehicle.location.global_frame.alt

        kml_template = '''<kml xmlns="http://www.opengis.net/kml/2.2"
         xmlns:gx="http://www.google.com/kml/ext/2.2">
           <Document>
             <name>Test camera KML</name>
             <open>1</open>
             <Camera>
               <longitude>{longitude}</longitude>
               <latitude>{latitude}</latitude>
               <altitude>{altitude}</altitude>
               <heading>{heading}</heading>
               <tilt>{tilt}</tilt>
               <altitudeMode>absolute</altitudeMode>
             </Camera>
           </Document>
         </kml>'''

        coord = {'longitude': lon,
                 'latitude': lat,
                 'altitude': alt,
                 'heading': 0,
                 'tilt': 0}

        with open("camera_tmp.kml", "w") as kml_file:
            kml_file.write(kml_template.format(**coord))

    # Arm the drone and take-off
    def start(self):
        # This method take-off the drone, reach each point of the mission, Return To Launch and take-On

        # Take-off
        self.arm_and_takeoff(self.takeoffAltitude)

        # Asked Altitude is reached
        while not self.isTakeoff(self.takeoffAltitude):
            time.sleep(1)
            self.updateKmlFile()

    # the drone will reach the launch position and land
    def stop(self):
        print('Return to launch')
        self.vehicle.mode = VehicleMode("RTL")

        # Floor is reached
        while self.isTakeoff(1):
            self.updateKmlFile()
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

        while self.vehicle.battery.level > 40:
            self.execute(liste)

            if self.patrol == Patrol.PATROL_GO_AND_BACK:
                if len(liste_back) == 0:
                    nb = len(liste)
                    i = nb - 2
                    while (i >= 0):
                        liste_back.append(liste[i])
                        i = i - 1
                self.execute(liste_back)

    def is_position_Reached(self,targetLocation):
        remainingDistance=get_distance_metres(self.vehicle.location.global_relative_frame, targetLocation)
        print "Distance to target: ", remainingDistance
        return (remainingDistance <= 5) #Just below target, in case of undershoot.

    def simple_goto(self,targetLocation):
        self.vehicle.simple_goto(targetLocation)
        while not self.is_position_Reached(targetLocation):
            time.sleep(0.5)

    def auto_goto(self,targetLocation):
        self.vehicle.commands.clear()
        self.vehicle.commands.add(Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT,
                        mavutil.mavlink.MAV_CMD_NAV_WAYPOINT, 0, 0, 0,
                        0, 0, 0, targetLocation.lat, targetLocation.lon, targetLocation.alt))
        while not self.is_position_Reached(targetLocation):
            time.sleep(0.5)

    # The drone will follow some positions in order to reach the patrol first position
    def goto_patrol_zone(self):
        # Arms vehicle and fly to aTargetAltitude.

        print(" *** GO TO PATROL ZONE *** ")
        liste = self.mission_location
        self.execute(liste)

    # Normal cycle for a drone to reach different points
    def execute(self, liste):

        self.charge_commands(liste)

        # Set mode to AUTO to start mission
        self.vehicle.mode = VehicleMode("AUTO")

        # Reset mission set to first (0) waypoint
        self.vehicle.commands.next = 0

        while self.vehicle.battery.level > 40:
            nextwaypoint = self.vehicle.commands.next
            # self.updateKmlFile()

            if nextwaypoint == self.vehicle.commands.count:
                print("Final point reached : ", self.vehicle.commands.count)
                break;
            time.sleep(1)
