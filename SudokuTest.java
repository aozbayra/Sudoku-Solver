import junit.framework.TestCase;
import java.util.Arrays;
import static org.junit.Assert.*;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class SudokuTest extends TestCase {
        Sudoku s = new Sudoku();       
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
        boolean[] array = s.candidates(4,5);
        boolean[] testArray = {false, false, false, true, true, false, false, false, false, false};
        assertTrue(Arrays.equals(testArray, array));
    }
    
    public void testNakedSingles() {
        int[] row = new int[9];
        String r = "024587693";
        int count = 0;
        for (int t = 0; t < row.length; t++) {
            row[t] = r.charAt(count) - 48;
            count++;
            if (row[t] == 0) {
                boolean d = s.nakedSingles();
                assertTrue(d);
            }
        }
        
        int[][] column = new int[1][9];
        String c = "410782693";
        int u = 0;
        for (int m = 0; m < column[1].length; m++){
            column[1][m] = c.charAt(u) - 48;
            if (column[1][m] == 0) {
                boolean x = s.nakedSingles();
                assertTrue(x);
            }
        }
        
        int[][] mat = new int[3][3];
        String test = "246813570";
        int v = 0;
        for (int z = 0; z < mat.length; z++) {
            for (int q = 0; q < mat[z].length; q++) {
                mat[z][q] = test.charAt(v) - 48;
                if (mat[z][q] == 0) {
                    boolean f = s.nakedSingles();
                    assertTrue(f);
                }
            }
        }
    }
    
    public void testHiddenSingles() {
    }
    
    public void testIsSolved() {
        boolean m = s.isSolved();
        assertFalse(m);
    }
    
    public void testSolve() {
        
    }
}
