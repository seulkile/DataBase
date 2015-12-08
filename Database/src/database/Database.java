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
import java.text.ParseException;
import java.util.Scanner;

/**
 *
 * @author Seulki + Raymond
 */
public class Database {

    Scanner kb = new Scanner(System.in);
    Connection con;
    Statement stmt;
    boolean flag = true;

    public void start() throws ParseException {
        // load the Jdbc-Odbc driver.
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(System.err);
        }

        try {
            // Connect to the Profile database
            con = DriverManager.getConnection("jdbc:odbc:Lab4");

            // Create statement objects.
            stmt = con.createStatement();

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
                + "3.Display the stops of a given trip \n"
                + "4.Display the weekly schedule of a given driver and date\n"
                + "5.Add a driver \n"
                + "6.Add a bus \n"
                + "7.Delete a bus \n"
                + "8.Record the actual data of a given trip offering\n"
                + "9.Exit");
        int option = kb.nextInt();
        kb.nextLine();
        return option;
    }

    private int printTripOfferingMenu(){
        System.out.println("1.Delete a tripOffering \n"
                + "2.Add trips\n"
                + "3.Change the Driver \n"
                + "4.Change the Bus");
        int option = kb.nextInt();
        return option;
    }

    private void Menu(Statement stmt, int option) throws SQLException,
            ParseException {
        switch (option) {
            case 1: //display all trip
                TripOffering tripOffering = new TripOffering(stmt);
                tripOffering.searchTheTripOffering();
                break;
            case 2:  //Edit the schedule
                int offeringOption = printTripOfferingMenu();
                offeringMenu(stmt, offeringOption);
                break;
            case 3:  //Display the stop
                TripStopInfo tr = new TripStopInfo(stmt);
                tr.displayStopInfo();
                break;
            case 4: // display the weekly schedule
                TripOffering weeklySchedule = new TripOffering(stmt);
                weeklySchedule.displayWeeklySchedule();
                break;
            case 5: // add a driver
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
            case 8: //record the actual data of a given trip offering
                ActualTripStopInfo ATSI = new ActualTripStopInfo(con);
                ATSI.recordActualTripStopInfo();
                break;
            case 9: //exit
                flag = false;
                break;
            default:
                System.out.println("It is an invalid input.");
                break;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        Database db = new Database();
        db.start();
    }

    private void offeringMenu(Statement stmt, int offeringOption)
            throws SQLException {
        TripOffering tr = new TripOffering(stmt);
        switch(offeringOption){
            case 1:  //delete a offering
                tr.deleteTheRecord();
                break;
            case 2: //add a or offering(s).
                tr.addTheRecord();
                break;
            case 3: //change the driver
                tr.changeDriver();
                break;
            case 4:  //change the bus
                tr.changeBus();
                break;
            default:
                System.out.println("It is an invalid input.");
                break;
        }
    }

}
