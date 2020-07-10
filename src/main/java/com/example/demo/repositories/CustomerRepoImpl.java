package com.example.demo.repositories;

import com.example.demo.models.Customer;
import com.example.demo.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepoImpl implements ICustomerRepository {
    private Connection conn;

    public CustomerRepoImpl() { this.conn = DatabaseConnectionManager.getDatabaseConnection(); }

    @Override
    public boolean create(Customer customer) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Customers(CustomerCPR, CustomerName, DriversLicenseID, CreditCardNo) " +
                        "VALUES ('" +
                            customer.getCPR() + "','" +
                            customer.getName() + "','" +
                            customer.getDriversLicenseID() + "','" +
                            customer.getCreditCardNo() + "')"
            );
            ps.executeUpdate();
            return true;
        } catch(SQLException s){
            s.printStackTrace();
        }
        return false;
    }

    @Override
    public Customer read(String cpr) {
        Customer customer = new Customer();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Customers WHERE CustomerCPR='" + cpr +"'");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                customer.setCPR(rs.getString(1));
                customer.setName(rs.getString(2));
                customer.setDriversLicenseID(rs.getString(3));
                customer.setCreditCardNo(rs.getString(4));
            }

        } catch(SQLException s){
            s.printStackTrace();
        }

        return customer;
    }

    @Override
    public List<Customer> readAll() {
        List<Customer> allCustomers = new ArrayList<Customer>();

        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Customers");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Customer tempC = new Customer();
                tempC.setCPR(rs.getString(1));
                tempC.setName(rs.getString(2));
                tempC.setDriversLicenseID(rs.getString(3));
                tempC.setCreditCardNo(rs.getString(4));
                allCustomers.add(tempC);
            }

        }catch (SQLException s){
            s.printStackTrace();
        }

        return allCustomers;
    }

    @Override
    public boolean update(Customer customer) {
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE Customers SET " +
                        "CustomerName='" + customer.getName() + "', " +
                        "DriversLicenseID='" + customer.getDriversLicenseID() + "'," +
                        "CreditCardNo='" + customer.getCreditCardNo() + "'" +
                        "WHERE CustomerCPR='" + customer.getCPR() + "';"
            );
            ps.executeUpdate();
            return true;
        } catch(SQLException s){
            s.printStackTrace();
        }
        return false;
    }

    //
    //
    //DELETE CUSTOMER
    //
    //

    @Override
    public boolean delete(String cpr) {
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM Customers WHERE CustomerCPR ='" + cpr + "';"
            );
            ps.executeUpdate();
            return true;
        } catch(SQLException s){
            s.printStackTrace();
        }
        return false;
    }
}
