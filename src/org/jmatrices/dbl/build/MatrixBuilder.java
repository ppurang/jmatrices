package org.jmatrices.dbl.build;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;
import org.jmatrices.dbl.transformer.MatrixEBETransformation;
import org.jmatrices.dbl.transformer.MatrixEBETransformer;

import java.util.Iterator;
import java.util.List;

/**
 * MatrixBuilder can be used to build (complicated-)matrices, especially prepopulated matrices
 * and to avoid circular dependencies between packages (many methods from MatrixFactory were moved here).
 *
 *
 * Date: 07.03.2004
 * Time: 15:56:59
 * @author Piyush Purang ppurang gmail com
 */
public class MatrixBuilder {

    /**
     * Gets a matrix of the asked dimensions, filled with random values
     *
     * @param rows number of rows in the matrix (>= 1)
     * @param cols number of columns in the matrix (>= 1)
     * @param hint acts as a hint for the right implementation to use
     * @return Matrix of the given dimensions filled with random values
     */
    public static Matrix getRandomMatrix(int rows, int cols, Matrix hint) {
        return MatrixEBETransformer.ebeTransform(MatrixFactory.getMatrix(rows, cols, hint), new MatrixEBETransformation() {
            public double transform(double element) {
                return Math.random();
            }
        });
    }

    /**
     * Gets a matrix of the asked dimensions.
     * <p/>
     * All elements are set to values in the passed array.
     * <p/>
     * If the array is bigger than the matrix then the unneeded values are discarded.
     * <pre>
     * Note: Theoretically we could deduct the dimensions of the matrix from the array.
     * But we don't so that the client code
     * a) knows what it is doing and
     * b) is disciplined enough to pass legal arguments
     * </pre>
     *
     * @param rows   number of rows in the matrix (>= 1)
     * @param cols   number of columns in the matrix (>= 1)
     * @param hint   acts as a hint for the right implementation to use
     * @param values initial value of the elements
     * @return Matrix of the given dimensions and values
     * @throws ArrayIndexOutOfBoundsException if the passed array is smaller in dimensions than the required matrix;
     *                                        this might happen if a row is smaller or a column is missing in the array.
     */
    public static Matrix getMatrixFromArray(int rows, int cols, Matrix hint, double values[][]) {
        Matrix m = MatrixFactory.getMatrix(rows, cols, hint);
        return populateElements(m, values);
    }

    private static Matrix populateElements(Matrix m, double[][] elems) {
        for (int i = 1; i < m.rows(); i++) {
            for (int j = 1; j < m.cols(); j++) {
                m.setValue(i, j, elems[i - 1][j - 1]);
            }
        }
        return m;
    }


    /**
     * Gets a column vector with list values composing the vector's values.
     * <br/>
     * <b>Note:</b> will throw an <code>IllegalArgumentException</code> if <code>list.size()<=0</code>
     *
     * @param list list containing the <code>Double</code> values that will be used to compose the vector
     * @param hint acts as a hint for the right implementation to use
     * @return column vector
     * @throws IllegalArgumentException if the size of the list is less than 1
     */
    public static Matrix getMatrix(List list, Matrix hint) {
        if (list.size() <= 0)
            throw new IllegalArgumentException("List size should atleast be 1");
        // return MatrixFactory.getEmptyMatrix();
        Matrix cv = MatrixFactory.getMatrix(list.size(), 1, hint);
        Iterator iter = list.iterator();
        int row = 1;
        while (iter.hasNext()) {
            Double o = (Double) iter.next();
            cv.setValue(row, 1, o.doubleValue());
            row++;
        }
        return cv;
    }

    /**
     * private constructor to avoid instances being creted.
     */
    private MatrixBuilder() {
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