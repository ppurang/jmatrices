package org.jmatrices.dbl;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * SparseMatrixImplTest
 * <br>Author: purangp</br>
 * <br>
 * Date: 16.06.2004
 * Time: 20:45:38
 * </br>
 */
public class SparseMatrixImplTest extends TestCase {
    // 1. override constructor
    public SparseMatrixImplTest(String name) {
        super(name);
    }

    // 2. implement tests as public methods
    public void testRowDimension() {
        try {
            Matrix m = new SparseMatrixImpl(0,23);
            fail("Should throw an illegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }
    public void testColumnDimension() {
        try {
            Matrix m = new SparseMatrixImpl(23,0);
            fail("Should throw an illegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    public void testBothDimension() {
        try {
            Matrix m = new SparseMatrixImpl(0,0);
            fail("Should throw an illegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    public void testGetValue() {
        Matrix m = new SparseMatrixImpl(100,100);
        m.setValue(10,25,10);
        assertTrue(m.getValue(10,25)==10);
    }

    public void testSetValue() {
        Matrix m = new SparseMatrixImpl(100,100);
        m.setValue(10,25,25);
        assertTrue(m.getValue(10,25)==25);
        m.setValue(10,25,10);
        assertTrue(m.getValue(10,25)==10);
        m.setValue(10,25,0);
        //ideally here we would like to check the Map's size
        // to make sure zero valued elements are removed from the map
        assertTrue(m.getValue(10,25)==0);
    }





    // 3. Write a suite() method that uses reflection to dynamically create a test suite containing all the testXXX() methods
    public static Test suite() {
        return new TestSuite(SparseMatrixImplTest.class);
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
