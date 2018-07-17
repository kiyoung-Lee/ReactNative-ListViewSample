from bs4 import BeautifulSoup
from shopping_mall_parser import parser
from shopping_mall_parser import loader

# target_name = "66girls"
# target_name = "naning9"
target_name = "mocobling"

config = loader.load(target_name)

if config is not None:
    try:
        target_mall = config[target_name]
        target_url = target_mall["url"]

        html = loader.get_html(target_url)
        soup = BeautifulSoup(html, 'html.parser')

        item_info_list = parser.parse(target_mall, soup)
        print("Get ItemList Finish")
    except KeyError:
        print("Invalid Shopping Mall")

print("end")
