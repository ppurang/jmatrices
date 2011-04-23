package org.jmatrices.dbl;

import org.jmatrices.dbl.db.DatabaseMatrix;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

/**
 * DatabaseMatrixTest
 *
 * @author ppurang Created 29.10.2004 - 23:46:47
 */
@Test(groups={"jmatrices.db"})
public class DatabaseMatrixTest extends AbstractMatrixTest {


    public DatabaseMatrixTest() {
        if (matrixToTest == null) {
            try {
                matrixToTest = new DatabaseMatrix(4, 4);
            } catch (IllegalStateException e) {
                System.out.println("The DatabaseMatrix couldn't be initialized " + e.getMessage());
            }
        }
    }

    public DatabaseMatrixTest(AbstractMatrix m) {
        super(m);
    }



    @AfterClass
    protected void tearDown() throws Exception {
        if (matrixToTest != null && matrixToTest instanceof DatabaseMatrix) {
            DatabaseMatrix dbm = (DatabaseMatrix) matrixToTest;
            dbm.getConnection().removeAll(dbm.getUUIDString());
            System.runFinalization();
        }
    }

}
