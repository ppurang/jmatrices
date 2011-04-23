package org.jcomplex.dbl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * ComplexImpl
 * <br>Author: purangp</br>
 * <br>
 * Date: 03.05.2004
 * Time: 22:11:28
 * </br>
 */
public class ComplexImpl implements Complex {
    private double real;
    private double imaginary;
    private static final String i = "i";

    public ComplexImpl() {
    }

    /**
     * @param real
     * @param imaginary
     */
    public ComplexImpl(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Gets the real part of the complex number
     *
     * @return
     */
    public double getReal() {
        return real;
    }

    /**
     * Gets the imaginary part of the complex number
     *
     * @return
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * Gets the theta/angle of the polar coordinates of the complex number
     *
     * @return
     */
    public double getTheta() {
        return Math.atan2(imaginary, real);
    }

    /**
     * Gets the modulus of the complex number.
     *
     * @return
     */
    public double getModulus() {
        double absGreater, absSmaller;
        if (Math.abs(real) > Math.abs(imaginary)) {
            absGreater = real;
            absSmaller = imaginary;
        } else {
            absGreater = imaginary;
            absSmaller = real;
        }
        if (absGreater * absGreater == Double.POSITIVE_INFINITY) {
            double quotient = absSmaller / absGreater;
            return Math.abs(absGreater) * Math.sqrt(quotient * quotient + 1);
        } else
            return Math.sqrt(real * real + imaginary * imaginary);
    }


    /**
     * Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p/>
     * The <code>toString</code> method for class <code>Object</code>
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `<code>@</code>', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    public String toString() {
        DecimalFormat format = new DecimalFormat();
        format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        format.setMinimumIntegerDigits(1);
        format.setMaximumFractionDigits(8);
        format.setMinimumFractionDigits(2);
        format.setGroupingUsed(false);
        return format.format(this.real) + " + " + i + format.format(this.imaginary);
    }
}
