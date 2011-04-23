package org.jmatrices.dbl.reworkedDecomposition;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.syntax.GaussSyntax;

/**
 * LUDecompositionTest
 *
 * @author purangp Created 04.11.2004 - 14:35:41
 */
public class LUDecompositionTest extends TestCase {
    private Matrix a;
    private Matrix b;
    private Matrix l;
    private Matrix u;
    private Matrix p;
    private Matrix x;
    private boolean issingular;

    //1. override constructor
    public LUDecompositionTest(String name) {
        super(name);
    }

    //2. implement tests as public methods
    public void testGetL() {
        assertTrue("Test getL failed",new LUDecomposition(a).getL().equals(l));
    }
    public void testGetU() {
        assertTrue("Test getU failed",new LUDecomposition(a).getU().equals(u));
    }
    public void testGetPivotMatrix() {
        assertTrue("Test getPivotMatrix failed",new LUDecomposition(a).getPivotMatrix().equals(p));
    }

    public void testSolve() {
        assertTrue("Test Solve failed",new LUDecomposition(a).solve(b).equals(x));
    }
    public void testIsSingular() {
        assertTrue("Test iusSingular failed",new LUDecomposition(a).isSingular() == issingular);
    }

    // 3. Write a suite() method that uses reflection to dynamically create a test suite containing all the testXXX() methods
    public static Test suite() {
        return new TestSuite(LUDecompositionTest.class);
    }

    // 4. Write a main() method to conveniently run the test with the textual test runner:
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    //the following are called in the order they are declared in

    protected void setUp() throws Exception {
        super.setUp();
        a = GaussSyntax.create
                ("{"
                + "3.00506436 2.65577048 3.08742844,"
                + "2.65577048 3.55545737 3.42362593,"
                + "3.08742844 3.42362593 4.02095978"
                + "}");
        b = GaussSyntax.create
                ("{"
                + "0.03177513,"
                + "0.41823100,"
                + "1.70129375"
                + "}");
        l = GaussSyntax.create
                ("{"
                + "1.00 0.00 0.00,"
                + "0.9733227565915665 1.00 0.00,"
                + "0.8601885133894795 -0.9023995917411973 1.00"
                + "}");
        u = GaussSyntax.create
                ("{"
                + "3.08742844 3.42362593 4.02095978,"
                + "0.00 -0.6765225477259653 -0.8262632172134188,"
                + "0.00 0.00 -0.7807770754412457"
                + "}");

        x = GaussSyntax.create
                ("{"
                + "-1.9439690153385272,"
                + "-1.5268677065315657,"
                + "3.2157951219785796"
                + "}");
        p = GaussSyntax.create
                ("{"
                + "3,"
                + "1,"
                + "2"
                + "}");
        issingular = false;
    }

    protected void runTest() throws Throwable {
        super.runTest();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
