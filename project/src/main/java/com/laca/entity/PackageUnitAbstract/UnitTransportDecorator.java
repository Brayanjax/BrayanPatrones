package com.laca.entity.PackageUnitAbstract;


import com.laca.entity.Interfaces.UnitTransDecorator;

public abstract class UnitTransportDecorator implements UnitTransDecorator {
    protected UnitTransporterAbstract unitTransporterAbstract;

    public UnitTransportDecorator(UnitTransporterAbstract unitTransporterAbstract) {
        this.unitTransporterAbstract = unitTransporterAbstract;
    }

    @Override
    public String Transport() {
        return "Paquete entregado";
    }
}
