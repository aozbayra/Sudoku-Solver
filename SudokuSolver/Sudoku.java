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
        
        boolean[] array = new boolean[10];
        int boxNumber = findBox(row, column);
        int boxRow = findBoxRow(boxNumber);
        int boxColumn = findBoxColumn(boxNumber);
        
        // Test if the cell is empty
        if (copyBoard[row][column] != 0) {
            return array; 
        }
        
        for (int i = 1; i < array.length; i++) {
            array[i] = true;
        }
        // Test for row
        for (int i = 0; i < copyBoard.length; i++) {
            if (copyBoard[row][i] != 0) {
                array[copyBoard[row][i]] = false;
            }
        }

        // Test for column
        for (int j = 0; j < copyBoard.length; j++) {
            if (copyBoard[j][column] != 0) {
                array[copyBoard[j][column]] = false;
            }
        }

        // Test for box
        for (int i = boxRow; i <= boxRow + 2; i++) {
            for (int j = boxColumn; j <= boxColumn + 2; j++) {
                if (copyBoard[i][j] != 0) {
                    array[copyBoard[i][j]] = false;
                }
            }
        }
        return array; 
    }
    
    public boolean nakedSingles() {
        int nakedSingle = 0; // The value of the naked single 
        // Generate candidates for the empty cells
        for (int i = 0; i < copyBoard.length; i++) {
            for (int j = 0; j < copyBoard[i].length; j++) {
                boolean[] array = candidates(i, j);
                int[] onlyCandidates = storeCandidates(array);
                if (onlyCandidates.length == 1) { 
                    nakedSingle = onlyCandidates[0]; 
                    copyBoard[i][j] = nakedSingle;
                    System.out.printf("Naked single %d has been placed" 
                                          + " into row: %d and column: %d\n", nakedSingle, i, j);
                    return true; 
                }
            }
        }
        return false; // No move has been made
    }
          
    public boolean hiddenSingles() {  
        for (int i = 0; i < copyBoard.length; i++) {
            for (int j = 0; j < copyBoard[i].length; j++) {
                boolean[] array = candidates(i, j);
                int boxNumber = findBox(i, j);
                int rowNumber = i;
                int columnNumber = j;
                int[] onlyCandidates = storeCandidates(array);
                // Test for box subunit
                for (int c = 0; c < onlyCandidates.length; c++) {
                    int hiddenSingle = onlyCandidates[c]; 
                    boolean ownBoxResult = compareOwnBox(hiddenSingle, rowNumber, columnNumber);
                    if (ownBoxResult) {  
                        copyBoard[i][j] = hiddenSingle;
                        System.out.printf("Hidden single from box %d has been placed" 
                                              + " into row: %d and column: %d\n", hiddenSingle, i, j); 
                        return true; 
                    }
                }
                // Test for row subunit
                for (int c = 0; c < onlyCandidates.length; c++) {
                    int hiddenSingle = onlyCandidates[c];
                    boolean ownRowResult = compareOwnRow(hiddenSingle, rowNumber, columnNumber);
                    if (ownRowResult) {
                        copyBoard[i][j] = hiddenSingle;
                        System.out.printf("Hidden single from row %d has been placed" 
                                              + " into row: %d and column: %d\n", hiddenSingle, i, j);
                        return true;  
                    }
                }
                // Test for column subunit
                for (int c = 0; c < onlyCandidates.length; c++) {
                    int hiddenSingle = onlyCandidates[c];
                    boolean ownColResult = compareOwnColumn(hiddenSingle, columnNumber, rowNumber);
                    if (ownColResult) {
                        copyBoard[i][j] = hiddenSingle;
                        System.out.printf("Hidden single from column %d has been placed" 
                                              + " into row: %d and column: %d\n", hiddenSingle, i, j); 
                        return true;
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
    
    private boolean compareOwnColumn(int hiddenSingle, int columnNumber, int rowNumber) {
        for (int i = 0; i < copyBoard.length; i++) {
            if (i == rowNumber) {
                continue;
            }
            boolean[] rowBool = candidates(i, columnNumber);
            int[] rowInt = storeCandidates(rowBool);
            boolean result = compareCell(hiddenSingle, rowInt);
            
            if (result) 
                return false;
        }
        return true;
    }
    
    private boolean compareOwnRow(int hiddenSingle, int rowNumber, int columnNumber) {
        for (int j = 0; j < copyBoard.length; j++) {
            if (j == columnNumber) {
                continue;
            }
            boolean[] colBool = candidates(rowNumber, j);
            int[] colInt = storeCandidates(colBool);
            boolean result = compareCell(hiddenSingle, colInt);
            if (result) {
                return false;
            }
        }
        return true;
    }
    
    private boolean compareOwnBox(int hiddenSingle, int rowNumber, int columnNumber) {
        int boxNumber = findBox(rowNumber, columnNumber);
        int boxRow = findBoxRow(boxNumber);
        int boxColumn = findBoxColumn(boxNumber);
        for (int i = boxRow; i <= boxRow + 2; i++) {
            for (int j = boxColumn; j <= boxColumn + 2; j++) {
                if (i == rowNumber && j == columnNumber)
                    continue;
                boolean[] boxBool = candidates(i, j);
                int[] boxInt = storeCandidates(boxBool);
                boolean result = compareCell(hiddenSingle, boxInt);
                if (result) {
                    return false;
                }
            }
        }
        return true;
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
                onlyCandidates[c] = i;
                c++;
            }
        }
        return onlyCandidates;
    }
   
    
    private int findBox(int row, int column) {
        int box = 1;
        for (int i = 2; i < row; i += 3) {
            box += 3;
        }
        for (int i = 2; i < column; i += 3) {
            box++;
        }
        return box;
    }
    
    private int findBoxRow(int boxNumber) {
        int boxRow = 0;
        for (int i = 3; i < copyBoard.length; i += 3) {
            if (boxNumber <= i) {
                return boxRow;
            } else
                boxRow += 3;
        }
        return boxRow;
    }
    
    private int findBoxColumn(int boxNumber) {
        int remainder = boxNumber % 3;
        if (remainder == 1) 
            return 0;
        else if (remainder == 2) 
            return 3;
        else
            return 6;
    }
    
    //public static void main(String[] args) {
//        int[][] matrix = new int[9][9];
//        //String digits = "406030020005042000200000400370006100500403007004800035008000003000160500050090802";
//        String digits = "010720000060001005020035000006008159000000000297500400000160090100300020000084070";
//        //String digits = "028007000016083070000020851137290000000730000000046307290070000000860140000300700";
//        int counter = 0;
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[i].length; j++) {
//                matrix[i][j] = digits.charAt(counter) - 48;
//                counter++;
//            }
//        }
//        Sudoku s = new Sudoku(matrix);
//        s.printBoard();
//        s.solve();
//        s.printBoard();
        
        
    //}
}
