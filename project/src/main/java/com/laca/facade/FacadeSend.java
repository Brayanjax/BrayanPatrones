package com.laca.facade;

import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;
import com.laca.entity.RouteC.Route;
import com.laca.entity.concretProduct.Product;

public class FacadeSend {

    private String UserName;
    private String routes;
    private Product product;
    private UnitTransporterAbstract unitTransport;

    public FacadeSend(String userName, Route routes, Product product, UnitTransporterAbstract unitTransport) {
        UserName = userName;
        this.routes = routes;
        this.product = product;
        this.unitTransport = unitTransport;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Route getRoutes() {
        return routes;
    }

    public void setRoutes(String routes) {
        this.routes = routes;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getUnitTransport() {
        return unitTransport;
    }

    public void setUnitTransport(String unitTransport) {
        this.unitTransport = unitTransport;
    }
}
