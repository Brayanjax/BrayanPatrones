package com.laca.entity.concretCreator;


import com.laca.entity.Interfaces.LogisticsRoad;
import com.laca.entity.Interfaces.Production;
import com.laca.entity.concretProduct.Product;

public class ProductLogistics implements LogisticsRoad {

    @Override
    public Production createPackage() {
        return null;
    }

    @Override
    public Production createPackage(String type, double weight, String name, String description, double price, double height, double width) {
        return new Product(type, weight, name, description, price, height, width);
    }

}
