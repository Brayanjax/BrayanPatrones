package com.laca.entity.concreteHandlers;

import com.laca.entity.Base.BaseHandler;
import com.laca.entity.Interfaces.LogisticsRoad;
import com.laca.entity.Interfaces.Production;
import com.laca.entity.concretProduct.Product;
import com.laca.entity.concretUsers.ClientUser;

public class HandlersClient extends BaseHandler {

  private final LogisticsRoad logisticsRoad;

  public HandlersClient(LogisticsRoad logisticsRoad) {
    this.logisticsRoad = logisticsRoad;
  }

  @Override
  public void handlePackageCreation(ClientUser client, Product product) {
    System.out.println("Handling package creation for client: " + client.getName());
    System.out.println("Product details:");
    System.out.println("Type: " + product.getType());
    System.out.println("Weight: " + product.getWeight() + " kg");
    System.out.println("Name: " + product.getName());
    System.out.println("Description: " + product.getDescription());

    Production createdPackage = logisticsRoad.createPackage(
        product.getType(), product.getWeight(), product.getName(),
        product.getDescription(), product.getPrice(), product.getHeight(), product.getWidth()
    );

    System.out.println("Package created by LogisticsRoad: " + createdPackage);

    passToNextHandler(client, product);
  }
}
