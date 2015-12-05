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
    	String createDriver = "CREATE TABLE Driver " +
				"(driverName VARCHAR(50) PRIMARY KEY, "+
				"DriverTelephoneNumber VARCHAR(15) NOT NULL)";
        stmt.execute(createDriver);
    }

    public void addDriver() throws SQLException {

        System.out.print("Enter a driverName : ");
        String driverName = kb.nextLine();
        System.out.print("Enter a DriverTelephoneNumber : ");
        String driverNumber = kb.nextLine();
        stmt.execute("INSERT INTO Driver VALUES "
                + "('" + driverName + "', '" + driverNumber + "')");
    }

    public void deleteDriver() throws SQLException {
        System.out.print("Enter a driverName : ");
        String driverName = kb.nextLine();
        stmt.executeUpdate("DELETE * FROM driverName WHERE driverName = "+driverName+"; ");
    }

}
