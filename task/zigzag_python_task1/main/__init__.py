import requests
import json
from bs4 import BeautifulSoup

from main.parser import test_test


def get_html(url):
    _html = ""
    resp = requests.get(url)
    if resp.status_code == 200:
        _html = resp.text
    return _html


with open('config.json', 'r') as f:
    config = json.load(f)

target_name = "66girls"

try:
    target_mall = config[target_name]
    target_url = target_mall["url"]

    html = get_html(target_url)
    soup = BeautifulSoup(html, 'html.parser')

    item_info_list = test_test(target_mall, soup)

except KeyError:
    print("Invalid Shopping Mall")

print("end")