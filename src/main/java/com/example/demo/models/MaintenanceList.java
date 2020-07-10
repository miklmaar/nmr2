package com.example.demo.models;

import java.util.LinkedList;

public class MaintenanceList {
    LinkedList<Motorhome> mhToClean;
    LinkedList<Motorhome> mhToRepair;

    public MaintenanceList(LinkedList<Motorhome> mhToClean, LinkedList<Motorhome> mhToRepair) {
        this.mhToClean = mhToClean;
        this.mhToRepair = mhToRepair;
    }


    public LinkedList<Motorhome> getMhToClean() {
        return mhToClean;
    }

    public void setMhToClean(LinkedList<Motorhome> mhToClean) {
        this.mhToClean = mhToClean;
    }

    public LinkedList<Motorhome> getMhToRepair() {
        return mhToRepair;
    }

    public void setMhToRepair(LinkedList<Motorhome> mhToRepair) {
        this.mhToRepair = mhToRepair;
    }


}
