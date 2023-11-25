package com.laca.BL.FactoryUnitTransport;


import com.laca.UnitTransportImplementation.*;
import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;
import com.laca.entity.concretUsers.*;

public class FactoryUnitTransporter {
    public static UnitTransporterAbstract createUnitTransport(String name, String plate, double high, double width, String type, double maxWeight,boolean isActive) {
        if ("Walk".equalsIgnoreCase( type)) {
            return new Walk(name, plate, high, width, type, maxWeight,isActive);
        } else if ("MotorCycle".equalsIgnoreCase( type)) {
            return new Motorcycle(name, plate, high, width, type, maxWeight,isActive);
        }else if ("Truck".equalsIgnoreCase( type)) {
            return new Truck(name, plate, high, width, type, maxWeight,isActive);
        }else if ("Van".equalsIgnoreCase( type)) {

            return new Van(name, plate, high, width, type, maxWeight,isActive);
        }
        throw new IllegalArgumentException("Invalid pastry type");
    }
}
