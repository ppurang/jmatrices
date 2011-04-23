package org.jmatrices.dbl;

import org.testng.annotations.Test;

/**
 * MathTest.
 *
 * @author ppurang
 *         created 14.05.2007 07:34:36
 */
@Test(groups = {"jmatrices.core"})
public class MathTest {
    @Test
    public strictfp void testEqualsApproximately() {
        double value1 = 1;
        double value2 = 2;
        double value3 = 3;
        double delta = 1;
        assert Math.equalsApproximately(value1, value2, delta);
        assert Math.equalsApproximately(value2, value1, delta);
        assert !Math.equalsApproximately(value1, value3, delta);
        assert !Math.equalsApproximately(value3, value1, delta);


        value1 = 0.1;
        value2 = 0.2;
        delta = 0.1;
        assert Math.equalsApproximately(value1, value2, delta);
        assert Math.equalsApproximately(value2, value1, delta);

        value1 = 0.001;
        value2 = 0.003;
        delta = 0.001;
        assert !Math.equalsApproximately(value1, value2, delta);
        assert !Math.equalsApproximately(value2, value1, delta);

        value1 = 0.000000000001;
        value2 = 0.0000000000001;
        delta = 0.0000000001;
        assert Math.equalsApproximately(value1, value2, delta);
        assert Math.equalsApproximately(value2, value1, delta);


        value1 = 0.001;
        value2 = 0.0015;
        delta = 0.001;
        assert Math.equalsApproximately(value1, value2, delta);
        assert Math.equalsApproximately(value2, value1, delta);


        value1 = 0.000000000001;
        value2 = 0.0000000000001;
        delta =  0.0000000000001;
        assert !Math.equalsApproximately(value1, value2, delta);
        assert !Math.equalsApproximately(value2, value1, delta);

        value1 = 0.000000000000000000000000000000000000000000000000001;
        value2 = 0.0000000000000000000000000000000000000000000000000001;
        delta =  0.000000000000000000000000000000000000000000000000001;
        assert Math.equalsApproximately(value1, value2, delta);
        assert Math.equalsApproximately(value2, value1, delta);

        
        assert Math.equalsApproximately(1.6175312366132595, 1.61753123661326);
        assert Math.equalsApproximately(1.61753123661326, 1.6175312366132595);

        assert !Math.equalsApproximately(0.43320593249426215, 0.43320595603137);
        assert !Math.equalsApproximately(0.43320595603137, 0.43320593249426215);

    }
}
