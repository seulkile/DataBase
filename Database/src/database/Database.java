/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Seulki + Raymond
 */
public class Database {

    Scanner kb = new Scanner(System.in);
    boolean flag = true;

    public void start() {
        // load the Jdbc-Odbc driver.
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(System.err);
        }

        Connection con;
        Statement stmt;

        try {
            // Connect to the Profile database
            con = DriverManager.getConnection("jdbc:odbc:Lab4");

            // Create statement objects.
            stmt = con.createStatement();
            
            // Create stops.
            createStops(stmt);
            
            while (flag) {
                int option = printMenu();
                Menu(stmt,option);
            }
            stmt.close();
            con.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
        }
    }

    private int printMenu() {
        System.out.println("Welcome to the Pomona Transit System.\n"
                + "1.Display all Trip \n"
                + "2.Edit the schedule\n"
                + "3.Display the stops of a given driver and date \n"
                + "4.Display the weekly schedule of a given driver and date\n"
<<<<<<< HEAD
                + "5.Add a driver \n"
                + "6.Add a bus \n"
                + "7.Delete a bus \n"
=======
                + "5.Add a bus \n"
                + "6.Delete a bus \n"
                + "7.Add Driver\n"
>>>>>>> e21fb68d1728760ba84cc7726834599fa5456c6f
                + "8.Record the actual data of a given trip offering\n"
                + "9.Exit");
        int option = kb.nextInt();
        kb.nextLine();
        return option;
    }

    private void Menu(Statement stmt, int option) throws SQLException {
        switch (option) {
            case 1: //display all trip 
                break;
            case 2:  //Edit the schedule
                break;
            case 3:  //Display the stop
                break;
            case 4: // display the weekly schedule
                break;
            case 5: // add a drive
                Driver driver = new Driver(stmt);
                driver.addDriver();
                break;
            case 6: //add bus
                Bus bus = new Bus(stmt);
                bus.addBus();
                break;
            case 7: //delete bus
                Bus bus1 = new Bus(stmt);
                bus1.deleteBus();
                break;
<<<<<<< HEAD
            case 8: //record the actual data of a given trip offering
                ActualTripStopInfo ATSI = new ActualTripStopInfo(stmt);
                ATSI.recordActualTripStopInfo();
                break;
=======
            case 7: //add driver
                Driver driver = new Driver(stmt);
                driver.addDriver();
                break;
            case 8://record the actual data of a given trip offering
                break;
>>>>>>> e21fb68d1728760ba84cc7726834599fa5456c6f
            case 9: //exit
                flag = false;
                break;
            default:
                System.out.println("It is an invalid input.");
                break;
        }
    }
    private void createStops(Statement stmt) throws SQLException
    {
        Stop stop = new Stop(stmt);
        //stop.addStop("47", "3801 W. Temple Avenue.");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Database db = new Database();
        db.start();
    }

}
