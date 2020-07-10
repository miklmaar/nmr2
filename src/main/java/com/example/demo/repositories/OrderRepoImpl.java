package com.example.demo.repositories;

import com.example.demo.models.Customer;
import com.example.demo.models.Motorhome;
import com.example.demo.models.Order;
import com.example.demo.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepoImpl implements IOrdersRepository {
    private Connection conn;

    public OrderRepoImpl(){ this.conn = DatabaseConnectionManager.getDatabaseConnection(); }

    @Override
    public boolean create(Order order) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Orders (CustomerCPR, NumberOfDays, DropOffDistance, DroppedOff)" +
                    "VALUES ('" +
                    order.getCustomer().getCPR() + "'," +
                    order.getNumberOfDays() + "," +
                    order.getDropOffDistance() + "," +
                    order.isDroppedOff() +
                    ")");
            ps.executeUpdate();
            return true;
        } catch(SQLException s){
            s.printStackTrace();
        }
        return false;
    }

    //
    //
    //Add a motorhome to existing order.
    //
    //

    @Override
    public boolean addMotorhomeToOrder(int OrderID, int mhID){
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT IGNORE INTO motorhome_orders (MotorhomeID, OrderID) VALUES (" + mhID + ", " + OrderID + ");");
            ps.executeUpdate();
            return true;
        } catch(SQLException s){
            s.printStackTrace();
        }
        return false;
    }

    //
    //
    //Delete motorhome from existing order
    //
    //

    @Override
    public boolean deleteMotorhomeFromOrder(int OrderID, int mhID){
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM Motorhome_Orders WHERE MotorhomeID=" + mhID + " AND OrderID=" + OrderID + ";"
            );
            ps.executeUpdate();
            return true;
        } catch (SQLException s){
            s.printStackTrace();
        }
        return false;
    }

    //
    //
    //READING ORDERS
    //
    //

    //Reads Order and Customer separately and puts Customer in the Order in the end.

    @Override
    public Order read(int id) {
        Order order = new Order();
        Customer customer = new Customer();
        ArrayList<Motorhome> mhs = new ArrayList<Motorhome>();

        try{
            PreparedStatement findCustomerInOrder = conn.prepareStatement("" +
                    "SELECT Orders.OrderID, Seasons, NumberOfDays, Orders.CustomerCPR, CustomerName, DriversLicenseID, CreditCardNo, m.MotorhomeID, m.LicensePlate, m.Model, m.Beds, m.Price, m.Cleaned, m.Repaired, m.Active, Orders.DropOffDistance, orders.DroppedOff\n" +
                    "FROM orders\n" +
                    "LEFT JOIN Customers c ON orders.CustomerCPR=c.CustomerCPR\n" +
                    "LEFT JOIN motorhome_orders mo on orders.OrderID = mo.OrderID\n" +
                    "LEFT JOIN motorhomes m on mo.MotorhomeID = m.MotorhomeID " +
                    "WHERE orders.OrderID=" + id);

            ResultSet rs = findCustomerInOrder.executeQuery();

            while(rs.next()){
                order.setOrderID(rs.getInt(1));
                order.setNumberOfDays(rs.getInt(3));
                customer.setCPR(rs.getString(4));
                customer.setName(rs.getString(5));
                customer.setDriversLicenseID(rs.getString(6));
                customer.setCreditCardNo(rs.getString(7));
                order.setCustomer(customer);

                Motorhome tempM = new Motorhome();
                tempM.setMotorhomeID(rs.getInt(8));
                tempM.setLicensePlate(rs.getString(9));
                tempM.setModelName(rs.getString(10));
                tempM.setNumberOfBeds(rs.getInt(11));
                tempM.setPricePerNight(rs.getDouble(12));
                tempM.setCleaned(rs.getBoolean(13));
                tempM.setRepaired(rs.getBoolean(14));
                tempM.setActive(rs.getBoolean(15));
                mhs.add(tempM);

                order.setDropOffDistance(rs.getInt(16));
                order.setDroppedOff(rs.getBoolean(17));
            }
            order.setMhInOrder(mhs);
            return order;
        } catch(SQLException e){
            e.printStackTrace();
        }

        return order;
    }

    //
    //
    //READING ALL ORDERS
    //Motorhomes will not be displayed when all orders are listed.
    //
    //

    @Override
    public List<Order> readAll() {
        List<Order> allOrders = new ArrayList<Order>();

        try{
            PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT * FROM Orders INNER JOIN Customers ON orders.CustomerCPR=customers.CustomerCPR ");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Order tempO = new Order();
                Customer tempC = new Customer();

                tempO.setOrderID(rs.getInt(1));
                tempC.setCPR(rs.getString(3));
                tempO.setNumberOfDays(rs.getInt(4));
                tempO.setDroppedOff(rs.getBoolean(5));
                tempO.setDropOffDistance(rs.getInt(6));
                tempC.setName(rs.getString(8));
                tempC.setDriversLicenseID(rs.getString(9));
                tempC.setCreditCardNo(rs.getString(10));
                tempO.setCustomer(tempC);
                allOrders.add(tempO);
            }

        } catch(SQLException s){
            s.printStackTrace();
        }

        return allOrders;
    }

    @Override
    public boolean update(Order order) {
        return false;
    }

    //
    //
    //Deleting an order and everything tied to it.
    //
    //

    @Override
    public boolean delete(int orderid) {
        try{
            PreparedStatement ps0 = conn.prepareStatement(
                    "DELETE FROM Motorhome_Orders WHERE OrderID=" + orderid + ";"
            );
            ps0.executeUpdate();
            PreparedStatement ps1 = conn.prepareStatement(
                    "DELETE FROM Orders WHERE OrderID=" + orderid + ";"
            );
            ps1.executeUpdate();
            return true;
        } catch (SQLException s){
            s.printStackTrace();
        }
        return false;
    }

    //
    //
    //REGISTERING DROP OFFS
    //
    //

    @Override
    public boolean finishOrder(Order order) {
        try{
            PreparedStatement ps0 = conn.prepareStatement(
                    "UPDATE Orders SET " +
                        "DroppedOff=true" + "," +
                        "DropOffDistance=" + order.getDropOffDistance() + " " +
                        "WHERE OrderID=" + order.getOrderID() + ";"
            );
            ps0.executeUpdate();

            PreparedStatement ps1 = conn.prepareStatement(
                    "UPDATE Motorhomes m JOIN Motorhome_Orders mo ON mo.MotorhomeID = m.MotorhomeID " +
                        "SET m.Cleaned=false, m.Repaired=false " +
                        "WHERE mo.OrderID=" + order.getOrderID() + ";"
            );
            ps1.executeUpdate();

            //Adding to cleaning queue
            String prepStat = "INSERT IGNORE INTO CleaningQueue(MotorhomeID) VALUES ";

            for(Motorhome motorhome : order.getMhInOrder()){
                prepStat += "(" + motorhome.getMotorhomeID() + "),";
            }

            prepStat = prepStat.substring(0, prepStat.length()-1);
            prepStat += ";";

            PreparedStatement ps2 = conn.prepareStatement(prepStat);
            ps2.executeUpdate();

            //Adding to repairing queue
            String prepStat2 = "INSERT IGNORE INTO RepairingQueue(MotorhomeID) VALUES ";

            for(Motorhome motorhome : order.getMhInOrder()){
                prepStat2 += "(" + motorhome.getMotorhomeID() + "),";
            }

            prepStat2 = prepStat2.substring(0, prepStat2.length()-1);
            prepStat2 += ";";

            PreparedStatement ps3 = conn.prepareStatement(prepStat2);
            ps3.executeUpdate();
            return true;
        } catch (SQLException s){
            s.printStackTrace();
        }
        return false;
    }
}
