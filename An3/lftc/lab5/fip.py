def readFip(fileName):
    fipTable = []
    with open(fileName, 'r') as f:
        lines = f.readlines()
        for line in lines:
            fipEntry = line.split()
            fipTable.append(fipEntry)
    return fipTable

def fipToCode(fipTable):
    code = ""
    for entry in fipTable:
        if entry[1] != '#' and entry[1] != 'main' and entry[1] != 'std' \
        and entry[1] != '<' and entry[1] != 'iostream' and entry[1] != '(' \
        and entry[1] != ')':
            code += entry[1] + ' '
        else:
            code += entry[1]
    return code

if __name__ == '__main__':
    fipTable = readFip('fip.txt')
    # print(fipTable)
    # print(fipToCode(fipTable))