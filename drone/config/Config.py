import logging
import yaml

class Config:
    def __init__(self):
        logging.basicConfig(level=logging.INFO)
        with open("config.yml", 'r') as stream:
            try:
                self.document = yaml.load(stream)
                self.socket_host = self.document["config"]["connection"]["socket"]["host"]
                self.socket_port = self.document["config"]["connection"]["socket"]["port"]
                self.drone_host = self.document["config"]["connection"]["drone"]["host"]
                self.drone_port = self.document["config"]["connection"]["drone"]["port"]
            except yaml.YAMLError as exc:
                print(exc)
