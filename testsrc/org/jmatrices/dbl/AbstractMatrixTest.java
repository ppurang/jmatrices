package org.jmatrices.dbl;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * AbstractMatrixTest
 *
 * @author purangp
 *         Created 17.10.2004 - 00:29:28
 */
public abstract class AbstractMatrixTest extends MatrixTest {

    public AbstractMatrixTest(String name) {
        super(name);
    }

    public AbstractMatrixTest(String name, AbstractMatrix m) {
        super(name, m);
    }

    public void testHashCode() {
        try {
            Matrix m = MatrixFactory.getMatrix(1,1,matrixToTest);
            Matrix mclone = MatrixFactory.getMatrix(1,1,matrixToTest);
            Matrix n22 = MatrixFactory.getMatrix(2,2,matrixToTest);
            Matrix n31 = MatrixFactory.getMatrix(3,1,matrixToTest);

            assertTrue("These matrices should have the same hashcode", m.hashCode() == mclone.hashCode());
            assertFalse("These matrices should not have the same hashcode", m.hashCode() == n22.hashCode());
            assertFalse("These matrices should not have the same hashcode", n22.hashCode() == n31.hashCode());

            m.setValue(1,1,1);
            assertFalse("These matrices should not have the same hashcode", m.hashCode() == mclone.hashCode());

            mclone.setValue(1,1,1);
            assertTrue("These matrices should have the same hashcode", m.hashCode() == mclone.hashCode());


            Matrix p23 = MatrixFactory.getMatrix(2,3,matrixToTest);
            Matrix p32 = MatrixFactory.getMatrix(3,2,matrixToTest);
            Matrix p61 = MatrixFactory.getMatrix(6,1,matrixToTest);
            Matrix p16 = MatrixFactory.getMatrix(1,6,matrixToTest);
            assertFalse("These matrices should not have the same hashcode", p23.hashCode() == p32.hashCode());
            assertFalse("These matrices should not have the same hashcode", p23.hashCode() == p61.hashCode());
            assertFalse("These matrices should not have the same hashcode", p23.hashCode() == p16.hashCode());
            assertFalse("These matrices should not have the same hashcode", p61.hashCode() == p16.hashCode());

            m.setValue(1,1,5);
            assertFalse("These matrices should not have the same hashcode", m.hashCode() == p16.hashCode());


        } catch (UnsupportedOperationException e) {
            //ignore it is fine to throw the UnsupportedOperationException
        } catch (IllegalArgumentException e) {
            //ignore some matrices don't allow illegal arguments like row != col or setting non-diagonal elements etc. etc.
        }


    }


    public void testClone() {
        Matrix clone = (Matrix) ((AbstractMatrix) matrixToTest).clone();
        assertEquals("The matrix and its clone must be equal in the begining.", matrixToTest, clone);
        clone.setValue(clone.rows(), clone.cols(), matrixToTest.getValue(clone.rows(), clone.cols()) - 1);
        assertFalse("A change in value in a clone should render it different from its source matrix.", matrixToTest.equals(clone));
    }

    // 3. Write a suite() method that uses reflection to dynamically create a test suite containing all the testXXX() methods
    public static Test suite() {      
        return new TestSuite(AbstractMatrixTest.class);
    }

}
