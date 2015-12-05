/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Seulki
 */
import java.sql.*;
import java.util.Scanner;

public class Bus {

    private Statement stmt;
    Scanner kb = new Scanner(System.in);

    public Bus(Statement stmt) {
        this.stmt = stmt;
    }

    public void makeTable() throws SQLException {
        String createBus = "CREATE TABLE Bus "
                + "( busId INT NOT NULL,  "
                + "  model VARCHAR(50) NOT NULL,  "
                + "  year INT NOT NULL "
                + ")";
        stmt.execute(createBus);
    }

    public void addBus() throws SQLException {

        System.out.print("Enter a BusId (int) : ");
        int busId = kb.nextInt();
        System.out.print("Enter a Bus model : ");
        String model = kb.nextLine();
        System.out.print("Enter a Bus yesr : ");
        int year = kb.nextInt();
        stmt.execute("INSERT INTO Bus VALUES "
                + "('" + busId + "', '" + model + "', '" + year + "')");
    }

    public void deleteBus() throws SQLException {
        System.out.print("Enter a BusId (int) : ");
        int busId = kb.nextInt();
        stmt.executeUpdate("DELETE * FROM Bus WHERE busId = "+busId+"; ");
//        stmt.executeUpdate("DELETE Person WHERE lastName = ‘Lee’ ");
    }

}
