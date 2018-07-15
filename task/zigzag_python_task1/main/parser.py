import requests
from main.item import item_info


class parser:
    def get_html(self, url):
        _html = ""
        resp = requests.get(url)
        if resp.status_code == 200:
            _html = resp.text
        return _html

    def parse(self, target_mall, soup):
        root_tag_list = target_mall["root_tag_list"]

        item_info_list = list()

        for root_tag in root_tag_list:
            is_container = root_tag["is_container"]
            item_code_pattern_key = root_tag["item_code_pattern"]
            item_detail_url_key = root_tag["item_detail_url_pattern"]
            image_url_key = root_tag["image_url_pattern"]
            item_name_key = root_tag["item_name_pattern"]
            item_price_key = root_tag["item_price_pattern"]

            cell_info = target_mall["cell_name"]
            cell_tag_name = cell_info["tag"]
            cell_class = cell_info["class"]

            if is_container:
                root_tag_name = root_tag["tag"]
                root_tag_class = root_tag["class"]
                item_container = soup.find(root_tag_name, root_tag_class)

                grid_name_key = root_tag["grid_name_key"]

                grid_info = target_mall[grid_name_key]
                grid_name = grid_info["tag"]
                grid_class = grid_info["class"]

                grid = item_container.find(grid_name, grid_class)
                cell_list = grid.find_all(cell_tag_name, cell_class)
            else:
                cell_list = soup.find_all(cell_tag_name, cell_class)

            for item_cell in cell_list:
                pattern = target_mall["pattern"]

                item_code = self.get_item_by_pattern(item_cell, pattern[item_code_pattern_key])
                item_detail_url = self.get_item_by_pattern(item_cell, pattern[item_detail_url_key])
                item_image_url = self.get_item_by_pattern(item_cell, pattern[image_url_key])
                item_name = self.get_item_by_pattern(item_cell, pattern[item_name_key])
                item_price = self.get_item_by_pattern(item_cell, pattern[item_price_key])

                item = item_info(item_code, item_detail_url, item_image_url, item_name, item_price)
                item_info_list.append(item)

        return item_info_list

    def get_item_by_pattern(self, item_cell, pattern_list):
        item_result = item_cell
        pattern_list_length = len(pattern_list)
        for idx in range(len(pattern_list)):
            item_result = self.parser_by_pattern(item_result, pattern_list[idx])

        if (idx + 1) == pattern_list_length:
            return item_result.encode('utf-8')

    def parser_by_pattern(self, target, pattern):
        command = pattern["command"]
        tag_name = pattern["tag"]
        if "class" in pattern:
            class_name = pattern["class"]

        if command == "find":
            return target.find(tag_name)
        elif command == "find_class":
            return target.find(tag_name, class_name)
        elif command == "element":
            return target[tag_name]
        elif command == "text":
            return target.text
        elif command == "find_all":
            return target.find_all(tag_name)
        elif command == "find_all_class":
            return target.find_all(tag_name, class_name)
