package org.jmatrices.dbl.decomposition;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;
import org.testng.annotations.Test;

/**
 * CholeskyTest.
 *
 * @author ppurang
 *         created 04.06.2007 08:35:15
 */
@Test(groups = {"jmatrices.decomposition"})
public class CholeskyTest extends AbstractDecompositionTest {
    private static final Matrix A_3_3 = MatrixFactory.getMatrix(3, 3, null);
    private static final Matrix B_1_3 = MatrixFactory.getMatrix(3, 1, null);
    private static final Matrix L_3_3 = MatrixFactory.getMatrix(3, 3, null);
    private static final Matrix X_3_1 = MatrixFactory.getMatrix(3, 1, null);

    static {
        A_3_3.setValue(1, 1, 3.00506436);
        A_3_3.setValue(1, 2, 2.65577048);
        A_3_3.setValue(1, 3, 3.08742844);
        A_3_3.setValue(2, 1, 2.65577048);
        A_3_3.setValue(2, 2, 3.55545737);
        A_3_3.setValue(2, 3, 3.42362593);
        A_3_3.setValue(3, 1, 3.08742844);
        A_3_3.setValue(3, 2, 3.42362593);
        A_3_3.setValue(3, 3, 4.02095978);

        B_1_3.setValue(1, 1, 0.03177513);
        B_1_3.setValue(2, 1, 0.41823100);
        B_1_3.setValue(3, 1, 1.70129375);

        L_3_3.setValue(1, 1, 1.73351215);
        L_3_3.setValue(1, 2, 0);
        L_3_3.setValue(1, 3, 0);
        L_3_3.setValue(2, 1, 1.53201723);
        L_3_3.setValue(2, 2, 1.09926365);
        L_3_3.setValue(2, 3, 0);
        L_3_3.setValue(3, 1, 1.78102499);
        L_3_3.setValue(3, 2, 0.63230050);
        L_3_3.setValue(3, 3, 0.67015361);

        X_3_1.setValue(1, 1, -1.9439690);
        X_3_1.setValue(2, 1, -1.5268677);
        X_3_1.setValue(3, 1, 3.2157951);

    }
    public static final double DELTA = 0.0000001;

    public static void testCholesky() {
        Cholesky cholesky = new Cholesky(A_3_3);
        Matrix l = cholesky.getL();
        Matrix x = cholesky.solve(B_1_3);
        //areApproximatelyEqual(l, L_3_3, DELTA);
        assert  areApproximatelyEqual(l, L_3_3, DELTA): String.format("matrix  "
                + l
                + " should be equal to "
                + L_3_3);

        assert areApproximatelyEqual(x, X_3_1, 0.09) : String.format("matrix  "
                + x
                + " should be equal to "
                + X_3_1);
    }

}
