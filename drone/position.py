class position:

    def __init__(self, latitude, longitude, altitude):
        self.latitude = latitude
        self.longitude = longitude
        self.altitude = altitude

    def setLatitude(self, latitude):
        self.latitude = latitude

    def getLatitude(self):
        return self.latitude

    def setLongitude(self, longitude):
        self.longitude = longitude

    def getLongitude(self):
        return self.longitude

    def setAltitude(self, altitude):
        self.altitude = altitude

    def getAltitude(self):
        return self.altitude