/**
 * Jmatrices - Matrix Library
 * Copyright (C) 2005  Piyush Purang
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
package org.jmatrices.dbl.decomposition;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;
import org.jmatrices.dbl.decomposition.Util;

/**
 * QR
 *
 * @author purangp
 *         Created 07.02.2005 - 01:58:00
 */
public class QR {

    /* ------------------------
   Class variables
 * ------------------------ */

    /**
     * Array for internal storage of decomposition.
     *
     * @serial internal array storage.
     */
    private MatrixAdaptor QR;

    /**
     * Row and column dimensions.
     *
     * @serial column dimension.
     * @serial row dimension.
     */
    private int m, n;

    /**
     * Array for internal storage of diagonal of R.
     *
     * @serial diagonal of R.
     */
    private MatrixAdaptor Rdiag;


/* ------------------------
   Constructor
 * ------------------------ */

    /**
     * QR Decomposition, computed by Householder reflections.
     *
     * @param matrix Rectangular matrix
     *               Structure to access R and the Householder vectors and compute Q.
     */

    public QR(Matrix matrix) {
        // Initialize.
        QR = new MatrixAdaptor(MatrixFactory.getMatrixClone(matrix));        //changed
        m = matrix.rows(); //changed
        n = matrix.cols();  //changed
        Rdiag = new MatrixAdaptor(MatrixFactory.getMatrix(n, 1, matrix));

        // Main loop.
        for (int k = 0; k < n; k++) {
            // Compute 2-norm of k-th column without under/overflow.
            double nrm = 0;
            for (int i = k; i < m; i++) {
                nrm = Util.hypot(nrm, QR.getValue(i, k));
            }

            if (nrm != 0.0) {
                // Form k-th Householder vector.
                if (QR.getValue(k, k) < 0) {
                    nrm = -nrm;
                }
                for (int i = k; i < m; i++) {
                    QR.setValue(i, k, QR.getValue(i, k) / nrm);
                }
                QR.setValue(k, k, QR.getValue(k, k) + 1.0);

                // Apply transformer to remaining columns.
                for (int j = k + 1; j < n; j++) {
                    double s = 0.0;
                    for (int i = k; i < m; i++) {
                        s += QR.getValue(i, k) * QR.getValue(i, j);
                    }
                    s = -s / QR.getValue(k, k);
                    for (int i = k; i < m; i++) {
                        QR.setValue(i, j, QR.getValue(i, j) + s * QR.getValue(i, k));
                    }
                }
            }
            Rdiag.setValue(k, 0, -nrm);
        }
    }

/* ------------------------
   Public Methods
 * ------------------------ */

    /**
     * Is the matrix full rank?
     *
     * @return true if R, and hence A, has full rank.
     */

    public boolean isFullRank() {
        for (int j = 0; j < n; j++) {
            if (Rdiag.getValue(j, 0) == 0)
                return false;
        }
        return true;
    }

    /**
     * Return the Householder vectors
     *
     * @return Lower trapezoidal matrix whose columns define the reflections
     */

    public Matrix getH() {
        //todo how will Matrixfactory know the right implementation?
        MatrixAdaptor H = new MatrixAdaptor(MatrixFactory.getMatrix(m, n, null));//changed
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i >= j) {
                    H.setValue(i, j, QR.getValue(i, j));
                } else {
                    H.setValue(i, j, 0.0);
                }
            }
        }
        return H.getAdaptee();  //changed
    }

    /**
     * Return the upper triangular factor
     *
     * @return R
     */

    public Matrix getR() {
        //todo how will Matrixfactory know the right implementation?
        MatrixAdaptor R = new MatrixAdaptor(MatrixFactory.getMatrix(n, n, null));//changed
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i < j) {
                    R.setValue(i, j, QR.getValue(i, j));
                } else if (i == j) {
                    R.setValue(i, j, Rdiag.getValue(i, 0));
                } else {
                    R.setValue(i, j, 0.0);
                }
            }
        }
        return R.getAdaptee(); //changed
    }

    /**
     * Generate and return the (economy-sized) orthogonal factor
     *
     * @return Q
     */

    public Matrix getQ() {
        //todo how will Matrixfactory know the right implementation?
        MatrixAdaptor Q = new MatrixAdaptor(MatrixFactory.getMatrix(m, n, null));//changed
        for (int k = n - 1; k >= 0; k--) {
            for (int i = 0; i < m; i++) {
                Q.setValue(i, k, 0.0);
            }
            Q.setValue(k, k, 1.0);
            for (int j = k; j < n; j++) {
                if (QR.getValue(k, k) != 0) {
                    double s = 0.0;
                    for (int i = k; i < m; i++) {
                        s += QR.getValue(i, k) * Q.getValue(i, j); //question should this really be Q? or rather is Q.getValue ever != 0
                    }
                    s = -s / QR.getValue(k, k);
                    for (int i = k; i < m; i++) {
                        Q.setValue(i, j, Q.getValue(i, j) + s * QR.getValue(i, k));
                    }
                }
            }
        }
        return Q.getAdaptee();
    }

    /**
     * Least squares solution of A*X = B
     *
     * @param b A Matrix with as many rows as A and any number of columns.
     * @return X that minimizes the two norm of Q*R*X-B.
     * @throws IllegalArgumentException Matrix row dimensions must agree.
     * @throws RuntimeException         Matrix is rank deficient.
     */

    public Matrix solve(Matrix b) {
        if (b.rows() != m) {
            throw new IllegalArgumentException("Matrix row dimensions must agree.");
        }
        if (!this.isFullRank()) {
            throw new RuntimeException("Matrix is rank deficient.");
        }

        // Copy right hand side
        int nx = b.cols();
        MatrixAdaptor X = new MatrixAdaptor(MatrixFactory.getMatrix(b.rows(), b.cols(), b));        //changed
        //todo use .clone() when moving to the new Matrix.
        for (int row = 1; row <= b.rows(); row++) {
            for (int col = 1; col <= b.cols(); col++) {
                X.setValue(row - 1, col - 1, b.getValue(row, col));
            }
        }

        // Compute Y = transpose(Q)*B
        for (int k = 0; k < n; k++) {
            for (int j = 0; j < nx; j++) {
                double s = 0.0;
                for (int i = k; i < m; i++) {
                    s += QR.getValue(i, k) * X.getValue(i, j);
                }
                s = -s / QR.getValue(k, k);
                for (int i = k; i < m; i++) {
                    X.setValue(i, j, X.getValue(i, j) + s * QR.getValue(i, k));
                }
            }
        }
        // Solve R*X = Y;
        for (int k = n - 1; k >= 0; k--) {
            for (int j = 0; j < nx; j++) {
                X.setValue(k, j, X.getValue(k, j) / Rdiag.getValue(k, 0));
            }
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < nx; j++) {
                    X.setValue(i, j, X.getValue(i, j) - X.getValue(k, j) * QR.getValue(i, k));
                }
            }
        }
        //return (new Matrix(X, n, nx).getMatrix(0, n - 1, 0, nx - 1));
        //return MatrixFactory.getMatrix(b.rows(), b.cols(), hint, X).getSubMatrix(1, 1, n, nx); //changed  taking care of 1,1 (index start) and differences between getMatrix and getSubMatrix
        return X.getAdaptee();
    }


   /** public static void main(String[] args) {
        Matrix m = GaussSyntax.create("{1 2 4, 1 4 16, 1 6 36, 1 8 64}");

        Matrix b = GaussSyntax.create("{16.99, 57.01, 120.99, 209.01}");

        QRDecomposition qrd = new QRDecomposition(m);
        System.out.println(qrd.getH());
        System.out.println(qrd.getQ());
        System.out.println(qrd.getR());
        System.out.println(qrd.isFullRank());
        System.out.println(qrd.solve(b));

        System.out.println("*********************");
        QR qr = new QR(m);
        System.out.println(qr.getH());
        System.out.println(qr.getQ());
        System.out.println(qr.getR());
        System.out.println(qr.isFullRank());
        System.out.println(qr.solve(b));

    } */
}
