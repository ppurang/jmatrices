package org.jmatrices.dbl;

import org.testng.annotations.Test;


/**
 * AbstractSquareMatrixTest
 *
 * @author ppurang
 *         Created 17.10.2004 - 02:49:59
 */
public abstract class AbstractSquareMatrixTest extends AbstractMatrixTest {


    public AbstractSquareMatrixTest() {
    }

    public AbstractSquareMatrixTest(AbstractSquareMatrix m) {
        super(m);
    }

    //2. implement tests as public methods
    @Test(groups={"jmatrices.core"})
    public void testSquareness() {
        assert matrixToTest.rows() == matrixToTest.cols(): String.format("The SquareMatrices must have same number of columns (%d) as rows(%d).", matrixToTest.cols(), matrixToTest.rows());
    }


}
