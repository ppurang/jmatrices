package org.jcomplex.dbl;

/**
 * Complex
 * <p>
 * Author: purangp
 * </p>
 * Date: 20.03.2004
 * Time: 18:37:23
 */
public interface Complex extends java.io.Serializable {
    /**
     * Gets the real part of the complex number
     * @return
     */
    double getReal();
    /**
     * Gets the imaginary part of the complex number
     * @return
     */
    double getImaginary();
    /**
     * Gets the theta/angle of the polar coordinates of the complex number
     * @return
     */
    double getTheta();
    /**
     * Gets the modulus of the complex number.
     * @return
     */
    double getModulus();
}
