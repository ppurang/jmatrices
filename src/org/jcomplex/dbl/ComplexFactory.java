package org.jcomplex.dbl;

/**
 * ComplexFactory
 * <br>Author: purangp</br>
 * <br>
 * Date: 03.05.2004
 * Time: 22:34:36
 * </br>
 */
public class ComplexFactory {

    public static Complex getComplex(){
        return new ComplexImpl();
    }

    public static Complex getComplex(double real, double imaginary){
        return new ComplexImpl(real,imaginary);
    }

    private ComplexFactory() {
    }
}
