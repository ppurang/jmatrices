package org.jmatrices.dbl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * TestAll
 *
 * @author purangp Created 10.11.2004 - 21:28:53
 */
public class TestAll extends TestCase {
    private static final String packageString = "org.jmatrices.dbl";
    private static final String[] allClassesToTest = {
        "ArrayMatrixImplTest",
        "DiagonalMatrixImplTest",
        "SingleValueMatrixImplTest",
        "DatabaseMatrixTest"
    };

    //1. override constructor
    public TestAll(String name) {
        super(name);
    }

    // 3. Write a suite() method that uses reflection to dynamically create a test suite containing all the testXXX() methods
    public static Test suite() {
        TestSuite allTests = new TestSuite("tests for package " + packageString);
        for (int count = 0; count < allClassesToTest.length; count++) {
              String className = allClassesToTest[count];
            try {
                allTests.addTestSuite(Class.forName(packageString + "." + className));
            } catch (ClassNotFoundException e) {
                System.out.println("The following class wasn't found " + packageString + "." + className);
                e.printStackTrace();
            }
        }
        return allTests;
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
