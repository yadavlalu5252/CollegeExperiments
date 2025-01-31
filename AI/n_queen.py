class QueenChessBoard:
    def __init__(self, size):
        # Board has dimensions size x size
        self.size = size
        # Columns[r] is a number c if a queen is placed at row r and column c.
        # Columns[r] is out of range if no queen is placed in row r.
        # Thus, after all queens are placed, they will be at positions
        # (columns[0], 0), (columns[1], 1), ... (columns[size - 1], size - 1)
        self.columns = []

    def place_in_next_row(self, column):
        self.columns.append(column)

    def remove_in_current_row(self):
        return self.columns.pop()

    def is_this_column_safe_in_next_row(self, column):
        # Index of next row
        row = len(self.columns)
        # Check column
        for queen_column in self.columns:
            if column == queen_column:
                return False
        # Check diagonal
        for queen_row, queen_column in enumerate(self.columns):
            if queen_column - queen_row == column - row:
                return False
        # Check other diagonal
        for queen_row, queen_column in enumerate(self.columns):
            if ((self.size - queen_column) - queen_row
                    == (self.size - column) - row):
                return False
        return True

    def display(self):
        for row in range(self.size):
            for column in range(self.size):
                if column == self.columns[row]:
                    print('Q', end=' ')
                else:
                    print('.', end=' ')
            print()


def solve_queen(size):
    """Display a chessboard for each possible configuration of placing n
    queens on an n x n chessboard and print the number of such
    configurations."""
    board = QueenChessBoard(size)
    number_of_solutions = 0
    row = 0
    column = 0
    # Iterate over rows of the board
    while True:
        # Place queen in the next row
        while column < size:
            if board.is_this_column_safe_in_next_row(column):
                board.place_in_next_row(column)
                row += 1
                column = 0
                break
            else:
                column += 1
        # If could not find a column to place in or if the board is full
        if column == size or row == size:
            # If the board is full, we have a solution
            if row == size:
                board.display()
                print()
                number_of_solutions += 1
                # Backtrack to find more solutions
                board.remove_in_current_row()
                row -= 1
            # Now backtrack
            try:
                prev_column = board.remove_in_current_row()
            except IndexError:
                # All queens removed; no more possible configurations
                break
            # Try the previous row again
            row -= 1
            # Start checking at column = (1 + value of column in the previous row)
            column = 1 + prev_column
    print('Number of solutions:', number_of_solutions)


n = int(input('Enter n: '))
solve_queen(n)