package org.jmatrices.dbl;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * ArrayMatrixImplTest
 *
 * @author purangp
 *         Created 17.10.2004 - 00:44:54
 */
public class ArrayMatrixImplTest extends AbstractMatrixTest {


    public ArrayMatrixImplTest(String name) {
        super(name);
    }

    public ArrayMatrixImplTest(String name, ArrayMatrixImpl m) {
        super(name,m);
    }

    // 3. Write a suite() method that uses reflection to dynamically create a test suite containing all the testXXX() methods
    public static Test suite() {
        return new TestSuite(ArrayMatrixImplTest.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        matrixToTest = new ArrayMatrixImpl(4, 4);
    }
}
