package com.laca.UnitTransportImplementation;


import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;


public class Truck extends UnitTransporterAbstract {
    public Truck() {
        this.setType("Truck");
        this.setMaxWeight(60);
    }

    public Truck(String name, String plate, double high, double width, String type, double maxWeight,boolean isActive,Long id) {
        super(name, plate, high, width, type, maxWeight,isActive,id);
        this.setType("Truck");
        this.setMaxWeight(60);
    }
}
