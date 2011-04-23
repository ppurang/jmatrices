package org.jmatrices.matrix.transform;

import org.jmatrices.matrix.Matrix;
import org.jmatrices.matrix.MatrixFactory;
import org.jmatrices.matrix.measure.MatrixProperty;
import org.jmatrices.matrix.operator.MatrixOperator;

/**
 * MatrixTransformer is responsible for classical non element-by-element transformation of a matrix
 * <p>
 * Captures classical transformations that aren't applicable in an element-by-element way (only exception is {@link #negate(org.jmatrices.matrix.Matrix)} )
 * </p>
 * <p><font color="blue">
 * We might decide to remove this restriction and provide some important ebe transformations here
 * or we might move them(to be methods) and negate to {@link MatrixEBETransformer}
 * </font></p>
 * <p>
 * Author: purangp
 * </p>
 * Date: 07.03.2004
 * Time: 17:03:20
 *
 * @see MatrixEBETransformation
 * @see MatrixEBETransformer
 */
public class MatrixTransformer {
    /**
     * Transforms the matrix by negating all the elements
     *
     * @param m Matrix
     * @return -a<sub>i</sub><sub>j</sub> for all i,j
     */
    public static Matrix negate(Matrix m) {
        return MatrixEBETransformer.ebeTransform(m, new MatrixEBETransformation() {
            public double transform(double element) {
                return -element;
            }
        });
    }

    /**
     * Transforms the matrix into it's transpose
     *
     * @param m
     * @return A'
     */
    public static Matrix transpose(Matrix m) {
        int rows = m.rows(), cols = m.cols();
        Matrix transposed = MatrixFactory.getMatrix(cols, rows);
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= cols; col++) {
                transposed.set(col, row, m.get(row, col));
            }
        }
        return transposed;
    }

    /**
     * Inverts the matrix
     *
     * @param m Matrix
     * @return A<sup>-1</sup>
     */
    public static Matrix inverse(Matrix m) {
        return MatrixOperator.solve(m, MatrixFactory.getIdentityMatrix(m.rows()));
    }

    /**
     * Column vector contatining the diagonal elements of the matrix
     * <pre>
     *    x=
     *  1 0 0
     *  0 1 0
     *  0 0 1
     *   then y = diagonal(x) is
     *  1
     *  1
     *  1
     * <pre>
     *
     * @param m
     * @return c(mx1) column vector containing the diagonal elements of the matrix
     */
    public static Matrix diagonal(Matrix m) {
        int rows = m.rows(), cols = m.cols();
        Matrix diagonal;
        if (rows == 1 && cols == 1) {
            return m;
        } else {
            diagonal = MatrixFactory.getMatrix(rows, 1);
            for (int row = 1; row <= rows; row++) {
                for (int col = 1; col <= cols; col++) {
                    if (row == col)
                        diagonal.set(row, 1, m.get(row, col));
                }
            }
        }
        return diagonal;
    }

    /**
     * Multiplies the square matrix with itself multiple times.
     *
     * @param m is a square matrix
     * @param n if n>1 performs the operation else if (n<=0) returns a matrix composed of ones.
     * @return C = A<sup>n</sup>
     */
    public static Matrix pow(Matrix m, int n) {
        if (MatrixProperty.isSquare(m)) {
            if (n <= 0)
                return MatrixFactory.getMatrix(m.rows(), m.cols(), 1);
            Matrix result = m;
            for (int counter = 2; counter <= n; counter++) {  //if n==1 then just return m goon with the loop iff n >= 2
                result = MatrixOperator.multiply(m, result);
            }
            return result;
        } else
            throw new IllegalArgumentException("m must be a square matrix");
    }


    /**
     * //secondary diagonal - the diagonal of a square matrix running from the lower left entry to the upper right entry
     * <pre>
     * to implement it use ....
     * 1. first element is 0 and col
     * 2. second element is (0+1)or1 and col-1
     * 3. third element is   (1+1)or2 and col-2
     * 4. and so on till rows() and 0 is reached ..
     * </pre>
     *
     * @param m
     * @return
     */
    public static Matrix secondaryDiagonal(Matrix m) {
        throw new UnsupportedOperationException("to be implemented");
    }

    /**
     * <pre>
     * x =
     * 1 -3
     * 2  2
     * 3 -1
     * <p/>
     * y = cumulativeColumnProduct(x);
     * 1 -3
     * 2 -6
     * 6 6
     * </pre>
     *
     * @param m
     * @return
     */
    public static Matrix cumulativeColumnProduct(Matrix m) {
        throw new UnsupportedOperationException("to be implemented");
    }

    /**
     * <pre>
     * x =
     * 1 -3
     * 2  2
     * 3 -1
     * <p/>
     * y = cumulativeColumnSum(x);
     * 1 -3
     * 3 -1
     * 6 -2
     * </pre>
     *
     * @param m
     * @return
     */
    public static Matrix cumulativeColumnSum(Matrix m) {
        throw new UnsupportedOperationException("to be implemented");
    }

    private MatrixTransformer() {
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