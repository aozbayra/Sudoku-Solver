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
          
    public boolean hiddenSingles() { // Test is required.
        boolean finalResult = false; // A move has not been made.
        // Generate candidates for the empty cells
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                boolean[] array = candidates(i, j);
                int boxNumber = findBox(i,j);
                int rowNumber = i;
                int columnNumber = j;
                int[] otherRows = getOtherRow(boxNumber, rowNumber);
                int[] otherColumns = getOtherColumn(boxNumber, columnNumber);
                int[] onlyCandidates = storeCandidates(array);
                for (int c = 0; c < onlyCandidates.length; c++) {
                    int hiddenSingle = onlyCandidates[c];
                    // Compare the cells in otherRows with this candidate.
                    boolean rowResult = compareOtherRows(hiddenSingle,otherRows); // returns true if candidate is unique.
                    // Compare the cells in otherColumns with this candidate.
                    boolean colResult = compareOtherColumns(hiddenSingle, otherColumns); // returns true if candidate is unique.
                    if (rowResult && colResult) {
                        finalResult = true; // A move has been made.
                    }
                    copyBoard[i - 1][j - 1] = hiddenSingle;
                    System.out.printf("%d has been placed into row: %d and column: %d", hiddenSingle, i, j); 
                }
                return finalResult;
            }
        }               
        return false;
    }
    private boolean compareOtherRows(int hiddenSingle, int[] otherRows) {
        boolean finalResult = true;
        for (int colFirst = 0; colFirst < copyBoard.length; colFirst++) {
            boolean[] colBoolFirst = candidates(otherRows[0], colFirst);
            int[] colIntFirst = storeCandidates(colBoolFirst);
            boolean resultFirst = compareCell(hiddenSingle, colIntFirst);
            // returns true if candidate exists in another cell.
            if (resultFirst) {
                return false; // Failed the other rows test
            }
        }
        for (int colSecond = 0; colSecond < copyBoard.length; colSecond++) {
            boolean[] colBoolSecond = candidates(otherRows[1], colSecond);
            int[] colIntSecond = storeCandidates(colBoolSecond);
            boolean resultSecond = compareCell(hiddenSingle, colIntSecond);
            if (resultSecond) {
                return false;
            }
        }
        return finalResult;
    }
    
    private boolean compareOtherColumns(int hiddenSingle, int[] otherColumns) {
        boolean finalResult = true;
        for (int rowFirst = 0; rowFirst < copyBoard.length; rowFirst++) {
            boolean[] rowBoolFirst = candidates(rowFirst, otherColumns[0]);
            int[] rowIntFirst = storeCandidates(rowBoolFirst);
            boolean resultFirst = compareCell(hiddenSingle, rowIntFirst);
            if (resultFirst) {
                return false;
            }
        }
        for (int rowSecond = 0; rowSecond < copyBoard.length; rowSecond++) {
            boolean[] rowBoolSecond = candidates(otherColumns[1], rowSecond);
            int[] rowIntSecond = storeCandidates(rowBoolSecond);
            boolean resultSecond = compareCell(hiddenSingle, rowIntSecond);
            if (resultSecond) {
                return false;
            }
        }
        return finalResult;
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
 
    public boolean checkColumns(int boxOfSingle, int columnOfSingle, int hiddenSingle) {
        int column1 = 0;
        // Checks which column of the Box the Value is in
        if (boxOfSingle == 1 || boxOfSingle == 4 || boxOfSingle == 7) {
            column1 = 1;
        } else if (boxOfSingle == 2 || boxOfSingle == 5 || boxOfSingle == 8) {
            column1 = 4;
        } else if (boxOfSingle == 3 || boxOfSingle == 6 || boxOfSingle == 9) {
            column1 = 7;
        } 
        
        int cr1 = 0;
        int cr2 = 0;
        // Assigns the two columns the original value is not in to the two ints
        if (columnOfSingle == column1) {
            cr1 = column1 + 1;
            cr2 = column1 + 2;
        } else if (columnOfSingle == (column1 + 1)) {
            cr1 = column1 - 1;
            cr2 = column1 + 1;
        } else if (columnOfSingle == (column1 + 2)) {
            cr1 = column1 - 2;
            cr2 = column1 - 1;
        } 
        // Checks the two columns to see if the hiddenSingle is in either column
        // Returns true if the hiddenSingle is not in either one and False if it is
        for (int j = 0; j < copyBoard.length; j++) {
            if (copyBoard[j][cr1] != hiddenSingle) {
                for (int i = 0; i < copyBoard.length; i++) {
                    if (copyBoard[i][cr2] != hiddenSingle) {
                        return true;
                    }
                }
            } else {
                return false;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        int[][] matrix = new int[9][9];
        String digits = "000704005020010070000080002090006250600070008053200010400090000030060090200407000";
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
        boolean[] array = s.candidates(1,5);
        //boolean x = s.hiddenSingles();
        // The candidates are 2 and 3.
//        int[] testArray = {1,2,3,4,5,6};
//        boolean y = s.compareCell(7,testArray);
//        System.out.println(y);
//        int[] otherRows = s.getOtherRow(9,8);
//        for (int value : otherRows) 
//            System.out.println(value);
        int[] otherColumns = s.getOtherColumn(8,4);
        for (int value : otherColumns)
            System.out.println(value);
        
        
        



    }
}
