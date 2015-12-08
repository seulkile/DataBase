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
public class TripOffering {

    private final Statement stmt;
    Scanner kb = new Scanner(System.in);

    public TripOffering(Statement stmt) {
        this.stmt = stmt;
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
                + " FROM TripOffering INNER JOIN Trip ON"
                + " TripOffering.TripNumber "
                + "= Trip.TripNumber WHERE TripOffering.[Date] = '" + date
                + "' AND "
                + "Trip.StartLocationName = '" + startLocation + "' AND "
                + "Trip.DestinationName = '" + destination + "'");
        System.out.printf("%-15s %s\n", "StartLocation : ", startLocation);
        System.out.printf("%-15s %s\n", "Destination : ", destination);
        System.out.printf("%-15s %s\n", "Date : ", date);
        System.out.printf("%-25s %-25s %-15s %-15s\n",
                "ScheduledStartTime", "ScheduledArrivalTime",
                "DriverName", "BusId");
        int rsTripNumber = 0;
        String rsStartTime = "";
        String rsArrivalTime = "";
        String rsDriverName = "";
        int rsBusId = 0;
        while (rs.next()) {
            rsTripNumber = rs.getInt("TripNumber");
            rsStartTime = rs.getString("ScheduledStartTime");
            rsArrivalTime = rs.getString("ScheduledArrivalTime");
            rsDriverName = rs.getString("DriverName");
            rsBusId = rs.getInt("BusId");
            System.out.printf("%-25s %-25s %-15s %-15d\n", rsStartTime,
                    rsArrivalTime, rsDriverName, rsBusId);
        }
        rs.close();
    }
    public void deleteTheRecord() throws SQLException {
        System.out.print("Enter the Trip Number : ");
        int tripNumber = kb.nextInt();
        System.out.print("Enter the Date : ");
        kb.nextLine();
        String date = kb.nextLine();
        System.out.print("Enter the ScheduledStartTime : ");
        String startTime = kb.nextLine();
        if (!checkingRecord(tripNumber, date, startTime)) {
            stmt.executeUpdate("DELETE FROM TripOffering WHERE TripNumber = "
                    + tripNumber + " AND [Date] = '" + date
                    + "' AND ScheduledStartTime = '" + startTime + "'");
        } else {
            System.err.println("The record does not exist.");
        }
    }

    private boolean checkingRecord(int tripNumber, String date,
            String scheduledStartTime) throws SQLException {
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

    public void addTheRecord() throws SQLException {
        boolean flag = true;
        int tripNumber;
        String date;
        String startTime;
        String arrivalTime;
        String driverName;
        int busId;
        String ans;
        char a;
        do {
            System.out.print("Enter the Trip Number : ");
            tripNumber = kb.nextInt();
            System.out.print("Enter the Date : ");
            kb.nextLine();
            date = kb.nextLine();
            System.out.print("Enter the ScheduledStartTime : ");
            startTime = kb.nextLine();

            if (checkingRecord(tripNumber, date, startTime)) {
                System.out.print("Enter the ScheduledArrivalTime : ");
                arrivalTime = kb.nextLine();
                System.out.print("Enter the driver's Name : ");
                driverName = kb.nextLine();
                while (checkingDriver(driverName)) {
                    System.out.print("Enter the driver's Name : ");
                    driverName = kb.nextLine();
                }
                System.out.print("Enter the bus ID : ");
                busId = kb.nextInt();
                while (checkingBus(busId)) {
                    System.out.print("Enter the bus Id : ");
                    busId = kb.nextInt();
                }
                kb.nextLine();
                stmt.executeUpdate("INSERT INTO TripOffering VALUES "
                        + "('" + tripNumber + "', '" + date + "', '"
                        + startTime
                        + "', '" + arrivalTime + "', '"
                        + driverName + "', '"
                        + busId + "')");
                System.out.println("Do you want to add more record? (y/n)  ");
                ans = kb.nextLine();
                a = ans.charAt(0);
                if (a == 'y' || a == 'Y') {
                    flag = true;
                } else {
                    flag = false;
                }

            } else {
                System.err.println("The record already exists");
            }
        } while (flag);
    }

    public void changeDriver() throws SQLException {
        int tripNumber;
        String date;
        String startTime;
        System.out.print("Enter the Trip Number : ");
        tripNumber = kb.nextInt();
        System.out.print("Enter the Date : ");
        kb.nextLine();
        date = kb.nextLine();
        System.out.print("Enter the ScheduledStartTime : ");
        startTime = kb.nextLine();
        if (!checkingRecord(tripNumber, date, startTime)) {
            System.out.print("Enter the Driver Name : ");
            String name = kb.nextLine();
            if (!checkingDriver(name)) {
                stmt.executeUpdate("UPDATE TripOffering "
                        + "SET DriverName = '" + name + "' "
                        + "WHERE TripNumber = "
                        + tripNumber + " AND [Date] = " + "'" + date + "'"
                        + " AND ScheduledStartTime=" + "'" + startTime + "'");
            } else {
                System.err.println("The driver does not exist.");
            }
        } else {
            System.err.println("The record does not exist.");
        }
    }

    private boolean checkingDriver(String name) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT DriverName"
                + " FROM Driver WHERE DriverName = '"
                + name + "'");
        String rsDriver = "";

        while (rs.next()) {
            rsDriver = rs.getString("DriverName");
        }
        rs.close();
        if ((rsDriver == null || rsDriver.isEmpty())) {
            System.err.println("The driver does not exist!");
            return true;
        }
        return false;
    }

    public void changeBus() throws SQLException {
        int tripNumber;
        String date;
        String startTime;
        System.out.print("Enter the Trip Number : ");
        tripNumber = kb.nextInt();
        System.out.print("Enter the Date : ");
        kb.nextLine();
        date = kb.nextLine();
        System.out.print("Enter the ScheduledStartTime : ");
        startTime = kb.nextLine();
        if (!checkingRecord(tripNumber, date, startTime)) {
            System.out.print("Enter the BusId : ");
            int busId = kb.nextInt();
            if (!checkingBus(busId)) {
                stmt.executeUpdate("UPDATE TripOffering "
                        + "SET BusId = " + busId + " "
                        + "WHERE TripNumber = "
                        + tripNumber + " AND [Date] = " + "'" + date + "'"
                        + " AND ScheduledStartTime=" + "'" + startTime + "'");
            } else {
                System.err.println("The Bus does not exists.");
            }
        } else {
            System.err.println("The record does not exists.");
        }

    }

    private boolean checkingBus(int busId) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT BusId"
                + " FROM Bus WHERE BusId = "
                + busId);
        int rsBusId = 0;

        while (rs.next()) {
            rsBusId = rs.getInt("BusId");
        }
        rs.close();
        if (rsBusId == 0) {
            System.err.println("The bus does not exist!");
            return true;
        }
        return false;
    }

    public void displayWeeklySchedule() throws SQLException {
        System.out.print("Enter the driver name : ");
        String driverName = kb.nextLine();
        System.out.print("Enter the Date : ");
        String date = kb.nextLine();
        ResultSet rs = stmt.executeQuery("SELECT TripOffering.TripNumber,"
                + " TripOffering.[Date],"
                + " TripOffering.ScheduledStartTime,"
                + " TripOffering.ScheduledArrivalTime,"
                + " TripOffering.DriverName, TripOffering.BusID,"
                + " Trip.StartLocationName, Trip.DestinationName"
                + " FROM TripOffering, Trip"
                + " WHERE TripOffering.DriverName = '" + driverName + "'"
                + " AND TripOffering.[Date] = '" + date
                + "' AND Trip.TripNumber = TripOffering.TripNumber");
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                "Date", "StartLocation", "Destination Name",
                "Scheduled Start Time", "ScheduledArrivalTime",
                "DriverName", "BusId");

        String rsStartTime = "";
        String rsArrivalTime = "";
        String rsDriverName = "";
        String rsStartLocation = "";
        String rsDestination = "";
        int rsBusId = 0;
        while (rs.next()) {
            rsStartTime = rs.getString("ScheduledStartTime");
            rsArrivalTime = rs.getString("ScheduledArrivalTime");
            rsDriverName = rs.getString("DriverName");
            rsBusId = rs.getInt("BusId");
            rsStartLocation = rs.getString("StartLocationName");
            rsDestination = rs.getString("DestinationName");
            System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                    date, rsStartLocation, rsDestination, rsStartTime,
                    rsArrivalTime, rsDriverName, rsBusId);
        }
        rs.close();
    }
}
