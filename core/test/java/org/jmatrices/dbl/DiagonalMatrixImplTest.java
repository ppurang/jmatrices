package org.jmatrices.dbl;

import org.testng.annotations.Test;

/**
 * DiagonalMatrixImplTest
 *
 * @author ppurang Created 17.10.2004 - 02:53:13
 */
public class DiagonalMatrixImplTest extends AbstractSquareMatrixTest {


    public DiagonalMatrixImplTest() {
        matrixToTest = new DiagonalMatrixImpl(4);
    }

    public DiagonalMatrixImplTest(DiagonalMatrixImpl m) {
        super(m);
    }

    //2. implement tests as public methods
    @Test(groups={"jmatrices.core"})
    public void testNonDiagonalValues() {
        //test non diagonals must be 0
    }
    @Test(groups={"jmatrices.core"})   
    public void testSetValue() {
        super.testSetValue();
        //test should be able to change diagonal but not
        //non-diagonal elems.
        try {
            matrixToTest.setValue(matrixToTest.rows() - 1, matrixToTest.cols() - 2, 1); //should fail
        } catch (IllegalArgumentException e) {
            //everything is fine
        }
        try {
            matrixToTest.setValue(0, 0, 1); //should fail
        } catch (IndexOutOfBoundsException e) {
            //everything is fine
        }
        matrixToTest.setValue(1, 1, 1); //should succeed
    }
}
