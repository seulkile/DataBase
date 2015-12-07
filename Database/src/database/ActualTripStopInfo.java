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
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class ActualTripStopInfo {

    private final Statement stmt;
    Scanner kb = new Scanner(System.in);

    public ActualTripStopInfo(Statement stmt) {
        this.stmt = stmt;
    }

    public void makeTable() throws SQLException {
        String createBus = "CREATE TABLE ActualTripStopInfo "
                + "( TripNumber INT,  "
                + "  Date VARCHAR(50),  "
                + "  ScheduledStartTime VARCHAR(50), "
                + "  StopNumber INT, "
                + "  ScheduledArrivalTime VARCHAR(50) NOT NULL, "
                + "  ActualStartTime VARCHAR(50) NOT NULL, "
                + "  ActualArrivalTime VARCHAR(50) NOT NULL, "
                + "  NumberOfPassengerIN INT NOT NULL, "
                + "  NumberOfPassengerOUT INT NOT NULL, "
                + "  PRIMARY KEY(TripNumber, Date, ScheduledStartTime, "
                + "  StopNumber) "
                + ")";
        stmt.execute(createBus);
    }

    public void recordActualTripStopInfo() throws SQLException, ParseException {

        System.out.print("Enter the TripNumber (int) : ");
        int tripNumber = kb.nextInt();
        kb.nextLine(); //resets caret
        System.out.print("Enter the Date : ");
        String date = kb.nextLine();
        System.out.print("Enter the ScheduledStartTime  : ");
        String scheduledStartTime = kb.nextLine();
        ResultSet rs = stmt.executeQuery("SELECT TripNumber, [Date],"
                + " ScheduledStartTime, ScheduledArrivalTime "
                + " FROM TripOffering WHERE TripNumber = "
                + tripNumber + " AND [Date] = " + "'" + date + "'"
                + " AND ScheduledStartTime=" + "'" + scheduledStartTime + "'");
        int rsTripNumber = 0;
        String rsDate = "";
        String rsScheduledStartTime = "";
        String rsScheduledArrivalTime = "";
        while (rs.next()) {
            rsTripNumber = rs.getInt("TripNumber");
            rsDate = rs.getString("Date");
            rsScheduledStartTime = rs.getString("ScheduledStartTime");
            //System.out.println(rsTripNumber + "," + rsDate + "," + rsScheduledStartTime);
            rsScheduledArrivalTime = rs.getString("ScheduledArrivalTime");
            // System.out.println(rsScheduledArrivalTime);
        }

        if (checkingRecord("TripOffering", tripNumber, date, scheduledStartTime)) {
            //throw new SQLException("No records found!");
            System.err.println("No record on the TripOffering");

        } else if (!checkingRecord("ActualTripStopInfo", tripNumber, date, scheduledStartTime)) {
            System.err.println("The record already exists on the ActualTripStopInfo.");
        } else {

            System.out.print("Enter the StopNumber (int) : ");
            int stopNumber = kb.nextInt();
            if (checkingStop(stopNumber)) {
                kb.nextLine(); // resets caret
                System.out.print("Enter the ActualStartTime : ");
                String actualStartTime = kb.nextLine();
                System.out.print("Enter the ActualArrivalTime : ");
                String actualArrivalTime = kb.nextLine();
                System.out.print("Enter the NumberOfPassengerIn (int) : ");
                int numberOfPassengerIn = kb.nextInt();
                System.out.print("Enter the NumberOfPassengerOut (int) : ");
                int numberOfPassengerOut = kb.nextInt();

                stmt.executeUpdate("INSERT INTO ActualTripStopInfo VALUES "
                        + "('" + tripNumber + "', '" + date + "', '" + scheduledStartTime
                        + "', '" + stopNumber + "' , '" + rsScheduledArrivalTime + "', '"
                        + actualStartTime + "', '"
                        + actualArrivalTime + "', '" + numberOfPassengerIn + "', '"
                        + numberOfPassengerOut + "')");

                String drivingTime = calculateDrivingTime(actualArrivalTime, actualStartTime);
                insertTripStopInfo(stopNumber,tripNumber,drivingTime);
            } else {
                System.err.println("There is no record for the stop");
            }

        } /////////////////////////SOMETHING WRONG WITH SCHEDULEDARRIVALTIME

    }

    private boolean checkingStop(int stopNumber) throws SQLException {
        ResultSet rs;
        String sql = "SELECT StopNumber FROM Stop WHERE StopNumber = "
                + stopNumber + " ";
        rs = stmt.executeQuery(sql);
        int rsStopNumber = 0;
        while (rs.next()) {
            rsStopNumber = rs.getInt("StopNumber");
        }
        if (rsStopNumber == 0) {
            return false;
        }
        return true;
    }

    private void insertTripStopInfo(int stopNumber, int tripNumber, String drivingTime) throws SQLException {
        ResultSet rs;
        String sql = "SELECT SequenceNumber FROM TripStopInfo WHERE StopNumber = "
                + stopNumber + " AND TripNumber = " + tripNumber;
        rs = stmt.executeQuery(sql);
        int rsSequenceNumber = 0;
        while (rs.next()) {
            rsSequenceNumber = rs.getInt("SequenceNumber");
        }
        if (rsSequenceNumber == 0) {
            stmt.executeUpdate("INSERT INTO TripStopInfo VALUES "
                    + "('" + tripNumber + "', '" + stopNumber
                    + "', '" + 1
                    + "', '" + drivingTime + "')");
        } else {
            stmt.executeUpdate("UPDATE TripStopInfo "
                    + "SET SequenceNumber = " + ++rsSequenceNumber
                    + " WHERE StopNumber = "
                    + stopNumber + " AND TripNumber = " + tripNumber);
        }

    }

    private boolean checkingRecord(String table, int tripNumber, String date, String scheduledStartTime) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT TripNumber, [Date],"
                + " ScheduledStartTime, ScheduledArrivalTime "
                + " FROM " + table + " WHERE TripNumber = "
                + tripNumber + " AND [Date] = " + "'" + date + "'"
                + " AND ScheduledStartTime=" + "'" + scheduledStartTime + "'");
        int rsTripNumber = 0;
        String rsDate = "";
        String rsScheduledStartTime = "";
        String rsScheduledArrivalTime = "";
        while (rs.next()) {
            rsTripNumber = rs.getInt("TripNumber");
            rsDate = rs.getString("Date");
            rsScheduledStartTime = rs.getString("ScheduledStartTime");
            //System.out.println(rsTripNumber + "," + rsDate + "," + rsScheduledStartTime);
            rsScheduledArrivalTime = rs.getString("ScheduledArrivalTime");
            // System.out.println(rsScheduledArrivalTime);
        }
        if (rsTripNumber == 0 || (rsDate == null || rsDate.isEmpty())
                || rsScheduledStartTime == null || rsScheduledStartTime.isEmpty()) {
            return true;
        }
        return false;
    }
    /*INSERT INTO ActualTripStopInfo(TripNumber, [Date], ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut)
     SELECT TripNumber, Date, ScheduledStartTime, 47, "7:30AM", "7:04AM", "7:40AM", 3, 10
     FROM TripOffering*/

    private String calculateDrivingTime(String actualArrivalTime, String actualStartTime) throws ParseException {
        DateFormat format = new SimpleDateFormat("HH:mm");
        Date time1 = format.parse(actualArrivalTime);
        Date time2 = format.parse(actualStartTime);
        long diff = time1.getTime() - time2.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        if (diffMinutes == 0) {
            return diffHours + ":" + "00";
        }
        return diffHours + ":" + diffMinutes;
    }

}
