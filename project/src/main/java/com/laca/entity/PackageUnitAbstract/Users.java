package com.laca.entity.PackageUnitAbstract;

import com.laca.entity.Interfaces.IConstructUser;

public class Users implements IConstructUser {
    private String name;
    private String identification;
    private  String factoryName;
    private  String type;
    private Long id;



    public Users(String name, String identification, String factoryName, String type) {
        this.name = name;
        this.identification = identification;
        this.factoryName = factoryName;
        this.type = type;
    }

    public Users() {

    }

    public Long getId() {
        return id;
    }

    public static void setId(long aLong) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void construction(String name, String identification, String factoryName, String type) {
        this.name = name;
        this.identification = identification;
        this.factoryName = factoryName;
        this.type = type;
    }
}
