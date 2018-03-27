from dronekit import LocationGlobal

# Content of the message received from the server
class Mission:
    def __init__(self, trame):
        self.type = ""              # type of message received
        self.liste = []             # goToZone positions
        self.patrol_liste = []      # patrol positions
        self.patrol = ""            # type of patrol, see Patrol class in module Utils
        self.altitude = 30          # Default altitude

        # Read JSON
        self.type = trame['type']
        patrol = trame['datas']['type']
        self.altitude = trame['datas']['altitude']

        # Read all positions
        # For the moment we only manage patrol position, In a second time
        # we will manage position to go to zone
        if self.type == "ASSIGN_MISSION":
            # decode position for GotoZone
            # elt1 = trame['datas']['locations']
            # for point in elt1:
            #     self.liste.append(LocationGlobal(point['lat'], point['lng'], self.altitude))

            elt1 = trame['datas']['locations']
            for point in elt1:
                self.patrol_liste.append(LocationGlobal(point['lat'], point['lng'], self.altitude))