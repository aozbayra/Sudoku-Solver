

public class Sudoku {
    int[][] board = new int[9][9];
    int[][] copyBoard = new int[9][9];

    public Sudoku() { 
    }
    
    public Sudoku(int[][] board) {
        for (int i = 0; i < copyBoard.length; i++) {
            for (int j = 0; j < copyBoard[i].length; j++) {
                copyBoard[i][j] = board[i][j];
            }  
        }
    }
    
    public int[][] board() {
        return copyBoard;
    }
    
    public void printBoard() {
        for (int i = 0; i < copyBoard.length; i++) {
            System.out.println();
            for (int j = 0; j < copyBoard[i].length; j++) {
                System.out.print(copyBoard[i][j]);
                System.out.print("  ");
            }
        }
        System.out.println();
    }
    
    public boolean[] candidates(int row, int column) {
        int rowLower = 0;
        int columnLower = 0;
        
        boolean[] array = new boolean[9];
        
        if (row == 1 || row == 4 || row == 7) {
            rowLower = row - 1;
        } else if (row == 2 || row == 5 || row == 8) {
            rowLower = row - 2;
        } else if (row == 3 || row == 6 || row == 9) {
            rowLower = row - 3;
        } else {
            System.out.println("Not a valid row number");
            return array;
        }
        
        if (column == 1 || column == 4 || column == 7) {
            columnLower = column - 1;
        } else if (column == 2 || column == 5 || column == 8) {
            columnLower = column - 2;
        } else if (column == 3 || column == 6 || column == 9) {
            columnLower = column - 3;
        } else {
            System.out.println("Not a valid column number");
            return array;
        }
        
        if (copyBoard[row - 1][column - 1] != 0) {
            return array; 
        }
         
        for (int i = 0; i < array.length; i++) {
            array[i] = true;
        }
        // Test for row
        for (int i = 0; i < copyBoard.length; i++) {
            if (copyBoard[row - 1][i] != 0) {
                array[copyBoard[row - 1][i] - 1] = false;
            }
        }
        // Test for column
        for (int j = 0; j < copyBoard.length; j++) {
            if (copyBoard[j][column - 1] != 0) {
                array[copyBoard[j][column - 1] - 1] = false;
            }
        }
        // Test for box
        for (int i = rowLower; i <= rowLower + 2; i++) {
            for (int j = columnLower; j <= columnLower + 2; j++) {
                if (copyBoard[i][j] != 0) {
                    array[copyBoard[i][j] - 1] = false;
                }
            }
        }
        return array; 
    }
    
    public boolean nakedSingles() {
        int c = 0; // Counter
        int nakedSingle = 0; // The value of the naked single 
        // Generate candidates for the empty cells
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                boolean[] array = candidates(i, j);
                for (boolean value : array) {
                    if (value == true) 
                        c++;
                }
                if (c == 1) { // If a cell has only one candidate
                    for (int d = 0; d < array.length; d++) {
                        if (array[d] == true) {
                            nakedSingle = d + 1; // Find out what that candidate is
                            d = array.length;
                        }
                    }
                    copyBoard[i -1][j - 1] = nakedSingle; // Assign it to its appropriate place in the board
                    System.out.printf("%d has been placed into row: %d and column: %d", nakedSingle, i, j);
                    return true; // A move has been made
                } else { // If a cell does not have only one candidate
                    c = 0; 
                } 
            }
        }
        return false; // No move has been made
    }
    private int findSingle(boolean[] array) {
        int single = 0;
        for (int x = 0; x < array.length; x++) {
            if (array[x] == true) {
                single = x + 1;
                array[x] = false; // Turn the extracted single off in the array
            }
        }
        return single;
    }
    
                
    
    public boolean hiddenSingles() {
        int hiddenSingle = 0;
        int checkHidden = 0;
        int rowOfSingle = 0;
        int columnOfSingle = 0;
        int boxOfSingle = 0;
        // Generate candidates for the empty cells
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                boolean[] array = candidates(i, j);
                hiddenSingle = findSingle(array); // First candidate to be a hidden single.
                rowOfSingle = i;
                columnOfSingle = j;
                boxOfSingle = findBox(rowOfSingle, columnOfSingle);
                // if a candidate can occupy more than one cell that row and column, then it is not hidden, else its hidden candidate.
            }
        }
                        
        return false;
    }
    
    public int findBox(int row, int column) {
        int box = 1;
        for (int i = 3; i < row; i += 3) {
            box += 3;
        }
        for (int i = 3;i < column; i += 3) {
            box++;
        }
        return box;
    }
 
    public static void main(String[] args) {
        int[][] matrix = new int[9][9];
        String digits = "100704005020010070000080002090006250600070008053200010400090000030060090200407000";
        int counter = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = digits.charAt(counter) - 48;
                counter++;
            }
        }
        
        Sudoku s = new Sudoku(matrix);
        int[][] testmatrix = s.board();
        s.printBoard();
        boolean[] array = s.candidates(1,4);
        s.nakedSingles();
        s.printBoard();
        //boolean x = s.hiddenSingles();
        
        
    }
}
