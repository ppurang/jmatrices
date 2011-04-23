package org.jmatrices.dbl.reworkedDecomposition;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.syntax.MatlabSyntax;

/**
 * CholeskyDecompositionTest
 *
 * @author purangp Created 03.11.2004 - 14:40:16
 */
public class CholeskyDecompositionTest extends TestCase {
    private Matrix a;
    private Matrix b;
    private Matrix l;
    private Matrix x;
    private boolean isspd;

    //1. override constructor
    public CholeskyDecompositionTest(String name) {
        super(name);
    }

    //2. implement tests as public methods
    public void testDecomposition() {
        //add some code here....
        assertTrue("decomposition of matrix " + a + " should equal " + l, new CholeskyDecomposition(a).getL().equals(l));
    }

    public void testSolution() {
        assertTrue("solution of matrix " + a + " and " + b + " should equal " + x, new CholeskyDecomposition(a).solve(b).equals(x));
    }

    public void testIsSpd() {
        assertTrue("isspd for Matrix a should be " + isspd, isspd == new CholeskyDecomposition(a).isSPD());
    }

    // 3. Write a suite() method that uses reflection to dynamically create a test suite containing all the testXXX() methods
    public static Test suite() {
        return new TestSuite(CholeskyDecompositionTest.class);
    }

    // 4. Write a main() method to conveniently run the test with the textual test runner:
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    //the following are called in the order they are declared in

    protected void setUp() throws Exception {
        super.setUp();
        a = MatlabSyntax.create
                ("["
                + "3.00506436, 2.65577048, 3.08742844;"
                + "2.65577048, 3.55545737, 3.42362593;"
                + "3.08742844, 3.42362593, 4.02095978"
                + "]");
        b = MatlabSyntax.create
                ("["
                + "0.03177513;"
                + "0.41823100;"
                + "1.70129375"
                + "]");
        l = MatlabSyntax.create
                ("["
                + "1.7335121459049545, 0.00, 0.00;"
                + "1.532017232341683, 1.099263648906908, 0.00;"
                + " 1.7810249828900122, 0.6323005092296218, 0.6701536065333025"
                + " ]");
        x = MatlabSyntax.create
                ("["
                + "-1.8905346249118304;"
                + "-1.501394438970513;"
                + "3.1418744016371627"
                + "]");

        isspd = true;

    }

    protected void runTest() throws Throwable {
        super.runTest();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
