package com.laca.UnitTransportImplementation;


import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;


public class Walk extends UnitTransporterAbstract {
    public Walk() {
        this.setType("Walk");
        this.setMaxWeight(30);
    }


    public Walk(String name, String plate, double high, double width, String type, double maxWeight,boolean isActive) {
        super(name, plate, high, width, type, maxWeight,isActive);
        this.setType("Walk");
        this.setMaxWeight(30);
    }

}
