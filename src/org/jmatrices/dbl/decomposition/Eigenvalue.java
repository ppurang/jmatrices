package org.jmatrices.dbl.decomposition;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;
import org.jmatrices.dbl.*;

/**
 * EigenvalueDecomposition
 * <p>
 * If A is symmetric, then A = V*D*V' where the eigenvalue matrix D is
 * diagonal and the eigenvector matrix V is orthogonal.
 * I.e. A = V.times(D.times(V.transpose())) and
 * V.times(V.transpose()) equals the identity matrix.
 * </p>
 * <p>
 * If A is not symmetric, then the eigenvalue matrix D is block diagonal
 * with the real eigenvalues in 1-by-1 blocks and any complex eigenvalues,
 * lambda + i*mu, in 2-by-2 blocks, [lambda, mu; -mu, lambda].  The
 * columns of V represent the eigenvectors in the sense that A*V = V*D,
 * i.e. A.times(V) equals V.times(D).  The matrix V may be badly
 * conditioned, or even singular, so the validity of the equation
 * A = V*D*inverse(V) depends upon V.cond().
 * </p>
 * <p><font color="red">
 * The code is basically JAMA code with modifications made to fit in the scheme of things.
 * </font></p>
 * <p>
 * Author: purangp
 * </p>
 * Date: 13.03.2004
 * Time: 14:25:02
 */
public class Eigenvalue {

    private Matrix hint;
    /**
     * Row and column dimension (square matrix).
     *
     * @serial matrix dimension.
     */
    private int n;

    /**
     * Symmetry flag.
     *
     * @serial internal symmetry flag.
     */
    private boolean issymmetric;

    /**
     * Arrays for internal storage of eigenvalues.
     *
     * @serial internal storage of eigenvalues.
     */
    private MatrixAdaptor d, e;

    /**
     * Array for internal storage of eigenvectors.
     *
     * @serial internal storage of eigenvectors.
     */
    private MatrixAdaptor V;

    /**
     * Array for internal storage of nonsymmetric Hessenberg form.
     *
     * @serial internal storage of nonsymmetric Hessenberg form.
     */
    private MatrixAdaptor H;

    /**
     * Working storage for nonsymmetric algorithm.
     *
     * @serial working storage for nonsymmetric algorithm.
     */
    private MatrixAdaptor ort;

/* ------------------------
   Private Methods
 * ------------------------ */

    // Symmetric Householder reduction to tridiagonal form.

    private void tred2() {

        //  This is derived from the Algol procedures tred2 by
        //  Bowdler, Martin, Reinsch, and Wilkinson, Handbook for
        //  Auto. Comp., Vol.ii-Linear Algebra, and the corresponding
        //  Fortran subroutine in EISPACK.

        for (int j = 0; j < n; j++) {
            //d[j] = V[n - 1][j];
            d.setValue(j,0,V.getValue(n-1,j));
        }

        // Householder reduction to tridiagonal form.

        for (int i = n - 1; i > 0; i--) {

            // Scale to avoid under/overflow.

            double scale = 0.0;
            double h = 0.0;
            for (int k = 0; k < i; k++) {
                //scale = scale + Math.abs(d[k]);
                scale = scale + Math.abs(d.getValue(k,0));
            }
            if (scale == 0.0) {
                //e[i] = d[i - 1];
                e.setValue(i,0,d.getValue(i-1,0));
                for (int j = 0; j < i; j++) {
                    //d[j] = V[i - 1][j];
                    d.setValue(j,0,V.getValue(i-1,j));
                    //V[i][j] = 0.0;
                    V.setValue(i,j,0.0);
                    //V[j][i] = 0.0;
                    V.setValue(j,i,0.0);
                }
            } else {

                // Generate Householder vector.

                for (int k = 0; k < i; k++) {
                    //d[k] /= scale;
                    d.setValue(k,0,d.getValue(k,0)/scale);
                    //h += d[k] * d[k];
                    h += d.getValue(k,0) * d.getValue(k,0);
                }
                //double f = d[i - 1];
                double f = d.getValue(i-1,0);
                double g = Math.sqrt(h);
                if (f > 0) {
                    g = -g;
                }
                //e[i] = scale * g;
                e.setValue(i,0,scale*g);
                h = h - f * g;
                //d[i - 1] = f - g;
                d.setValue(i-1,0,f-g);
                for (int j = 0; j < i; j++) {
                    //e[j] = 0.0;
                    e.setValue(j,0,0.0);
                }

                // Apply similarity transformer to remaining columns.

                for (int j = 0; j < i; j++) {
                    //f = d[j];
                    f = d.getValue(j,0);
                    //V[j][i] = f;
                    V.setValue(j,i,f);
                    //g = e[j] + V[j][j] * f;
                    g = e.getValue(j,0) + V.getValue(i,j) * f;
                    for (int k = j + 1; k <= i - 1; k++) {
                        //g += V[k][j] * d[k];
                        g += V.getValue(k,j) * d.getValue(k,0);
                        //e[k] += V[k][j] * f;
                        e.setValue(k,0,e.getValue(k,0) +V.getValue(k,j)*f);
                    }
                    //e[j] = g;
                    e.setValue(j,0,g);
                }
                f = 0.0;
                for (int j = 0; j < i; j++) {
                    //e[j] /= h;
                    e.setValue(j,0,e.getValue(j,0)/h);
                    //f += e[j] * d[j];
                    f += e.getValue(j,0) * d.getValue(j,0);
                }
                double hh = f / (h + h);
                for (int j = 0; j < i; j++) {
                    //e[j] -= hh * d[j];
                    e.setValue(j,0,e.getValue(j,0)-hh*d.getValue(j,0));
                }
                for (int j = 0; j < i; j++) {
                    //f = d[j];
                    f = d.getValue(j,0);
                    //g = e[j];
                    g = e.getValue(j,0);
                    for (int k = j; k <= i - 1; k++) {
                        //V[k][j] -= (f * e[k] + g * d[k]);
                        V.setValue(k,j, V.getValue(k,j) - (f*e.getValue(k,0)+g*d.getValue(k,0)));
                    }
                    //d[j] = V[i - 1][j];
                    d.setValue(j,0,V.getValue(i-1,j));
                    //V[i][j] = 0.0;
                    V.setValue(i,j,0);
                }
            }
            //d[i] = h;
            d.setValue(i,0,h);
        }

        // Accumulate transformations.

        for (int i = 0; i < n - 1; i++) {
            //V[n - 1][i] = V[i][i];
            V.setValue(n-1,i,V.getValue(i,i));
            //V[i][i] = 1.0;
            V.setValue(i,i,1);
            //double h = d[i + 1];
            double h = d.getValue(i+1,0);
            if (h != 0.0) {
                for (int k = 0; k <= i; k++) {
                    //d[k] = V[k][i + 1] / h;
                    d.setValue(k,0,V.getValue(k,i+1)/h);
                }
                for (int j = 0; j <= i; j++) {
                    double g = 0.0;
                    for (int k = 0; k <= i; k++) {
                        //g += V[k][i + 1] * V[k][j];
                        g += V.getValue(k,i+1) * V.getValue(k,j);
                    }
                    for (int k = 0; k <= i; k++) {
                        //V[k][j] -= g * d[k];
                        V.setValue(k,j,V.getValue(k,j)-g*d.getValue(k,0));
                    }
                }
            }
            for (int k = 0; k <= i; k++) {
                //V[k][i + 1] = 0.0;
                V.setValue(k,i+1,0);
            }
        }
        for (int j = 0; j < n; j++) {
            //d[j] = V[n - 1][j];
            d.setValue(j,0,V.getValue(n-1,j));
            //V[n - 1][j] = 0.0;
            V.setValue(n-1,j,0);
        }
        //V[n - 1][n - 1] = 1.0;
        V.setValue(n-1,n-1,1);
        //e[0] = 0.0;
        e.setValue(0,0,0);
    }

    // Symmetric tridiagonal QL algorithm.

    private void tql2() {

        //  This is derived from the Algol procedures tql2, by
        //  Bowdler, Martin, Reinsch, and Wilkinson, Handbook for
        //  Auto. Comp., Vol.ii-Linear Algebra, and the corresponding
        //  Fortran subroutine in EISPACK.

        for (int i = 1; i < n; i++) {
            //e[i - 1] = e[i];
            e.setValue(i-1,0,e.getValue(i,0));
        }
        //e[n - 1] = 0.0;
        e.setValue(n-1,1,0);

        double f = 0.0;
        double tst1 = 0.0;
        double eps = Math.pow(2.0, -52.0);
        for (int l = 0; l < n; l++) {

            // Find small subdiagonal element

            //tst1 = Math.max(tst1, Math.abs(d[l]) + Math.abs(e[l]));
            tst1 = Math.max(tst1, Math.abs(d.getValue(l,0)) + Math.abs(e.getValue(l,0)));
            int m = l;
            while (m < n) {
                //if (Math.abs(e[m]) <= eps * tst1) {
                if (Math.abs(e.getValue(m,0)) <= eps * tst1) {
                    break;
                }
                m++;
            }

            // If m == l, d[l] is an eigenvalue,
            // otherwise, iterate.

            if (m > l) {
                int iter = 0;
                do {
                    iter = iter + 1;  // (Could check iteration count here.)

                    // Compute implicit shift

                    //double g = d[l];
                    double g = d.getValue(l,0);
                    //double p = (d[l + 1] - g) / (2.0 * e[l]);
                    double p = (d.getValue(l+1,0) - g) / (2.0 * e.getValue(l,0));
                    double r = Util.hypot(p, 1.0);
                    if (p < 0) {
                        r = -r;
                    }
                    //d[l] = e[l] / (p + r);
                    d.setValue(l,0,e.getValue(l,0)/(p+r));
                    //d[l + 1] = e[l] * (p + r);
                    d.setValue(l+1,0,e.getValue(l,0) * (p+r));
                    //double dl1 = d[l + 1];
                    double dl1 = d.getValue(l+1,0);
                    //double h = g - d[l];
                    double h = g - d.getValue(l,0);
                    for (int i = l + 2; i < n; i++) {
                        //d[i] -= h;
                        d.setValue(i,0,d.getValue(i,0)-h);
                    }
                    f = f + h;

                    // Implicit QL transformer.

                    //p = d[m];
                    p = d.getValue(m,0);
                    double c = 1.0;
                    double c2 = c;
                    double c3 = c;
                    //double el1 = e[l + 1];
                    double el1 = e.getValue(l+1,0);
                    double s = 0.0;
                    double s2 = 0.0;
                    for (int i = m - 1; i >= l; i--) {
                        c3 = c2;
                        c2 = c;
                        s2 = s;
                        //g = c * e[i];
                        g = c * e.getValue(i,0);
                        h = c * p;
                        //r = Util.hypot(p, e[i]);
                        r = Util.hypot(p, e.getValue(i,0));
                        //e[i + 1] = s * r;
                        e.setValue(i+1,0,s*r);
                        //s = e[i] / r;
                        s = e.getValue(i,0) / r;
                        c = p / r;
                        //p = c * d[i] - s * g;
                        p = c * d.getValue(i,0) - s * g;
                        //d[i + 1] = h + s * (c * g + s * d[i]);
                        d.setValue(i+1,0,h + s * (c * g + s * d.getValue(i,0)));

                        // Accumulate transformer.

                        for (int k = 0; k < n; k++) {
                            //h = V[k][i + 1];
                            h = V.getValue(k,i+1);
                            //V[k][i + 1] = s * V[k][i] + c * h;
                            V.setValue(k,i+1,s * V.getValue(k,i) + c * h);
                            //V[k][i] = c * V[k][i] - s * h;
                            V.setValue(k,i,c * V.getValue(k,i) - s * h);
                        }
                    }
                    //p = -s * s2 * c3 * el1 * e[l] / dl1;
                    p = -s * s2 * c3 * el1 * e.getValue(l,0) / dl1;
                    //e[l] = s * p;
                    e.setValue(l,0,s * p);
                    //d[l] = c * p;
                    d.setValue(l,0,c * p);

                    // Check for convergence.

                //} while (Math.abs(e[l]) > eps * tst1);
                } while (Math.abs(e.getValue(l,0)) > eps * tst1);
            }
            //d[l] = d[l] + f;
            d.setValue(l,0,d.getValue(l,0)+f);
            //e[l] = 0.0;
            e.setValue(l,0,0);
        }

        // Sort eigenvalues and corresponding vectors.

        for (int i = 0; i < n - 1; i++) {
            int k = i;
            //double p = d[i];
            double p = d.getValue(i,0);
            for (int j = i + 1; j < n; j++) {
                //if (d[j] < p) {
                if (d.getValue(j,0) < p) {
                    k = j;
                    p = d.getValue(j,0);
                }
            }
            if (k != i) {
                //d[k] = d[i];
                d.setValue(k,0,d.getValue(i,0));
                //d[i] = p;
                d.setValue(i,0,p);
                for (int j = 0; j < n; j++) {
                    //p = V[j][i];
                    p = V.getValue(j,i);
                    //V[j][i] = V[j][k];
                    V.setValue(j,i,V.getValue(j,k));
                    //V[j][k] = p;
                    V.setValue(j,k,p);
                }
            }
        }
    }

    // Nonsymmetric reduction to Hessenberg form.

    private void orthes() {

        //  This is derived from the Algol procedures orthes and ortran,
        //  by Martin and Wilkinson, Handbook for Auto. Comp.,
        //  Vol.ii-Linear Algebra, and the corresponding
        //  Fortran subroutines in EISPACK.

        int low = 0;
        int high = n - 1;

        for (int m = low + 1; m <= high - 1; m++) {

            // Scale column.

            double scale = 0.0;
            for (int i = m; i <= high; i++) {
                //scale = scale + Math.abs(H[i][m - 1]);
                scale = scale + Math.abs(H.getValue(i,m-1));
            }
            if (scale != 0.0) {

                // Compute Householder transformer.

                double h = 0.0;
                for (int i = high; i >= m; i--) {
                    //ort[i] = H[i][m - 1] / scale;
                    ort.setValue(i,0,H.getValue(i,m-1)/scale);
                    //h += ort[i] * ort[i];
                    h += ort.getValue(i,0) * ort.getValue(i,0);
                }
                double g = Math.sqrt(h);
                //if (ort[m] > 0) {
                if (ort.getValue(m,0) > 0) {
                    g = -g;
                }
                //h = h - ort[m] * g;
                h = h - ort.getValue(m,0) * g;
                //ort[m] = ort[m] - g;
                ort.setValue(m,0,ort.getValue(m,0)- g);

                // Apply Householder similarity transformer
                // H = (I-u*u'/h)*H*(I-u*u')/h)

                for (int j = m; j < n; j++) {
                    double f = 0.0;
                    for (int i = high; i >= m; i--) {
                        //f += ort[i] * H[i][j];
                        f += ort.getValue(i,0) * H.getValue(i,j);
                    }
                    f = f / h;
                    for (int i = m; i <= high; i++) {
                        //H[i][j] -= f * ort[i];
                        H.setValue(i,j,H.getValue(i,j)-f*ort.getValue(i,0));
                    }
                }

                for (int i = 0; i <= high; i++) {
                    double f = 0.0;
                    for (int j = high; j >= m; j--) {
                        //f += ort[j] * H[i][j];
                        f += ort.getValue(j,0) * H.getValue(i,j);
                    }
                    f = f / h;
                    for (int j = m; j <= high; j++) {
                        //H[i][j] -= f * ort[j];
                        H.setValue(i,j,H.getValue(i,j)-f*ort.getValue(j,1));
                    }
                }
                //ort[m] = scale * ort[m];
                ort.setValue(m,0,ort.getValue(m,0) * scale);
                //H[m][m - 1] = scale * g;
                H.setValue(m,m-1,scale*g);
            }
        }

        // Accumulate transformations (Algol's ortran).

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //V[i][j] = (i == j ? 1.0 : 0.0);
                V.setValue(i,j,(i == j ? 1.0 : 0.0));
            }
        }

        for (int m = high - 1; m >= low + 1; m--) {
            //if (H[m][m - 1] != 0.0) {
            if (H.getValue(m,m-1) != 0.0) {
                for (int i = m + 1; i <= high; i++) {
                    //ort[i] = H[i][m - 1];
                    ort.setValue(i,0,H.getValue(i,m-1));
                }
                for (int j = m; j <= high; j++) {
                    double g = 0.0;
                    for (int i = m; i <= high; i++) {
                        //g += ort[i] * V[i][j];
                        g += ort.getValue(i,0) * V.getValue(i,j);
                    }
                    // Double division avoids possible underflow
                    //g = (g / ort[m]) / H[m][m - 1];
                    g = (g / ort.getValue(m,0)) / H.getValue(m,m-1);
                    for (int i = m; i <= high; i++) {
                        //V[i][j] += g * ort[i];
                        V.setValue(i,j,V.getValue(i,j) + g*ort.getValue(i,0));
                    }
                }
            }
        }
    }


    // Complex scalar division.

    private transient double cdivr, cdivi;

    private void cdiv(double xr, double xi, double yr, double yi) {
        double r, d;
        if (Math.abs(yr) > Math.abs(yi)) {
            r = yi / yr;
            d = yr + r * yi;
            cdivr = (xr + r * xi) / d;
            cdivi = (xi - r * xr) / d;
        } else {
            r = yr / yi;
            d = yi + r * yr;
            cdivr = (r * xr + xi) / d;
            cdivi = (r * xi - xr) / d;
        }
    }


    // Nonsymmetric reduction from Hessenberg to real Schur form.

    private void hqr2() {

        //  This is derived from the Algol procedure hqr2,
        //  by Martin and Wilkinson, Handbook for Auto. Comp.,
        //  Vol.ii-Linear Algebra, and the corresponding
        //  Fortran subroutine in EISPACK.

        // Initialize

        int nn = this.n;
        int n = nn - 1;
        int low = 0;
        int high = nn - 1;
        double eps = Math.pow(2.0, -52.0);
        double exshift = 0.0;
        double p = 0, q = 0, r = 0, s = 0, z = 0, t, w, x, y;

        // Store roots isolated by balanc and compute matrix norm

        double norm = 0.0;
        for (int i = 0; i < nn; i++) {
            if (i < low | i > high) {
                //d[i] = H[i][i];
                d.setValue(i,0,H.getValue(i,i));
                //e[i] = 0.0;
                e.setValue(i,0,0);
            }
            for (int j = Math.max(i - 1, 0); j < nn; j++) {
                //norm = norm + Math.abs(H[i][j]);
                norm = norm + Math.abs(H.getValue(i,j));
            }
        }

        // Outer loop over eigenvalue index

        int iter = 0;
        while (n >= low) {

            // Look for single small sub-diagonal element

            int l = n;
            while (l > low) {
                //s = Math.abs(H[l - 1][l - 1]) + Math.abs(H[l][l]);
                s = Math.abs(H.getValue(l-1,l-1)) + Math.abs(H.getValue(l,l));
                if (s == 0.0) {
                    s = norm;
                }
                //if (Math.abs(H[l][l - 1]) < eps * s) {
                if (Math.abs(H.getValue(l,l-1)) < eps * s) {
                    break;
                }
                l--;
            }

            // Check for convergence
            // One root found

            if (l == n) {
                //H[n][n] = H[n][n] + exshift;
                H.setValue(n,n,H.getValue(n,n)+exshift);
                //d[n] = H[n][n];
                d.setValue(n,0,H.getValue(n,n));
                //e[n] = 0.0;
                e.setValue(n,0,0);
                n--;
                iter = 0;

                // Two roots found

            } else if (l == n - 1) {
                //w = H[n][n - 1] * H[n - 1][n];
                w = H.getValue(n,n-1)*H.getValue(n,n-1);
                //p = (H[n - 1][n - 1] - H[n][n]) / 2.0;
                p = (H.getValue(n-1,n-1) - H.getValue(n,n)) / 2.0;
                q = p * p + w;
                z = Math.sqrt(Math.abs(q));
                //H[n][n] = H[n][n] + exshift;
                H.setValue(n,n,H.getValue(n,n)+exshift);
                //H[n - 1][n - 1] = H[n - 1][n - 1] + exshift;
                H.setValue(n-1,n-1,H.getValue(n-1,n-1)+exshift);
                //x = H[n][n];
                x = H.getValue(n,n);

                // Real pair

                if (q >= 0) {
                    if (p >= 0) {
                        z = p + z;
                    } else {
                        z = p - z;
                    }
                    //d[n - 1] = x + z;
                    d.setValue(n-1,0,x+z);
                    //d[n] = d[n - 1];
                    d.setValue(n,0,d.getValue(n-1,0));
                    if (z != 0.0) {
                        //d[n] = x - w / z;
                        d.setValue(n,0,x-w/z);
                    }
                    //e[n - 1] = 0.0;
                    e.setValue(n-1,0,0);
                    //e[n] = 0.0;
                    e.setValue(n,0,0);
                    //x = H[n][n - 1];
                    x = H.getValue(n,n-1);
                    s = Math.abs(x) + Math.abs(z);
                    p = x / s;
                    q = z / s;
                    r = Math.sqrt(p * p + q * q);
                    p = p / r;
                    q = q / r;

                    // Row modification

                    for (int j = n - 1; j < nn; j++) {
                        //z = H[n - 1][j];
                        z = H.getValue(n-1,j);
                        //H[n - 1][j] = q * z + p * H[n][j];
                        H.setValue(n-1,j,q * z + p * H.getValue(n,j));
                        //H[n][j] = q * H[n][j] - p * z;
                        H.setValue(n,j,q * H.getValue(n,j) - p * z);
                    }

                    // Column modification

                    for (int i = 0; i <= n; i++) {
                        //z = H[i][n - 1];
                        z = H.getValue(i,n-1);
                        //H[i][n - 1] = q * z + p * H[i][n];
                        H.setValue(i,n-1,q * z + p * H.getValue(i,n));
                        //H[i][n] = q * H[i][n] - p * z;
                        H.setValue(i,n,q * H.getValue(i,n) - p * z);
                    }

                    // Accumulate transformations

                    for (int i = low; i <= high; i++) {
                        //z = V[i][n - 1];
                        z = V.getValue(i,n-1);
                        //V[i][n - 1] = q * z + p * V[i][n];
                        V.setValue(i,n-1,q * z + p * V.getValue(i,n));
                        //V[i][n] = q * V[i][n] - p * z;
                        V.setValue(i,n,q * V.getValue(i,n) - p * z);
                    }

                    // Complex pair

                } else {
                    //d[n - 1] = x + p;
                    d.setValue(n-1,0,x+p);
                    //d[n] = x + p;
                    d.setValue(n,0,x+p);
                    //e[n - 1] = z;
                    e.setValue(n-1,0,z);
                    //e[n] = -z;
                    e.setValue(n,1,-z);
                }
                n = n - 2;
                iter = 0;

                // No convergence yet

            } else {

                // Form shift

                //x = H[n][n];
                x = H.getValue(n,n);
                y = 0.0;
                w = 0.0;
                if (l < n) {
                    //y = H[n - 1][n - 1];
                    y = H.getValue(n-1,n-1);
                    //w = H[n][n - 1] * H[n - 1][n];
                    w = H.getValue(n,n-1) * H.getValue(n-1,n);
                }

                // Wilkinson's original ad hoc shift

                if (iter == 10) {
                    exshift += x;
                    for (int i = low; i <= n; i++) {
                        //H[i][i] -= x;
                        H.setValue(i,i,H.getValue(i,i)-x);;
                    }
                    //s = Math.abs(H[n][n - 1]) + Math.abs(H[n - 1][n - 2]);
                    s = Math.abs(H.getValue(n,n-1)) + Math.abs(H.getValue(n-1,n-2));
                    x = y = 0.75 * s;
                    w = -0.4375 * s * s;
                }

                // MATLAB's new ad hoc shift

                if (iter == 30) {
                    s = (y - x) / 2.0;
                    s = s * s + w;
                    if (s > 0) {
                        s = Math.sqrt(s);
                        if (y < x) {
                            s = -s;
                        }
                        s = x - w / ((y - x) / 2.0 + s);
                        for (int i = low; i <= n; i++) {
                            //H[i][i] -= s;
                            H.setValue(i,i,H.getValue(i,i)-s);
                        }
                        exshift += s;
                        x = y = w = 0.964;
                    }
                }

                iter = iter + 1;   // (Could check iteration count here.)

                // Look for two consecutive small sub-diagonal elements

                int m = n - 2;
                while (m >= l) {
                    //z = H[m][m];
                    z = H.getValue(m,m);
                    r = x - z;
                    s = y - z;
                    //p = (r * s - w) / H[m + 1][m] + H[m][m + 1];
                    p = (r * s - w) / H.getValue(m+1,m) + H.getValue(m,m+1);
                    //q = H[m + 1][m + 1] - z - r - s;
                    q = H.getValue(m+1,m+1) - z - r - s;
                    //r = H[m + 2][m + 1];
                    r = H.getValue(m+2,m+1);
                    s = Math.abs(p) + Math.abs(q) + Math.abs(r);
                    p = p / s;
                    q = q / s;
                    r = r / s;
                    if (m == l) {
                        break;
                    }
                    //if (Math.abs(H[m][m - 1]) * (Math.abs(q) + Math.abs(r)) <
                    if (Math.abs(H.getValue(m,m-1)) * (Math.abs(q) + Math.abs(r)) <
                            //eps * (Math.abs(p) * (Math.abs(H[m - 1][m - 1]) + Math.abs(z) +
                            //Math.abs(H[m + 1][m + 1])))) {
                            eps * (Math.abs(p) * (Math.abs(H.getValue(m-1,m-1)) + Math.abs(z) +
                            Math.abs(H.getValue(m+1,m+1))))) {
                        break;
                    }
                    m--;
                }

                for (int i = m + 2; i <= n; i++) {
                    //H[i][i - 2] = 0.0;
                    H.setValue(i,i-2,0);
                    if (i > m + 2) {
                        //H[i][i - 3] = 0.0;
                        H.setValue(i,i-3,0);
                    }
                }

                // Double QR step involving rows l:n and columns m:n

                for (int k = m; k <= n - 1; k++) {
                    boolean notlast = (k != n - 1);
                    if (k != m) {
                        //p = H[k][k - 1];
                        p = H.getValue(k,k-1);
                        //q = H[k + 1][k - 1];
                        q = H.getValue(k+1,k-1);
                        //r = (notlast ? H[k + 2][k - 1] : 0.0);
                        r = (notlast ? H.getValue(k+2,k-1): 0.0);
                        x = Math.abs(p) + Math.abs(q) + Math.abs(r);
                        if (x != 0.0) {
                            p = p / x;
                            q = q / x;
                            r = r / x;
                        }
                    }
                    if (x == 0.0) {
                        break;
                    }
                    s = Math.sqrt(p * p + q * q + r * r);
                    if (p < 0) {
                        s = -s;
                    }
                    if (s != 0) {
                        if (k != m) {
                            //H[k][k - 1] = -s * x;
                            H.setValue(k,k-1,-s * x);
                        } else if (l != m) {
                            //H[k][k - 1] = -H[k][k - 1];
                            H.setValue(k,k-1,-H.getValue(k,k-1));
                        }
                        p = p + s;
                        x = p / s;
                        y = q / s;
                        z = r / s;
                        q = q / p;
                        r = r / p;

                        // Row modification

                        for (int j = k; j < nn; j++) {
                            //p = H[k][j] + q * H[k + 1][j];
                            p = H.getValue(k,j) + q * H.getValue(k+1,j);
                            if (notlast) {
                                //p = p + r * H[k + 2][j];
                                p = p + r * H.getValue(k+2,j);
                                //H[k + 2][j] = H[k + 2][j] - p * z;
                                H.setValue(k+2,j,H.getValue(k+2,j) - p * z);
                            }
                            //H[k][j] = H[k][j] - p * x;
                            H.setValue(k,j,H.getValue(k,j)- p * x);
                            //H[k + 1][j] = H[k + 1][j] - p * y;
                            H.setValue(k+1,j,H.getValue(k+1,j)- p * y);
                        }

                        // Column modification

                        for (int i = 0; i <= Math.min(n, k + 3); i++) {
                            //p = x * H[i][k] + y * H[i][k + 1];
                            p = x * H.getValue(i,k) + y * H.getValue(i,k+1);
                            if (notlast) {
                                //p = p + z * H[i][k + 2];
                                p = p + z * H.getValue(i,k+2);
                                //H[i][k + 2] = H[i][k + 2] - p * r;
                                H.setValue(i,k+2,H.getValue(i,k+2) - p * r);
                            }
                            //H[i][k] = H[i][k] - p;
                            H.setValue(i,k,H.getValue(i,k)-p);
                            //H[i][k + 1] = H[i][k + 1] - p * q;
                            H.setValue(i,k+1,H.getValue(i,k+1)-p*q);
                        }

                        // Accumulate transformations

                        for (int i = low; i <= high; i++) {
                            //p = x * V[i][k] + y * V[i][k + 1];
                            p = x * V.getValue(i,k) + y * V.getValue(i,k+1);
                            if (notlast) {
                                //p = p + z * V[i][k + 2];
                                p = p + z * V.getValue(i,k+2);
                                //V[i][k + 2] = V[i][k + 2] - p * r;
                                V.setValue(i,k+2,V.getValue(i,k+2) - p * r);
                            }
                            //V[i][k] = V[i][k] - p;
                            V.setValue(i,k,V.getValue(i,k)-p);
                            //V[i][k + 1] = V[i][k + 1] - p * q;
                            V.setValue(i,k+1,V.getValue(i,k+1)-p* q);
                        }
                    }  // (s != 0)
                }  // k loop
            }  // check convergence
        }  // while (n >= low)

        // Backsubstitute to find vectors of upper triangular form

        if (norm == 0.0) {
            return;
        }

        for (n = nn - 1; n >= 0; n--) {
            //p = d[n];
            p = d.getValue(n,0);
            //q = e[n];
            q = e.getValue(n,0);

            // Real vector

            if (q == 0) {
                int l = n;
                //H[n][n] = 1.0;
                H.setValue(n,n,1);
                for (int i = n - 1; i >= 0; i--) {
                    //w = H[i][i] - p;
                    w = H.getValue(i,i) - p;
                    r = 0.0;
                    for (int j = l; j <= n; j++) {
                        //r = r + H[i][j] * H[j][n];
                        r = r + H.getValue(i,j)*H.getValue(j,n);
                    }
                    //if (e[i] < 0.0) {
                    if (e.getValue(i,0) < 0.0) {
                        z = w;
                        s = r;
                    } else {
                        l = i;
                        //if (e[i] == 0.0) {
                        if (e.getValue(i,0) == 0.0) {
                            if (w != 0.0) {
                                //H[i][n] = -r / w;
                                H.setValue(i,n,-r / w);
                            } else {
                                //H[i][n] = -r / (eps * norm);
                                H.setValue(i,n,-r / (eps * norm));
                            }

                            // Solve real equations

                        } else {
                            //x = H[i][i + 1];
                            x = H.getValue(i,i+1);
                            //y = H[i + 1][i];
                            y = H.getValue(i+1,1);
                            //q = (d[i] - p) * (d[i] - p) + e[i] * e[i];
                            q = (d.getValue(i,0) - p) * (d.getValue(i,0) - p) + e.getValue(i,0) * e.getValue(i,0);
                            t = (x * s - z * r) / q;
                            //H[i][n] = t;
                            H.setValue(i,n,t);
                            if (Math.abs(x) > Math.abs(z)) {
                                //H[i + 1][n] = (-r - w * t) / x;
                                H.setValue(i+1,n,(-r - w * t) / x);
                            } else {
                                //H[i + 1][n] = (-s - y * t) / z;
                                H.setValue(i+1,n,(-s - y * t) / z);
                            }
                        }

                        // Overflow control
                        //t = Math.abs(H[i][n]);
                        t = Math.abs(H.getValue(i,n));
                        if ((eps * t) * t > 1) {
                            for (int j = i; j <= n; j++) {
                                //H[j][n] = H[j][n] / t;
                                H.setValue(j,n,H.getValue(j,n)/t);
                            }
                        }
                    }
                }
                // Complex vector
            } else if (q < 0) {
                int l = n - 1;

                // Last vector component imaginary so matrix is triangular

                //if (Math.abs(H[n][n - 1]) > Math.abs(H[n - 1][n])) {
                if (Math.abs(H.getValue(n,n-1)) > Math.abs(H.getValue(n-1,n))) {
                    //H[n - 1][n - 1] = q / H[n][n - 1];
                    H.setValue(n-1,n-1,q / H.getValue(n,n-1));
                    //H[n - 1][n] = -(H[n][n] - p) / H[n][n - 1];
                     H.setValue(n-1,n,-(H.getValue(n,n) - p) / H.getValue(n,n-1));
                } else {
                    //cdiv(0.0, -H[n - 1][n], H[n - 1][n - 1] - p, q);
                    cdiv(0.0, -H.getValue(n-1,n), H.getValue(n-1,n-1) - p, q);
                    //H[n - 1][n - 1] = cdivr;
                   H.setValue(n-1,n-1,cdivr);
                   // H[n - 1][n] = cdivi;
                    H.setValue(n-1,n-1,cdivi);
                }
                //H[n][n - 1] = 0.0;
                H.setValue(n,n-1,0);
                //H[n][n] = 1.0;
                H.setValue(n,n,1);
                for (int i = n - 2; i >= 0; i--) {
                    double ra, sa, vr, vi;
                    ra = 0.0;
                    sa = 0.0;
                    for (int j = l; j <= n; j++) {
                        //ra = ra + H[i][j] * H[j][n - 1];
                        ra = ra + H.getValue(i,j) * H.getValue(j,n-1);
                        //sa = sa + H[i][j] * H[j][n];
                        sa = sa + H.getValue(i,j) * H.getValue(j,n);
                    }
                    //w = H[i][i] - p;
                    w = H.getValue(i,i) - p;

                    //if (e[i] < 0.0) {
                    if (e.getValue(i,0) < 0.0) {
                        z = w;
                        r = ra;
                        s = sa;
                    } else {
                        l = i;
                        //if (e[i] == 0) {
                        if (e.getValue(i,0) == 0) {
                            cdiv(-ra, -sa, w, q);
                            //H[i][n - 1] = cdivr;
                            H.setValue(i,n-1,cdivr);
                            //H[i][n] = cdivi;
                            H.setValue(i,n,cdivi);;
                        } else {

                            // Solve complex equations
                            //x = H[i][i + 1];
                            x = H.getValue(i,i+1);
                            //y = H[i + 1][i];
                            y = H.getValue(i+1,i);


                            //vr = (d[i] - p) * (d[i] - p) + e[i] * e[i] - q * q;
                            vr = (d.getValue(i,0) - p) * (d.getValue(i,0) - p) + e.getValue(i,0) * e.getValue(i,0) - q * q;
                            //vi = (d[i] - p) * 2.0 * q;
                            vi = (d.getValue(i,0) - p) * 2.0 * q;
                            if (vr == 0.0 & vi == 0.0) {
                                vr = eps * norm * (Math.abs(w) + Math.abs(q) +
                                        Math.abs(x) + Math.abs(y) + Math.abs(z));
                            }
                            cdiv(x * r - z * ra + q * sa, x * s - z * sa - q * ra, vr, vi);
                            //H[i][n - 1] = cdivr;
                            H.setValue(i,n-1,cdivr);
                            //H[i][n] = cdivi;
                            H.setValue(i,n,cdivi);;
                            if (Math.abs(x) > (Math.abs(z) + Math.abs(q))) {
                                //H[i + 1][n - 1] = (-ra - w * H[i][n - 1] + q * H[i][n]) / x;
                                 H.setValue(i+1,n-1,(-ra - w * H.getValue(i,n-1) + q * H.getValue(i,n)) / x);
                                //H[i + 1][n] = (-sa - w * H[i][n] - q * H[i][n - 1]) / x;
                                H.setValue(i+1,n,(-sa - w * H.getValue(i,n) - q * H.getValue(i,n-1)) / x);
                            } else {
                                cdiv(-r - y * H.getValue(i,n-1), -s - y * H.getValue(i,n), z, q);
                                //H[i + 1][n - 1] = cdivr;
                                H.setValue(i+1,n-1,cdivr);
                                //H[i + 1][n] = cdivi;
                                H.setValue(i+1,n,cdivi);
                            }
                        }

                        // Overflow control

                        //t = Math.max(Math.abs(H[i][n - 1]), Math.abs(H[i][n]));
                        t = Math.max(Math.abs(H.getValue(i,n-1)), Math.abs(H.getValue(i,n)));
                        if ((eps * t) * t > 1) {
                            for (int j = i; j <= n; j++) {
                                //H[j][n - 1] = H[j][n - 1] / t;
                                H.setValue(j,n-1,H.getValue(j,n-1) / t);
                                //H[j][n] = H[j][n] / t;
                                 H.setValue(j,n,H.getValue(j,n) / t);
                            }
                        }
                    }
                }
            }
        }

        // Vectors of isolated roots

        for (int i = 0; i < nn; i++) {
            if (i < low | i > high) {
                for (int j = i; j < nn; j++) {
                    //V[i][j] = H[i][j];
                    V.setValue(i,j,H.getValue(i,j));
                }
            }
        }

        // Back transformer to getValue eigenvectors of original matrix

        for (int j = nn - 1; j >= low; j--) {
            for (int i = low; i <= high; i++) {
                z = 0.0;
                for (int k = low; k <= Math.min(j, high); k++) {
                    //z = z + V[i][k] * H[k][j];
                    z = z + V.getValue(i,k) * H.getValue(k,j);
                }
                //V[i][j] = z;
                V.setValue(i,j,z);
            }
        }
    }


/* ------------------------
   Constructor
 * ------------------------ */

    /**
     * Check for symmetry, then construct the eigenvalue decomposition
     *
     * @param matrix Square matrix
     *            Structure to access D and V.
     */

    public Eigenvalue(Matrix matrix) {
        hint = matrix;
        MatrixAdaptor A = new MatrixAdaptor(MatrixFactory.getMatrixClone(matrix));
        n = matrix.cols();
        V = new MatrixAdaptor(MatrixFactory.getMatrix(n,n,matrix));
        d = new MatrixAdaptor(MatrixFactory.getMatrix(n,1,matrix));;
        e = new MatrixAdaptor(MatrixFactory.getMatrix(n,1,matrix));;


        /*issymmetric = true;
        for (int j = 0; (j < n) & issymmetric; j++) {
            for (int i = 0; (i < n) & issymmetric; i++) {
                issymmetric = (A[i][j] == A[j][i]);
            }
        } */

        //if (issymmetric) {
        if (Util.isSymmetric(matrix)) {  //A = A'
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    V.setValue(i,j,A.getValue(i,j));
                }
            }

            // Tridiagonalize.
            tred2();

            // Diagonalize.
            tql2();

        } else {
            H = new MatrixAdaptor(MatrixFactory.getMatrix(n,n,matrix));;
            ort = new MatrixAdaptor(MatrixFactory.getMatrix(n,1,matrix));;

            for (int j = 0; j < n; j++) {
                for (int i = 0; i < n; i++) {
                    H.setValue(i,j,A.getValue(i,j));
                }
            }

            // Reduce to Hessenberg form.
            orthes();

            // Reduce Hessenberg to real Schur form.
            hqr2();
        }
    }

/* ------------------------
   Public Methods
 * ------------------------ */

    /**
     * Return the eigenvector matrix
     *
     * @return V
     */

    public Matrix getV() {
        return V.getAdaptee();
    }

    /**
     * Return the real parts of the eigenvalues
     * <p/>
     * todo a way to test eigen values is -> sum of eigenvalues = getTrace of the matrix and product of eigenvalues = detrminant of the matrix
     *
     * @return real(diag(D))
     */

    public Matrix getRealEigenvalues() {
        return d.getAdaptee();
    }

    /**
     * Return the imaginary parts of the eigenvalues
     *
     * @return imag(diag(D))
     */

    public Matrix getImagEigenvalues() {
        return e.getAdaptee();
    }

    /**
     * Return the block diagonal eigenvalue matrix
     *
     * @return D
     */

    public Matrix getD() {
        MatrixAdaptor D = new MatrixAdaptor(MatrixFactory.getMatrix(n,n,null));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //D[i][j] = 0.0;
                D.setValue(i,j,0);
            }
            //D[i][i] = d[i];
            D.setValue(i,i,d.getValue(i,0));
            //if (e[i] > 0) {
            if (e.getValue(i,0) > 0) {
                //D[i][i + 1] = e[i];
                D.setValue(i,i+1,e.getValue(i,0));
            //} else if (e[i] < 0) {
            } else if (e.getValue(i,0) < 0) {
                D.setValue(i,i-1,e.getValue(i,0));
            }
        }
        return D.getAdaptee();
    }
}
