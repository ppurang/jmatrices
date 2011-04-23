package org.jmatrices.dbl.db;

import org.doomdark.uuid.UUIDGenerator;

import java.sql.*;
import java.io.Serializable;

/**
 * MatrixDatabaseConnection <br>Author: purangp</br> <br> Date: 25.06.2004 Time: 20:37:41 </br>
 */
public class MatrixDatabaseConnection implements Serializable {

    private static MatrixDatabaseConnection matrixDatabaseConnection;
    private Connection sqlConnection;
    private Statement stmt;
    public static final String TABLE_NAME = "matrixtable";
    public static final String GET_VALUE_SQL = "SELECT value FROM " + TABLE_NAME + " WHERE uuid = '$1' and row = $2 and col = $3";
    public static final String SET_VALUE_SQL = "INSERT INTO " + TABLE_NAME + "(uuid,row,col,value) values('$1',$2,$3,$4)";
    public static final String REMOVE_ROW_SQL = "DELETE FROM " + TABLE_NAME + " WHERE uuid = '$1' and row = $2 and col = $3";
    public static final String REMOVE_MATRIX_SQL = "DELETE FROM " + TABLE_NAME + " WHERE uuid = '$1'";


    private MatrixDatabaseConnection() throws SQLException, ClassNotFoundException {
        //Setup the connection
        sqlConnection  = DBUtility.getConnection("matrixdb","purangp","none");
        stmt = sqlConnection.createStatement();
    }

    public static MatrixDatabaseConnection getInstance() {
        if (matrixDatabaseConnection == null) {
            try {
                matrixDatabaseConnection = new MatrixDatabaseConnection();
            } catch (SQLException e) {
                throw new IllegalStateException("Illegal state caused by an sql exception " + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Illegal state caused by a class not found exception " + e.getMessage());
            }
            return matrixDatabaseConnection;
        } else
            return matrixDatabaseConnection;
    }

    public static String generateUUIDString() {
        return UUIDGenerator.getInstance().generateRandomBasedUUID().toString();
    }

    public void removeValue(String uuid, int row, int col) throws SQLException {
        String sql = REMOVE_ROW_SQL.replaceFirst("\\$1",uuid);
        sql = sql.replaceFirst("\\$2",""+row);
        sql = sql.replaceFirst("\\$3",""+col);
        stmt.executeUpdate(sql);
    }

    public String removeValueSQL(String uuid, int row, int col) throws SQLException {
        String sql = REMOVE_ROW_SQL.replaceFirst("\\$1",uuid);
        sql = sql.replaceFirst("\\$2",""+row);
        sql = sql.replaceFirst("\\$3",""+col);
        return sql;
    }
    public void removeAll(String uuid) throws SQLException {
        String sql = REMOVE_MATRIX_SQL.replaceFirst("\\$1",uuid);
        stmt.executeQuery(sql);
    }

    public void setValue(String uuid, int row, int col, double value) throws SQLException {
       //removeValue(uuid,row,col);
        stmt.addBatch(removeValueSQL(uuid,row,col));
        String sql = SET_VALUE_SQL.replaceFirst("\\$1",uuid);
        sql = sql.replaceFirst("\\$2",""+row);
        sql = sql.replaceFirst("\\$3",""+col);
        sql = sql.replaceFirst("\\$4",""+value);
        stmt.addBatch(sql);
        stmt.executeBatch();
    }

    public double getValue(String uuid, int row, int col) throws SQLException {
        String sql = GET_VALUE_SQL.replaceFirst("\\$1",uuid);
        sql = sql.replaceFirst("\\$2",""+row);
        sql = sql.replaceFirst("\\$3",""+col);
        double toReturn = 0;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next())
            toReturn = rs.getDouble("value");
        return toReturn;
    }


    public static void main(String[] args) {
        MatrixDatabaseConnection connection = MatrixDatabaseConnection.getInstance();
        try {
            connection.setValue("uuid",1,1,10);
            connection.setValue("uuid",1,2,20);
            connection.setValue("uuid",1,3,10);
            connection.setValue("uuid",2,1,30);
            connection.setValue("uuid",2,2,40);
            connection.setValue("uuid",2,3,50);

            System.out.println(connection.getValue("uuid",1,1));
            System.out.println(connection.getValue("uuid",10,10));

            connection.removeValue("uuid",2,3);
            connection.reset();
            //connection.removeAll("uuid");
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
    }

    public void reset() {
        if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    stmt = null;
                }
            }
            if (sqlConnection != null) {
                try {
                    sqlConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    sqlConnection = null;
                }
            }
    }

    /**
     * todo refactor!
     */
    private static class DBUtility {
        private static Connection con;
        private static String url = "jdbc:postgresql://127.0.0.1:5432/";
        private static String user = "ppurang";
        private static String pass = "piyday99";

        public static Connection getConnection(String database, String user, String password) throws ClassNotFoundException, SQLException {
            if (con != null)
                return con;
            else {
                Class.forName("org.postgresql.Driver");
                con = DriverManager.getConnection(url+database, user, password);
                //stmt = con.createStatement();
                //dbmd = con.getMetaData();
                return con;
            }
        }
        public static Connection getConnection() throws ClassNotFoundException, SQLException {
            if (con != null)
                return con;
            else {
                Class.forName("org.postgresql.Driver");
                con = DriverManager.getConnection(url, user, pass);
                return con;
            }
        }

        public static void reset() {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                } finally {
                    con = null;
                }
            }
        }
    }
}
