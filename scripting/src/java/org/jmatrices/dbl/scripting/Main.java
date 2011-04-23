package org.jmatrices.scripting;


import org.mozilla.javascript.tools.shell.JSConsole;

/**
 * Main.
 *
 * @author ppurang
 *         created 27.04.2007 18:18:53
 */
public class Main {

    public static void main(String[] args) {
        JSConsole console = new JSConsole(args);
        console.pack();
        console.setVisible(true);

        /*Matrix b = MatrixFactory.getMatrix(3, 3, null);
        b.setValue(1, 1, 20);
        b.setValue(1, 2, 21);
        b.setValue(1, 3, 15);
        b.setValue(2, 1, 15);
        b.setValue(2, 2, 16);
        b.setValue(2, 3, 17);
        b.setValue(3, 1, 17);
        b.setValue(3, 2, 18);
        b.setValue(3, 3, 17);
        System.out.println(MatrixMeasure.getProduct(b));
        double product = ((20 * 21) * 15) * ((15 * 16) * 17) * ((17 * 18) * 17);
        System.out.println(product);
        assert MatrixMeasure.getProduct(b) == product;
*/
    }
}
