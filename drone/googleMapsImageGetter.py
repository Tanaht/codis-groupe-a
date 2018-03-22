import math
from PIL import Image
import urllib
import os

class googleMapsImageGetter:

    def __init__(self, name, lat, lon, alt):
        self.name = name
        self.lat = lat
        self.lon = lon
        self.alt = alt
        self.mapType = 's'
        self.size = 256*5

    def getImage(self):
        try:
            # Get the high resolution image
            img = self.generateImage()
        except IOError:
            print("Error when generating image")
        else:
            # Save the image to disk
            img.save(self.name+'.png')

    def getXY(self):
        # Generates an X,Y tile coordinate based on the latitude, longitude and altitude
        # Returns an X,Y tile coordinate
        tile_size = 256
        # A zoom level of 2 will have 2^2 = 4 tiles
        numTiles = 1 << self.alt
        # Find the x_point given the longitude
        point_x = (tile_size / 2 + self.lon * tile_size / 360.0) * numTiles // tile_size
        # Convert the latitude to radians and take the sine
        sin_y = math.sin(self.lat * (math.pi / 180.0))
        # Calulate the y coorindate
        point_y = ((tile_size / 2) + 0.5 * math.log((1 + sin_y) / (1 - sin_y)) * -(tile_size / (2 * math.pi))) * numTiles // tile_size
        return int(point_x), int(point_y)

    def generateImage(self):
        start_x, start_y = self.getXY()
        # Set the size of the image
        width = self.size
        height = self.size
        # Create a new image of the size require
        map_img = Image.new('RGB', (width, height))
        # Get all tiles
        for x in range(0, 5):
            for y in range(0, 5):
                url = 'https://mt0.google.com/vt/lyrs=s&x=' + str(start_x + x) + '&y=' + str(start_y + y) + '&z=' + str(self.alt)
                current_tile = str(x) + '-' + str(y)
                urllib.urlretrieve(url, current_tile)
                im = Image.open(current_tile)
                map_img.paste(im, (x * 256, y * 256))
                os.remove(current_tile)
        return map_img
