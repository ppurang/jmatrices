package org.jmatrices.dbl.db.cache;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * CacheKeyTest
 * <br>Author: purangp</br>
 * <br>
 * Date: 12.06.2004
 * Time: 04:04:23
 * </br>
 */
public class CacheKeyTest extends TestCase {

    // 1. override constructor
    public CacheKeyTest(String name) {
        super(name);
    }

    // 2. implement tests as public methods
    public void testRowValue() {
        try {
            CacheKey entry01 = new CacheKey(0, 1);
            fail("Should raise an IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    public void testColValue() {
        try {
            CacheKey entry10 = new CacheKey(1, 0);
            fail("Should raise an IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    public void testCompareTo() {
        CacheKey entry11, entry12, entry21, entry22;
        entry11 = new CacheKey(1, 1);
        entry12 = new CacheKey(1, 2);
        entry21 = new CacheKey(2, 1);
        entry22 = new CacheKey(2, 2);

        assertTrue(entry11.compareTo(entry11) == 0);
        assertTrue(entry11.compareTo(entry12) == -1);
        assertTrue(entry11.compareTo(entry21) == -1);
        assertTrue(entry11.compareTo(entry22) == -1);

        assertTrue(entry12.compareTo(entry11) == 1);
        assertTrue(entry12.compareTo(entry12) == 0);
        assertTrue(entry12.compareTo(entry21) == -1);
        assertTrue(entry12.compareTo(entry22) == -1);

        assertTrue(entry21.compareTo(entry11) == 1);
        assertTrue(entry21.compareTo(entry12) == 1);
        assertTrue(entry21.compareTo(entry21) == 0);
        assertTrue(entry21.compareTo(entry22) == -1);

        assertTrue(entry22.compareTo(entry11) == 1);
        assertTrue(entry22.compareTo(entry12) == 1);
        assertTrue(entry22.compareTo(entry21) == 1);
        assertTrue(entry22.compareTo(entry22) == 0);
    }

    // 3. Write a suite() method that uses reflection to dynamically create a test suite containing all the testXXX() methods
    public static Test suite() {
        return new TestSuite(CacheKeyTest.class);
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
