from dronekit import connect, VehicleMode, LocationGlobalRelative, LocationGlobal, Command
import time
from pymavlink import mavutil


class NotreDrone:
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

    def arm_and_takeoff(self, aTargetAltitude):
        # Arms vehicle and fly to aTargetAltitude.

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

    def isTakeoff(self, aTargetAltitude):
        # Wait until the vehicle reaches a safe height before processing the goto (otherwise the command
        #  after Vehicle.simple_takeoff will execute immediately).
        return self.vehicle.location.global_relative_frame.alt >= aTargetAltitude * 0.95  # Trigger just below target alt.

    def changeMission(self, maListe):
        # Put mission's point to reach to the dronekit vehicle for automatic mode

        cmds = self.vehicle.commands

        print(" Clear any existing commands")
        self.vehicle.commands.clear()

        print(" Define/add new commands.")
        # Add new commands. The meaning/order of the parameters is documented in the Command class.

        # Add MAV_CMD_NAV_TAKEOFF command. This is ignored if the vehicle is already in the air.
        # self.vehicle.commands.add(
        #    Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, mavutil.mavlink.MAV_CMD_NAV_TAKEOFF, 0, 0,
        #            0, 0,
        #            0, 0, 0, 0, 30))

        for point in maListe:
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
        cmds.upload()

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

    def start(self):
        # This method take-off the drone, reach each point of the mission, Return To Launch and take-On

        # Take-off
        self.arm_and_takeoff(self.takeoffAltitude)

        # Asked Altitude is reached
        while not self.isTakeoff(self.takeoffAltitude):
            time.sleep(0.5)
            self.updateKmlFile()

        # Set mode to AUTO to start mission
        self.vehicle.mode = VehicleMode("AUTO")

        print("Starting mission")
        # Reset mission set to first (0) waypoint
        self.vehicle.commands.next = 0

        while True:
            nextwaypoint = self.vehicle.commands.next
            # print('Distance to waypoint (%s): %s' % ( nextwaypoint, distance_to_current_waypoint()))

            self.updateKmlFile()

            if nextwaypoint == self.vehicle.commands.count:
                print("Exit 'standard' mission when start heading to final waypoint : ", self.vehicle.commands.count)
                break;
            time.sleep(1)

        print('Return to launch')
        self.vehicle.mode = VehicleMode("RTL")

        # Down is reached
        while self.isTakeoff(1):
            self.updateKmlFile()
            time.sleep(1)

        # Close vehicle object before exiting script
        print("Close vehicle object")
        self.vehicle.close()

        # Shut down simulator if it was started.
        if self.sitl is not None:
            self.sitl.stop()
