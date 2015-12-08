/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Seulki + Raymond
 */
public class TripStopInfo {

    private final Statement stmt;
    Scanner kb = new Scanner(System.in);

    public TripStopInfo(Statement stmt) {
        this.stmt = stmt;
    }

    public void displayStopInfo() throws SQLException {
        String[] columns = {"TripNumber", "StopNumber", "SequenceNumber",
            "DrivingTime"};
        System.out.print("Enter the TripNumber : ");
        int tripNumber = kb.nextInt();
        ResultSet rs = stmt.executeQuery("SELECT * FROM TripStopInfo"
                + " WHERE TripNumber = "+
                tripNumber);
        for(String s : columns){
            System.out.printf("%-15s ",s);
        }
        System.out.println();
        int rsTripNumber = 0;
        int rsStopNumber = 0;
        int rsSeNumber =0;
        String DrivingTime ="";
        while (rs.next()) {
            rsTripNumber =rs.getInt("TripNumber");
            rsStopNumber = rs.getInt("StopNumber");
            rsSeNumber = rs.getInt("SequenceNumber");
            DrivingTime = rs.getString("DrivingTime");
            System.out.printf("%-15d %-15d %-15d %-15s\n\n" ,
                    rsTripNumber,rsStopNumber,
                    rsSeNumber,DrivingTime);
        }
        rs.close();
    }
}
