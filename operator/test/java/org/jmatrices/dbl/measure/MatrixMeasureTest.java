package org.jmatrices.dbl.measure;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;
import org.testng.annotations.Test;

/**
 * MatrixMeasureTest.
 *
 * @author ppurang
 *         created 09.04.2007 21:10:23
 */
@Test(groups = {"jmatrices.measure"})
public class MatrixMeasureTest {
    private static final Matrix TEST_MATRIX_3_3 = MatrixFactory.getMatrix(3, 3, null);

    private static final double VALUE_11 = 0.950129285147175;
    private static final double VALUE_12 = 0.48598246870930;
    private static final double VALUE_13 = 0.456467665168341;
    private static final double VALUE_21 = 0.231138513574288;
    private static final double VALUE_22 = 0.891298966148902;
    private static final double VALUE_23 = 0.0185036432482244;
    private static final double VALUE_31 = 0.606842583541787;
    private static final double VALUE_32 = 0.762096833027395;
    private static final double VALUE_33 = 0.821407164295253;
    private static final double PRODUCT_VALUES = VALUE_11 * VALUE_12 * VALUE_13
            * VALUE_21 * VALUE_22 * VALUE_23
            * VALUE_31 * VALUE_32 * VALUE_33;


    static {
        TEST_MATRIX_3_3.setValue(1, 1, VALUE_11);
        TEST_MATRIX_3_3.setValue(1, 2, VALUE_12);
        TEST_MATRIX_3_3.setValue(1, 3, VALUE_13);
        TEST_MATRIX_3_3.setValue(2, 1, VALUE_21);
        TEST_MATRIX_3_3.setValue(2, 2, VALUE_22);
        TEST_MATRIX_3_3.setValue(2, 3, VALUE_23);
        TEST_MATRIX_3_3.setValue(3, 1, VALUE_31);
        TEST_MATRIX_3_3.setValue(3, 2, VALUE_32);
        TEST_MATRIX_3_3.setValue(3, 3, VALUE_33);
    }

    @Test
    public void testBreadth() {
        Matrix colAsBreath = MatrixFactory.getMatrix(4, 3, null);
        Matrix rowAsBreath = MatrixFactory.getMatrix(3, 4, null);
        Matrix sqrBreath = MatrixFactory.getMatrix(4, 4, null);

        assert MatrixMeasure.breadth(colAsBreath) == 3 : String.format("Breadth should be 3 and not %d", MatrixMeasure.breadth(colAsBreath));
        assert MatrixMeasure.breadth(rowAsBreath) == 3 : String.format("Breadth should be 3 and not %d", MatrixMeasure.breadth(rowAsBreath));
        assert MatrixMeasure.breadth(sqrBreath) == 4 : String.format("Breadth should be 4 and not %d", MatrixMeasure.breadth(sqrBreath));
    }

    @Test
    public void testLength() {
        Matrix colAsBreath = MatrixFactory.getMatrix(4, 3, null);
        Matrix rowAsBreath = MatrixFactory.getMatrix(3, 4, null);
        Matrix sqrBreath = MatrixFactory.getMatrix(4, 4, null);

        assert MatrixMeasure.length(colAsBreath) == 4 : String.format("Length should be 4 and not %d", MatrixMeasure.length(colAsBreath));
        assert MatrixMeasure.length(rowAsBreath) == 4 : String.format("Length should be 4 and not %d", MatrixMeasure.length(rowAsBreath));
        assert MatrixMeasure.length(sqrBreath) == 4 : String.format("Length should be 4 and not %d", MatrixMeasure.length(sqrBreath));
    }

    @Test
    public void testGetNormInfinity() {
        Matrix a = MatrixFactory.getMatrix(1, 1, null);
        a.setValue(1, 1, 30);
        assert MatrixMeasure.getNormInfinity(a) == 30 : String.format("Norm Infinity should be 30 and not %f", MatrixMeasure.getNormInfinity(a));

        Matrix b = MatrixFactory.getMatrix(2, 1, null);
        b.setValue(1, 1, 30);
        b.setValue(2, 1, 40);
        assert MatrixMeasure.getNormInfinity(b) == 40 : String.format("Norm Infinity should be 40 and not %f", MatrixMeasure.getNormInfinity(b));

        Matrix c = MatrixFactory.getMatrix(2, 12, null);
        c.setValue(1, 1, 30);
        c.setValue(1, 2, 30);
        c.setValue(2, 1, 40);
        c.setValue(2, 2, 40);
        assert MatrixMeasure.getNormInfinity(c) == 80 : String.format("Norm Infinity should be 80 and not %f", MatrixMeasure.getNormInfinity(c));

    }

    @Test
    public void testDeterminant() {
        assert org.jmatrices.dbl.Math.equalsApproximately(MatrixMeasure.getDeterminant(TEST_MATRIX_3_3), 0.428912016642189) : String.format("Determinant should be 0.428912016642189 and not %10.20f", MatrixMeasure.getDeterminant(TEST_MATRIX_3_3));
        Matrix c = MatrixFactory.getIdentityMatrix(5);
        assert MatrixMeasure.getDeterminant(c) == 1 : "The determinant of an identity matrix is 1";
        Matrix a = MatrixFactory.getMatrix(3, 3, null);
        a.setValue(1, 1, 3);
        a.setValue(1, 2, 2);
        a.setValue(1, 3, 1);
        a.setValue(2, 1, 0);
        a.setValue(2, 2, 1);
        a.setValue(2, 3, -2);
        a.setValue(3, 1, 1);
        a.setValue(3, 2, 3);
        a.setValue(3, 3, 4);
        assert MatrixMeasure.getDeterminant(a) == 25 : String.format("Determinant should be 25 and not %f", MatrixMeasure.getDeterminant(a));
    }

    @Test
    public void testTrace() {
        assert org.jmatrices.dbl.Math.equalsApproximately(MatrixMeasure.getTrace(TEST_MATRIX_3_3), 2.66283541559133) : String.format("Trace should be 2.66283541559133 and not %10.20f", MatrixMeasure.getTrace(TEST_MATRIX_3_3));
        Matrix c = MatrixFactory.getIdentityMatrix(5);
        assert MatrixMeasure.getTrace(c) == 5 : "The trace of an identity matrix is 1*dimension";
        Matrix a = MatrixFactory.getMatrix(1, 1, null);
        a.setValue(1, 1, 30);
        assert MatrixMeasure.getTrace(a) == 30 : String.format("Trace should be 30 and not %f", MatrixMeasure.getTrace(a));

    }

    @Test
    public void testRank() {
        assert MatrixMeasure.getRank(TEST_MATRIX_3_3) == 3 : String.format("Rank should be 3 and not %d", MatrixMeasure.getRank(TEST_MATRIX_3_3));
    }

    @Test
    public void testMax() {
        Matrix c = MatrixFactory.getIdentityMatrix(10);
        assert MatrixMeasure.getMax(c) == 1 : "The max of an identity matrix is 1";
        Matrix a = MatrixFactory.getMatrix(1, 1, null);
        a.setValue(1, 1, 30);
        assert MatrixMeasure.getMax(a) == 30 : String.format("Max should be 30 and not %f", MatrixMeasure.getMax(a));
        Matrix b = MatrixFactory.getMatrix(3, 3, null);
        b.setValue(1, 1, 0);
        b.setValue(1, 2, 1);
        b.setValue(1, 3, 15);
        b.setValue(2, 1, 15);
        b.setValue(2, 2, 16);
        b.setValue(2, 3, 17);
        b.setValue(3, 1, 17);
        b.setValue(3, 2, 18);
        b.setValue(3, 3, 17);
        assert MatrixMeasure.getMax(b) == 18 : String.format("Max should be 18 and not %f", MatrixMeasure.getMax(b));

    }


    @Test
    public void testMin() {
        Matrix c = MatrixFactory.getIdentityMatrix(10);
        assert MatrixMeasure.getMin(c) == 0 : "The min of an identity matrix is 0";
        Matrix a = MatrixFactory.getMatrix(1, 1, null);
        a.setValue(1, 1, 30);
        assert MatrixMeasure.getMin(a) == 30 : String.format("Min should be 30 and not %f", MatrixMeasure.getMin(a));
        Matrix b = MatrixFactory.getMatrix(3, 3, null);
        b.setValue(1, 1, 20);
        b.setValue(1, 2, 21);
        b.setValue(1, 3, 15);
        b.setValue(2, 1, 15);
        b.setValue(2, 2, 16);
        b.setValue(2, 3, 17);
        b.setValue(3, 1, 17);
        b.setValue(3, 2, 18);
        b.setValue(3, 3, 17);
        assert MatrixMeasure.getMin(b) == 15 : String.format("Max should be 15 and not %f", MatrixMeasure.getMin(b));

    }


    @Test
    public void testSum() {
        Matrix c = MatrixFactory.getIdentityMatrix(10);
        assert MatrixMeasure.getSum(c) == 10 : "The sum of an identity matrix is dim*1";
        Matrix a = MatrixFactory.getMatrix(1, 1, null);
        a.setValue(1, 1, 30);
        assert MatrixMeasure.getSum(a) == 30 : String.format("Sum should be 30 and not %f", MatrixMeasure.getSum(a));
        Matrix b = MatrixFactory.getMatrix(3, 3, null);
        b.setValue(1, 1, 20);
        b.setValue(1, 2, 21);
        b.setValue(1, 3, 15);
        b.setValue(2, 1, 15);
        b.setValue(2, 2, 16);
        b.setValue(2, 3, 17);
        b.setValue(3, 1, 17);
        b.setValue(3, 2, 18);
        b.setValue(3, 3, 17);
        double sum = (20 + 21 + (2 * 15) + 16 + (3 * 17) + 18);
        assert MatrixMeasure.getSum(b) == sum : String.format("Sum should be %f and not %f", sum, MatrixMeasure.getSum(b));

    }

    //@Test  todo find out why the product isn't being respected?
    @Test(enabled = false)
    public void testProduct() {
        //assert org.jmatrices.dbl.Math.equalsApproximately(MatrixMeasure.getProduct(TEST_MATRIX_3_3), 0.000305218591078338) : String.format("Product should be 0.000305218591078338 and not %10.20f", MatrixMeasure.getProduct(TEST_MATRIX_3_3));
        assert org.jmatrices.dbl.Math.equalsApproximately(MatrixMeasure.getProduct(TEST_MATRIX_3_3), PRODUCT_VALUES) : String.format("Product should be 0.000305218591078338 and not %10.20f", MatrixMeasure.getProduct(TEST_MATRIX_3_3));
        Matrix c = MatrixFactory.getIdentityMatrix(10);
        assert MatrixMeasure.getProduct(c) == 0 : "The product of an identity matrix is 0";
        Matrix a = MatrixFactory.getMatrix(1, 1, null);
        a.setValue(1, 1, 30);
        assert MatrixMeasure.getProduct(a) == 30 : String.format("Product should be 30 and not %f", MatrixMeasure.getProduct(a));
        Matrix b = MatrixFactory.getMatrix(3, 3, null);
        b.setValue(1, 1, 20);
        b.setValue(1, 2, 21);
        b.setValue(1, 3, 15);
        b.setValue(2, 1, 15);
        b.setValue(2, 2, 16);
        b.setValue(2, 3, 17);
        b.setValue(3, 1, 17);
        b.setValue(3, 2, 18);
        b.setValue(3, 3, 17);
        double product = 20 * 21 * 15 * 15 * 16 * 17 * 17 * 17 * 18;
        assert MatrixMeasure.getProduct(b) == product : String.format("Product should be %f and not %f", product, MatrixMeasure.getProduct(b));
    }
}
