import json


class config_loader:

    @staticmethod
    def load(name):
        file_name = ""

        if name == "66girls":
            file_name = "six_six_girls_config"
        elif name == "naning9":
            file_name = "naning_nine_config"
        elif name == "mocobling":
            file_name = "mocobling_config"

        file_path = '../config/' + file_name + '.json'

        try:
            with open(file_path, 'r') as f:
                config = json.load(f)
                return config
        except IOError:
            print("Invalid Config File Name")