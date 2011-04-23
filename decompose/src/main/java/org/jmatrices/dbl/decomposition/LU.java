package org.jmatrices.dbl.decomposition;

import org.jmatrices.dbl.Matrices;
import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;


/**
 * LUDecomposition <P> For an m-by-n matrix A with m >= n, the LU decomposition is an m-by-n unit lower triangular
 * matrix L, an n-by-n upper triangular matrix U, and a permutation vector piv of length m so that A(piv,:) = L*U. If m
 * < n, then L is m-by-m and U is m-by-n. </p> <P> The LU decompostion with pivoting always exists, even if the matrix
 * is singular, so the constructor will never fail.  The primary use of the LU decomposition is in the solution of
 * square systems of simultaneous linear equations.  This will fail if isNonsingular() returns false. </p>
 * <p/>
 * http://www.hku.hk/cc/sp2/software/hpf/Course/HTMLQuestionsnode65.html http://csep10.phys.utk.edu/guidry/phys594/lectures/linear_algebra/lanotes/node3.html
 * </p> <p><font color="red"> The code is basically JAMA code with modifications made to fit in the scheme of things.
 * </font></p>
 * <p/>
 * @author ppurang </p> Date: 08.03.2004 Time: 23:37:48
 */
public final strictfp class LU {
/* ------------------------
   Class variables
 * ------------------------ */

    /**
     * Array for internal storage of decomposition.
     *
     * @serial internal array storage.
     */
    private Matrix LU;

    /**
     * Row and column dimensions, and pivot sign.
     *
     * @serial column dimension.
     * @serial row dimension.
     * @serial pivot sign.
     */
    private int m, n, pivsign;

    /**
     * Internal storage of pivot vector.
     *
     * @serial pivot vector.
     */
    private Matrix piv;


/* ------------------------
   Constructor
 * ------------------------ */
    /**
     * LU Decomposition
     *
     * @param a Rectangular matrix
     */
    public LU(Matrix a) {   //renamed A to a
        // Use a "left-looking", dot-product, Crout/Doolittle algorithm.

        LU = MatrixFactory.getMutableMatrixClone(a);     //changed
        m = a.rows();
        n = a.cols();
        //piv = new int[m];  //todo change this into a matrix?
        piv = MatrixFactory.getMatrix(m, 1, a);
        for (int i = 1; i <= m; i++) {
            piv.setValue(i, 1, i);    //changed so as to begin the indices from 1 and not 0
            //either we could do it here or we could have adjusted it later
        }
        pivsign = 1;
        Matrix LUrowi;
        Matrix LUcolj = MatrixFactory.getMatrix(m, 1, a);

        // Outer loop.
        for (int j = 1; j <= n; j++) {

            // Make a copy of the j-th column to localize references.
            for (int i = 1; i <= m; i++) {
                LUcolj.setValue(i, 1, LU.getValue(i, j));
            }

            // Apply previous transformations.
            for (int i = 1; i <= m; i++) {
                //LUrowi = LU[i];
                LUrowi = Matrices.getRowMatrix(LU, i);

                // Most of the time is spent in the following dot product.

                int kmax = Math.min(i, j);
                double s = 0.0;
                for (int k = 1; k < kmax; k++) {
                    //s += LUrowi[k] * LUcolj[k];

                    s = s + LUrowi.getValue(1, k) * LUcolj.getValue(k, 1);
                }

                //LUrowi[j] = LUcolj[i] -= s;
                LUcolj.setValue(i, 1, LUcolj.getValue(i, 1) - s);

                LUrowi.setValue(1, j, LUcolj.getValue(i, 1));
                LU.setValue(i, j, LUcolj.getValue(i, 1));
            }
            // Find pivot and exchange if necessary.

            int p = j;
            for (int i = j + 1; i <= m; i++) {
                if (Math.abs(LUcolj.getValue(i, 1)) > Math.abs(LUcolj.getValue(p, 1))) {
                    p = i;
                }
            }

            if (p != j) {
                for (int k = 1; k <= n; k++) {
                    double t = LU.getValue(p, k);
                    LU.setValue(p, k, LU.getValue(j, k));
                    LU.setValue(j, k, t);
                }

                double k = piv.getValue(p, 1);
                piv.setValue(p, 1, piv.getValue(j, 1));
                piv.setValue(j, 1, k);
                pivsign = -pivsign;
            }

            // Compute multipliers.
            if (j <= m & LU.getValue(j, j) != 0.0) {
                for (int i = j + 1; i <= m; i++) {
                    LU.setValue(i, j, LU.getValue(i, j) / LU.getValue(j, j));
                }
            }
        }
    }

/* ------------------------
   Public Methods
 * ------------------------ */
    /**
     * Is the matrix nonsingular?
     *
     * @return true if U, and hence A, is nonsingular.
     */
    public boolean isNonsingular() {
        for (int j = 1; j <= n; j++) {
            if (LU.getValue(j, j) == 0)
                return false;
        }
        return true;
    }

    //added this method
    public boolean isSingular() {
        return !isNonsingular();
    }

    /**
     * Return lower triangular factor
     *
     * @return L
     */
    public Matrix getL() {
        Matrix L = MatrixFactory.getMatrix(m, n, LU); //changed
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (i > j) {
                    L.setValue(i, j, LU.getValue(i, j));
                } else if (i == j) {
                    L.setValue(i, j, 1.0);
                } else {
                    L.setValue(i, j, 0.0);
                    ;
                }
            }
        }
        return L;     //changed
    }

    /**
     * Return upper triangular factor
     *
     * @return U
     */

    public Matrix getU() {
        Matrix U = MatrixFactory.getMatrix(m, n, LU); //changed
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i <= j) {
                    U.setValue(i, j, LU.getValue(i, j));
                } else {
                    U.setValue(i, j, 0.0);
                }
            }
        }
        return U;
    }

    /**
     * Return pivot permutation vector
     *
     * @return piv
     */

    public Matrix getPivotMatrix() {

        return piv;
    }

    /**
     * Determinant
     *
     * @return det(A)
     * @throws IllegalArgumentException Matrix must be square
     */

    public double det() {
        if (m != n) {
            throw new IllegalArgumentException("Matrix must be square.");
        }
        double d = (double) pivsign;
        for (int j = 1; j <= n; j++) {
            d *= LU.getValue(j, j);
        }
        return d;
    }

    /**
     * Solve A*X = B
     *
     * @param b A Matrix with as many rows as A and any number of columns.
     * @return X so that L*U*X = B(piv,:)
     * @throws IllegalArgumentException Matrix row dimensions must agree.
     * @throws RuntimeException         Matrix is singular.
     */

    public Matrix solve(Matrix b) {
        if (b.rows() != m) {
            throw new IllegalArgumentException("Matrix row dimensions must agree.");
        }
        if (!this.isNonsingular()) {
            throw new RuntimeException("Matrix is singular.");
        }

        // Copy right hand side with pivoting
        int nx = b.cols();
        Matrix Xmat = Matrices.getSubMatrix(b, convertVectorToArray(piv), 0 + 1, nx - 1 + 1);//changed to reflect indices from 1,1   //piv must have indices begining from 1
        //Matrix Xmat = B.getSubMatrix(getAdjustedPivot(),0+1,nx-1+1);//if we wouldn't have changed piv earlier we could have done that in a method named adjustedPivot .. I tested it and it was working!
        Matrix X = MatrixFactory.getMutableMatrixClone(Xmat);

        // Solve L*Y = B(piv,:)
        for (int k = 1; k <= n; k++) {
            for (int i = k + 1; i <= n; i++) {
                for (int j = 1; j <= nx; j++) {
                    X.setValue(i, j, X.getValue(i, j) - (X.getValue(k, j) * LU.getValue(i, k)));
                }
            }
        }
        // Solve U*X = Y;
        for (int k = n; k >= 1; k--) {
            for (int j = 1; j <= nx; j++) {
                X.setValue(k, j, X.getValue(k, j) / LU.getValue(k, k));
            }
            for (int i = 1; i < k; i++) {
                for (int j = 1; j <= nx; j++) {
                    X.setValue(i, j, X.getValue(i, j) - (X.getValue(k, j) * LU.getValue(i, k)));
                }
            }
        }
        return X;
    }

    private int[] convertVectorToArray(Matrix p) {
        int[] toReturn = new int[p.rows()];
        for (int row = 1; row <= p.rows(); row++) {
            toReturn[row - 1] = (int) p.getValue(row, 1);
        }
        return toReturn;
    }

   /** public static void main(String[] args) {
        Matrix a = GaussSyntax.create
                ("{"
                + "3.00506436 2.65577048 3.08742844,"
                + "2.65577048 3.55545737 3.42362593,"
                + "3.08742844 3.42362593 4.02095978"
                + "}");
        Matrix b = GaussSyntax.create
                ("{"
                + "0.03177513,"
                + "0.41823100,"
                + "1.70129375"
                + "}");
        LU decomposition = new LU(a);    */
        /*System.out.println(decomposition.getL());
        System.out.println("********************");
        System.out.println(decomposition.getU());
        System.out.println("********************");
        System.out.println(decomposition.isSingular()); */
        //System.out.println("********************");
        //System.out.println(decomposition.solve(b));
        /*System.out.println("********************");
        System.out.println(decomposition.getPivotMatrix()); */
        //System.out.println("********************");
        //org.jmatrices.dbl.decomposition.LU lu = new org.jmatrices.dbl.decomposition.LU(a);
        /*System.out.println(decomposition.getL());
        System.out.println("********************");
        System.out.println(decomposition.getU());
        System.out.println("********************");
        System.out.println(decomposition.isSingular()); */
        //System.out.println("********************");
        //System.out.println(lu.solve(b));
        /*System.out.println("********************");

        System.out.println(decomposition.getPivotMatrix());

    }     */

/* ------------------------
   Temporary, experimental code.
   ------------------------ *\

   \** LU Decomposition, computed by Gaussian elimination.
   <P>
   This constructor computes L and U with the "daxpy"-based elimination
   algorithm used in LINPACK and MATLAB.  In Java, we suspect the dot-product,
   Crout algorithm will be faster.  We have temporarily included this
   constructor until timing experiments confirm this suspicion.
   <P>
   @param  A             Rectangular matrix
   @param  linpackflag   Use Gaussian elimination.  Actual value ignored.
   @return               Structure to access L, U and piv.
   *\

   public LUDecomposition (Matrix A, int linpackflag) {
      // Initialize.
      LU = A.getArrayCopy();
      m = A.getRowDimension();
      n = A.getColumnDimension();
      piv = new int[m];
      for (int i = 0; i < m; i++) {
         piv[i] = i;
      }
      pivsign = 1;
      // Main loop.
      for (int k = 0; k < n; k++) {
         // Find pivot.
         int p = k;
         for (int i = k+1; i < m; i++) {
            if (Math.abs(LU[i][k]) > Math.abs(LU[p][k])) {
               p = i;
            }
         }
         // Exchange if necessary.
         if (p != k) {
            for (int j = 0; j < n; j++) {
               double t = LU[p][j]; LU[p][j] = LU[k][j]; LU[k][j] = t;
            }
            int t = piv[p]; piv[p] = piv[k]; piv[k] = t;
            pivsign = -pivsign;
         }
         // Compute multipliers and eliminate k-th column.
         if (LU[k][k] != 0.0) {
            for (int i = k+1; i < m; i++) {
               LU[i][k] /= LU[k][k];
               for (int j = k+1; j < n; j++) {
                  LU[i][j] -= LU[i][k]*LU[k][j];
               }
            }
         }
      }
   }

\* ------------------------
   End of temporary code.
 * ------------------------ */

}
