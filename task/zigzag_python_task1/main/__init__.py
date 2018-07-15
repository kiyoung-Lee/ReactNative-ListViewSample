from bs4 import BeautifulSoup
from main.loader import config_loader
from main.parser import parser

# target_name = "66girls"
# target_name = "naning9"
target_name = "mocobling"

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
        item_code_start_pos = item_code.find('index_no')
        item_code_end_pos = item_code.find('&')

        substring_start_idx = item_code_start_pos + 9
        item.code = item_code[substring_start_idx:item_code_end_pos]

        # detail_vice_url
        detail_view_url = item.detail_view_url
        item.detail_view_url = "http://www.naning9.com" + detail_view_url
        print("result")

def mocobling_parser(item_list):
    for item in item_list:
        # item_code

        # detail_view_url
        detail_view_url = item.detail_view_url
        item.detail_view_url = "http://www.mocobling.com" + detail_view_url

        # item_name
        # 한글주석
        item_name = item.name
        item.name = item_name.encode('utf-8').decode('utf-8')
        print("test")

if config is not None:
    try:
        target_mall = config[target_name]
        target_url = target_mall["url"]

        parser = parser()

        html = parser.get_html(target_url)
        soup = BeautifulSoup(html, 'html.parser')

        item_info_list = parser.parse(target_mall, soup)
        mocobling_parser(item_info_list)
    except KeyError:
        print("Invalid Shopping Mall")




print("end")
