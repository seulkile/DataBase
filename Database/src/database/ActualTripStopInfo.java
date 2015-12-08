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
import java.text.ParseException;
import java.util.Scanner;

public class ActualTripStopInfo {

    /*Connection is used because multiple queries are executed.
      A single statement will close any existing resultsets before
      a new query is executed. Therefore we use multiple statements.
    */
    private final Connection con;
    private final Statement stmt;
    Scanner kb = new Scanner(System.in);

    public ActualTripStopInfo(Connection con) throws SQLException {
        this.con = con;
        stmt = con.createStatement();
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

    public void recordActualTripStopInfo() throws SQLException, ParseException
    {

        System.out.print("Enter the TripNumber (int) : ");
        int tripNumber = kb.nextInt();
        kb.nextLine(); //resets caret
        System.out.print("Enter the Date : ");
        String date = kb.nextLine();
        System.out.print("Enter the ScheduledStartTime  : ");
        String scheduledStartTime = kb.nextLine();
        ResultSet rs = con.createStatement().executeQuery("SELECT TripNumber,"
                + "[Date],"
                + " ScheduledStartTime "
                + " FROM TripOffering WHERE TripNumber = "
                + tripNumber + " AND [Date] = " + "'" + date + "'"
                + " AND ScheduledStartTime=" + "'" + scheduledStartTime + "'");
        int rsTripNumber = 0;
        String rsDate = "";
        String rsScheduledStartTime = "";
        String calculatedArrivalTime = "";
        while (rs.next()) {
            rsTripNumber = rs.getInt("TripNumber");
            rsDate = rs.getString("Date");
            rsScheduledStartTime = rs.getString("ScheduledStartTime");
        }

        if(checkingRecord("TripOffering", tripNumber, date,
                scheduledStartTime))
        {
            //throw new SQLException("No records found!");
            System.err.println("No record on the TripOffering");

        } else if (!checkingRecord("ActualTripStopInfo", tripNumber,
                date, scheduledStartTime)) {
            System.err.println("The record already exists on the"
                    + "ActualTripStopInfo.");
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
                ResultSet rs2 = con.createStatement().executeQuery("SELECT"
                        + " DrivingTime FROM"
                        + " TripStopInfo,"
                        + " TripOffering "
                        + " WHERE TripOffering.TripNumber = " + tripNumber
                        + " AND "
                        + " TripStopInfo.TripNumber = " + tripNumber + " AND "
                        + " TripStopInfo.StopNumber = " + stopNumber);
                String drivingTime = "";
                while (rs2.next()) {
                    drivingTime = rs2.getString("DrivingTime");
                }
                rs2.close();
                calculatedArrivalTime = calculateArrivalTime
                        (rsScheduledStartTime, drivingTime);
                con.createStatement().executeUpdate("INSERT INTO"
                        + " ActualTripStopInfo VALUES "
                        + "('" + tripNumber + "', '" + date + "', '"
                        + scheduledStartTime
                        + "', '" + stopNumber + "' , '" + calculatedArrivalTime
                        + "', '"
                        + actualStartTime + "', '"
                        + actualArrivalTime + "', '" + numberOfPassengerIn
                        + "', '"
                        + numberOfPassengerOut + "')");


                //insertTripStopInfo(stopNumber,tripNumber,drivingTime);
            } else {
                System.err.println("There is no record for the stop");
            }
        }
        rs.close();
    }
    public String calculateArrivalTime(String startTime, String drivingTime)
    {
        int driveTime = Integer.parseInt(drivingTime);
        String[] time = startTime.split(":");
        int hours = Integer.parseInt(time[0]);
        int minutes = Integer.parseInt(time[1]);
        if(minutes + driveTime > 60)
        {
            hours++;
            minutes -= 60;
        }
        else if(minutes + driveTime < 60)
        {
            minutes += driveTime;
        }
        String result = Integer.toString(hours) + ":" +
                Integer.toString(minutes);
        return result;
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

    private boolean checkingRecord(String table, int tripNumber, String date,
            String scheduledStartTime) throws SQLException {
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
            rsScheduledArrivalTime = rs.getString("ScheduledArrivalTime");
        }
        rs.close();
        if (rsTripNumber == 0 || (rsDate == null || rsDate.isEmpty())
                || rsScheduledStartTime == null ||
                rsScheduledStartTime.isEmpty()) {
            return true;
        }
        return false;
    }
}
