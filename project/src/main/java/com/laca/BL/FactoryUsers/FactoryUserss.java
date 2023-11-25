package com.laca.BL.FactoryUsers;



import com.laca.entity.Interfaces.IConstructUser;
import com.laca.entity.concretUsers.*;

public class FactoryUserss {

    public static IConstructUser createUser(String name, String identification, String factoryName, String type) {
        if ("AdminUser".equalsIgnoreCase(type)) {
            return new AdminUser(name,identification,factoryName,type);
        } else if ("ClientUser".equalsIgnoreCase(type)) {
            return new ClientUser(name,identification,factoryName,type);
        }else if ("RutesUser".equalsIgnoreCase(type)) {
            return new RutesUser(name,identification,factoryName,type);
        }else if ("TransportUser".equalsIgnoreCase(type)) {
            return new TransportUser(name,identification,factoryName,type);
        }else if ("VisualizatorPackagesInProgress".equalsIgnoreCase(type)) {
            return new VisualizatorPackagesInProgress(name,identification,factoryName,type);
        }
        throw new IllegalArgumentException("Invalid pastry type: " + type);
    }
}
