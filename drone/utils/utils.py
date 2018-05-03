from dronekit import LocationGlobal
import math
import base64
import pyscreenshot as ImageGrab
from PIL import Image
import PIL

# Define all patrol mode
class Patrol:
    PATROL_GO_AND_BACK = "SEGMENT"
    PATROL_CIRCLE = "CIRCLE"
    PATROL_ZONE = "GRID"
    BATTERY_LOW = 0


# Define Photo screenshot
class Photo:
    index = 0
    name = "Image_drone_"

    # Take a photo from Google Earth at a specific position and save it
    @staticmethod
    def take_photo(location):
        print("We take a nice picture : {}:{}:{}" .format(location.lat, location.lon, location.alt))

        imageName = "utils/photos/" + Photo.name + str(Photo.index) + ".jpg"
        # full screenshoot saved to a file
        ImageGrab.grab_to_file(imageName, "JPEG")

        # resize the photo to 800*600
        basewidth = 800
        img = Image.open(imageName)
        wpercent = (basewidth / float(img.size[0]))
        hsize = int((float(img.size[1]) * float(wpercent)))
        img = img.resize((basewidth, hsize), PIL.Image.ANTIALIAS)
        img.save(imageName)

        # save the picture on disk
        # image.save(imageName + ".jpg", "JPEG")
        # read the picture from disk for encode task
        with open(imageName, "rb") as imageFile:
            encoded_image = base64.b64encode(imageFile.read())

        Photo.index +=1;
        return True, encoded_image


def get_location_metres(original_location, dNorth, dEast):
    """
    Returns a LocationGlobal object containing the latitude/longitude `dNorth` and `dEast` metres from the
    specified `original_location`. The returned Location has the same `alt` value
    as `original_location`.

    The function is useful when you want to move the vehicle around specifying locations relative to
    the current vehicle position.
    The algorithm is relatively accurate over small distances (10m within 1km) except close to the poles.
    For more information see:
    http://gis.stackexchange.com/questions/2951/algorithm-for-offsetting-a-latitude-longitude-by-some-amount-of-meters
    """
    earth_radius = 6378137.0  # Radius of "spherical" earth
    # Coordinate offsets in radians
    dLat = dNorth / earth_radius
    dLon = dEast / (earth_radius * math.cos(math.pi * original_location.lat / 180))

    # New position in decimal degrees
    newlat = original_location.lat + (dLat * 180 / math.pi)
    newlon = original_location.lon + (dLon * 180 / math.pi)
    return LocationGlobal(newlat, newlon, original_location.alt)


def get_distance_metres(aLocation1, aLocation2):
    """
    Returns the ground distance in metres between two LocationGlobal objects.

    This method is an approximation, and will not be accurate over large distances and close to the
    earth's poles. It comes from the ArduPilot test code:
    https://github.com/diydrones/ardupilot/blob/master/Tools/autotest/common.py
    """
    dlat = aLocation2.lat - aLocation1.lat
    dlong = aLocation2.lon - aLocation1.lon
    return math.sqrt((dlat * dlat) + (dlong * dlong)) * 1.113195e5


# Create a file for a live view in Google Earth
def update_kml_file(location):
    #   Create KML file in order to update the google earth view

    lat = location.lat
    lon = location.lon
    alt = location.alt

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

    with open("utils/Google_Earth/camera_tmp.kml", "w") as kml_file:
        kml_file.write(kml_template.format(**coord))
