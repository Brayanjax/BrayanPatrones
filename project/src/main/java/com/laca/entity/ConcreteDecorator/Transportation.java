package com.laca.entity.ConcreteDecorator;
import com.laca.entity.PackageUnitAbstract.UnitTransportDecorator;
import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;


public class Transportation extends UnitTransportDecorator {

    public Transportation(UnitTransporterAbstract unitTransporterAbstract) {
        super(unitTransporterAbstract);
    }
    @Override
    public String Transport(){
        return "Se agrego capacidad de entregar";// ver como hacer para eliminar el producto
    }
}
