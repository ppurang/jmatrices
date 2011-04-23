package org.jmatrices.dbl.decomposition;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;
import org.jmatrices.dbl.*;

/**
 * CholeskyDecomposition
 * <p>
 * It is a special case of LU decomposition applicable only if matrix to be decomposed is symmetric positive definite.
 * </p>
 * <p>
 * For a symmetric, positive definite matrix A, the Cholesky decomposition
 * is an lower triangular matrix L so that A = L*L'.
 * </p>
 * <p>
 * If the matrix is not symmetric or positive definite, the constructor
 * returns a partial decomposition and sets an internal flag that may
 * be queried by the isSPD() method.
 * </p>
 * <p><font color="red">
 * The code is basically JAMA code with modifications made to fit in the scheme of things.
 * </font></p>
 * <p>
 * http://ikpe1101.ikp.kfa-juelich.de/briefbook_data_analysis/node33.html
 * http://grids.ucs.indiana.edu/ptliupages/projects/HPJava/talks/beijing/hpspmd/HPJava/node15.html (pseudo code)
 * </p>
 * <p>
 * Author: purangp
 * </p>
 * Date: 13.03.2004
 * Time: 01:04:45
 */
public class CholeskyDecomposition {

/* ------------------------
   Class variables
 * ------------------------ */

    /**
     * Array for internal storage of decomposition.
     *
     * @serial internal array storage.
     */
    private double[][] L;

    /**
     * Row and column dimension (square matrix).
     *
     * @serial matrix dimension.
     */
    private int n;

    /**
     * Symmetric and positive definite flag.
     *
     * @serial is symmetric and positive definite flag.
     */
    private boolean isspd;

    private Matrix hint;

/* ------------------------
   Constructor
 * ------------------------ */

    /**
     * Cholesky algorithm for symmetric and positive definite matrix.
     *
     * @param matrix Square, symmetric matrix.
     *            Structure to access L and isspd flag.
     */

    public CholeskyDecomposition(Matrix matrix) {
        hint = matrix;
        // Initialize.
        double[][] A = Matrices.getArray(matrix);
        n = matrix.rows();
        L = new double[n][n];
        //output2DArray(L);
        isspd = (matrix.cols() == n);
        // Main loop.
        //System.out.println("INITIATING MAIN LOOP (int j = 0; j < "+n+"; j++)");
        for (int j = 0; j < n; j++) {               //loop over the row's of L
            //System.out.println("\t Iteration " + j + " with value of j = " + j);
            double[] Lrowj = L[j];                  //retrieve the jth row of L decomposed matrix
            //System.out.print("\t L[j] = " + output1DArray(L[j])) ;
            double d = 0.0;
            //System.out.println("\t\tINITIATING INNER LOOP (int k = 0; k < "+ j +"; k++)");
            for (int k = 0; k < j; k++) {           //loop over the rows of L such that the rows are smaller than the main row
                //System.out.println("\t\t\t Iteration " + k + " with value of k = " + k);

                double[] Lrowk = L[k];              //retrieve the kth row of L decomposed matrix 0 <=  k <j
                //System.out.print("\t\t\t L[k] = " + output1DArray(L[k])) ;
                double s = 0.0;                     //running total of column
                //System.out.println("\t\t\t\tINITIATING INNER LOOP (int i = 0; i < "+ k +"; i++)");
                for (int i = 0; i < k; i++) {       //loop over the columns
                    //s += Lrowk[i] * Lrowj[i];
                    //System.out.println("\t\t\t\t\t Iteration " + i + " with value of i = " + i);
                    //System.out.println("\t\t\t\t\t Calculating value of s with s = " + s + ", Lrowk[i]= " + Lrowk[i] + ", Lrowj[i]= " + Lrowj[i]);
                    s = s + Lrowk[i] * Lrowj[i];    //multiply the ith element of the jth row with the ith element of the kth row and maintain the running total
                    //System.out.println("\t\t\t\t\t Value of s = " + s + " ");
                }
                //Lrowj[k] = s = (A[j][k] - s) / L[k][k];
                //System.out.println("\t\t\t Calculating value of s with s = " + s + ", A[j,k]= " + A[j][k] + ", L[k,k]= " + L[k][k]);
                s = (A[j][k] - s) / L[k][k];        //retrieve the j,k element from the src matrix subtract the running total and divide by the k,k element from the L
                //System.out.println("\t\t\t Value of s = " + s );
                Lrowj[k] = s;                       // set the kth column of jth row of L to s
                //System.out.println("\t\t\t Value of Lrowj[k] = " + Lrowj[k] );
                d = d + s * s;                      //
                //System.out.println("\t\t\t Value of d = " + d );
                isspd = isspd & (A[k][j] == A[j][k]); // deduce spd
                //System.out.println("\t\t\t Value of isspd = " + isspd );
            }
            d = A[j][j] - d;
            //System.out.println("\t Value of d = " + d );
            isspd = isspd & (d > 0.0);
            //System.out.println("\t Value of isspd = " + isspd );
            L[j][j] = Math.sqrt(Math.max(d, 0.0));      //set j,j element of L to sqrt of positive d's
            //System.out.println("\t Value of L["+j+"]["+j+"] = " + L[j][j] );
            //System.out.println("\t\tINITIATING INNER LOOP (int k = "+(j+1)+"; k < "+ n +"; k++)");
            for (int k = j + 1; k < n; k++) {           //set all j,k elements to 0 such that  j+1 <= k < n
                //System.out.println("\t\t\t Iteration " + k + " with value of k = " + k);
                L[j][k] = 0.0;
                //System.out.println("\t\t\t Value of L["+j+"]["+k+"] = " + 0.0 );
            }
            //System.out.println("\tL = " + output2DArray(L));
        }

    }
    /*String output1DArray(double[] d1) {
        StringBuffer sbuf = new StringBuffer();
        for (int i = 0; i < d1.length; i++) {
            sbuf.append(d1[i] + " ");

        }
        return sbuf.append("\n").toString();
    }
    String output2DArray (double[][]  d2) {
        StringBuffer sbuf = new StringBuffer();
        for (int i = 0; i < d2.length; i++) {

            for (int j = 0; j < d2[i].length; j++) {
                sbuf.append(d2[i][j] + " ");
            }
            sbuf.append("\n");
        }
       return sbuf.toString();
    }     */
/* ------------------------
   Temporary, experimental code.
 * ------------------------ *\

   \** Right Triangular Cholesky Decomposition.
   <P>
   For a symmetric, positive definite matrix A, the Right Cholesky
   decomposition is an upper triangular matrix R so that A = R'*R.
   This constructor computes R with the Fortran inspired column oriented
   algorithm used in LINPACK and MATLAB.  In Java, we suspect a row oriented,
   lower triangular decomposition is faster.  We have temporarily included
   this constructor here until timing experiments confirm this suspicion.
   *\

   \** Array for internal storage of right triangular decomposition. **\
   private transient double[][] R;

   \** Cholesky algorithm for symmetric and positive definite matrix.
   @param  A           Square, symmetric matrix.
   @param  rightflag   Actual value ignored.
   @return             Structure to access R and isspd flag.
   *\

   public CholeskyDecomposition (Matrix Arg, int rightflag) {
      // Initialize.
      double[][] A = Arg.getArray();
      n = Arg.getColumnDimension();
      R = new double[n][n];
      isspd = (Arg.getColumnDimension() == n);
      // Main loop.
      for (int j = 0; j < n; j++) {
         double d = 0.0;
         for (int k = 0; k < j; k++) {
            double s = A[k][j];
            for (int i = 0; i < k; i++) {
               s = s - R[i][k]*R[i][j];
            }
            R[k][j] = s = s/R[k][k];
            d = d + s*s;
            isspd = isspd & (A[k][j] == A[j][k]);
         }
         d = A[j][j] - d;
         isspd = isspd & (d > 0.0);
         R[j][j] = Math.sqrt(Math.max(d,0.0));
         for (int k = j+1; k < n; k++) {
            R[k][j] = 0.0;
         }
      }
   }

   \** Return upper triangular factor.
   @return     R
   *\

   public Matrix getR () {
      return new Matrix(R,n,n);
   }

\* ------------------------
   End of temporary code.
 * ------------------------ */

/* ------------------------
   Public Methods
 * ------------------------ */

    /**
     * Is the matrix symmetric and positive definite?
     *
     * @return true if A is symmetric and positive definite.
     */

    public boolean isSPD() {
        return isspd;
    }

    /**
     * Return triangular factor.
     *
     * @return L
     */

    public Matrix getL() {
        return MatrixFactory.getMatrix(n, n, hint, L);
    }

    /**
     * Solve A*X = B
     *
     * @param b  A Matrix with as many rows as A and any number of columns.
     * @return X so that L*L'*X = B
     * @throws IllegalArgumentException Matrix row dimensions must agree.
     * @throws RuntimeException         Matrix is not symmetric positive definite.
     */

    public Matrix solve(Matrix b) {
        if (b.rows() != n) {
            throw new IllegalArgumentException("Matrix row dimensions must agree.");
        }
        if (!isspd) {
            throw new RuntimeException("Matrix is not symmetric positive definite.");
        }

        // Copy right hand side.
        double[][] X = Matrices.getArray(b);
        int nx = b.cols();

        // Solve L*Y = B;
        for (int k = 0; k < n; k++) {
            for (int i = k + 1; i < n; i++) {
                for (int j = 0; j < nx; j++) {
                    X[i][j] -= X[k][j] * L[i][k];
                }
            }
            for (int j = 0; j < nx; j++) {
                X[k][j] /= L[k][k];
            }
        }

        // Solve L'*X = Y;
        for (int k = n - 1; k >= 0; k--) {
            for (int j = 0; j < nx; j++) {
                X[k][j] /= L[k][k];
            }
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < nx; j++) {
                    X[i][j] -= X[k][j] * L[k][i];
                }
            }
        }
        return MatrixFactory.getMatrix(n, nx, hint, X);
    }
}
