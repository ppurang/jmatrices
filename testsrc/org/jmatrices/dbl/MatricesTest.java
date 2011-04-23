package org.jmatrices.dbl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * MatricesTest
 *
 * @author purangp
 *         Created 17.10.2004 - 02:42:47
 */
public class MatricesTest extends TestCase {
    //1. override constructor
    public MatricesTest(String name) {
        super(name);
    }

    //2. implement tests as public methods
    public void testGetRowMatrix() {
        //todo write it!
    }

    // 3. Write a suite() method that uses reflection to dynamically create a test suite containing all the testXXX() methods
    public static Test suite() {
        return new TestSuite(MatricesTest.class);
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
