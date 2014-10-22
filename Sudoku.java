import java.util.Arrays;

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
        for (int i = 0; i < copyBoard.length; i++) {
            System.out.println();
            for (int j = 0; j < copyBoard[i].length; j++) {
                System.out.print(copyBoard[i][j]);
                System.out.print("  ");
            }
        }
        System.out.println();
        return copyBoard;
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
        
        for (int i = 0; i < copyBoard.length; i++) {
            if (copyBoard[row - 1][i] != 0) {
                array[copyBoard[row - 1][i] - 1] = false;
            }
        }
        
        for (int j = 0; j < copyBoard.length; j++) {
            if (copyBoard[j][column - 1] != 0) {
                array[copyBoard[j][column - 1] - 1] = false;
            }
        }
        
        for (int i = rowLower; i <= rowLower + 2; i++) {
            for (int j = columnLower; j <= columnLower + 2; j++) {
                if (copyBoard[i][j] != 0) {
                    array[copyBoard[i][j] - 1] = false;
                }
            }
        }
        return array; 
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
        boolean[] array = s.candidates(1,7);
        for (boolean value: array) {
            System.out.println(value);
        }
    }
}
