package com.example.demo.repositories;

import com.example.demo.models.Motorhome;
import com.example.demo.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MotorhomeRepoImpl implements IMotorhomeRepository {
    private Connection conn;

    public MotorhomeRepoImpl(){
        this.conn = DatabaseConnectionManager.getDatabaseConnection();
    }

    @Override
    public boolean create(Motorhome motorhome) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Motorhomes (LicensePlate, Model, Beds, Price, Cleaned, Repaired, Active) " +
                    "VALUES ('"+
                    motorhome.getLicensePlate() + "','" +
                    motorhome.getModelName() + "','" +
                    motorhome.getNumberOfBeds() + "','" +
                    motorhome.getPricePerNight() + "'," +
                    motorhome.isCleaned() + "," +
                    motorhome.isRepaired() + "," +
                    motorhome.isActive() + "" +
                    ");");
            ps.executeUpdate();
            return true;
        } catch(SQLException s){
            s.printStackTrace();
        }
        return false;
    }

    @Override
    public Motorhome read(int ID) {
        Motorhome motorhome = new Motorhome();

        try {
            PreparedStatement getSingleMH = conn.prepareStatement("SELECT * FROM Motorhomes WHERE MotorhomeID='" + ID + "'");
            ResultSet rs = getSingleMH.executeQuery();
            while(rs.next()){
                motorhome.setMotorhomeID(rs.getInt(1));
                motorhome.setLicensePlate(rs.getString(2));
                motorhome.setModelName(rs.getString(3));
                motorhome.setNumberOfBeds(rs.getInt(4));
                motorhome.setPricePerNight(rs.getDouble(5));
                motorhome.setCleaned(rs.getBoolean(6));
                motorhome.setRepaired(rs.getBoolean(7));
                motorhome.setActive(rs.getBoolean(8));
            }
        }
        catch(SQLException s){
            s.printStackTrace();
        }

        return motorhome;
    }

    @Override
    public List<Motorhome> readAll() {
        List<Motorhome> allMotorhomes = new ArrayList<Motorhome>();

        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Motorhomes");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Motorhome tempMH = new Motorhome();
                tempMH.setMotorhomeID(rs.getInt(1));
                tempMH.setLicensePlate(rs.getString(2));
                tempMH.setModelName(rs.getString(3));
                tempMH.setNumberOfBeds(rs.getInt(4));
                tempMH.setPricePerNight(rs.getDouble(5));
                tempMH.setCleaned(rs.getBoolean(6));
                tempMH.setRepaired(rs.getBoolean(7));
                tempMH.setActive(rs.getBoolean(8));
                allMotorhomes.add(tempMH);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return allMotorhomes;
    }

    @Override
    public boolean update(Motorhome motorhome) {
        try{
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE Motorhomes SET " +
                    "LicensePlate='" + motorhome.getLicensePlate() + "', " +
                    "Model='" + motorhome.getModelName() + "', " +
                    "Beds='" + motorhome.getNumberOfBeds() + "', " +
                    "Price='" + motorhome.getPricePerNight() + "', " +
                    "Cleaned=" + motorhome.isCleaned() + ", " +
                    "Repaired=" + motorhome.isRepaired() + ", " +
                    "Active=" + motorhome.isActive() + " "+
                    "WHERE MotorhomeID=" + motorhome.getMotorhomeID() + ";"
            );
            ps.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int ID) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE Motorhomes SET Active=false WHERE MotorhomeID='" + ID +"';");
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean activate(int ID){
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE Motorhomes SET Active=true WHERE MotorhomeID='" + ID +"';");
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
