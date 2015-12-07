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
import java.util.Scanner;

public class Driver {

    private final Statement stmt;
    Scanner kb = new Scanner(System.in);

    public Driver(Statement stmt) {
        this.stmt = stmt;
    }

    public void makeTable() throws SQLException {
        String createDriver = "CREATE TABLE Driver "
                + "(driverName VARCHAR(50) PRIMARY KEY, "
                + "DriverTelephoneNumber VARCHAR(15) NOT NULL)";
        stmt.execute(createDriver);
    }

    public void addDriver() throws SQLException {
        System.out.print("Enter a driverName : ");
        String driverName = kb.nextLine();
        if (!checkingDriver(driverName)) {
            System.out.print("Enter a DriverTelephoneNumber : ");
            String driverNumber = kb.nextLine();
            stmt.execute("INSERT INTO Driver VALUES "
                    + "('" + driverName + "', '" + driverNumber + "')");
        } else {
            System.err.println("Driver already exist.");
        }
    }

    public void deleteDriver() throws SQLException {
        System.out.print("Enter a driverName : ");
        String driverName = kb.nextLine();
        if (checkingDriver(driverName)) {
            stmt.executeUpdate("DELETE * FROM driverName WHERE driverName = " + driverName + "; ");
        } else {
            System.err.println("The driver does not exist.");
        }
    }

    private boolean checkingDriver(String name) throws SQLException {
        ResultSet rs;
        String sql = "SELECT DriverName FROM Driver WHERE DriverName = '"
                + name + "' ";
        rs = stmt.executeQuery(sql);
        String rsName = "";
        while (rs.next()) {
            rsName = rs.getString("DriverName");
            
        }
        System.out.println(rsName);
        if (rsName.equals("")) {
            return false;
        }
        return true;
    }
}
