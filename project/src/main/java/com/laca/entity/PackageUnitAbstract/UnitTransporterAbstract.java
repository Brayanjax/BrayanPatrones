package com.laca.entity.PackageUnitAbstract;


import com.laca.entity.Interfaces.IProduct;
import com.laca.entity.Interfaces.Observable;
import com.laca.entity.Interfaces.Observer;

import java.util.ArrayList;

public  class UnitTransporterAbstract implements IProduct, Observable {
        private String name;
        private String plate;
        private double high;
        private double width;
        private String type;
        private double maxWeight;
        private boolean isActive;

        private Long id;
        private ArrayList<Observer> unitTransporterAbstracts;
        public UnitTransporterAbstract(){}


    public UnitTransporterAbstract(String name, String plate, double high, double width, String type, double maxWeight,boolean isActive,Long id) {
        this.name = name;
        this.plate = plate;
        this.high =  high;
        this.width = width;
        this.type = type;
        this.maxWeight =  maxWeight;
        this.isActive= isActive;
        this.id = id;
    }

    public static void setId(long aLong) {
    }
    @Override
    public void addObserver(Observer o) {
        unitTransporterAbstracts.add(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        unitTransporterAbstracts.remove(o);
    }

    @Override
    public void notifyObserver(UnitTransporterAbstract unitTransporterAbstract) {
        for (Observer observer : unitTransporterAbstracts){
            observer.update(unitTransporterAbstract);
        }
    }
    @Override
    public IProduct clonar() {
        UnitTransporterAbstract unitTransporter = null;
        try {
            unitTransporter=(UnitTransporterAbstract) clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return unitTransporter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public boolean getIsActive() {return isActive;}

    public void setIsActive(boolean active) {isActive = active;}

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UnitTransporter{" +
                "name='" + name + '\'' +
                ", plate='" + plate + '\'' +
                ", high=" + high +
                ", width=" + width +
                ", type='" + type + '\'' +
                ", maxWeight=" + maxWeight +
                '}';
    }
}
