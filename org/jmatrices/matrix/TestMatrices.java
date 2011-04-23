package org.jmatrices.matrix;

import org.jmatrices.matrix.decomposition.LUDecomposition;
import org.jmatrices.matrix.decomposition.SingularValueDecomposition;
import org.jmatrices.matrix.measure.MatrixMeasure;
import org.jmatrices.matrix.measure.MatrixProperty;
import org.jmatrices.matrix.operator.MatrixOperator;
import org.jmatrices.matrix.rowcoltr.ColumnTransformer;
import org.jmatrices.matrix.rowcoltr.RowTransformer;
import org.jmatrices.matrix.transformer.MatrixEBETransformation;
import org.jmatrices.matrix.transformer.MatrixEBETransformer;
import org.jmatrices.matrix.transformer.MatrixTransformer;

/**
 * TestMatrices
 * <p>
 * <font color="blue">
 * todo - This is the reason why we need a Junit test suite!!
 * </font>
 * </p>
 * <p>
 * Author: purangp
 * </p>
 * Date: 07.03.2004
 * Time: 16:49:52
 */
public class TestMatrices {

    public static void main(String[] args) {
        //TestMatrices.testTransformations();
        //TestMatrices.testTranspose();
        //TestMatrices.testMultiply();
        //TestMatrices.testConcatenation();
        //TestMatrices.testColumnOperators();
        //TestMatrices.testRowOperators();
        //TestMatrices.testMatrixMeasures();
        //TestMatrices.testDiagonalTrace();
        //TestMatrices.testHeavyMatrix();
        //TestMatrices.testGet();
        //TestMatrices.testPow();
        //TestMatrices.testIsSymetric();
        //TestMatrices.testIsIdempotent();
        //TestMatrices.testTrace();
        //TestMatrices.testSubmatrix();
        //TestMatrices.testLUDecomposition();
        //TestMatrices.testDeterminant();
        //TestMatrices.testIdentityMatrix();
        //TestMatrices.testSolve();
        //TestMatrices.testInverse();
        //TestMatrices.testSVD();
        TestMatrices.testIsScalarMatrix();
    }

    private static void testIsScalarMatrix() {
        System.out.println(MatrixProperty.isScalar(get22Scalar()));
        System.out.println(MatrixProperty.isScalar(get33NotScalar()));
    }

    private static Matrix get22Scalar() {
        double[][] elems = {{2, 0, },
                            {0, 2, }, };
        return MatrixFactory.getMatrix(2, 2, elems);
    }

    private static Matrix get33NotScalar() {
        double[][] elems = {{2, 0, 0, },
                            {0, 2, 0},
                            {0, 0, 0}, };
        return MatrixFactory.getMatrix(3, 3, elems);
    }

    /**
     * The results are mixed...
     * When we compare it with solutions given on the webpage http://web.mit.edu/be.400/www/SVD/Singular_Value_Decomposition.htm
     * Everything is fine except U's 3,2 and 4,2 values do not match and S's dimensions don't agree [played around with code to rectify the dimension of S]
     * <p/>
     * When we compare it with matlab nothing seems to agree except cond(), rank() and norm2();
     */


    private static void testSVD() {
        testSVD(new SingularValueDecomposition(get42SVD()));
        System.out.println("==========");
        testSVD(new SingularValueDecomposition(get22Cond()));
    }

    private static void testSVD(SingularValueDecomposition svd) {
        System.out.println(svd.getU());
        System.out.println(svd.getS());   //doesn't agree in dimensions with matlab
        System.out.println(svd.getV());
        System.out.println(svd.rank());  // 2 agrees with matlab
        System.out.println(svd.cond());   //14.9330 agrees with matlab solution
        System.out.println(svd.norm2());  //1.58 agrees with matlab solution

    }

    private static Matrix get22Cond() {
        double[][] elems = {{1.2969, 0.8648, },
                            {0.2161, 0.1414, }, };
        return MatrixFactory.getMatrix(2, 2, elems);
    }

    //the following matrix cortusey ..
    //http://web.mit.edu/be.400/www/SVD/Singular_Value_Decomposition.htm
    private static Matrix get42SVD() {
        double[][] elems = {{2, 4, },
                            {1, 3, },
                            {0, 0, },
                            {0, 0, }, };
        return MatrixFactory.getMatrix(4, 2, elems);
    }

    private static void testIdentityMatrix() {
        System.out.println(MatrixFactory.getIdentityMatrix(get33X().rows()));
    }

    private static void testSolve() {
        Matrix solution = MatrixOperator.solve(get33X(), get31B());
        System.out.println(solution);
        System.out.println(MatrixOperator.multiply(MatrixTransformer.inverse(get33X()), get31B()));
        System.out.println("");
    }

    private static void testInverse() {
        System.out.println("");
        System.out.println(MatrixTransformer.inverse(get33X()));
    }


    private static void testLUDecomposition() {
        LUDecomposition lu = new LUDecomposition(get33X());
        System.out.println(lu.getL());
        System.out.println(lu.getU());
        System.out.println(lu.isNonsingular());
        System.out.println(lu.getPivotMatrix());
    }

    private static Matrix get31B() {
        double[][] elems = {{14},
                            {3},
                            {8}, };
        return MatrixFactory.getMatrix(3, 1, elems);
    }

    private static Matrix get33X() {
        double[][] elems = {{1, 2, 3, },
                            {2, -1, 1, },
                            {3, 4, -1, }, };
        return MatrixFactory.getMatrix(3, 3, elems);
    }

    private static void testDeterminant() {
        System.out.println(MatrixMeasure.getDeterminant(getIdempotentMatrix()));
        System.out.println(MatrixMeasure.getDeterminant(get33X()));
        System.out.println(MatrixMeasure.getDeterminant(get55Matrix()));
        System.out.println(MatrixMeasure.getDeterminant(getSymetricMatrix()));
    }

    private static void testSubmatrix() {
        testSubmatrix(get55Matrix());
        System.out.println("@@@@@@@@@@@@@@@@@@@@HeavyMatrix@@@@@@@@@@@@@@@@@@@@@@@@@@@@@q");
        testSubmatrix(new HeavyMatrixImpl(5, 5, get55Matrix().get()));
    }

    private static void testSubmatrix(Matrix matrix) {
        System.out.println("1,1 -> 5,5");
        System.out.println(matrix.getSubMatrix(1, 1, 5, 5));
        System.out.println("1,1 -> 1,1");
        System.out.println(matrix.getSubMatrix(1, 1, 1, 1));
        System.out.println("2,2 -> 5,5");
        System.out.println(matrix.getSubMatrix(2, 2, 5, 5));
        System.out.println("2,2 -> 4,5");
        System.out.println(matrix.getSubMatrix(2, 2, 4, 5));

        System.out.println("{1,3,4} -> {2,4,5}");
        System.out.println(matrix.getSubMatrix(new int[]{1, 3, 4}, new int[]{2, 4, 5}));

        System.out.println("1,1 -> {2,4}");
        System.out.println(matrix.getSubMatrix(1, 1, new int[]{2, 4}));

        System.out.println("2,3 -> {1,4,5}");
        System.out.println(matrix.getSubMatrix(2, 3, new int[]{1, 4, 5}));

        System.out.println("{1,3,5} -> 3,5");
        System.out.println(matrix.getSubMatrix(new int[]{1, 3, 5}, 3, 5));
    }


    private static Matrix get55Matrix() {
        double[][] elems = {{11, 12, 13, 14, 15, },
                            {21, 22, 23, 24, 25, },
                            {31, 32, 33, 34, 35, },
                            {41, 42, 43, 44, 45, },
                            {51, 52, 53, 54, 55, }, };
        return MatrixFactory.getMatrix(5, 5, elems);
    }

    private static Matrix get35Matrix() {
        double[][] elems = {{11, 12, 13, 14, 15, },
                            {21, 22, 23, 24, 25, },
                            {31, 32, 33, 34, 35, }, };
        return MatrixFactory.getMatrix(3, 5, elems);
    }

    private static Matrix get53Matrix() {
        double[][] elems = {{11, 12, 13, },
                            {21, 22, 23, },
                            {31, 32, 33, },
                            {41, 42, 43, },
                            {51, 52, 53, }};
        return MatrixFactory.getMatrix(5, 3, elems);
    }


    private static void testTrace() {
        // works on the fact that getTrace(AB) = getTrace(BA) where A mxn and B nxm matrices
        Matrix a = getIdempotentMatrix(), b = getSymetricMatrix();
        System.out.println(MatrixMeasure.getTrace(MatrixOperator.multiply(a, b)) == MatrixMeasure.getTrace(MatrixOperator.multiply(b, a)));
    }

    private static void testIsIdempotent() {
        System.out.println(MatrixProperty.isIdempotent(getIdempotentMatrix()));
    }

    private static Matrix getIdempotentMatrix() {
        double[][] elems = {{1 / 6, -1 / 3, 1 / 6, },
                            {-1 / 3, 2 / 3, -1 / 3, },
                            {1 / 6, -1 / 3, 1 / 6, }, };
        return MatrixFactory.getMatrix(3, 3, elems);
    }

    private static void testIsSymetric() {
        System.out.println(MatrixProperty.isSymmetric(getSymetricMatrix()));
    }

    private static Matrix getSymetricMatrix() {
        double[][] elems = {{1, 5, 6, },
                            {5, 2, 0, },
                            {6, 0, -4, }, };
        return MatrixFactory.getMatrix(3, 3, elems);
    }

    private static void testPow() {
        Matrix m = MatrixFactory.getMatrix(3, 3, 2);
        System.out.println(MatrixTransformer.pow(m, 0));
        System.out.println(MatrixTransformer.pow(m, 1));  //2
        System.out.println(MatrixTransformer.pow(m, 2));  //12
        System.out.println(MatrixTransformer.pow(m, 3));  //72
        //System.out.println(MatrixTransformer.pow(m,10));
    }

    private static void testGet() {
        Matrix m1 = MatrixFactory.getMatrix(3, 4);
        double[][] store = m1.get();
        store[0][0] = 1;
        System.out.println(m1);
        System.out.println(MatrixFactory.getMatrix(3, 4, store));

    }

    private static void testHeavyMatrix() {
        HeavyMatrixImpl hm = new HeavyMatrixImpl(3, 2);
        hm.set(1, 1, 1);
        hm.set(1, 2, 4);
        hm.set(2, 1, 2);
        hm.set(2, 2, 5);
        hm.set(3, 1, 3);
        hm.set(3, 2, 6);
        testHeavyMatrix(hm);


    }

    private static void testHeavyMatrix(HeavyMatrixImpl hm) {
        System.out.println(hm);
        Matrix row1 = hm.getRow(1);
        System.out.println(row1);
        System.out.println(hm.getColumn(1));
        System.out.println(hm.getRow(3));
        System.out.println(hm.getColumn(2));
    }

    private static void testTransformations() {
        testScalarTransformation();
        testFunctionTransformation();
    }

    private static void testScalarTransformation() {
        Matrix m = MatrixFactory.getMatrix(3, 3);
        int s = 3;

        final int sf = s;
        Matrix mi = MatrixEBETransformer.ebeTransform(m, new MatrixEBETransformation() {
            public double transform(double element) {
                return element + sf; //could be +, *, -, /
            }
        });
        System.out.println(mi);
    }

    private static void testFunctionTransformation() {
        Matrix m = MatrixFactory.getMatrix(3, 3);
        Matrix mi = MatrixEBETransformer.ebeTransform(m, new MatrixEBETransformation() {
            public double transform(double element) {
                return Math.cos(element);
            }
        });
        System.out.println(mi);
    }

    private static void testTranspose() {
        Matrix m = MatrixFactory.getMatrix(2, 3);
        ((LightMatrixImpl) m).toString();
        ((LightMatrixImpl) MatrixTransformer.transpose(m)).toString();
    }

    private static void testMultiply() {
        Matrix a = MatrixFactory.getMatrix(3, 4);
        a.set(1, 1, 1);
        a.set(1, 4, 3);
        a.set(2, 1, 4);
        a.set(2, 2, 5);
        a.set(3, 3, 4);
        a.set(3, 4, 5);

        Matrix b = MatrixFactory.getMatrix(4, 3);
        b.set(1, 1, 3);
        b.set(4, 1, 7);
        b.set(4, 2, 8);
        b.set(4, 3, 9);

        ((LightMatrixImpl) MatrixOperator.multiply(a, b)).toString();

    }

    private static void testConcatenation() {
        Matrix a = MatrixFactory.getMatrix(3, 3);
        Matrix b = MatrixFactory.getMatrix(3, 4);
        ((LightMatrixImpl) MatrixOperator.horizontalConcatenation(a, b)).toString();

        Matrix c = MatrixFactory.getMatrix(3, 4);
        Matrix d = MatrixFactory.getMatrix(5, 4);
        ((LightMatrixImpl) MatrixOperator.verticalConcatenation(c, d)).toString();

    }

    private static void testColumnOperators() {
        Matrix a = MatrixFactory.getMatrix(3, 4);
        a.set(1, 1, 1);
        a.set(1, 4, 3);
        a.set(2, 1, 4);
        a.set(2, 2, 5);
        a.set(2, 4, -5);
        ;
        a.set(2, 3, -5);
        a.set(3, 3, 4);
        a.set(3, 4, 5);
        ((LightMatrixImpl) a).toString();
        ((LightMatrixImpl) ColumnTransformer.sum(a)).toString();
        ((LightMatrixImpl) ColumnTransformer.product(a)).toString();

        ((LightMatrixImpl) ColumnTransformer.max(a)).toString();
        ((LightMatrixImpl) ColumnTransformer.min(a)).toString();

        ((LightMatrixImpl) ColumnTransformer.mean(a, false)).toString();
        ((LightMatrixImpl) ColumnTransformer.mean(a, true)).toString();

    }


    private static void testRowOperators() {
        Matrix a = MatrixFactory.getMatrix(3, 4);
        a.set(1, 1, 1);
        a.set(1, 4, 3);
        a.set(2, 1, 4);
        a.set(2, 2, 5);
        a.set(2, 4, -5);
        ;
        a.set(2, 3, -5);
        a.set(3, 3, 4);
        a.set(3, 4, 5);
        ((LightMatrixImpl) a).toString();
        ((LightMatrixImpl) RowTransformer.sum(a)).toString();
        ((LightMatrixImpl) RowTransformer.product(a)).toString();

        ((LightMatrixImpl) RowTransformer.max(a)).toString();
        ((LightMatrixImpl) RowTransformer.min(a)).toString();

        ((LightMatrixImpl) RowTransformer.mean(a, false)).toString();
        ((LightMatrixImpl) RowTransformer.mean(a, true)).toString();

    }

    private static void testMatrixMeasures() {
        Matrix a = MatrixFactory.getMatrix(2, 2);
        a.set(1, 1, 2);
        a.set(1, 2, 3);
        a.set(2, 1, 2);
        a.set(2, 2, 3);
        ((LightMatrixImpl) a).toString();
        System.out.println(MatrixMeasure.getMax(a));
        System.out.println(MatrixMeasure.getMin(a));
        System.out.println(MatrixMeasure.getSum(a));
        System.out.println(MatrixMeasure.getProduct(a));
        System.out.println(MatrixMeasure.getMean(a, false));
        System.out.println(MatrixMeasure.getMean(a, true));
    }

    private static void testDiagonalTrace() {
        Matrix a = MatrixFactory.getMatrix(2, 3);
        a.set(1, 1, 1);
        a.set(1, 2, 0);
        a.set(1, 3, 1);
        a.set(2, 1, 0);
        a.set(2, 2, 1);
        a.set(2, 3, 1);
        ((LightMatrixImpl) a).toString();
        ((LightMatrixImpl) MatrixTransformer.diagonal(a)).toString();
        System.out.println(MatrixMeasure.getTrace(a));
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