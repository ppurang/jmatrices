package org.jcomplex.dbl;

/**
 * ComplexNumberTest
 * <br>Author: purangp</br>
 * <br>
 * Date: 18.05.2004
 * Time: 19:34:59
 * </br>
 */
public class ComplexNumberTest {
    public static void main(String[] args) {
        int max_i= Integer.MAX_VALUE;
        double max_d = Double.MAX_VALUE;
        double  d = max_d*(double)max_i;
        System.out.println("max_i -> "+max_i);
        System.out.println("max_d -> "+max_d);
        System.out.println("d -> "+d);

        double s = 0.0d;
        double y= 1d;
        //s = s - 2d;
        //y= s;
        y = s = s -2d;

        System.out.println("s " + s);
        System.out.println("y " + y);

        s -= 2 * 3;
        s = s - 2*3;
                System.out.println("s " + s);
        System.out.println("y " + y);
    }
}
