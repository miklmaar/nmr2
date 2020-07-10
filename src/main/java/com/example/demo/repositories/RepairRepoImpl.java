package com.example.demo.repositories;

import com.example.demo.models.Motorhome;
import com.example.demo.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class RepairRepoImpl implements IMaintenanceRepository {
    private Connection conn;

    public RepairRepoImpl(){
        this.conn = DatabaseConnectionManager.getDatabaseConnection();
    }


    @Override
    public boolean addToQueue(Motorhome motorhome) {
        return false;
    }

    @Override
    public Motorhome readFirstFromQueue() {
        Motorhome motorhome = new Motorhome();
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM RepairingQueue ORDER BY Priority LIMIT 1;"
            );
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                motorhome.setMotorhomeID(rs.getInt(1));
            }
        } catch(SQLException s){
            s.printStackTrace();
        }
        return motorhome;
    }

    @Override
    public Motorhome popFromQueue() {
        Motorhome motorhome = new Motorhome();
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM RepairingQueue ORDER BY Priority LIMIT 1;"
            );
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                motorhome.setMotorhomeID(rs.getInt(1));
            }

            PreparedStatement ps1 = conn.prepareStatement(
                    "DELETE FROM RepairingQueue WHERE MotorhomeID=" + motorhome.getMotorhomeID()
            );
            ps1.executeUpdate();
        } catch(SQLException s){
            s.printStackTrace();
        }
        return motorhome;
    }

    @Override
    public LinkedList<Motorhome> readList() {
        LinkedList<Motorhome> cleanQueue = new LinkedList<>();
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM RepairingQueue ORDER BY Priority;"
            );
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Motorhome tempM = new Motorhome();
                tempM.setMotorhomeID(rs.getInt(1));
                cleanQueue.add(tempM);
            }
        } catch(SQLException s){
            s.printStackTrace();
        }
        return cleanQueue;
    }
}
