package com.laca.entity.Interfaces;

public interface LogisticsRoad {

    Production createPackage();

    Production createPackage(String type, double weight, String name, String description, double price, double height, double width);
}
