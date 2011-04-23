package org.jmatrices.dbl.decomposition;

import org.testng.annotations.Test;
import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;

/**
 * Eigenvalue2Test.
 *
 * @author ppurang
 *         created 04.06.2007 08:35:15
 */
@Test(groups = {"jmatrices.decomposition"})
public class Eigenvalue2Test extends AbstractDecompositionTest {
    private static final Matrix TEST_MATRIX_3_3 = MatrixFactory.getMatrix(3, 3, null);
    private static final Matrix RESULT_V_MATRIX_3_3 = MatrixFactory.getMatrix(3, 3, null);
    private static final Matrix RESULT_D_MATRIX_3_3 = MatrixFactory.getMatrix(3, 3, null);

    static {
        TEST_MATRIX_3_3.setValue(1, 1, 4);
        TEST_MATRIX_3_3.setValue(1, 2, 8);
        TEST_MATRIX_3_3.setValue(1, 3, 1);
        TEST_MATRIX_3_3.setValue(2, 1, 9);
        TEST_MATRIX_3_3.setValue(2, 2, 4);
        TEST_MATRIX_3_3.setValue(2, 3, 2);
        TEST_MATRIX_3_3.setValue(3, 1, 5);
        TEST_MATRIX_3_3.setValue(3, 2, 5);
        TEST_MATRIX_3_3.setValue(3, 3, 7);

        RESULT_V_MATRIX_3_3.setValue(1, 1, -0.66930459);
        RESULT_V_MATRIX_3_3.setValue(1, 2, -0.64076622);
        RESULT_V_MATRIX_3_3.setValue(1, 3, -0.40145623);
        RESULT_V_MATRIX_3_3.setValue(2, 1, 0.71335708);        
        RESULT_V_MATRIX_3_3.setValue(2, 2, -0.72488533);
        RESULT_V_MATRIX_3_3.setValue(2, 3, -0.26047487);
        RESULT_V_MATRIX_3_3.setValue(3, 1, -0.019156716);
        RESULT_V_MATRIX_3_3.setValue(3, 2, -0.91339349);
        RESULT_V_MATRIX_3_3.setValue(3, 3, 1.6734214);

        RESULT_D_MATRIX_3_3.setValue(1, 1, -4.4979246);
        RESULT_D_MATRIX_3_3.setValue(1, 2, 0);
        RESULT_D_MATRIX_3_3.setValue(1, 3, 0);
        RESULT_D_MATRIX_3_3.setValue(2, 1, 0);
        RESULT_D_MATRIX_3_3.setValue(2, 2, 14.475702);
        RESULT_D_MATRIX_3_3.setValue(2, 3, 0);
        RESULT_D_MATRIX_3_3.setValue(3, 1, 0);
        RESULT_D_MATRIX_3_3.setValue(3, 2, 0);
        RESULT_D_MATRIX_3_3.setValue(3, 3, 5.0222223);

    }


    public void testEigenvalues() {
        Eigenvalue eigenvalues = new Eigenvalue(TEST_MATRIX_3_3);
        Matrix v = eigenvalues.getV();
        Matrix d = eigenvalues.getD();

        assert areApproximatelyEqual(d, RESULT_D_MATRIX_3_3, 0.0001) : String.format("matrix  "
                + d
                + " should be equal to "
                + RESULT_D_MATRIX_3_3);
        
        assert areApproximatelyEqual(v, RESULT_V_MATRIX_3_3, 0.0001) : String.format("matrix  "
                + v
                + " should be equal to "
                + RESULT_V_MATRIX_3_3);
    }
}
