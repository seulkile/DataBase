/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

<<<<<<< HEAD
/**
 *
 * @author Seulki + Raymond
 */
import java.sql.*;
import java.util.Scanner;

public class Driver {

    private final Statement stmt;
=======
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Seulki
 */
public class Driver {

    private Statement stmt;
>>>>>>> e21fb68d1728760ba84cc7726834599fa5456c6f
    Scanner kb = new Scanner(System.in);

    public Driver(Statement stmt) {
        this.stmt = stmt;
    }

    public void makeTable() throws SQLException {
<<<<<<< HEAD
    	String createDriver = "CREATE TABLE Driver " +
				"(driverName VARCHAR(50) PRIMARY KEY, "+
				"DriverTelephoneNumber VARCHAR(15) NOT NULL)";
        stmt.execute(createDriver);
=======
        String createBus = "CREATE TABLE Bus "
                + "( DriverName VARCHAR(50) NOT NULL,  "
                + "  DriverTelephoneNumber VARCHAR(50) NOT NULL "
                + ")";
        stmt.execute(createBus);
>>>>>>> e21fb68d1728760ba84cc7726834599fa5456c6f
    }

    public void addDriver() throws SQLException {

<<<<<<< HEAD
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

=======
        System.out.print("Enter a DriverName : ");
        String name = kb.nextLine();
        System.out.print("Enter a Driver's TelephoneNumber : ");
        String telephoneNumber = kb.nextLine();
        stmt.execute("INSERT INTO Driver VALUES "
                + "('" + name + "', '" + telephoneNumber + "')");
    }
>>>>>>> e21fb68d1728760ba84cc7726834599fa5456c6f
}
