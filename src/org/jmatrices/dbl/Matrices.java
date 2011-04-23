/**
 * Jmatrices - Matrix Library
 * Copyright (C) 2004  Piyush Purang
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library, see License.txt; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.jmatrices.dbl;

import java.util.Iterator;
import java.util.List;

/**
 * Matrices plays the same role that Collections does for collection classes
 * <br>Author: purangp</br>
 * <br>
 * Date: 18.05.2004
 * Time: 19:21:00
 * </br>
 */
public final class Matrices {
    /**
     * Gets the entire row as a Matrix.
     *
     * @param m   Matrix who's row is required
     * @param row the row in the Matrix m
     * @return one-row Matrix (row Vector)
     */
    public static Matrix getRowMatrix(Matrix m, int row) {
        Matrix n = MatrixFactory.getMatrix(1, m.cols(), m);
        for (int col = 1; col <= m.cols(); col++) {
            n.setValue(1, col, m.getValue(row, col));
        }
        return n;
    }

    /**
     * Gets the entire column as a Matrix.
     *
     * @param m   Matrix who's column is required
     * @param col the column in the Matrix m
     * @return one-column Matrix (column Vector)
     */
    public static Matrix getColumnMatrix(Matrix m, int col) {
        Matrix n = MatrixFactory.getMatrix(m.rows(), 1, m);
        for (int row = 1; row <= m.rows(); row++) {
            n.setValue(row, 1, m.getValue(row, col));
        }
        return n;
    }

    /**
     * Get a submatrix.
     *
     * @param n    Matrix who's submatrix is needed
     * @param rowI Initial row index
     * @param colI Initial column index
     * @param rowF Final row index
     * @param colF Final column index
     * @return A(rowI:rowF,colI:colF)
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */
    public static Matrix getSubMatrix(Matrix n, int rowI, int colI, int rowF, int colF) {
        Matrix m = MatrixFactory.getMatrix(rowF - rowI + 1, colF - colI + 1, n);
        int rows_m = m.rows(), cols_m = m.cols();
        if (rows_m > n.rows() || cols_m > n.cols())
            throw new IllegalArgumentException("The submatrix being extracted violates dimension constraints");
        else {
            //for (int row = rowI, rowm = 1; row <= rowF; row++, rowm++) {
            for (int row = rowI; row <= rowF; row++) {
                //for (int col = colI, colm = 1; col <= colF; col++, colm++) {
                for (int col = colI; col <= colF; col++) {
                    //m.setValue(rowm,colm,getValue(row,col));
                    m.setValue(row - rowI + 1, col - colI + 1, n.getValue(row, col));
                }
            }
        }
        return m;

    }

    /**
     * Get a submatrix.
     *
     * @param n Matrix who's submatrix is needed
     * @param r Array of row indices.
     * @param c Array of column indices.
     * @return A(r(:),c(:))
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */
    public static Matrix getSubMatrix(Matrix n, int[] r, int[] c) {
        Matrix m = MatrixFactory.getMatrix(r.length, c.length, n);
        for (int row = 1; row <= r.length; row++) {
            for (int col = 1; col <= c.length; col++) {
                m.setValue(row, col, n.getValue(r[row - 1], c[col - 1]));
            }
        }
        return m;
    }

    /**
     * Get a submatrix.
     *
     * @param n    Matrix who's submatrix is needed
     * @param rowI Initial row index
     * @param rowF Final row index
     * @param c    Array of column indices.
     * @return A(i0:i1,c(:))
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */
    public static Matrix getSubMatrix(Matrix n, int rowI, int rowF, int[] c) {
        Matrix m = MatrixFactory.getMatrix(rowF - rowI + 1, c.length, n);
        if (m.rows() > n.rows())
            throw new IllegalArgumentException("The submatrix being extracted violates dimension constraints");
        else {
            for (int row = rowI; row <= rowF; row++) {
                for (int col = 1; col <= c.length; col++) {
                    m.setValue(row - rowI + 1, col, n.getValue(row, c[col - 1]));
                }
            }
        }
        return m;
    }

    /**
     * Get a submatrix.
     *
     * @param n    Matrix who's submatrix is needed
     * @param r    Array of row indices.
     * @param colI Initial column index
     * @param colF Final column index
     * @return A(r(:),j0:j1)
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */
    public static Matrix getSubMatrix(Matrix n, int[] r, int colI, int colF) {
        Matrix m = MatrixFactory.getMatrix(r.length, colF - colI + 1, n);
        if (m.cols() > n.cols())
            throw new IllegalArgumentException("The submatrix being extracted violates dimension constraints");
        else {
            for (int row = 1; row <= r.length; row++) {
                for (int col = colI; col <= colF; col++) {
                    m.setValue(row, col - colI + 1, n.getValue(r[row - 1], col));
                }
            }
        }
        return m;
    }

    /**
     * Gets the <code>col</code> column of the matrix as a <code>double array</code>
     *
     * @param m
     * @param col
     * @return 1D double array
     */
    public static double[] getColumnArray(Matrix m, int col) {
        //todo
        //return getColumnArray(m.getColumnMatrix(col));
        double[] d = new double[m.rows()];
        for (int row = 1; row <= m.rows(); row++) {
            d[row - 1] = m.getValue(row, col);
        }
        return d;
    }

    /**
     * Gets the <code>row</code> row of the matrix as a <code>double array</code>
     *
     * @param m
     * @param row
     * @return 1D double array
     */
    public static double[] getRowArray(Matrix m, int row) {
        //todo
        //return getRowArray(m.getRowMatrix(row));
        double[] d = new double[m.cols()];
        for (int col = 1; col <= m.cols(); col++) {
            d[col - 1] = m.getValue(row, col);
        }
        return d;
    }


    /**
     * Gets all the elements of the matrix as two-dimensional array
     *
     * @param m
     * @return 2D double array
     */
    public static double[][] getArray(Matrix m) {
        double[][] toReturn = new double[m.rows()][m.cols()];
        for (int row = 1; row <= m.rows(); row++) {
            for (int col = 1; col <= m.cols(); col++) {
                toReturn[row - 1][col - 1] = m.getValue(row, col);
            }
        }
        return toReturn;
    }

    /**
     * private constructor to prohibit object creation
     */
    private Matrices() {
    }

    public static Matrix getMatrixFromArray(int n, int n1, Matrix hint, double[][] elems) {
        Matrix toReturn = MatrixFactory.getMatrix(n, n1, hint);
        for (int i = 0; i < elems.length; i++) {
            for (int j = 0; j < elems[i].length; j++) {
                double elem = elems[j][j];
                toReturn.setValue(i + 1, j + 1, elem);
            }
        }
        return toReturn;
    }

    public static Matrix getColumnMatrixFromList(Matrix hint, List list) {
        if (list.size() <= 0)
            throw new IllegalArgumentException("Array list size should atleast be 1");
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
}
