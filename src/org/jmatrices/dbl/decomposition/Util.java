package org.jmatrices.dbl.decomposition;

import org.jmatrices.dbl.Matrix;

/**
 * Util
 * <br/>
 * Author: purangp
 * <br/>
 * Date: 13.03.2004
 * Time: 13:54:46
 */
public final class Util {
    
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

    static boolean isSymmetric(Matrix a) {
        boolean symmetric = true;
        if (a.rows() != a.cols()) {
            symmetric = false;
        } else {
            twoloops:
            for (int row = 0; row <= a.rows(); row++) {
                for (int col = 0; col <= a.cols(); col++) {
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
