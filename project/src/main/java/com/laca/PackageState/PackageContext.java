package com.laca.PackageState;


import com.laca.entity.Interfaces.PackageState;

public class PackageContext{
    private PackageState state;

    public PackageContext(PackageState state) {
        this.state = state;
    }

    public void setState(PackageState state) {
        this.state = state;
    }

    public void applyState() {
        state.handleState(this);
    }
}