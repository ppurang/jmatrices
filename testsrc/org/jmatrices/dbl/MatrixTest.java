package org.jmatrices.dbl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author purangp created 16.10.2004 - 22:25:21
 */
public abstract class MatrixTest extends TestCase {
    protected Matrix matrixToTest;

    //1. override constructor
    public MatrixTest(String name) {
        super(name);
    }

    public MatrixTest(String name, Matrix m) {
        super(name);
        matrixToTest = m;
    }

    //2. implement tests as public methods
    //test rows and cols
    public void testRows() {
        String msg = "Number of Rows can't be less than 1";
        assertTrue(msg, matrixToTest.rows() >= 1);
    }

    public void testCols() {
        String msg = "Number of Columns can't be less than 1";
        assertTrue(msg, matrixToTest.cols() >= 1);
    }

    //test get bounds
    public void testGetRowLowerBound() {
        String msg = "Row can't be less than 1";
        try {
            matrixToTest.getValue(0, matrixToTest.cols());
            assertTrue(msg, false);
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        }

        try {
            matrixToTest.getValue(-1, matrixToTest.cols());
            assertTrue(msg, false);
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        }
    }

    public void testGetColLowerBound() {
        String msg = "Column can't be less than 1";
        try {
            matrixToTest.getValue(matrixToTest.rows(), 0);
            assertTrue(msg, false);
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        }

        try {
            matrixToTest.getValue(matrixToTest.rows(), -1);
            assertTrue(msg, false);
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        }
    }

    public void testGetRowUpperBound() {
        String msg = "Row can't be greater than number of rows in the matrix";
        try {
            matrixToTest.getValue(matrixToTest.rows() + 1, matrixToTest.cols());
            assertTrue(msg, false);
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        }
    }

    public void testGetColUpperBound() {
        String msg = "Column can't be greater than number of columns in the matrix";
        try {
            matrixToTest.getValue(matrixToTest.rows(), matrixToTest.cols() + 1);
            assertTrue(msg, false);
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        }
    }

    //test set bounds
    public void testSetRowLowerBound() {
        String msg = "Row can't be less than 1";
        try {
            matrixToTest.setValue(0, matrixToTest.cols(), 0);
            assertTrue(msg, false);
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        } catch (UnsupportedOperationException e) {
            //everything is fine
        } catch (IllegalArgumentException e) {
            //everything is fine
        }

        try {
            matrixToTest.setValue(-1, matrixToTest.cols(), 0);
            assertTrue(msg, false);
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        } catch (UnsupportedOperationException e) {
            //everything is fine
        } catch (IllegalArgumentException e) {
            //everything is fine
        }
    }

    public void testSetColLowerBound() {
        String msg = "Column can't be less than 1";
        try {
            matrixToTest.setValue(matrixToTest.rows(), 0, 0);
            assertTrue(msg, false);
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        } catch (UnsupportedOperationException e) {
            //everything is fine
        } catch (IllegalArgumentException e) {
            //everything is fine
        }

        try {
            matrixToTest.setValue(matrixToTest.rows(), -1, 0);
            assertTrue(msg, false);
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        } catch (UnsupportedOperationException e) {
            //everything is fine
        } catch (IllegalArgumentException e) {
            //everything is fine
        }
    }

    public void testSetRowUpperBound() {
        String msg = "Row can't be greater than number of rows in the matrix";
        try {
            matrixToTest.setValue(matrixToTest.rows() + 1, matrixToTest.cols(), 0);
            assertTrue(msg, false);
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        } catch (UnsupportedOperationException e) {
            //everything is fine
        } catch (IllegalArgumentException e) {
            //everything is fine
        }

    }

    public void testSetColUpperBound() {
        String msg = "Column can't be greater than number of columns in the matrix";
        try {
            matrixToTest.setValue(matrixToTest.rows(), matrixToTest.cols() + 1, 0);
            assertTrue(msg, false);
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        } catch (UnsupportedOperationException e) {
            //everything is fine
        } catch (IllegalArgumentException e) {
            //everything is fine
        }
    }

    //test iterate
    public void testGetIteration() {
        for (int row = 1; row <= matrixToTest.rows(); row++) {
            for (int col = 1; col <= matrixToTest.cols(); col++) {
                matrixToTest.getValue(row, col);
            }
        }
    }

    public void testSetIteration() {
        try {
            for (int row = 1; row <= matrixToTest.rows(); row++) {
                for (int col = 1; col <= matrixToTest.cols(); col++) {
                    matrixToTest.setValue(row, col, Math.random());
                }
            }
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        } catch (UnsupportedOperationException e) {
            //everything is fine
        } catch (IllegalArgumentException e) {
            //everything is fine
        }
    }

    //test setting a value
    public void testSetValue() {
        String msg = "Value set must be returned";
        matrixToTest.setValue(1, 1, 1);
        assertTrue(msg, matrixToTest.getValue(1, 1) == 1D);

        matrixToTest.setValue(matrixToTest.rows(), matrixToTest.cols(), 1);
        assertTrue(msg, matrixToTest.getValue(matrixToTest.rows(), matrixToTest.cols()) == 1D);

        try {
            int randomRow = new Double(Math.random() * matrixToTest.rows()).intValue();
            int randomCol = new Double(Math.random() * matrixToTest.cols()).intValue();
            matrixToTest.setValue((randomRow != 0) ? randomRow : 1, (randomCol != 0) ? randomCol : 1, 1);
            assertTrue(msg, matrixToTest.getValue((randomRow != 0) ? randomRow : 1, (randomCol != 0) ? randomCol : 1) == 1D);
        } catch (IllegalArgumentException e) {
            //some matrices might throw IllegalArgumentException
        }
    }

    //test equals
    public void testEquals() {
        assertTrue("Matrix must be equal to itself", matrixToTest.equals(matrixToTest));
        //remark we use null instead of using matrixToTest as the hint otherwise it might result in a matrix like DiagonalMatrix which doesn't allow all setvalues to go through
        //remark but that means testing DatabaseMatrix might require the test class to override this method suitably.        
        assertFalse("Matrices with different dimensions can't be equal", matrixToTest.equals(MatrixFactory.getMatrix(matrixToTest.rows() + 1, matrixToTest.cols() + 1, null)));
        assertFalse("The chances of a matrix to be equal to a random Matrix must be near to zero", matrixToTest.equals(MatrixFactory.getRandomMatrix(matrixToTest.rows(), matrixToTest.cols(), null)));
        assertFalse("Matrices with different dimensions can't be equal", matrixToTest.equals(MatrixFactory.getMatrix(matrixToTest.rows() + 1, matrixToTest.cols(), null)));
        assertFalse("Matrices with different dimensions can't be equal", matrixToTest.equals(MatrixFactory.getMatrix(matrixToTest.rows(), matrixToTest.cols() + 1, null)));

    }

    // 3. Write a suite() method that uses reflection to dynamically create a test suite containing all the testXXX() methods
    public static Test suite() {
        return new TestSuite(MatrixTest.class);
    }

    // 4. Write a main() method to conveniently run the test with the textual test runner:
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    //the following are called in the order they are declared in

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void runTest() throws Throwable {
        super.runTest();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
