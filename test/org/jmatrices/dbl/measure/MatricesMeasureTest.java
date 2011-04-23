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
public class MatricesMeasureTest {
    
    @Test
    public void testAreEqual() {
        Matrix a = MatrixFactory.getIdentityMatrix(3);
        Matrix b = MatrixFactory.getMatrix(3, 3, null);
        b.setValue(1, 1, 1);
        b.setValue(1, 2, 0);
        b.setValue(1, 3, 0);
        b.setValue(2, 1, 0);
        b.setValue(2, 2, 1);
        b.setValue(2, 3, 0);
        b.setValue(3, 1, 0);
        b.setValue(3, 2, 0);
        b.setValue(3, 3, 1);
        assert MatricesMeasure.areEqual(a, b) : "Identity matrices of the same dimension are equal";
        assert !MatricesMeasure.areEqual(a, MatrixFactory.getIdentityMatrix(4)) : "Identity matrices of different dimension aren't equal";
    }

    @Test
    public void testAreApproximatelyEqual() {
        Matrix a = MatrixFactory.getMatrix(3, 3, null);
        a.setValue(1, 1, 0.001);
        a.setValue(1, 2, 0.002);
        a.setValue(1, 3, 0.003);
        a.setValue(2, 1, 0.004);
        a.setValue(2, 2, 0.005);
        a.setValue(2, 3, 0.006);
        a.setValue(3, 1, 0.007);
        a.setValue(3, 2, 0.008);
        a.setValue(3, 3, 0.009);
        Matrix b = MatrixFactory.getMatrix(3, 3, null);
        b.setValue(1, 1, 0.0009);
        b.setValue(1, 2, 0.0019);
        b.setValue(1, 3, 0.0029);
        b.setValue(2, 1, 0.0039);
        b.setValue(2, 2, 0.0049);
        b.setValue(2, 3, 0.0059);
        b.setValue(3, 1, 0.0069);
        b.setValue(3, 2, 0.0071);
        b.setValue(3, 3, 0.0091);
        assert MatricesMeasure.areApproximatelyEqual(a, b, 0.0001);
    }

    @Test
    public void testAreSameDimension() {
        Matrix colAsBreath = MatrixFactory.getMatrix(4, 3, null);
        Matrix rowAsBreath = MatrixFactory.getMatrix(3, 4, null);
        Matrix sqrBreath = MatrixFactory.getMatrix(4, 4, null);
        Matrix sqrIdentity = MatrixFactory.getIdentityMatrix(4);
        assert !MatricesMeasure.areSameDimension(colAsBreath, rowAsBreath) : "Aren't of equal dimensions";
        assert MatricesMeasure.areSameDimension(sqrBreath, sqrIdentity) : "Are of equal dimensions";
    }

    @Test(expectedExceptions = {java.lang.IllegalArgumentException.class})
    public void testDotProduct() {
        Matrix a = MatrixFactory.getIdentityMatrix(3);
        assert MatricesMeasure.dotProduct(a, a) == 3 : "Dot product of identity matrices is equal to the dimension of the matrix";
        MatricesMeasure.dotProduct(a, MatrixFactory.getIdentityMatrix(4)); //"this should throw an IllegalArgumentException"
    }

}
