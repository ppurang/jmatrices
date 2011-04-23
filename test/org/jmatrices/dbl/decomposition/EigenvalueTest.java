package org.jmatrices.dbl.decomposition;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;

/**
 * EigenvalueTest.
 *
 * @author ppurang
 *         created 04.06.2007 08:35:15
 */
//@Test(groups = {"jmatrices.decomposition"})
public class EigenvalueTest extends AbstractDecompositionTest {
    private static final Matrix TEST_MATRIX_3_3 = MatrixFactory.getMatrix(3, 3, null);
    private static final Matrix RESULT_V_MATRIX_3_3 = MatrixFactory.getMatrix(3, 3, null);
    private static final Matrix RESULT_D_MATRIX_3_3 = MatrixFactory.getMatrix(3, 3, null);

    static {
        TEST_MATRIX_3_3.setValue(1, 1, 0.950129285147175);
        TEST_MATRIX_3_3.setValue(1, 2, 0.48598246870930);
        TEST_MATRIX_3_3.setValue(1, 3, 0.456467665168341);
        TEST_MATRIX_3_3.setValue(2, 1, 0.231138513574288);
        TEST_MATRIX_3_3.setValue(2, 2, 0.891298966148902);
        TEST_MATRIX_3_3.setValue(2, 3, 0.0185036432482244);
        TEST_MATRIX_3_3.setValue(3, 1, 0.606842583541787);
        TEST_MATRIX_3_3.setValue(3, 2, 0.762096833027395);
        TEST_MATRIX_3_3.setValue(3, 3, 0.821407164295253);

        RESULT_V_MATRIX_3_3.setValue(1, 1, -0.657135773910652);
        RESULT_V_MATRIX_3_3.setValue(1, 2, -0.786463842194327);
        RESULT_V_MATRIX_3_3.setValue(1, 3, 0.761377441758592);
        RESULT_V_MATRIX_3_3.setValue(2, 1, -0.227457182064722);
        RESULT_V_MATRIX_3_3.setValue(2, 2, 0.377064186925044);
        RESULT_V_MATRIX_3_3.setValue(2, 3, -0.637958964615391);
        RESULT_V_MATRIX_3_3.setValue(3, 1, -0.718634681165628);
        RESULT_V_MATRIX_3_3.setValue(3, 2, 0.489180154809547);
        RESULT_V_MATRIX_3_3.setValue(3, 3, 0.11538089377362);

        RESULT_D_MATRIX_3_3.setValue(1, 1, 1.61753123661326);
        RESULT_D_MATRIX_3_3.setValue(1, 2, 0);
        RESULT_D_MATRIX_3_3.setValue(1, 3, 0);
        RESULT_D_MATRIX_3_3.setValue(2, 1, 0);
        RESULT_D_MATRIX_3_3.setValue(2, 2, 0.43320595603137);
        RESULT_D_MATRIX_3_3.setValue(2, 3, 0);
        RESULT_D_MATRIX_3_3.setValue(3, 1, 0);
        RESULT_D_MATRIX_3_3.setValue(3, 2, 0);
        RESULT_D_MATRIX_3_3.setValue(3, 3, 0.612098222946702);

    }


    public void testEigenvalues() {
        Eigenvalue eigenvalues = new Eigenvalue(TEST_MATRIX_3_3);
        Matrix v = eigenvalues.getV();
        Matrix d = eigenvalues.getD();

        assert areApproximatelyEqual(v, RESULT_V_MATRIX_3_3) : String.format("matrix  "
                + v
                + " should be equal to "
                + RESULT_V_MATRIX_3_3);

        assert areApproximatelyEqual(d, RESULT_D_MATRIX_3_3) : String.format("matrix  "
                + d
                + " should be equal to "
                + RESULT_D_MATRIX_3_3);
    }
}
