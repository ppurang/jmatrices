package org.jmatrices.dbl;

import org.testng.annotations.Test;

/**
 * SparseMatrixImplTest
 * <br>@author ppurang</br>
 * <br>
 * Date: 16.06.2004
 * Time: 20:45:38
 * </br>
 */
@Test(groups={"jmatrices.core"})
public class SparseMatrixImplTest extends AbstractMatrixTest {


    public SparseMatrixImplTest() {
        matrixToTest = new SparseMatrixImpl(4, 4);
    }

    // 2. implement tests as public methods
    @Test(expectedExceptions = {java.lang.IllegalArgumentException.class})
    public void testRowDimension() {
        Matrix m = new SparseMatrixImpl(0, 23);
    }

    @Test(expectedExceptions = {java.lang.IllegalArgumentException.class})
    public void testColumnDimension() {
        Matrix m = new SparseMatrixImpl(23, 0);
    }

    @Test(expectedExceptions = {java.lang.IllegalArgumentException.class})
    public void testBothDimension() {
        Matrix m = new SparseMatrixImpl(0, 0);
    }

    @Test
    public void testGetValue() {
        Matrix m = new SparseMatrixImpl(100, 100);
        m.setValue(10, 25, 10);
        assert m.getValue(10, 25) == 10;
    }

    @Test
    public void testSetValue2() {
        Matrix m = new SparseMatrixImpl(100, 100);
        m.setValue(10, 25, 25);
        assert m.getValue(10, 25) == 25;
        m.setValue(10, 25, 10);
        assert m.getValue(10, 25) == 10;
        m.setValue(10, 25, 0);
        //ideally here we would like to check the Map's size
        // to make sure zero valued elements are removed from the map
        assert m.getValue(10, 25) == 0;
    }
}
