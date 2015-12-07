/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Seulki
 */
public class TripOffering {

    private final Statement stmt;
    private final Connection con;
    Scanner kb = new Scanner(System.in);

    public TripOffering(Statement stmt, Connection con) {
        this.stmt = stmt;
        this.con = con;
    }

    public void searchTheTripOffering() throws SQLException {
        System.out.print("Enter the Date : ");
        String date = kb.nextLine();
        System.out.print("Enter the StartLocation Name : ");
        String startLocation = kb.nextLine();
        System.out.print("Enter the Destination : ");
        String destination = kb.nextLine();    
        ResultSet rs = stmt.executeQuery("SELECT TripOffering.TripNumber,"
                + " TripOffering.ScheduledStartTime,"
                + " TripOffering.ScheduledArrivalTime,"
                + " TripOffering.DriverName, TripOffering.BusId"
                + " FROM TripOffering INNER JOIN Trip ON TripOffering.TripNumber "
                + "= Trip.TripNumber WHERE TripOffering.[Date] = '" + date + "' AND "
                + "Trip.StartLocationName = '" + startLocation + "' AND "
                + "Trip.DestinationName = '" + destination + "'");
        System.out.printf("%-15s %s\n" ,"Date : ", date);
        System.out.printf("%-15s %s\n" ,"StartLocation : ",startLocation);
        System.out.printf("%-15s %s\n" ,"Destination : ",destination);
        System.out.printf("%-15s %-25s %-25s %-10s %-10s\n", "TripNumber",
                "ScheduledStratTime","ScheduledArrivalTime","DriveName","BusId" );
        int rsTripNumber = 0;
        String rsStartTime = "";
        String rsArrivalTime = "";
        String rsDriverName = "";
        int rsBusId = 0;
        while(rs.next()){
            rsTripNumber = rs.getInt("TripNumber");
            rsStartTime = rs.getString("ScheduledStartTime");
            rsArrivalTime = rs.getString("ScheduledArrivalTime");
            rsDriverName = rs.getString("DriverName");
            rsBusId = rs.getInt("BusId");
            System.out.printf("%-15d %-25s %-25s %-10s %-25d\n", rsTripNumber,rsStartTime,
                    rsArrivalTime,rsDriverName,rsBusId);
        }
        rs.close();
    }
}
