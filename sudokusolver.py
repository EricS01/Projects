board = [
    [7,8,0,4,0,0,1,2,0],
    [6,0,0,0,7,5,0,0,9],
    [0,0,0,6,0,1,0,7,8],
    [0,0,7,0,4,0,2,6,0],
    [0,0,1,0,5,0,9,3,0],
    [9,0,4,0,6,0,0,0,5],
    [0,7,0,3,0,0,0,1,2],
    [1,2,0,0,0,7,4,0,0],
    [0,4,9,2,0,6,0,0,7]
]

def valid_board(bor, num, pos):
    # checking row
    for i in range(len(bor[0])):
        if bor[pos[0]][i] == num and pos[1] != i:
            return False
    # checking column
    for j in range(len(bor)):
        if bor[j][pos[1]] == num and pos[0] != j:
            return False
    # checking 3x3 box
    sub_box_x = pos[1] // 3
    sub_box_y = pos[0] // 3

    for i in range(sub_box_y * 3, sub_box_y * 3 + 3):
        for j in range(sub_box_x * 3, sub_box_x * 3 + 3):
            if bor[i][j] == num and (i,j) != pos:
                return False

    return True

def solver(bor):

    find = get_empty(bor)
    if not find:
        return True
    else:
        row, col = find

    for i in range(1,10):
        if valid_board(bor, i, (row, col)):
            bor[row][col] = i

            if solver(bor) :
                return True

            bor[row][col] = 0
    return False

def print_board(bor):

    for i in range(len(bor)):
        if i % 3 == 0 and i != 0 :
            print("- - - - - - - - - - - - -")

        for j in range(len(bor[0])):
            if j % 3 == 0 and j != 0:
                print(" | ", end="")

            if j == 8:
                print(bor[i][j])
            else:
                print(str(bor[i][j]) + " ", end="")

def get_empty(bor):

    for i in range(len(bor)):
        for j in range(len(bor[0])):
            if bor[i][j] == 0:
                return (i, j) 

    return None

print_board(board)
solver(board)
print("========================")
print_board(board)