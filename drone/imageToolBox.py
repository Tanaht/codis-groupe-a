import base64

class imageToolBox:

    def __init__(self):
        self.sendPhotoType = "SEND_PHOTO"


    def convertPngToBase64(self, imagePath):
        with open(imagePath, "rb") as imageFile:
            str = base64.b64encode(imageFile.read())
            return str

    # Drone -> Serveur
    ## Envoi d'une photo
    # json
    # {
    #     type: "SEND_PHOTO",
    #     datas: {
    #        photo: String, / * Photo encodee en Base64 * /
    #        date: long, / * Timestamp * /
    #        location: {lat: float, lng: float}
    #     }
    # }

    def getJsonFromPng(self, date, position, imagePath):
        imageBase64 = self.convertPngToBase64(imagePath)
        json_data = {
            'type': self.sendPhotoType,
            'datas': {
                'photo': imageBase64,
                'date': date,
                'location': {
                    'lat': position.getLatitude(),
                    'lng': position.getLongitude()
                }
            }
        }
        return json_data