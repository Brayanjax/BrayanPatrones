package com.laca.facade;

import com.laca.entity.RouteC.Route;
import com.laca.entity.concretProduct.Product;
import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;
import com.laca.PackageState.PackageContext;
import com.laca.PackageState.PendingState;
import com.laca.PackageState.InTransitState;
import com.laca.PackageState.DeliveredState;


public class FacadeSend {

    private Route route;
    private Product product;
    private UnitTransporterAbstract unitTransport;
    private PackageContext packageContext;



    public FacadeSend() {
        this.packageContext = new PackageContext(new PendingState());
    }

    public void prepareShipment(Product product, UnitTransporterAbstract unitTransport, Route route) {
        this.product = product;
        this.unitTransport = unitTransport;
        this.route = route;
    }

    public void sendPackage() {

        this.packageContext.setState(new InTransitState());
    }

    public void packageDelivered() {

        this.packageContext.setState(new DeliveredState());
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setUnitTransport(UnitTransporterAbstract unitTransport) {
        this.unitTransport = unitTransport;
    }

    public PackageContext getPackageContext() {
        return packageContext;
    }

    public void setPackageContext(PackageContext packageContext) {
        this.packageContext = packageContext;
    }


}
