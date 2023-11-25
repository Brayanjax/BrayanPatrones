package com.laca.entity.concretUsers;


import com.laca.entity.Interfaces.IConstructUser;
import com.laca.entity.PackageUnitAbstract.Users;

public class ClientUser extends Users {
    public ClientUser(String name, String identification, String factoryName, String type) {
        super(name,identification,factoryName,type);
    }
}
