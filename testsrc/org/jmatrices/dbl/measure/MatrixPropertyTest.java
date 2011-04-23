package org.jmatrices.dbl.measure;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;
import org.jmatrices.dbl.syntax.GaussSyntax;

/**
 * MatrixPropertyTest
 *
 * @author purangp Created 11.11.2004 - 22:58:34
 */
public class MatrixPropertyTest extends TestCase {
    Matrix oneElementMatrix = MatrixFactory.getMatrix(1, 1, null);
    Matrix emptyColumnMatrix = MatrixFactory.getMatrix(2, 1, null);
    Matrix emptyRowMatrix = MatrixFactory.getMatrix(1, 2, null);
    Matrix emptySquareMatrix = MatrixFactory.getMatrix(2, 2, null);

    Matrix symmetricMatrix = GaussSyntax.create("{1 5 6, 5 2 0, 6 0 -4}");
    //todo need an example of a skewSymmetricMatrix
    Matrix skewSymmetricMatrix = null;

    //1. override constructor
    public MatrixPropertyTest(String name) {
        super(name);
    }

    //2. implement tests as public methods
    public void testIsColumnVector() {

        assertTrue("oneElementMatrix is a Column Vector", MatrixProperty.isColumnVector(oneElementMatrix));
        assertTrue("emptyColumnMatrix is a Column Vector", MatrixProperty.isColumnVector(emptyColumnMatrix));
        assertFalse("emptyRowMatrix is not a Column Vector", MatrixProperty.isColumnVector(emptyRowMatrix));
        assertFalse("emptySquareMatrix is not a Column Vector", MatrixProperty.isColumnVector(emptySquareMatrix));
    }

    public void testIsRowVector() {
        assertTrue("oneElementMatrix is a Row Vector", MatrixProperty.isRowVector(oneElementMatrix));
        assertFalse("emptyColumnMatrix is not a Row Vector", MatrixProperty.isRowVector(emptyColumnMatrix));
        assertTrue("emptyRowMatrix is a Row Vector", MatrixProperty.isRowVector(emptyRowMatrix));
        assertFalse("emptySquareMatrix is not a Row Vector", MatrixProperty.isRowVector(emptySquareMatrix));
    }

    public void tesIsVector() {
        assertTrue("oneElementMatrix is a Vector", MatrixProperty.isVector(oneElementMatrix));
        assertTrue("emptyColumnMatrix is a Vector", MatrixProperty.isVector(emptyColumnMatrix));
        assertTrue("emptyRowMatrix is a Vector", MatrixProperty.isVector(emptyRowMatrix));
        assertFalse("emptySquareMatrix is not a Vector", MatrixProperty.isVector(emptySquareMatrix));
    }

    public void tesIsSquare() {
        assertTrue("oneElementMatrix is a Square Matrix", MatrixProperty.isSquare(oneElementMatrix));
        assertFalse("emptyColumnMatrix is not a Square Matrix", MatrixProperty.isSquare(emptyColumnMatrix));
        assertFalse("emptyRowMatrix is not a Square Matrix", MatrixProperty.isSquare(emptyRowMatrix));
        assertTrue("emptySquareMatrix is a Square Matrix", MatrixProperty.isSquare(emptySquareMatrix));
    }

    public void testIsSymmetric() {
        assertTrue("symmetricMatrix must be found symmetric", MatrixProperty.isSymmetric(symmetricMatrix));
        //todo add a test with a non square matrix
        //todo add a test with skew symmetric matrix
    }

    public void testIsSkewSymmetric() {
        //todo add a test with skew symmetric matrix
        assertFalse("symmetricMatrix must not be found symmetric", MatrixProperty.isSkewSymmetric(symmetricMatrix));
        //todo add a test with a non square matrix
    }

    public void testIsIdempotent() {
        assertTrue("The matrix is idempotent", MatrixProperty.isIdempotent(getIdempotentMatrix()));
        assertTrue("The identity matrix is always idempotent", MatrixProperty.isIdempotent(MatrixFactory.getIdentityMatrix(4, null)));
    }

    private static Matrix getIdempotentMatrix() {
        double[][] elems = {{1 / 6, -1 / 3, 1 / 6, },
                            {-1 / 3, 2 / 3, -1 / 3, },
                            {1 / 6, -1 / 3, 1 / 6, }, };
        return MatrixFactory.getMatrix(3, 3, null, elems);
    }

    public void testIsSingular() {
        //todo find a singular and non singular matrix
    }

    public void testIsIdentity() {

    }

    // 3. Write a suite() method that uses reflection to dynamically create a test suite containing all the testXXX() methods
    public static Test suite() {
        return new TestSuite(MatrixPropertyTest.class);
    }

    // 4. Write a main() method to conveniently run the test with the textual test runner:
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    //the following are called in the order they are declared in

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void runTest() throws Throwable {
        super.runTest();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
