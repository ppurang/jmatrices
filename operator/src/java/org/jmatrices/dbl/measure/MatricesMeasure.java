package org.jmatrices.dbl.measure;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.operator.MatrixOperator;

/**
 * MatricesMeasure provides methods applicable to two (or more) matrices.
 * <p/>
 * Given two matrices <strong> A,B -yields-> value</strong> ,where value is a number, boolean, or any other primitive type
 * </p>
 * <p/>
 * @author ppurang
 * </p>
 * Date: 12.03.2004
 * Time: 12:51:46
 */
public final class MatricesMeasure {
    /**
     * Compares elements of matrices to try and determine equality of matrices.
     *
     * @param a Matrix
     * @param b Matrix
     * @return true iff element in a at a given position equals the element in b at the same position
     */
    public static boolean areEqual(final Matrix a, final Matrix b) {
        if (areSameDimension(a, b)) {
            for (int row = 1; row <= a.rows(); row++) {
                for (int col = 1; col <= a.cols(); col++) {
                    if (a.getValue(row, col) != b.getValue(row, col)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * Compares elements of matrices to try and determine approximate equality of matrices.
     * <p/>
     * Uses {@link #areApproximatelyEqual(org.jmatrices.dbl.Matrix, org.jmatrices.dbl.Matrix, double)}
     * where allowed delta equals {@link org.jmatrices.dbl.Math#APPROXIMATION_DELTA}
     *
     * @param a Matrix
     * @param b Matrix
     * @return true iff element in a at a given position approximately equals the element in b at the same position
     */
    public static boolean areApproximatelyEqual(final Matrix a, final Matrix b) {
        return areApproximatelyEqual(a, b, org.jmatrices.dbl.Math.APPROXIMATION_DELTA);
    }

    /**
     * Compares elements of matrices to try and determine approximate equality of matrices.
     * <p/>
     * Uses {@link org.jmatrices.dbl.Math#equalsApproximately(double,double,double)} to detrmine equality between
     * corresponding elements in the two matrices.
     *
     * @param a            Matrix
     * @param b            Matrix
     * @param allowedDelta allowed percision
     * @return true iff element in a at a given position approximately equals the element in b at the same position
     */
    public static boolean areApproximatelyEqual(final Matrix a, final Matrix b, final double allowedDelta) {
        if (areSameDimension(a, b)) {
            for (int row = 1; row <= a.rows(); row++) {
                for (int col = 1; col <= a.cols(); col++) {
                    if (!org.jmatrices.dbl.Math.equalsApproximately(
                            a.getValue(row, col),
                            b.getValue(row, col),
                            allowedDelta)) {
                        return false;
                    }
                }
            }
            return true;
        } else  {
            return false;
        }
    }

    /**
     * Compares dimensions of the two matrices
     *
     * @param a Matrix
     * @param b Matrix
     * @return true if rows and columns, i.e. the dimensions of a and b are are equal
     */
    public static boolean areSameDimension(final Matrix a, final Matrix b) {
        return (a.rows() == b.rows() && a.cols() == b.cols());
    }

    /**
     * The dot or scalar product expects two vectors
     * <p/>
     * It basically does an element-by-element multiplication and sums the elements of the resulting matrix. So the two matrices need to be of the same dimensions
     * </p>
     *
     * @param a an mxn Matrix
     * @param b an mxn Matrix
     * @return sum of element by element multiplication of the two matrices
     */
    public static double dotProduct(final Matrix a, final Matrix b) {
        if (areSameDimension(a, b)) {
            return MatrixMeasure.getSum(MatrixOperator.multiplyEBE(a, b));
        }
        else {
            throw new IllegalArgumentException("Matrices don't conform");
        }
    }

    private MatricesMeasure() {

    }
}

/**
 *  Jmatrices - Matrix Library
 Copyright (C) 2004  Piyush Purang

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library, see License.txt; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
