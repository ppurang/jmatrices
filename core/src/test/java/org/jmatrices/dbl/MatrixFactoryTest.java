package org.jmatrices.dbl;

import org.testng.annotations.Test;

/**
 * MatricesTest
 *
 * @author ppurang
 *         Created 17.10.2004 - 02:42:47
 */
@Test(groups = {"jmatrices.core"})

public class MatrixFactoryTest {

    //2. implement tests as public methods
    @Test(groups = {"jmatrices.core"})
    public void testDefaultMatrixSelectionStrategy() {
        double[][] elems = new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix testMatrix = Matrices.getMatrixFromArray(3, 3, null, elems);
        Matrix diagMatrix = MatrixFactory.getIdentityMatrix(3);
        Matrix resultArrayMatrix = MatrixFactory.getMatrix(3, 3, testMatrix);
        Matrix resultDiagonalMatrix = MatrixFactory.getMatrix(3, 3, diagMatrix);

        assert resultArrayMatrix instanceof ArrayMatrixImpl :
                "result array matrix is  " + resultArrayMatrix.getClass().getName();
        assert resultDiagonalMatrix instanceof ArrayMatrixImpl :
                        "result diag matrix is  " + resultArrayMatrix.getClass().getName();

    }


}
