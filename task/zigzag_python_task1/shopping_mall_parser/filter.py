def execute_filter_for_item(target_mall, item):
    filter_contract = target_mall["filter"]

    # item_code
    if "item_code" in filter_contract:
        item_code_filter_list = filter_contract["item_code"]
        item.code = get_value_by_filter(item.code, item_code_filter_list)

    # detail_view_url
    if "detail_view_url" in filter_contract:
        detail_view_url_filter_list = filter_contract["detail_view_url"]
        item.detail_view_url = get_value_by_filter(item.detail_view_url, detail_view_url_filter_list)

    # item_image_url
    if "item_image_url" in filter_contract:
        item_image_url_filter_list = filter_contract["item_image_url"]
        item.item_image_url = get_value_by_filter(item.item_image_url, item_image_url_filter_list)

    # item_name
    if "item_name" in filter_contract:
        item_name_filter_list = filter_contract["item_name"]
        item.name = get_value_by_filter(item.name, item_name_filter_list)


def get_value_by_filter(value, filter_list):
    filter_value_result = value
    result_list = list()

    for filter in filter_list:
        filter_value_result = execute_command_filter(value, filter, result_list)
        result_list.append(filter_value_result)

    return filter_value_result


def execute_command_filter(value, filter, result_value_list):
    command = filter["command"]
    parameter_list = filter["parameters"]

    if command == "find":
        return value.find(parameter_list[0])
    elif command == "plus_back_get_result":
        return result_value_list[parameter_list[0]] + parameter_list[1]
    elif command == "substring_get_result":
        start = result_value_list[parameter_list[0]]
        end = result_value_list[parameter_list[1]]
        return value[start:end]
    elif command == "plus_front":
        return parameter_list[0] + value
    elif command == "split":
        return value.split(parameter_list[0])
    elif command == "element":
        split_list = result_value_list[parameter_list[0]]
        return split_list[parameter_list[1]]
    elif command == "strip":
        return value.strip(parameter_list[0])
    elif command == "encode":
        return value.encode(parameter_list[0])
    elif command == "decode_get_result":
        return result_value_list[parameter_list[0]].decode(parameter_list[1])
