/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Seulki
 */
public class Driver {

    private Statement stmt;
    Scanner kb = new Scanner(System.in);

    public Driver(Statement stmt) {
        this.stmt = stmt;
    }

    public void makeTable() throws SQLException {
        String createBus = "CREATE TABLE Bus "
                + "( DriverName VARCHAR(50) NOT NULL,  "
                + "  DriverTelephoneNumber VARCHAR(50) NOT NULL "
                + ")";
        stmt.execute(createBus);
    }

    public void addDriver() throws SQLException {

        System.out.print("Enter a DriverName : ");
        String name = kb.nextLine();
        System.out.print("Enter a Driver's TelephoneNumber : ");
        String telephoneNumber = kb.nextLine();
        stmt.execute("INSERT INTO Driver VALUES "
                + "('" + name + "', '" + telephoneNumber + "')");
    }
}
