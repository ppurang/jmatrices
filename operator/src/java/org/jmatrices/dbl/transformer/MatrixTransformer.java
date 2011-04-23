package org.jmatrices.dbl.transformer;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;
import org.jmatrices.dbl.Matrices;
import org.jmatrices.dbl.measure.MatrixMeasure;
import org.jmatrices.dbl.measure.MatrixProperty;
import org.jmatrices.dbl.operator.MatrixOperator;

import java.util.ArrayList;

/**
 * MatrixTransformer is responsible for classical non element-by-element transformation of a matrix
 * <p/>
 * Captures classical transformations that aren't applicable in an element-by-element way (only exception is {@link #negate(org.jmatrices.dbl.Matrix)} )
 * <p/>
 * <p><font color="blue">
 * We might decide to remove this restriction and provide some important ebe transformations here
 * or we might move them(to be methods) and negate to {@link MatrixEBETransformer}
 * </font></p>
 * <p/>
 * @author ppurang
 * <br/>
 * Date: 07.03.2004
 * Time: 17:03:20
 *
 * @see MatrixEBETransformation
 * @see MatrixEBETransformer
 */
public final class MatrixTransformer {
    /**
     * Transforms the matrix by negating all the elements
     *
     * @param m Matrix
     * @return -a<sub>i</sub><sub>j</sub> for all i,j
     */
    public static Matrix negate(final Matrix m) {
        return MatrixEBETransformer.ebeTransform(m, new MatrixConditionalEBETransformation() {
            public double transform(int row, int col, double element) {
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
    public static Matrix transpose(final Matrix m) {
        int rows = m.rows(), cols = m.cols();
        Matrix transposed = MatrixFactory.getMatrix(cols, rows, m);
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= cols; col++) {
                transposed.setValue(col, row, m.getValue(row, col));
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
    public static Matrix inverse(final Matrix m) {
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
    public static Matrix diagonal(final Matrix m) {
        int rows = m.rows(), cols = m.cols();
        Matrix diagonal;
        if (rows == 1 && cols == 1) {
            return m;
        } else {
            diagonal = MatrixFactory.getMatrix(rows, 1, m);
            for (int row = 1; row <= rows; row++) {
                for (int col = 1; col <= cols; col++) {
                    if (row == col)
                        diagonal.setValue(row, 1, m.getValue(row, col));
                }
            }
        }
        return diagonal;
    }

    /**
     * Returns a matrix with diagonal (indicated by offset)
     * composed of the elements from the passed row or column vector <code>m</code>
     *
     * @param m a column or row Vector
     * @param offset the diagonal to be embeded (=0 will embed the elements as the main diagonal)
     * @return
     */
    public static Matrix embedDiagonal(final Matrix m, int offset) {
        int d = Math.abs(offset) + MatrixMeasure.length(m);
        Matrix dm = MatrixFactory.getMatrix(d, d, m);
        Matrix n = m;

        switch (m.rows()) {
            case 1:
                //if row matrix let us convert it into column matrix and let program execution fall through to the next case
                n = MatrixTransformer.transpose(m);
            default:
                //not a row matrix
                switch (n.cols()) {
                    //if it is column vector or was a row vector and now is a column vector
                    case 1:
                        if (offset > 0) {
                            for (int row = 1; row <= MatrixMeasure.length(n); row++) {
                                dm.setValue(row, row + offset, n.getValue(row, 1));
                            }
                        } else {
                            for (int row = 1; row <= MatrixMeasure.length(n); row++) {
                                dm.setValue(row + Math.abs(offset), row, n.getValue(row, 1));
                            }
                        }
                        return dm;
                }
        }
        throw new IllegalArgumentException("Matrix m must be a row or column vector");

    }

    /**
     * Gets the elements of the indicated diagonal (offset) as a column vector
     * @param m
     * @param offset the diagonal to be extracted (=0 will extract the main diagonal)
     * @return Column vector of elements extracted from the indiacated diagonal
     */
    public static Matrix extractDiagonal(final Matrix m, int offset) {
        if (MatrixProperty.isVector(m)) {
            throw new IllegalArgumentException("Matrix m can't be a Column or a Row Vector");
        } else {
            int length = MatrixMeasure.length(m);
            if (offset > 0) {
                System.out.println("offset > 0");
                //Matrix cv = MatrixFactory.getMatrix(length-offset,1);
                ArrayList list = new ArrayList(10);
                for (int row = 1; row <= length - offset; row++) {
                    //System.out.println("Trying to access" + row+","+ (row + offset));
                    if (row <= m.rows() && row + offset <= m.cols())  {
                       //System.out.println("Adding "+m.getValue(row, row + offset));
                       list.add(new Double(m.getValue(row, row + offset)));
                    }
                }
                return Matrices.getColumnMatrixFromList(m,list);
            } else {
                if (length + offset < 1)
                    throw new IllegalArgumentException("Length of the matrix and offset combine to yield illegal matrix indices");
                    //return MatrixFactory.getEmptyMatrix();
                //Matrix cv = MatrixFactory.getMatrix(length + offset, 1);
                ArrayList list = new ArrayList(10);
                for (int row = 1; row <= length + offset; row++) {
                    //System.out.println("Trying to access" + (row + Math.abs(offset)) +","+ row);
                    if (row + Math.abs(offset) <= m.rows() && row <= m.cols()) {
                        //System.out.println("Adding "+m.getValue(row + Math.abs(offset), row));
                        list.add(new Double(m.getValue(row + Math.abs(offset), row)));
                    }
                }
                return Matrices.getColumnMatrixFromList(m,list);
            }
        }
    }

    /**
     * Extracts the upper triangular matrix given an offset that indicates the relative diagonal
     * <br/>
     *
     * @param m
     * @param offset
     * @return
     */
    public static Matrix extractUpperTriangular(final Matrix m, int offset) {
        int rows = m.rows(), cols = m.cols();
        Matrix ut = MatrixFactory.getMatrix(rows, cols,m);
        for (int row = 1; row <= MatrixMeasure.length(m) - Math.abs(offset); row++) {
            for (int col = row + Math.abs(offset); col <= MatrixMeasure.length(m); col++) {
                if (row <= rows && col <= cols)
                    ut.setValue(row, col, m.getValue(row, col));
            }
        }

        if (offset < 0) {
            for (int row = 1; row <= m.rows(); row++) {
                for (int col = Math.max(1, row + offset); col <= Math.min(MatrixMeasure.length(m), row - offset - 1); col++) {
                    if (row <= rows && col <= cols)
                        ut.setValue(row, col, m.getValue(row, col));
                }
            }

        }
        return ut;
    }

    /**
     * Extracts the lower triangular matrix given an offset that indicates the relative diagonal
     * <br/>
     *
     * @param m
     * @param offset
     * @return
     */
    public static Matrix extractLowerTriangular(final Matrix m, int offset) {
        return MatrixOperator.subtract(m, MatrixTransformer.extractUpperTriangular(m, offset + 1));
    }

    /**
     * Multiplies the square matrix with itself multiple times.
     *
     * @param m is a square matrix
     * @param n if n>1 performs the operation else if (n<=0) returns an immutable matrix composed of ones.
     * @return C = A<sup>n</sup>
     */
    public static Matrix pow(final Matrix m, int n) {
        if (MatrixProperty.isSquare(m)) {
            if (n <= 0)
                return MatrixFactory.getSingleValueMatrix(m.rows(), m.cols(), 1);
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
    public static Matrix secondaryDiagonal(final Matrix m) {
        throw new UnsupportedOperationException("to be implemented");
    }

    /**
     * <pre>
     * x =
     * 1 -3
     * 2  2
     * 3 -1
     *
     * y = cumulativeColumnProduct(x);
     * 1 -3
     * 2 -6
     * 6 6
     * </pre>
     *
     * @param m
     * @return
     */
    public static Matrix cumulativeColumnProduct(final Matrix m) {
       Matrix result = MatrixFactory.getMatrix(m.rows(),m.cols(),m);
       for(int row=1; row<=m.rows();row++){
           for(int col=1; col<=m.cols(); col++){
               if(row==1)           //todo could be done using conditional transformation
                result.setValue(row,col,m.getValue(row,col));
               else
               result.setValue(row,col,result.getValue(row-1,col)*m.getValue(row,col));
           }
       }
        return result;
    }

    /**
     * <pre>
     * x =
     * 1 -3
     * 2  2
     * 3 -1
     *
     * y = cumulativeColumnSum(x);
     * 1 -3
     * 3 -1
     * 6 -2
     * </pre>
     *
     * @param m
     * @return
     */
    public static Matrix cumulativeColumnSum(final Matrix m) {
       Matrix result = MatrixFactory.getMatrix(m.rows(),m.cols(),m);
       for(int row=1; row<=m.rows();row++){
           for(int col=1; col<=m.cols(); col++){
               if(row==1)            //todo could be done using conditional transformation
                result.setValue(row,col,m.getValue(row,col));
               else
               result.setValue(row,col,result.getValue(row-1,col)+m.getValue(row,col));
           }
       }
        return result;
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