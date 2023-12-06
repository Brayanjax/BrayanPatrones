package com.laca.entity.Interfaces;

import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;

public interface Observable {
    void addObserver(Observer o);
    void deleteObserver(Observer o);
    void notifyObserver(UnitTransporterAbstract u);

}
