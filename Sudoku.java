import java.util.*;

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
        
        boolean[] array = new boolean[9];
        int boxNumber = findBox(row, column);
        int boxRow = findBoxRow(boxNumber);
        int boxColumn = findBoxColumn(boxNumber);
        
        // Test if the cell is empty
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
        for (int i = boxRow; i <= boxRow + 2; i++) {
            for (int j = boxColumn; j <= boxColumn + 2; j++) {
                if (copyBoard[i - 1][j - 1] != 0) {
                    array[copyBoard[i - 1][j - 1] - 1] = false;
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
                c = getLength(array);
                if (c == 1) { // If a cell has only one candidate
                    for (int d = 0; d < array.length; d++) {
                        if (array[d] == true) {
                            nakedSingle = d + 1; // Find out what that candidate is
                            d = array.length;
                        }
                    }
                    copyBoard[i -1][j - 1] = nakedSingle; // Assign it to its appropriate place in the board
                    System.out.printf("Naked single %d has been placed into row: %d and column: %d\n", nakedSingle, i, j);
                    return true; // A move has been made
                } else { // If a cell does not have only one candidate
                    c = 0; 
                } 
            }
        }
        return false; // No move has been made
    }
          
    public boolean hiddenSingles() {  
        // Generate candidates for the empty cells
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                boolean[] array = candidates(i, j);
                int boxNumber = findBox(i,j);
                int rowNumber = i;
                int columnNumber = j;
                int[] otherRows = getOtherRow(boxNumber, rowNumber); // Find other rows that are present in the cell of the candidate
                int[] otherColumns = getOtherColumn(boxNumber, columnNumber); // Find other columns that are present in the cell of the canddate
                int[] onlyCandidates = storeCandidates(array); // Store only candidates in an integer array
                for (int c = 0; c < onlyCandidates.length; c++) {
                    int hiddenSingle = onlyCandidates[c]; // Test each candidate
                    // Compare the cells in otherRows with this candidate.
                    boolean rowResult = compareOtherRows(hiddenSingle,otherRows); // returns true if candidate is unique.
                    // Compare the cells in otherColumns with this candidate.
                    boolean colResult = compareOtherColumns(hiddenSingle, otherColumns); // returns true if candidate is unique.
                    if (rowResult && colResult) {  // If it remains unique after being tested through other rows and columns
                        copyBoard[i - 1][j - 1] = hiddenSingle; // make the move
                        System.out.printf("Hidden single %d has been placed into row: %d and column: %d\n", hiddenSingle, i, j); 
                        return true; // A move has been made.
                    }

                }
                
            }
        }               
        return false;  // no hidden single has been found throughout the matrix
    }
    
    public boolean isSolved() {
        for (int i = 0; i < copyBoard.length; i++) {
            for (int j = 0; j < copyBoard[i].length; j++) {
                if (copyBoard[i][j] == 0) {
                    return false;  // Return false if you catch an empty cell
                }
            }
        }
        return true;  // Return true if there is no empty cell
    }
    
    public void solve() {
        while (!isSolved() && (nakedSingles() || hiddenSingles()));
    }
            
    private boolean compareOtherRows(int hiddenSingle, int[] otherRows) {

        for (int colFirst = 1; colFirst <= copyBoard.length; colFirst++) {
            boolean[] colBoolFirst = candidates(otherRows[0], colFirst);
            int[] colIntFirst = storeCandidates(colBoolFirst);
            boolean resultFirst = compareCell(hiddenSingle, colIntFirst);
            // returns true if candidate exists in another cell.
            if (resultFirst) {
                return false; // Failed the other rows test
            }
        }
        for (int colSecond = 1; colSecond <= copyBoard.length; colSecond++) {
            boolean[] colBoolSecond = candidates(otherRows[1], colSecond);
            int[] colIntSecond = storeCandidates(colBoolSecond);
            boolean resultSecond = compareCell(hiddenSingle, colIntSecond);
            if (resultSecond) {
                return false;
            }
        }
        return true;
    }
    
    private boolean compareOtherColumns(int hiddenSingle, int[] otherColumns) {

        for (int rowFirst = 1; rowFirst <= copyBoard.length; rowFirst++) {
            boolean[] rowBoolFirst = candidates(rowFirst, otherColumns[0]);
            int[] rowIntFirst = storeCandidates(rowBoolFirst);
            boolean resultFirst = compareCell(hiddenSingle, rowIntFirst);
            if (resultFirst) {
                return false;
            }
        }
        for (int rowSecond = 1; rowSecond <= copyBoard.length; rowSecond++) {
            boolean[] rowBoolSecond = candidates(otherColumns[1], rowSecond);
            int[] rowIntSecond = storeCandidates(rowBoolSecond);
            boolean resultSecond = compareCell(hiddenSingle, rowIntSecond);
            if (resultSecond) {
                return false;
            }
        }
        return true;
    }
    
    
    private int[] getOtherRow(int boxNumber, int rowNumber) {
        int boxRow = findBoxRow(boxNumber);
        //System.out.println(boxRow);
        int[] otherRows = new int[2];
        int c = 0;
        for (int i = boxRow; i <= (boxRow + 2); i++) {
            if (i != rowNumber) {
                otherRows[c] = i;
                c++;
            }
        }
        return otherRows;
    }
    
    private int[] getOtherColumn(int boxNumber, int columnNumber) {
        int boxColumn = findBoxColumn(boxNumber);
        //System.out.println(boxColumn);
        int[] otherColumns = new int[2];
        int c = 0;
        for (int i = boxColumn; i <= (boxColumn + 2); i++) {
            if (i != columnNumber) {
                otherColumns[c] = i;
                c++;
            }
        }
        return otherColumns;
    }
            
    private boolean compareCell(int element, int[] array) {
        if (array.length == 0) 
            return false; // The candidate is not in the cell
        for (int i : array) {
            if (element == i) {
                return true; // The candidate is in the cell
            }
        }
        return false;
    }
        
    
    private int getLength(boolean[] array) {
        int length = 0;
        for (boolean value : array) {
            if (value == true) {
                length++;
            }
        }
        return length;
    }
                           
    private int[] storeCandidates(boolean[] array) {
        int c = 0;
        int[] onlyCandidates = new int[getLength(array)];
        for (int i = 0; i < array.length; i++) {
            if (array[i] == true) {
                onlyCandidates[c] = i + 1;
                c++;
            }
        }
        return onlyCandidates;
    }
   
    
    private int findBox(int row, int column) {
        int box = 1;
        for (int i = 3; i < row; i += 3) {
            box += 3;
        }
        for (int i = 3;i < column; i += 3) {
            box++;
        }
        return box;
    }
    
    private int findBoxRow(int boxNumber) {
        int boxRow = 1;
        for (int i = 3; i < 10; i += 3) {
            if (boxNumber <= i) {
                return boxRow;
            } else
                boxRow += 3;
        }
        return boxRow;
    }
    
    private int findBoxColumn(int boxNumber) {
        int boxColumn = 1;
        int remainder = boxNumber % 3;
        if (remainder == 1) 
            boxColumn = 1;
        else if (remainder == 2) 
            boxColumn = 4;
        else
            boxColumn = 7;
        return boxColumn;
    }
    
    public static void main(String[] args) {
        int[][] matrix = new int[9][9];
        String digits = "000704005000010079000080000090006050000000008053209010400090000030060090200407000";
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
        s.solve();
        s.printBoard();

    }
}
