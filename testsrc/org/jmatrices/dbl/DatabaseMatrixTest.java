package org.jmatrices.dbl;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.jmatrices.dbl.db.DatabaseMatrix;

/**
 * DatabaseMatrixTest
 *
 * @author purangp Created 29.10.2004 - 23:46:47
 */
public class DatabaseMatrixTest extends AbstractMatrixTest {

    //1. override constructor
    public DatabaseMatrixTest(String name) {
        super(name);
    }

    //2. implement tests as public methods


    // 3. Write a suite() method that uses reflection to dynamically create a test suite containing all the testXXX() methods
    public static Test suite() {
        return new TestSuite(DatabaseMatrixTest.class);
    }

    // 4. Write a main() method to conveniently run the test with the textual test runner:
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    protected void setUp() throws Exception {
        super.setUp();
        if (matrixToTest == null) {
            try {
                matrixToTest = new DatabaseMatrix(4, 4);
            } catch (IllegalStateException e) {
                System.out.println("The DatabaseMatrix couldn't be initialized " + e.getMessage());
                //make sure inability to initialize the matrix doesn't hinder other tests.
                matrixToTest = new ArrayMatrixImpl(4,4);
            }
        }
    }

    protected void tearDown() throws Exception {

        if (matrixToTest != null && matrixToTest instanceof DatabaseMatrix) {
            DatabaseMatrix dbm = (DatabaseMatrix) matrixToTest;
            dbm.getConnection().removeAll(dbm.getUUIDString());
            System.runFinalization();
        }
    }

}
