package org.jmatrices.dbl.decomposition;

import org.jmatrices.dbl.Math;
import org.jmatrices.dbl.Matrix;

/**
 * AbstractDecompositionTest.
 *
 * @author ppurang
 *         created 04.06.2007 08:35:15
 */
public class AbstractDecompositionTest {
    
    static boolean areApproximatelyEqual(final Matrix a, final Matrix b) {
        return areApproximatelyEqual(a, b, Math.APPROXIMATION_DELTA);
    }

    static boolean areApproximatelyEqual(final Matrix a, final Matrix b, final double delta) {
        if (areSameDimension(a, b)) {
            for (int row = 1; row <= a.rows(); row++) {
                for (int col = 1; col <= a.cols(); col++) {
                    if (!org.jmatrices.dbl.Math.equalsApproximately(a.getValue(row, col), b.getValue(row, col), delta)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    static boolean areSameDimension(final Matrix a, final Matrix b) {
        return (a.rows() == b.rows() && a.cols() == b.cols());
    }
}
