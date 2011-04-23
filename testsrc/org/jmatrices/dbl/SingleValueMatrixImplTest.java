package org.jmatrices.dbl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * SingleValueMatrixImplTest
 *
 * @author purangp
 *         Created 17.10.2004 - 03:18:49
 */
public class SingleValueMatrixImplTest extends TestCase {
    //1. override constructor
    public SingleValueMatrixImplTest(String name) {
        super(name);
    }

    //2. implement tests as public methods
    public void testSomeThing() {
        //add some code here....
    }

    // 3. Write a suite() method that uses reflection to dynamically create a test suite containing all the testXXX() methods
    public static Test suite() {
        return new TestSuite(SingleValueMatrixImplTest.class);
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
