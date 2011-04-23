package org.jmatrices.dbl;

import org.testng.annotations.Test;

/**
 * AbstractMatrixTest
 *
 * @author ppurang
 *         Created 17.10.2004 - 00:29:28
 */
public abstract class AbstractMatrixTest extends MatrixTest {


    public AbstractMatrixTest() {
        
    }

    public AbstractMatrixTest(AbstractMatrix m) {
        super(m);
    }


    //testClone
    @Test(groups={"jmatrices.core"})
    public void testClone() {
        Matrix clone = (Matrix) ((AbstractMatrix) matrixToTest).clone();
        assert matrixToTest.equals(clone):"The matrix and its clone must be equal in the begining.";
        clone.setValue(clone.rows(), clone.cols(), matrixToTest.getValue(clone.rows(), clone.cols()) - 1);
        assert !(matrixToTest.equals(clone)):"A change in value in a clone should render it different from its source matrix.";
    }
}
