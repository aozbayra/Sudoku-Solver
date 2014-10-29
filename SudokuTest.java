import junit.framework.TestCase;
import java.util.Arrays;
import static org.junit.Assert.*;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class SudokuTest extends TestCase {      
    /**
     * A test method.
     * (Replace "X" with a name describing the test.  You may write as
     * many "testSomething" methods in this class as you wish, and each
     * one will be called when running JUnit over this class.)
     */
    public void testCandidates() {

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
        boolean[] array = s.candidates(4,5);  // 3 and 4 should come out
        boolean[] testArray = {false, false, true, true, false, false, false, false, false};
        assertTrue(Arrays.equals(testArray, array));
    }
    
    public void testNakedSinglesRow() {
        int[][] row = new int[9][9];
        String r = "032714685020010070000080002090006250600070008053200010400090000030060090200407000";
        int count = 0;
        for (int t = 0; t < row.length; t++) {
            for (int y = 0; y < row[t].length; y++) {
                row[t][y] = r.charAt(count) - 48;
                count++;
            }
        }
        Sudoku s = new Sudoku(row);
        boolean d = s.nakedSingles();
        assertTrue(d);
    }
    
    public void testNakedSinglesColumn() {
        
        int[][] column = new int[9][9];
        String c = "300704005820010070900080002790006250600070008153200010400090000030060090200407000";
        int u = 0;
        for (int m = 0; m < column.length; m++){
            for (int o = 0; o < column[m].length; o++) {
                column[m][o] = c.charAt(u) - 48;
                u++;
            }
        }
        Sudoku s = new Sudoku(column);
        boolean x = s.nakedSingles();
        assertTrue(x);
    }
    
    public void testNakedSinglesBox() {
        
        int[][] box = new int[9][9];
        String test = "369704005524010070780080002090006250600070008053200010400090000030060090200407000";
        int v = 0;
        for (int z = 0; z < box.length; z++) {
            for (int q = 0; q < box[z].length; q++) {
                box[z][q] = test.charAt(v) - 48;
                v++;   
            }
        }
        Sudoku s = new Sudoku(box);
        boolean f = s.nakedSingles();
        assertTrue(f);
    }
    
    public void testHiddenSinglesRow() {
        int[][] matrix = new int[9][9];
        String digits = "000724005000010070000080002090006250602070008053200010400090000030060090200407000";
        int counter = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = digits.charAt(counter) - 48;
                counter++;
            }
        }
        
        Sudoku s = new Sudoku(matrix);
        boolean a = s.hiddenSingles();
        assertTrue(a);
    }
    
    public void testIsSolved() {
    }
    
    public void testSolve() {
        
    }
}
