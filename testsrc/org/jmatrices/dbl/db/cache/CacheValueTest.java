package org.jmatrices.dbl.db.cache;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * CacheValueTest
 * <br>Author: purangp</br>
 * <br>
 * Date: 13.06.2004
 * Time: 01:15:10
 * </br>
 */
public class CacheValueTest extends TestCase {
    // 1. override constructor
    public CacheValueTest(String name) {
        super(name);
    }

    // 2. implement tests as public methods
    public void testNumberOfRetrivals() {
        CacheValue cacheValueEntry = new CacheValue(2.3D);
        assertTrue(cacheValueEntry.getNumberOfRetrievals() == 0);
        cacheValueEntry.getValue();
        assertTrue(cacheValueEntry.getNumberOfRetrievals() == 1);
    }

    public void testTimeSinceLastRetrieval() {
        CacheValue cacheValueEntry = new CacheValue(2.3D);
        try {
            cacheValueEntry.getTimeSinceLastRetrival();
            fail("Should raise an IllegalStateException");
        } catch (IllegalStateException e) {
        }
        cacheValueEntry.getValue();
        //The following loop is essential for making the compiler include it in the final bytecode
        // and the interpreter to do some work or else the cacheValueEntry.getTimeSinceLastRetrival() will
        //return 0 and the test will fail when actually it should pass.  Next scope goes together
        {
            int j = 0;
            for (int i = 0; i <= 100000; i++)
                j = i + 1000;
            assertTrue(cacheValueEntry.getTimeSinceLastRetrival() != 0L);
            j++;
        }
    }


    // 3. Write a suite() method that uses reflection to dynamically create a test suite containing all the testXXX() methods
    public static Test suite() {
        return new TestSuite(CacheValueTest.class);
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
