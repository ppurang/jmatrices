package org.jmatrices.dbl.decomposition;

import org.jmatrices.dbl.Matrix;

/**
 * Util
 * <br/>
 * @author ppurang
 * <br/>
 * Date: 13.03.2004
 * Time: 13:54:46
 */
public final strictfp class Util {
    
    public static double hypot(double a, double b) {
        double r;
        if (Math.abs(a) > Math.abs(b)) {
            r = b / a;
            r = Math.abs(a) * Math.sqrt(1 + r * r);
        } else if (b != 0) {
            r = a / b;
            r = Math.abs(b) * Math.sqrt(1 + r * r);
        } else {
            r = 0.0;
        }
        return r;
    }

    /**
     * Signum function
     * returns 1 if the <code>a</code>
     * is greater than zero, 0 if it equals zero and -1 if it is
     * less than zero.
     *
     * @param a
     * @return
     */
    public static int sign(int a) {
        if (a > 0)
            return 1;
        else if (a < 0)
            return -1;

        return 0;
    }

    /**
     * Signum function
     * returns 1 if the <code>a</code>
     * is greater than zero, 0 if it equals zero and -1 if it is
     * less than zero.
     *
     * @param a
     * @return
     */
    public static int sign(double a) {
        if (a > 0)
            return 1;
        else if (a < 0)
            return -1;

        return 0;
    }

    public static boolean isSymmetric(final Matrix a) {
        boolean symmetric = true;
        final int aRows = a.rows();
        final int aCols = a.cols();
        if (aRows != aCols) {
            symmetric = false;
        } else {
            twoloops:
            for (int row = 1; row < aRows; row++) {
                for (int col = 1; col < aCols; col++) {
                    if (a.getValue(row, col) == a.getValue(col, row)) {
                        continue;
                    } else {
                        symmetric = false;
                        break twoloops;
                    }
                }
            }
        }
        return symmetric;
    }

    public static boolean isSymmetric(final MatrixAdaptor a) {
        boolean symmetric = true;
        final int aRows = a.rows();
        final int aCols = a.cols();
        if (aRows != aCols) {
            symmetric = false;
        } else {
            twoloops:
            for (int row = 0; row <= aRows; row++) {
                for (int col = 0; col <= aCols; col++) {
                    if (a.getValue(row, col) == a.getValue(col, row)) {
                        continue;
                    } else {
                        symmetric = false;
                        break twoloops;
                    }
                }
            }
        }
        return symmetric;
    }

    private Util() {
    }
}
