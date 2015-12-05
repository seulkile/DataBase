/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Seulki + Raymond
 */
import java.sql.*;

public class Stop {

    private final Statement stmt;

    public Stop(Statement stmt) {
        this.stmt = stmt;
    }

    public void makeTable() throws SQLException {
        String createStop = "CREATE TABLE Stop "
                + "( StopNumber INT PRIMARY KEY,  "
                + "  StopAddress VARCHAR(50) NOT NULL,  "
                + ")";
        stmt.execute(createStop);
    }

    public void addStop(String stopNumber, String stopAddress) throws SQLException {
        stmt.execute("INSERT INTO Stop VALUES "
                + "('" + stopNumber + "', '" + stopAddress + "')");
    }

    public void deleteStop(String stopNumber) throws SQLException {
        stmt.executeUpdate("DELETE * FROM Stop WHERE busId = "+stopNumber+"; ");
    }
}
