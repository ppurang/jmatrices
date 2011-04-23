package org.jmatrices.dbl;

import org.testng.annotations.Test;

/**
 * MatricesTest
 *
 * @author ppurang
 *         Created 17.10.2004 - 02:42:47
 */
@Test(groups={"jmatrices.core"})

public class MatricesTest {

    //2. implement tests as public methods
    @Test(groups={"jmatrices.core"})
    public void testGetRowMatrix(){
        //todo write it! write what?
    }


    public void testGetMatrixFromArray() {
        double[][] elems = new double[][]{{1,2,3},{4,5,6},{7,8,9}};
        Matrix testMatrix = Matrices.getMatrixFromArray(3,3,null,elems);

        Matrix resultMatrix = new ArrayMatrixImpl(3, 3) ; //1 2 3, 4 5 6, 7 8 9
        resultMatrix.setValue(1,1,1); resultMatrix.setValue(1,2,2); resultMatrix.setValue(1,3,3);
        resultMatrix.setValue(2,1,4); resultMatrix.setValue(2,2,5); resultMatrix.setValue(2,3,6);
        resultMatrix.setValue(3,1,7); resultMatrix.setValue(3,2,8); resultMatrix.setValue(3,3,9);

        assert resultMatrix.equals(testMatrix):"The two matrices aren't equal! " + resultMatrix.toString() + "\n" + testMatrix.toString();
    }

}
