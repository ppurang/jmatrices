package org.jmatrices.matrix;

import org.jmatrices.matrix.transform.MatrixConditionalEBETransformation;
import org.jmatrices.matrix.transform.MatrixEBETransformation;
import org.jmatrices.matrix.transform.MatrixEBETransformer;

/**
 * MatrixFactory is one-stop shop for creating matrices.
 * <ol>
 * <li>Rectangular(Square, Vector) matrices, initialized to 0.0, scalar or array of values</li>
 * <li>Scalar Matrices including Identity matrix</li>
 * </ol>
 * <p>
 * <font color="blue">
 * todo how to provide a mechanism to change implementations (from LightMatrixImpl to HeavyMatrixImpl), intelligent choice or user's choice?
 * <br/>todo consider deserialization of matrices from ascii, mathml, xml files.
 * <br/>todo serialization? through matrix interface ?
 * </font>
 * </p><p>
 * Author: purangp
 * </p>
 * Date: 07.03.2004
 * Time: 15:56:59
 */
public class MatrixFactory {
    /**
     * Gets a matrix of the asked dimensions.
     * <p/>
     * All elements are set to 0.0
     *
     * @param rows number of rows in the matrix (> 1)
     * @param cols number of columns in the matrix (> 1)
     * @return Matrix of the given dimensions
     */
    public static Matrix getMatrix(int rows, int cols) {
        return new LightMatrixImpl(rows, cols);
    }

    /**
     * Gets a matrix of the asked dimensions.
     * <p/>
     * All elements are set to scalar.
     *
     * @param rows   number of rows in the matrix (> 1)
     * @param cols   number of columns in the matrix (> 1)
     * @param scalar initial value of the elements
     * @return Matrix of the given dimensions and value
     */
    public static Matrix getMatrix(int rows, int cols, double scalar) {
        return scalarAddition(getMatrix(rows, cols), scalar);
    }

    /**
     * Gets a matrix of the asked dimensions.
     * <p/>
     * All elements are set to values in the passed array.
     *
     * @param rows   number of rows in the matrix (> 1)
     * @param cols   number of columns in the matrix (> 1)
     * @param values initial value of the elements
     * @return Matrix of the given dimensions and values
     */
    public static Matrix getMatrix(int rows, int cols, double values[][]) {
        return new LightMatrixImpl(rows, cols, values);
    }

    /**
     * Gets an Identity matrix
     *
     * @param dim dimension of the square matrix
     * @return Square matrix with the diagonal elements set to 1.
     */
    public static Matrix getIdentityMatrix(int dim) {
        return getScalarMatrix(dim, 1);
    }

    /**
     * Gets a scalar matrix.
     *
     * @param dim    dimension of the square matrix
     * @param scalar the value the main diagonal elements have to be set to
     * @return Square matrix with the diagonal elements set to scalar value.
     */
    public static Matrix getScalarMatrix(int dim, final double scalar) {
        Matrix m = MatrixFactory.getMatrix(dim, dim);
        return MatrixEBETransformer.ebeTransform(m, new MatrixConditionalEBETransformation() {
            public double transform(int row, int col, double element) {
                if (row == col)
                    return scalar;
                return
                        element;
            }
        });
    }


    private MatrixFactory() {
    }

    /**
     * todo perhaps should be moved to transform package and made public!
     *
     * @param m
     * @param s
     * @return
     */
    private static Matrix scalarAddition(Matrix m, final double s) {
        return MatrixEBETransformer.ebeTransform(m, new MatrixEBETransformation() {
            public double transform(double element) {
                return element + s; //could be +, *, -, /
            }
        });
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