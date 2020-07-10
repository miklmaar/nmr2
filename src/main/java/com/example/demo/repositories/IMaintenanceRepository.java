package com.example.demo.repositories;

import com.example.demo.models.Motorhome;

import java.util.LinkedList;

public interface IMaintenanceRepository {
    public boolean addToQueue(Motorhome motorhome);

    public Motorhome readFirstFromQueue();

    public Motorhome popFromQueue();

    public LinkedList<Motorhome> readList();
}