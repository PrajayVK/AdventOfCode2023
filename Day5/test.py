def parse_map_section(section):
    mapping = {}
    lines = section.strip().split('\n')
    for line in lines:
        dest_start, src_start, range_length = map(int, line.split())
        for i in range(range_length):
            mapping[src_start + i] = dest_start + i
    return mapping

def process_input(file_path):
    with open(file_path, 'r') as file:
        input_data = file.read()

    sections = input_data.split('\n\n')
    if len(sections) < 2:
        raise ValueError("Input file does not contain expected sections.")

    seeds_section = sections[0].split(':')
    if len(seeds_section) < 2:
        raise ValueError("Seeds section is not formatted as expected.")

    seeds = list(map(int, seeds_section[1].split()))

    mappings = {}
    for section in sections[1:]:
        title, data = section.split(':')
        mappings[title.strip()] = parse_map_section(data)

    return seeds, mappings

def convert_through_mappings(number, mappings):
    for key in mappings:
        number = mappings[key].get(number, number)
    return number

def main(file_path):
    seeds, mappings = process_input(file_path)
    lowest_location = min(convert_through_mappings(seed, mappings) for seed in seeds)
    print("The lowest location number is:", lowest_location)


if __name__ == "__main__":
    file_path = "input.txt"  # Replace with the path to your input file
    main(file_path)
