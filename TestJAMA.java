import Jama.*;
/**
 * TestJAMA
 * <br/>
 * Author: purangp
 * <br/>
 * Date: 13.03.2004
 * Time: 12:29:37
 */
public class TestJAMA {
    public static void main(String[] args) {
            //double[][] vals = {{1.,2.,3},{4.,5.,6.},{7.,8.,10.}};
        double[][] elems = {{2, 4, },
                            {1, 3,},
                            {0, 0,},
                            {0, 0,},};

        testJama(elems);

        }

    public static void testJama(double[][] values) {
        Matrix A = new Matrix(values);
            System.out.println("The Matrix A is ");
            A.print(5, 1);
            System.out.println();

            Matrix b = Matrix.random(3,1);
            System.out.println("The vector b is ");
            b.print(5, 1);
            System.out.println();

            /*Matrix x = A.solve(b);
            System.out.println("The solution to Ax = b is ");
            x.print(5, 1);
            System.out.println();

            Matrix r = A.times(x).minus(b);
            double rnorm = r.normInf();
            System.out.println("Infinity norm of residual is " + rnorm); */
        

            System.out.println("The SVD of A is ");
            SingularValueDecomposition svd = A.svd();
            Matrix S = svd.getS();
            System.out.println("S is ");
            S.print(5, 5);
            System.out.println();
            Matrix U = svd.getU();
            System.out.println("U is ");
            U.print(5, 5);
            System.out.println();
            Matrix V = svd.getV();
            System.out.println("V is ");
            V.print(5, 5);
            System.out.println();

            Matrix W = (U.times(S)).times(V.transpose());
            System.out.println("W = U*S*V' is ");
            W.print(5, 1);
            System.out.println();

    }

}
