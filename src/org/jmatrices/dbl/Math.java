package org.jmatrices.dbl;

/**
 * Math is a utility class that defines mathematical functions not available in the java.lang.Math.
 *
 * @author ppurang
 *         created 14.05.2007 07:30:57
 */
public final strictfp class Math {
    /**
     * APPROXIMATION_DELTA indicates the delta to be used while comparing two values using
     * {@link Math#equalsApproximately(double, double)}. This value reflects the value set through system-wide
     * property {@link Math#DELTA_ALLOWED_PROPERTY}. If it isn't explicitely set then the value defaults to
     * {@link Math#DEFAULT_APPROXIMATION_DELTA}
     */
    public static final double APPROXIMATION_DELTA = getSystemWideDelta();
    /**
     * Property key used to figure out the system wide delta allowed. See {@link Math#APPROXIMATION_DELTA}.
     */
    public static final String DELTA_ALLOWED_PROPERTY = "org.jmatrices.delta";
    /**
     * Default value of the {@link Math#APPROXIMATION_DELTA} if none is set explicitely using the property
     * {@link Math#DELTA_ALLOWED_PROPERTY}. 
      */
    public static final double DEFAULT_APPROXIMATION_DELTA = 0.00000001;

    private static double getSystemWideDelta() {
        double result = DEFAULT_APPROXIMATION_DELTA;
        String anotherValue = System.getProperty(DELTA_ALLOWED_PROPERTY);
        if(anotherValue != null && anotherValue.trim().length() > 0 ) {
            result = Double.valueOf(anotherValue).doubleValue();
        }
        return result;
    }

    /**
     * Tests two numbers for approximate equality.
     * <p/>
     * Essentialy <code>java.lang.Math.abs(value1 - value2) <= deltaAllowed</code>.
     *
     * @param value1       the first value
     * @param value2       the second value
     * @param deltaAllowed the allowed notion of equality
     * @return <code>true</code> iff <code>value1-deltaAllowed &lt;= value2 &lt;=  value1+deltaAllowed</code>
     */
    public static boolean equalsApproximately(double value1, double value2, double deltaAllowed) {
        boolean result = false;
        if (java.lang.Math.abs(value1 - value2) <= deltaAllowed) {
            result = true;
        }
        return result;
    }

    /**
     * Tests two numbers for approximate equality using the <code>org.jmatrices.dbl.Math.APPROXIMATION_DELTA</code>.
     * <p/>
     * Essentialy <code>java.lang.Math.abs(value1 - value2) <= org.jmatrices.dbl.Math.APPROXIMATION_DELTA</code>.
     *
     * @param value1       the first value
     * @param value2       the second value
     * @return <code>true</code> iff <code>value1-APPROXIMATION_DELTA &lt;= value2 &lt;=  value1+APPROXIMATION_DELTA</code>
     */
    public static boolean equalsApproximately(double value1, double value2) {
        return equalsApproximately(value1, value2, APPROXIMATION_DELTA);
    }
}
