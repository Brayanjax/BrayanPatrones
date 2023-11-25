package com.laca.entity.concretProduct;


import com.laca.entity.Interfaces.Production;

public class Product implements Production {
    private String type;
    private double weight;
    private String name;
    private String description;
    private double price;
    private double height;
    private double width;

    public Product(String type, double weight, String name, String description, double price, double height, double width) {
        this.type = type;
        this.weight = weight;
        this.name = name;
        this.description = description;
        this.price = price;
        this.height = height;
        this.width = width;
    }

    public Product() {

    }

    @Override
    public void create() {
        System.out.println("Package created:");
        System.out.println("Type: " + type);
        System.out.println("Weight: " + weight + " kg");
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Price: $" + price);
        System.out.println("Height: " + height + " cm");
        System.out.println("Width: " + width + " cm");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setId(long aLong) {/////////////////////REVISAAAAAR LINEA 54 EN PRODUCT SERVICE
    }
}
