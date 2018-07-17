from model.item import item_info
from shopping_mall_parser import filter


def parse(target_mall, soup):
    root_tag_list = target_mall["root_tag_list"]
    item_info_list = list()

    for root_tag in root_tag_list:
        is_container = root_tag["is_container"]
        cell_info = target_mall["cell_name"]
        cell_tag_name = cell_info["tag"]
        cell_class = cell_info["class"]

        if is_container:
            cell_list = get_cell_list_container_type(root_tag, soup, target_mall, cell_tag_name, cell_class)
        else:
            cell_list = soup.find_all(cell_tag_name, cell_class)

        for item_cell in cell_list:
            item = get_item_raw_data(target_mall, root_tag, item_cell)
            filter.execute_filter_for_item(target_mall, item)
            item_info_list.append(item)

    return item_info_list


def get_cell_list_container_type(root_tag, soup, target_mall, cell_tag_name, cell_class):
    root_tag_name = root_tag["tag"]
    root_tag_class = root_tag["class"]
    item_container = soup.find(root_tag_name, root_tag_class)

    grid_name_key = root_tag["grid_name_key"]

    grid_info = target_mall[grid_name_key]
    grid_name = grid_info["tag"]
    grid_class = grid_info["class"]

    grid = item_container.find(grid_name, grid_class)
    cell_list = grid.find_all(cell_tag_name, cell_class)
    return cell_list


def get_item_raw_data(target_mall, root_tag, item_cell):
    pattern = target_mall["pattern"]
    item_code_pattern_key = root_tag["item_code_pattern"]
    item_detail_url_key = root_tag["item_detail_url_pattern"]
    image_url_key = root_tag["image_url_pattern"]
    item_name_key = root_tag["item_name_pattern"]
    item_price_key = root_tag["item_price_pattern"]

    item_code = get_item_by_pattern(item_cell, pattern[item_code_pattern_key])
    item_detail_url = get_item_by_pattern(item_cell, pattern[item_detail_url_key])
    item_image_url = get_item_by_pattern(item_cell, pattern[image_url_key])
    item_name = get_item_by_pattern(item_cell, pattern[item_name_key])
    item_price = get_item_by_pattern(item_cell, pattern[item_price_key])

    item = item_info(item_code, item_detail_url, item_image_url, item_name, item_price)
    return item


def get_item_by_pattern(item_cell, pattern_list):
    item_result = item_cell
    pattern_list_length = len(pattern_list)
    for idx in range(len(pattern_list)):
        item_result = parse_by_pattern(item_result, pattern_list[idx])

    if (idx + 1) == pattern_list_length:
        return item_result


def parse_by_pattern(target, pattern):
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
