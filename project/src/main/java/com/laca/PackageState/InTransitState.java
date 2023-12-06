package com.laca.PackageState;

import com.laca.PackageState.PackageContext;
import com.laca.entity.Interfaces.PackageState;


public class InTransitState implements PackageState {
    @Override
    public void handleState(PackageContext context) {
        System.out.println("El paquete está en tránsito.");

    }
}