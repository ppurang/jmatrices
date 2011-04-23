package org.jmatrices.dbl;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * ArrayMatrixImplTest
 *
 * @author ppurang
 *         Created 17.10.2004 - 00:44:54
 */
@Test(groups={"jmatrices.core"})
public class ArrayMatrixImplTest extends AbstractMatrixTest {


    public ArrayMatrixImplTest() {
    }

    public ArrayMatrixImplTest(ArrayMatrixImpl m) {
        super(m);
    }

    @BeforeClass
    protected void setUp() throws Exception {
        matrixToTest = new ArrayMatrixImpl(4, 4);
    }


}
