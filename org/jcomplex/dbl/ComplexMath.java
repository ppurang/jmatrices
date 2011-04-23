package org.jcomplex.dbl;

/**
 * ComplexMath
 * <p/>
 * Author: purangp
 * </p>
 * Date: 20.03.2004
 * Time: 22:35:06
 */
public strictfp final class ComplexMath {
    public static final Complex negate(Complex a) {
        return ComplexFactory.getComplex(-a.getReal(), -a.getImaginary());
    }

    public static final Complex conjugate(Complex a) {
        return ComplexFactory.getComplex(a.getReal(), -a.getImaginary());
    }

    public static final Complex square(Complex a) {
        return ComplexMath.multiply(a, a);
    }

    public static final Complex absoluteSquare(Complex a) {
        return ComplexMath.multiply(a, ComplexMath.conjugate(a));
    }

    public static final Complex add(Complex a, Complex b) {
        return ComplexFactory.getComplex(a.getReal() + b.getReal(), a.getImaginary() + b.getImaginary());
    }

    public static final Complex subtract(Complex a, Complex b) {
        return ComplexFactory.getComplex(a.getReal() - b.getReal(), a.getImaginary() - b.getImaginary());
    }

    public static final Complex multiply(Complex a, Complex b) {
        return ComplexFactory.getComplex(a.getReal() * b.getReal() - a.getImaginary() * b.getImaginary(),
                a.getReal() * b.getImaginary() + a.getImaginary() * b.getReal());
    }

    public static final Complex reciprocal(Complex a) {
        double x = a.getReal(), y = a.getImaginary(), sumOfSquares = x * x + y * y;
        return ComplexFactory.getComplex(x / sumOfSquares, -y / sumOfSquares);
    }

    public static final Complex divide(Complex a, Complex b) {
        double x = a.getReal(), y = a.getImaginary(), u = b.getReal(), v = b.getImaginary(), sumOfSquares = u * u + v * v;
        return ComplexFactory.getComplex((x * u + y * v) / sumOfSquares, (-x * v + y * u) / sumOfSquares);
    }


    public static final Complex sqrt(Complex a) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    public static final Complex exp(Complex a) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    public static final Complex log(Complex a) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    public static final Complex pow(Complex a, double n) {
        throw new UnsupportedOperationException("not yet implemented");
    }


}
