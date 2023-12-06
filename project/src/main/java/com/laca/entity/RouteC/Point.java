package com.laca.entity.RouteC;

public class Point {
    private String name;
    private String description;
    private Coordinates coordinates;

    public Point(String name, String description, Coordinates coordinates) {
        this.name = name;
        this.description = description;
        this.coordinates = coordinates;
    }

    public Point() {

    }


    public Point clone() {

        return new Point(name, description, coordinates.clone());
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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Point{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}