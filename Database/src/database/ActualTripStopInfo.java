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

    public void recordActualTripStopInfo() throws SQLException {

        System.out.print("Enter the TripNumber (int) : ");
        int tripNumber = kb.nextInt();
        kb.nextLine(); //resets caret
        System.out.print("Enter the Date : ");
        String date = kb.nextLine();
        System.out.print("Enter the ScheduledStartTime  : ");
        String scheduledStartTime = kb.nextLine();
        
        try
        {
            ResultSet rs = stmt.executeQuery("SELECT TripNumber, [Date]," 
                + " ScheduledStartTime, ScheduledArrivalTime "
                + " FROM TripOffering WHERE TripNumber = "
                + tripNumber + " AND [Date] = " + "'" + date + "'"
                + " AND ScheduledStartTime=" + "'" + scheduledStartTime + "'");
            int rsTripNumber = 0;
            String rsDate = "";
            String rsScheduledStartTime = "";
            String rsScheduledArrivalTime = "";
            while(rs.next())
            {
                rsTripNumber = rs.getInt("TripNumber");
                rsDate = rs.getString("Date");
                rsScheduledStartTime = rs.getString("ScheduledStartTime");
                //System.out.println(rsTripNumber + "," + rsDate + "," + rsScheduledStartTime);
                rsScheduledArrivalTime=rs.getString("ScheduledArrivalTime");
               // System.out.println(rsScheduledArrivalTime);
            }
            
            if( //Check to make sure the record exists in TripOffering
               rsTripNumber == 0 || (rsDate == null || rsDate.isEmpty()) ||
               rsScheduledStartTime == null || rsScheduledStartTime.isEmpty()
              )
            {
                //throw new SQLException("No records found!");
                System.out.println("No record");
            }
            
            System.out.print("Enter the StopNumber (int) : ");
            int stopNumber = kb.nextInt();
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
                    +"('"+ tripNumber + "', '"+ date +"', '" + scheduledStartTime +
                    "', '" + stopNumber + "' , '" + rsScheduledArrivalTime+ "', '" +  actualStartTime + "', '" + 
                    actualArrivalTime + "', '" + numberOfPassengerIn + "', '" +
                    numberOfPassengerOut + "')");
        }   /////////////////////////SOMETHING WRONG WITH SCHEDULEDARRIVALTIME
        catch(SQLException e)
        {
            e.printStackTrace(System.err);
        }
    }
    /*INSERT INTO ActualTripStopInfo(TripNumber, [Date], ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut)
   SELECT TripNumber, Date, ScheduledStartTime, 47, "7:30AM", "7:04AM", "7:40AM", 3, 10
   FROM TripOffering*/

}
