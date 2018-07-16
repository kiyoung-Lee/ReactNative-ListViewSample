from bs4 import BeautifulSoup
from main.loader import config_loader
from main.parser import parser

# target_name = "66girls"
# target_name = "naning9"
target_name = "mocobling"

config = config_loader.load(target_name)

if config is not None:
    try:
        target_mall = config[target_name]
        target_url = target_mall["url"]

        parser = parser()

        html = parser.get_html(target_url)
        soup = BeautifulSoup(html, 'html.parser')

        item_info_list = parser.parse(target_mall, soup)
    except KeyError:
        print("Invalid Shopping Mall")

print("end")
