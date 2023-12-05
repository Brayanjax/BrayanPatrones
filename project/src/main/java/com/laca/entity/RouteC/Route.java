package com.laca.entity.RouteC;


import com.laca.entity.Interfaces.RoutePrototype;

public class Route implements RoutePrototype {
    private String type;
    private String name;
    private String description;
    private Point startPoint;
    private Point endPoint;
    private  Long id;

    public Route(String type, String name, String description, Point startPoint, Point endPoint,Long id) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.id = id;
    }

    public Route() {

    }

    public Route(String type, String name, String description, Point clone, Point clone1) {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public RoutePrototype clone() {
        return new Route(type, name, description, startPoint.clone(), endPoint.clone());
    }

    @Override
    public String toString() {
        return "Route{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                '}';
    }
}
