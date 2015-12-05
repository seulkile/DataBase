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

public class Bus {

    private final Statement stmt;
    Scanner kb = new Scanner(System.in);

    public Bus(Statement stmt) {
        this.stmt = stmt;
    }

    public void makeTable() throws SQLException {
        String createBus = "CREATE TABLE Bus "
                + "( BusId INT PRIMARY KEY,  "
                + "  Model VARCHAR(50) NOT NULL,  "
                + "  Year INT NOT NULL "
                + ")";
        stmt.execute(createBus);
    }

    public void addBus() throws SQLException {

        System.out.print("Enter a BusId (int) : ");
        int busId = kb.nextInt();
        if (!checkingBus(busId)) {
            kb.nextLine();
            System.out.print("Enter a Bus model : ");
            String model = kb.nextLine();
            System.out.print("Enter a Bus year : ");
            int year = kb.nextInt();
            stmt.execute("INSERT INTO Bus VALUES "
                    + "('" + busId + "', '" + model + "', '" + year + "')");
        } else {
            System.err.println("The Bus ID is already exist.");
        }
    }

    public void deleteBus() throws SQLException {
        System.out.print("Enter a BusId (int) : ");
        int busId = kb.nextInt();
        if (checkingBus(busId)) {
            stmt.executeUpdate("DELETE * FROM Bus WHERE busId = " + busId + "; ");
        } else { 
            System.err.println("The Bus doesnot exist. ");
        }
    }

    private boolean checkingBus(int busId) throws SQLException {
        ResultSet rs;
        String sql = "SELECT busId FROM Bus WHERE busId = "
                + busId + " ";
        rs = stmt.executeQuery(sql);
        int rsBusId = 0;
        while (rs.next()) {
            rsBusId = rs.getInt("busId");
        }
        if (rsBusId == 0) {
            return false;
        }
        return true;
    }
}
