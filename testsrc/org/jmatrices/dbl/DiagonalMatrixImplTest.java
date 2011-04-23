package org.jmatrices.dbl;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * DiagonalMatrixImplTest
 *
 * @author purangp Created 17.10.2004 - 02:53:13
 */
public class DiagonalMatrixImplTest extends AbstractSquareMatrixTest {
    //1. override constructor
    public DiagonalMatrixImplTest(String name) {
        super(name);
    }

    public DiagonalMatrixImplTest(String name, DiagonalMatrixImpl m) {
        super(name, m);
    }

    //2. implement tests as public methods
    public void testGetValue() {
        //test non diagonals must be 0
    }

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
        //should fail
        matrixToTest.setValue(1, 1, 1); //should succeed
    }


    // 3. Write a suite() method that uses reflection to dynamically create a test suite containing all the testXXX() methods
    public static Test suite() {
        return new TestSuite(DiagonalMatrixImplTest.class);
    }

    //the following are called in the order they are declared in
    protected void setUp() throws Exception {
        super.setUp();
        matrixToTest = new DiagonalMatrixImpl(4);
    }

}
