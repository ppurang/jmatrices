package org.jmatrices.matrix.decomposition;

/**
 * Util
 * <br/>
 * Author: purangp
 * <br/>
 * Date: 13.03.2004
 * Time: 13:54:46
 */
class Util {
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

    private Util() {
    }
}
