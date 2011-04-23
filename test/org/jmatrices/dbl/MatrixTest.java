package org.jmatrices.dbl;

import org.testng.annotations.Test;

/**
 * @author ppurang created 16.10.2004 - 22:25:21
 */ 
public abstract class MatrixTest {
    protected Matrix matrixToTest;


    public MatrixTest() {
    }

    public MatrixTest(Matrix m) {
        matrixToTest = m;
    }

    @Test(groups={"jmatrices.core"})
    public void testRows() {
        String msg = "Number of Rows can't be less than 1";
        assert matrixToTest.rows() >= 1 : msg;
    }

    @Test(groups={"jmatrices.core"})
    public void testCols() {
        String msg = "Number of Columns can't be less than 1";
        assert matrixToTest.cols() >= 1 : msg;
    }

    //test get bounds
    @Test(groups={"jmatrices.core"})
    public void testGetRowLowerBound() {
        String msg = "Row can't be less than 1";
        try {
            matrixToTest.getValue(0, matrixToTest.cols());
            assert false : msg;
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        }

        try {
            matrixToTest.getValue(-1, matrixToTest.cols());
            assert false : msg;
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        }
    }

    @Test(groups={"jmatrices.core"})
    public void testGetColLowerBound() {
        String msg = "Column can't be less than 1";
        try {
            matrixToTest.getValue(matrixToTest.rows(), 0);
            assert false : msg;
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        }

        try {
            matrixToTest.getValue(matrixToTest.rows(), -1);
            assert false : msg;
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        }
    }

    @Test(groups={"jmatrices.core"})
    public void testGetRowUpperBound() {
        String msg = "Row can't be greater than number of rows in the matrix";
        try {
            matrixToTest.getValue(matrixToTest.rows() + 1, matrixToTest.cols());
            assert false : msg;
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        }
    }

    @Test(groups={"jmatrices.core"})
    public void testGetColUpperBound() {
        String msg = "Column can't be greater than number of columns in the matrix";
        try {
            matrixToTest.getValue(matrixToTest.rows(), matrixToTest.cols() + 1);
            assert false : msg;
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        }
    }

    //test set bounds
    @Test(groups={"jmatrices.core"})
    public void testSetRowLowerBound() {
        String msg = "Row can't be less than 1";
        try {
            matrixToTest.setValue(0, matrixToTest.cols(), 0);
            assert false : msg;
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        } catch (UnsupportedOperationException e) {
            //everything is fine
        } catch (IllegalArgumentException e) {
            //everything is fine
        }

        try {
            matrixToTest.setValue(-1, matrixToTest.cols(), 0);
            assert false : msg;
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        } catch (UnsupportedOperationException e) {
            //everything is fine
        } catch (IllegalArgumentException e) {
            //everything is fine
        }
    }

    @Test(groups={"jmatrices.core"})
    public void testSetColLowerBound() {
        String msg = "Column can't be less than 1";
        try {
            matrixToTest.setValue(matrixToTest.rows(), 0, 0);
            assert false : msg;
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        } catch (UnsupportedOperationException e) {
            //everything is fine
        } catch (IllegalArgumentException e) {
            //everything is fine
        }

        try {
            matrixToTest.setValue(matrixToTest.rows(), -1, 0);
            assert false : msg;
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        } catch (UnsupportedOperationException e) {
            //everything is fine
        } catch (IllegalArgumentException e) {
            //everything is fine
        }
    }

    @Test(groups={"jmatrices.core"})
    public void testSetRowUpperBound() {
        String msg = "Row can't be greater than number of rows in the matrix";
        try {
            matrixToTest.setValue(matrixToTest.rows() + 1, matrixToTest.cols(), 0);
            assert false : msg;
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        } catch (UnsupportedOperationException e) {
            //everything is fine
        } catch (IllegalArgumentException e) {
            //everything is fine
        }

    }

    @Test(groups={"jmatrices.core"})
    public void testSetColUpperBound() {
        String msg = "Column can't be greater than number of columns in the matrix";
        try {
            matrixToTest.setValue(matrixToTest.rows(), matrixToTest.cols() + 1, 0);
            assert false : msg;
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        } catch (UnsupportedOperationException e) {
            //everything is fine
        } catch (IllegalArgumentException e) {
            //everything is fine
        }
    }

    //test iteration using for loops over all elements
    @Test(groups={"jmatrices.core"})
    public void testGetIteration() {
        for (int row = 1; row <= matrixToTest.rows(); row++) {
            for (int col = 1; col <= matrixToTest.cols(); col++) {
                matrixToTest.getValue(row, col);
            }
        }
    }

    @Test(groups={"jmatrices.core"})
    public void testSetIteration() {
        try {
            for (int row = 1; row <= matrixToTest.rows(); row++) {
                for (int col = 1; col <= matrixToTest.cols(); col++) {
                    matrixToTest.setValue(row, col, java.lang.Math.random());
                }
            }
        } catch (UnsupportedOperationException e) {
            //everything is fine
        } catch (IllegalArgumentException e) {
            //everything is fine
        }
    }

    //test setting a value
    @Test(groups={"jmatrices.core"})
    public void testSetValue() {
        String msg = "Value set must be returned";
        matrixToTest.setValue(1, 1, 1);
        assert matrixToTest.getValue(1, 1) == 1D : msg;

        matrixToTest.setValue(matrixToTest.rows(), matrixToTest.cols(), 1);
        assert matrixToTest.getValue(matrixToTest.rows(), matrixToTest.cols()) == 1D : msg;

        try {
            int randomRow = new Double(java.lang.Math.random() * matrixToTest.rows()).intValue();
            int randomCol = new Double(java.lang.Math.random() * matrixToTest.cols()).intValue();
            matrixToTest.setValue((randomRow != 0) ? randomRow : 1, (randomCol != 0) ? randomCol : 1, 1);
            assert matrixToTest.getValue((randomRow != 0) ? randomRow : 1, (randomCol != 0) ? randomCol : 1) == 1D : msg;
        } catch (IllegalArgumentException e) {
            //some matrices might throw IllegalArgumentException
        }
    }

    //test equals
    @Test(groups={"jmatrices.core"})
    public void testEquals() {
        Matrix randomMatrix = MatrixFactory.getMatrix(matrixToTest.rows(), matrixToTest.cols(), null);
        for(int row=1; row <= randomMatrix.rows(); row++) {
            for (int col=1; col <= randomMatrix.cols(); col++) {
                randomMatrix.setValue(row, col, java.lang.Math.random());                
            }
        }
        assert matrixToTest.equals(matrixToTest) : "Matrix must be equal to itself";
        //remark we use null instead of using matrixToTest as the hint otherwise it might result in a matrix like DiagonalMatrix which doesn't allow all setvalues to go through
        //remark but that means testing DatabaseMatrix might require the test class to override this method suitably.
        assert !matrixToTest.equals(MatrixFactory.getMatrix(matrixToTest.rows() + 1, matrixToTest.cols() + 1, null)) : "Matrices with different dimensions can't be equal";
        assert !matrixToTest.equals(randomMatrix) : "The chances of a matrix to be equal to a random Matrix must be near to zero";
        assert !matrixToTest.equals(MatrixFactory.getMatrix(matrixToTest.rows() + 1, matrixToTest.cols(), null)) : "Matrices with different dimensions can't be equal";
        assert !matrixToTest.equals(MatrixFactory.getMatrix(matrixToTest.rows(), matrixToTest.cols() + 1, null)) : "Matrices with different dimensions can't be equal";

    }

    //test hashCode
    @Test(groups={"jmatrices.core"})
    public void testHashCode() {
        try {
            Matrix m = MatrixFactory.getMatrix(1, 1, matrixToTest);
            Matrix mclone = MatrixFactory.getMatrix(1, 1, matrixToTest);
            Matrix n22 = MatrixFactory.getMatrix(2, 2, matrixToTest);
            Matrix n31 = MatrixFactory.getMatrix(3, 1, matrixToTest);

            assert m.hashCode() == mclone.hashCode(): "These matrices should have the same hashcode";
            assert !(m.hashCode() == n22.hashCode()):"These matrices should not have the same hashcode";
            assert !(n22.hashCode() == n31.hashCode()):"These matrices should not have the same hashcode";

            m.setValue(1, 1, 1);
            assert !(m.hashCode() == mclone.hashCode()) :"These matrices should not have the same hashcode";

            mclone.setValue(1, 1, 1);
            assert m.hashCode() == mclone.hashCode():"These matrices should have the same hashcode";


            Matrix p23 = MatrixFactory.getMatrix(2, 3, matrixToTest);
            Matrix p32 = MatrixFactory.getMatrix(3, 2, matrixToTest);
            Matrix p61 = MatrixFactory.getMatrix(6, 1, matrixToTest);
            Matrix p16 = MatrixFactory.getMatrix(1, 6, matrixToTest);
            assert !(p23.hashCode() == p32.hashCode()):"These matrices should not have the same hashcode";
            assert !(p23.hashCode() == p61.hashCode()):"These matrices should not have the same hashcode";
            assert !(p23.hashCode() == p16.hashCode()):"These matrices should not have the same hashcode";
            assert !(p61.hashCode() == p16.hashCode()):"These matrices should not have the same hashcode";

            m.setValue(1, 1, 5);
            assert !(m.hashCode() == p16.hashCode()):"These matrices should not have the same hashcode";


        } catch (UnsupportedOperationException e) {
            //ignore it is fine to throw the UnsupportedOperationException
        } catch (IllegalArgumentException e) {
            //ignore some matrices don't allow illegal arguments like row != col or setting non-diagonal elements etc. etc.
        }


    }

    
}
