package com.laca.entity.Base;

import com.laca.entity.Interfaces.Handler;
import com.laca.entity.concretProduct.Product;
import com.laca.entity.concretUsers.ClientUser;

public abstract class BaseHandler implements Handler {
  private Handler nextHandler;

  @Override
  public void setNextHandler(Handler nextHandler) {
    this.nextHandler = nextHandler;
  }
  protected void passToNextHandler(ClientUser client, Product product) {
    if (nextHandler != null) {
      nextHandler.handlePackageCreation(client, product);
    } else {
      System.out.println("Espera un momento, el sistema esta sobrecargado.");
    }
  }
}
