package com.laca.UnitTransportImplementation;


import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;

public class Motorcycle extends UnitTransporterAbstract {
    public Motorcycle() {
        this.setType("Motorcycle");
        this.setMaxWeight(30);
    }

    public Motorcycle(String name, String plate, double high, double width, String type, double maxWeight,boolean isActive,Long id) {
        super(name, plate, high, width, type, maxWeight,isActive,id);
        this.setType("Motorcycle");
        this.setMaxWeight(30);
    }
}
