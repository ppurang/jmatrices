package org.jmatrices.dbl.db;

import org.jmatrices.dbl.AbstractMatrix;
import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;
import org.jmatrices.dbl.MutableMatrixProducer;

import java.sql.SQLException;

/**
 * DatabaseMatrix
 * <br>@author ppurang</br>
 * <br>
 * Date: 10.06.2004
 * Time: 23:18:43
 * </br>
 */
public class DatabaseMatrix extends AbstractMatrix implements MutableMatrixProducer {
    protected MatrixDatabaseConnection connection;
    protected String uuidString;

    private static final DatabaseMatrix producer = new DatabaseMatrix();
    static {
        MatrixFactory.getInstance().registerMatrixProducer(producer.getClass().getName(), producer);
    }


    public DatabaseMatrix() {
    }

    public DatabaseMatrix(int rows, int cols) {
        super(rows, cols);
        connection = MatrixDatabaseConnection.getInstance();
        uuidString = MatrixDatabaseConnection.generateUUIDString().replaceAll("-","");
    }


    public String getUUIDString() {
        return uuidString;
    }

    public MatrixDatabaseConnection getConnection() {
        return connection;
    }


    /**
     * Sets an element at the given position to a new value
     *
     * @param row   row in which the element occurs
     * @param col   column in which the element occurs
     * @param value the new value to be set
     */
    protected void setValue0(int row, int col, double value) {
        try {
            connection.setValue(uuidString,row,col,value);
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't setValue("+ row+","+col+","+value+") because of " + e.getMessage());
        }
    }

    /**
     * Gets the value of the element at the given row and column
     *
     * @param row row in which the element occurs
     * @param col column in which the element occurs
     * @return value of the element
     */
    protected double getValue0(int row, int col) {
        try {
            return connection.getValue(uuidString,row,col);
        } catch (SQLException e) {
            return 0;
        }
    }

    public int hashCode() {
        throw new UnsupportedOperationException("DatabaseMatrix is too huge to support this operation");
    }

    /**
     * Called by the garbage collector on an object when garbage collection
     * determines that there are no more references to the object.
     * A subclass overrides the <code>finalize</code> method to dispose of
     * system resources or to perform other cleanup.
     * <p/>
     * The general contract of <tt>finalize</tt> is that it is invoked
     * if and when the Java<font size="-2"><sup>TM</sup></font> virtual
     * machine has determined that there is no longer any
     * means by which this object can be accessed by any thread that has
     * not yet died, except as a result of an action taken by the
     * finalization of some other object or class which is ready to be
     * finalized. The <tt>finalize</tt> method may take any action, including
     * making this object available again to other threads; the usual purpose
     * of <tt>finalize</tt>, however, is to perform cleanup actions before
     * the object is irrevocably discarded. For example, the finalize method
     * for an object that represents an input/output connection might perform
     * explicit I/O transactions to break the connection before the object is
     * permanently discarded.
     * <p/>
     * The <tt>finalize</tt> method of class <tt>Object</tt> performs no
     * special action; it simply returns normally. Subclasses of
     * <tt>Object</tt> may override this definition.
     * <p/>
     * The Java programming language does not guarantee which thread will
     * invoke the <tt>finalize</tt> method for any given object. It is
     * guaranteed, however, that the thread that invokes finalize will not
     * be holding any user-visible synchronization locks when finalize is
     * invoked. If an uncaught exception is thrown by the finalize method,
     * the exception is ignored and finalization of that object terminates.
     * <p/>
     * After the <tt>finalize</tt> method has been invoked for an object, no
     * further action is taken until the Java virtual machine has again
     * determined that there is no longer any means by which this object can
     * be accessed by any thread that has not yet died, including possible
     * actions by other objects or classes which are ready to be finalized,
     * at which point the object may be discarded.
     * <p/>
     * The <tt>finalize</tt> method is never invoked more than once by a Java
     * virtual machine for any given object.
     * <p/>
     * Any exception thrown by the <code>finalize</code> method causes
     * the finalization of this object to be halted, but is otherwise
     * ignored.
     *
     * @throws Throwable the <code>Exception</code> raised by this method
     */
    protected void finalize() throws Throwable {
        super.finalize();
        connection.removeAll(uuidString);
        connection.reset();
        uuidString = null;
    }


    protected Matrix createNascentClone() {
        return new DatabaseMatrix(rows(),cols());
    }


    public Matrix getMatrix(int rows, int cols) {
        return new DatabaseMatrix(rows,cols);
    }
}
