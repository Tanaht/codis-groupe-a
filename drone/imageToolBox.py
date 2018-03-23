import base64

class imageToolBox:

    #
    # Usage :
    # (with a file called "file.png")
    #
    # imageToolBox = imageToolBox()
    #
    # imgtb.convertPngToBase64('file.png')
    # return a String representing the image file in base64
    #
    # imgtb.getJsonFromPng(date, position, 'file.png')
    # return a Json with given information and base64 image
    #
    def convertPngToBase64(self, imagePath):
        with open(imagePath, "rb") as imageFile:
            str = base64.b64encode(imageFile.read())
            return str

    def getJsonFromPng(self, date, position, imagePath):
        imageBase64 = self.convertPngToBase64(imagePath)
        json_data = {
            'date': date,
            'coordonees': {
                'lat': position.getLatitude(),
                'lng': position.getLongitude()
            },
            'photo': imageBase64
        }
        return json_data