package org.jmatrices.matrix.operator;

import org.jmatrices.matrix.Matrix;
import org.jmatrices.matrix.MatrixFactory;
import org.jmatrices.matrix.decomposition.LUDecomposition;
import org.jmatrices.matrix.decomposition.QRDecomposition;
import org.jmatrices.matrix.measure.MatrixProperty;

/**
 * MatrixOperator provides important operations that can be performed on two or more matrices
 * <p>
 * Given two matrices <strong> A,B -yields-> C</strong> ,where C is another matrix.
 * </p>
 * <p>
 * All operations on a matrix fitting this pattern can be found here!
 * </p>
 * <p>
 * Author: purangp
 * </p>
 * Date: 07.03.2004
 * Time: 18:09:51
 */
public class MatrixOperator {
    /**
     * Applies element-by-element operation combining the elements of the two matrices
     * <strong>Usage:</strong> from {@link MatrixOperator#add(org.jmatrices.matrix.Matrix, org.jmatrices.matrix.Matrix)}
     * <pre>
     * public static Matrix add(Matrix a, Matrix b) {
     *       return applyEBEOperation(a, b, new MatrixEBEOperation() {
     *           public double apply(double a, double b) {
     *               return a + b;
     *           }
     *       });
     *   }
     * </pre>
     * <strong>Hypothetical Usage:</strong>
     * <pre>
     * public static Matrix doSomeThing(Matrix a, Matrix b, final double n) {
     *       return applyEBEOperation(a, b, new MatrixEBEOperation() {
     *           public double apply(double a, double b) {
     *               return (a^2 + b^2)^n;
     *           }
     *       });
     *   }
     * </pre>
     *
     * @param a  Matrix
     * @param b  Matrix
     * @param mo Class
     * @return resultant Matrix
     */

    public static Matrix applyEBEOperation(Matrix a, Matrix b, MatrixEBEOperation mo) {
        int rows_a = a.rows(), cols_a = a.cols(), rows_b = b.rows(), cols_b = b.cols();
        Matrix c;
        if (rows_a != rows_b && cols_a != cols_b) {
            throw new IllegalArgumentException("Dimensions of a and b don't conform");
        } else {
            c = MatrixFactory.getMatrix(rows_a, cols_b);
            for (int row = 1; row <= rows_a; row++) {
                for (int col = 1; col <= cols_a; col++) {
                    c.set(row, col, mo.apply(a.get(row, col), b.get(row, col)));
                }
            }
        }
        return c;
    }

    /**
     * Solves the system of equations. Matrix x doesn't need to be square!
     * <p/>
     * Please pay attention to the dimensions of the two matrices!
     *
     * @param x The coefficient matrix
     * @param b The constant vector
     * @return The solution matrix or c = x / b
     */
    public static Matrix solve(Matrix x, Matrix b) {
        return (MatrixProperty.isSquare(x) ?
                new LUDecomposition(x).solve(b) :
                new QRDecomposition(x).solve(b));
    }

    /**
     * Matrix Addition
     * <p/>
     * Matrices must be of the same dimensions!
     *
     * @param a Matrix
     * @param b Matrix
     * @return c = a + b
     */
    public static Matrix add(Matrix a, Matrix b) {
        return applyEBEOperation(a, b, new MatrixEBEOperation() {
            public double apply(double a, double b) {
                return a + b;
            }
        });
    }

    /**
     * Matrix Subtraction
     * <p/>
     * Matrices must be of the same dimensions!
     *
     * @param a Matrix
     * @param b Matrix
     * @return c = a - b
     */
    public static Matrix subtract(Matrix a, Matrix b) {
        return applyEBEOperation(a, b, new MatrixEBEOperation() {
            public double apply(double a, double b) {
                return a - b;
            }
        });
    }

    /**
     * Matrix multiplication
     * <p/>
     * Matrix dimensions must conform with the rules of matrix multiplication!
     *
     * @param a Matrix
     * @param b Matrix
     * @return c = a * b
     */
    public static Matrix multiply(Matrix a, Matrix b) {
        int rows_a = a.rows(), cols_a = a.cols(), rows_b = b.rows(), cols_b = b.cols();
        Matrix c;
        if (cols_a != rows_b) {
            throw new IllegalArgumentException("Dimensions of matrices don't conform for multiplication");
        } else {
            c = MatrixFactory.getMatrix(rows_a, cols_b);
            //pick a row in a
            for (int row_a = 1; row_a <= rows_a; row_a++) {

                //pick a col in b
                for (int col_b = 1; col_b <= cols_b; col_b++) {
                    //iterate over all cols in the selected row for a

                    for (int col_a = 1; col_a <= cols_a; col_a++) {
                        double tmp = 0D;
                        //iterate over all rows in the selected col for b
                        for (int row_b = 1; row_b <= rows_b; row_b++) {
                            double elem_a = a.get(row_a, row_b), elem_b = b.get(row_b, col_b);
                            tmp = tmp + elem_a * elem_b;
                        }
                        c.set(row_a, col_b, tmp);
                    }
                }
            }
        }
        return c;
    }

    /**
     * Element-by-elemnt Matrix Multiplication
     * <p/>
     * Matrices must be of the same dimensions!
     *
     * @param a Matrix
     * @param b Matrix
     * @return c = a .* b
     */
    public static Matrix multiplyEBE(Matrix a, Matrix b) {
        return applyEBEOperation(a, b, new MatrixEBEOperation() {
            public double apply(double a, double b) {
                return a * b;
            }
        });
    }

    /**
     * Element-by-elemnt Matrix division
     * <p/>
     * Matrices must be of the same dimensions!
     *
     * @param a Matrix
     * @param b Matrix
     * @return c = a ./ b
     */
    public static Matrix divideEBE(Matrix a, Matrix b) {
        return applyEBEOperation(a, b, new MatrixEBEOperation() {
            public double apply(double a, double b) {
                return a / b;
            }
        });
    }

    /**
     * Concatenates <code>a</code> and <code>b</code> horizontally with
     * <code>b</code>'s columns attached to the end of <code>a</code>
     * <p/>
     * rows of <code>a</code> must be equal to rows of <code>b</code>
     *
     * @param a Matrix
     * @param b Matrix
     * @return c = a~b //gauss syntax
     */
    public static Matrix horizontalConcatenation(Matrix a, Matrix b) {
        int rows_a = a.rows(), cols_a = a.cols(), rows_b = b.rows(), cols_b = b.cols();
        Matrix c;
        if (rows_a != rows_b) {
            throw new IllegalArgumentException("Dimensions of a and b don't conform");
        } else {
            c = MatrixFactory.getMatrix(rows_a, cols_a + cols_b);
            for (int row = 1; row <= rows_a; row++) {
                for (int col_a = 1; col_a <= cols_a; col_a++) {
                    c.set(row, col_a, a.get(row, col_a));
                }
                for (int col_b = 1; col_b <= cols_b; col_b++) {
                    c.set(row, cols_a + col_b, b.get(row, col_b));
                }
            }
        }
        return c;
    }

    /**
     * Concatenates <code>a</code> and <code>b</code> vertically with
     * <code>b</code>'s rows following the <code>a</code>'s rows
     * <p/>
     * cols of <code>a</code> must be equal to colss of <code>b</code>
     *
     * @param a Matrix
     * @param b Matrix
     * @return c = a|b //gauss syntax
     */
    public static Matrix verticalConcatenation(Matrix a, Matrix b) {
        int rows_a = a.rows(), cols_a = a.cols(), rows_b = b.rows(), cols_b = b.cols();
        Matrix c;
        if (cols_a != cols_b) {
            throw new IllegalArgumentException("Dimensions of a and b don't conform");
        } else {
            c = MatrixFactory.getMatrix(rows_a + rows_b, cols_a);
            for (int col = 1; col <= cols_a; col++) {
                for (int row_a = 1; row_a <= rows_a; row_a++) {
                    c.set(row_a, col, a.get(row_a, col));
                }
                for (int row_b = 1; row_b <= rows_b; row_b++) {
                    c.set(rows_a + row_b, col, b.get(row_b, col));
                }
            }
        }
        return c;
    }

    /**
     * <font color="blue">todo implement</font>
     *
     * @param a
     * @param b
     * @return
     */
    public static Matrix kroneckeProduct(Matrix a, Matrix b) {
        //todo: see gauss help file (F1)
        throw new UnsupportedOperationException("not implemented!");
    }

    /**
     * <font color="blue">todo implement</font>
     *
     * @param a
     * @param b
     * @return
     */
    public static Matrix horizontalDirectProduct(Matrix a, Matrix b) {
        //todo: see gauss help file (F1)
        throw new UnsupportedOperationException("not implemented!");
    }

    private MatrixOperator() {
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