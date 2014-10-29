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
    
    public void testNakedSingles() {
        
    }
    
    public void testHiddenSingles() {
    }
    
    public void testIsSolved() {
    }
    
    public void testSolve() {
        
    }
}
