package com.laca.PackageState;


import com.laca.entity.Interfaces.PackageState;

public class DeliveredState implements PackageState {
    @Override
    public void handleState(PackageContext context) {
        System.out.println("El paquete está en entregado.");

    }
}
