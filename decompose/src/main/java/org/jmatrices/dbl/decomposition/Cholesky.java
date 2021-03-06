package org.jmatrices.dbl.decomposition;

import org.jmatrices.dbl.Matrices;
import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;


/**
 * Created by IntelliJ IDEA.
 * User: ppurang
 * Date: 16.10.2004
 * Time: 11:01:41
 * To change this template use File | Settings | File Templates.
 */
public final strictfp class Cholesky {
    /**
     * Array for internal storage of decomposition.
     *
     * @serial internal array storage.
     */
    //private double[][] L;
    private Matrix L;

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

    /**
     * Cholesky algorithm for symmetric and positive definite matrix.
     *
     * @param matrix Square, symmetric matrix.
     *               Structure to access L and isspd flag.
     */

    public Cholesky(Matrix matrix) {
        //double[][] A = Matrices.getArray(matrix);
        Matrix A = MatrixFactory.getMatrixClone(matrix);  //A isn't modified

        n = matrix.rows();
        //L = new double[n][n];
        L = MatrixFactory.getMatrix(n, n, matrix);

        isspd = (matrix.cols() == n);

        //for (int j = 0; j < n; j++) {
        for (int j = 1; j <= n; j++) {
            //double[] Lrowj = L[j];
            Matrix Lrowj = Matrices.getRowMatrix(L, j);
            double d = 0.0;
            //for (int k = 0; k < j; k++) {
            for (int k = 1; k < j; k++) {             //todo[fixed] an error here changed  k<=j to k<j
                //double[] Lrowk = L[k];
                Matrix Lrowk = Matrices.getRowMatrix(L, k);
                double s = 0.0;
                //for (int i = 0; i < k; i++) {
                for (int i = 1; i < k; i++) {     //todo perhaps need to change the same here.. I did that i<=k to i<k
                    //s += Lrowk[i] * Lrowj[i];
                    s = s + Lrowk.getValue(1, i) * Lrowj.getValue(1, i);
                }
                //Lrowj[k] = s = (A[j][k] - s) / L[k][k];
                s = (A.getValue(j, k) - s) / L.getValue(k, k);
                Lrowj.setValue(1, k, s);
                L.setValue(j,k,s);
                d = d + s * s;
                //isspd = isspd & (A[k][j] == A[j][k]);
                isspd = isspd & (A.getValue(k, j) == A.getValue(j, k));
            }
            //d = A[j][j] - d;
            d = A.getValue(j, j) - d;
            isspd = isspd & (d > 0.0);
            //L[j][j] = Math.sqrt(Math.max(d, 0.0));
            L.setValue(j, j, Math.sqrt(Math.max(d, 0.0)));
            //for (int k = j + 1; k < n; k++) {
            for (int k = j + 1; k <= n; k++) {    //todo changed k=j+1+1 to k=j+1
                //L[j][k] = 0.0;
                L.setValue(j, k, 0.0);
            }
        }
    }

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
        //return MatrixFactory.getMatrix(n, n, hint, L);
        return MatrixFactory.getMatrixClone(L);
    }

    /**
     * Solve A*X = B
     *
     * @param b A Matrix with as many rows as A and any number of columns.
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

        //double[][] X = Matrices.getArray(b);
        MatrixAdaptor X = new MatrixAdaptor(MatrixFactory.getMutableMatrixClone(b));
        MatrixAdaptor Ladapted = new MatrixAdaptor(L);
        int nx = b.cols();

        // Solve L*Y = B;
        for (int k = 0; k < n; k++) {
          for (int j = 0; j < nx; j++) {
             for (int i = 0; i < k ; i++) {
                 //X[k][j] -= X[i][j]*L[k][i];
                 X.setValue(k, j, X.getValue(k, j) - (X.getValue(i, j) * Ladapted.getValue(i, k)));
             }
             //X[k][j] /= L[k][k];
             X.setValue(k,j, X.getValue(k,j)/Ladapted.getValue(k,k));
          }
        }

        // Solve L'*X = Y;
        for (int k = n-1; k >= 0; k--) {
          for (int j = 0; j < nx; j++) {
             for (int i = k+1; i < n ; i++) {
                 //X[k][j] -= X[i][j]*L[i][k];
                 X.setValue(k, j, X.getValue(k, j) - (X.getValue(i, j) * Ladapted.getValue(i, k)));
             }
             //X[k][j] /= L[k][k];
              X.setValue(k,j, X.getValue(k,j)/Ladapted.getValue(k,k));
          }
        }

        //return MatrixFactory.getMatrix(n, nx, hint, X);
        return X.getAdaptee();
    }

   /** public static void main(String[] args) {
        Matrix m = MatlabSyntax.create("   [     1,0,0;0,1,0;0,0,1    ]   ");
        Matrix b = MatlabSyntax.create("   [     1;1;1 ]   ");
   */
        /*org.jmatrices.dbl.decomposition.CholeskyDecomposition cdl = new org.jmatrices.dbl.decomposition.CholeskyDecomposition(m);
        System.out.println(cdl.getL());
        System.out.println(cdl.solve(b));

        Cholesky cd = new Cholesky(m);
        System.out.println(cd.getL());
        System.out.println(cd.solve(b));*/

   /**     testCholskeyDecomposition();

    }*/


   /** public static void testCholskeyDecomposition() {
        Matrix A = MatlabSyntax.create
                ("["
                + "3.00506436, 2.65577048, 3.08742844;"
                + "2.65577048, 3.55545737, 3.42362593;"
                + "3.08742844, 3.42362593, 4.02095978"
                + "]");
        Matrix B = MatlabSyntax.create("[0.03177513;0.41823100;1.70129375]");
        Matrix L = MatlabSyntax.create
                ("["
                +"1.73351215,1.53201723,1.78102499;"
                + "0,1.09926365,0.63230050;"
                + "0,0,0.67015361"
                + "]");

        //System.out.println(A);
        //new Cholesky(A).getL();
        //System.out.println(new CholeskyDecomposition(A).solve(B));
        System.out.println(new Cholesky(A).isSPD());
        System.out.println(new Cholesky(A).solve(B));
        System.out.println("***********************************************************");
        //new org.jmatrices.dbl.decomposition.CholeskyDecomposition(A).getL();
        //System.out.println(new org.jmatrices.dbl.decomposition.CholeskyDecomposition(A).solve(B));
        System.out.println(new org.jmatrices.dbl.decomposition.CholeskyDecomposition(A).getL());


    }   */
}
