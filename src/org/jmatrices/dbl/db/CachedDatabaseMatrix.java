package org.jmatrices.dbl.db;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.db.cache.ReadCache;
import org.jmatrices.dbl.db.cache.WriteCache;

/**
 * DatabaseMatrix
 * <br>Author: purangp</br>
 * <br>
 * Date: 10.06.2004
 * Time: 23:18:43
 * </br>
 */
public class CachedDatabaseMatrix extends DatabaseMatrix {
    private WriteCache writeCache;
    private ReadCache readCache;

    public CachedDatabaseMatrix(int rows, int cols) {
        super(rows, cols);
        writeCache = new WriteCache();
        readCache = new ReadCache();
    }


    /**
     * Sets an element at the given position to a new value
     *
     * @param row   row in which the element occurs
     * @param col   column in which the element occurs
     * @param value the new value to be set
     */
    protected void setValue0(int row, int col, double value) {
        readCache.invalidate(row,col);
        writeCache.setValue(row,col,value);
        readCache.setValue(row,col,value);
    }

    /**
     * Gets the value of the element at the given row and column
     *
     * @param row row in which the element occurs
     * @param col column in which the element occurs
     * @return value of the element
     */
    protected double getValue0(int row, int col) {
        Double value = writeCache.getValue(row,col);
        if(value == null) {
            value = readCache.getValue(row,col);
        }
        if (value == null)
            throw new AssertionError("value can't be null");
        return value.doubleValue();
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
        readCache = null;
        writeCache = null;
    }

    protected Matrix createClone() {
        return new CachedDatabaseMatrix(rows(),cols());
    }
}
