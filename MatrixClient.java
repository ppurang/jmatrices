import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;
import org.jmatrices.dbl.measure.MatricesMeasure;
import org.jmatrices.dbl.operator.MatrixOperator;
import org.jmatrices.dbl.transformer.MatrixTransformer;

/**
 * MatrixClient
 * <br>Author: purangp</br>
 * <br>
 * Date: 22.05.2004
 * Time: 17:29:47
 * </br>
 */
public class MatrixClient {
    public static void main(String[] args) {
        Matrix a = MatrixFactory.getRandomMatrix(3,3,null);
        Matrix b = MatrixFactory.getRandomMatrix(3,1,null);
        // invert a using LUDecomposition
        Matrix aInv = MatrixTransformer.inverse(a);
        // solve the system using LUDecomposition
        Matrix c = MatrixOperator.solve(a,b);
        // solve the system by multiplying aInv with b
        Matrix cThroughInv = MatrixOperator.multiply(aInv,b);
        //print things out
        System.out.println(a);System.out.println("");
        System.out.println(b);System.out.println("");
        System.out.println(c);System.out.println("");
        System.out.println(cThroughInv);System.out.println("");
        //test for equality between the two ways of doing things
        System.out.println(MatricesMeasure.areEqual(c,cThroughInv));
    }
}
