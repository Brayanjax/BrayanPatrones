package com.laca.entity.Interfaces;

import com.laca.entity.concretProduct.Product;
import com.laca.entity.concretUsers.ClientUser;

public interface Handler {
  void handlePackageCreation(ClientUser client, Product product);
  void setNextHandler(Handler nextHandler);
}
