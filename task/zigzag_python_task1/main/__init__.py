from bs4 import BeautifulSoup
from main.loader import config_loader
from main.parser import parser

# target_name = "66girls"
target_name = "naning9"
# target_name = "mocobling"

config = config_loader.load(target_name)

def six_girls_paser(item_list):
    for item in item_list:
        # item_code
        item_code = item.code
        split_code = item_code.split('_')
        item.code = split_code[1]

        # item_detail_view_url
        item_detail_view_url = item.detail_view_url
        item.detail_view_url = 'www.66girls.co.kr/' + item_detail_view_url

        item_image_url = item.item_image_url
        item.item_image_url = item_image_url.strip('//')

def naning_nine_parser(item_list):
    for item in item_list:
        # item_code
        item_code = item.code
        result = item_code.rfind('index_no')
        print("result")

if config is not None:
    try:
        target_mall = config[target_name]
        target_url = target_mall["url"]

        parser = parser()

        html = parser.get_html(target_url)
        soup = BeautifulSoup(html, 'html.parser')

        item_info_list = parser.parse(target_mall, soup)
        naning_nine_parser(item_info_list)
    except KeyError:
        print("Invalid Shopping Mall")




print("end")
