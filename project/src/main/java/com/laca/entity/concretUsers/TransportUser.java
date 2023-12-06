package com.laca.entity.concretUsers;



import com.laca.entity.Interfaces.IConstructUser;
import com.laca.entity.Interfaces.Observer;
import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;
import com.laca.entity.PackageUnitAbstract.Users;

public class TransportUser extends Users implements Observer {
    public TransportUser(String name, String identification, String factoryName, String type) {
        super(name,identification,factoryName,type);
    }



    @Override
    public void update(UnitTransporterAbstract unitTransporterAbstract) {
        System.out.println("Se registro o elimino una unidad de transporte: "+unitTransporterAbstract);
    }
}
