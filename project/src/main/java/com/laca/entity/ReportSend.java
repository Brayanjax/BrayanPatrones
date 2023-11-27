package com.laca.entity;

public class ReportSend {

    private String UserName;
    private  String rutes;
    private  String product;
    private  String unitTransport;

    public ReportSend(String userName, String rutes, String product, String unitTransport) {
        UserName = userName;
        this.rutes = rutes;
        this.product = product;
        this.unitTransport = unitTransport;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getRutes() {
        return rutes;
    }

    public void setRutes(String rutes) {
        this.rutes = rutes;
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
