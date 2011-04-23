package org.jmatrices.dbl;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * AbstractSquareMatrixTest
 *
 * @author purangp
 *         Created 17.10.2004 - 02:49:59
 */
public abstract class AbstractSquareMatrixTest extends AbstractMatrixTest{
    //1. override constructor
    public AbstractSquareMatrixTest(String name) {
        super(name);
    }

    public AbstractSquareMatrixTest(String name, AbstractSquareMatrix m) {
        super(name, m);
    }

    //2. implement tests as public methods
    public void testSquareness() {
        assertTrue("The SquareMatrices must have same number of columns as rows.", matrixToTest.rows() == matrixToTest.cols());
    }

    // 3. Write a suite() method that uses reflection to dynamically create a test suite containing all the testXXX() methods
    public static Test suite() {
        return new TestSuite(AbstractSquareMatrixTest.class);
    }

}
