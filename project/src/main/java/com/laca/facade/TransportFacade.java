package com.laca.facade;

import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;
import com.laca.entity.RouteC.Route;
import com.sun.jdi.connect.Transport;
import com.sun.jdi.connect.spi.TransportService;


import com.laca.entity.concretProduct.Product;
import com.laca.service.ProductService;
import com.laca.service.RouteService;
import com.laca.service.UnitTransportService;

    public class TransportFacade {
        private ProductService productService;
        private RouteService routeService;
        private UnitTransportService transportService;

        public TransportFacade(ProductService productService, RouteService routeService, UnitTransportService transportService) {
            this.productService = productService;
            this.routeService = routeService;
            this.transportService = transportService;
        }

        public void registerTransport(String productId, String routeId, String transportUnitId) {

            Product product = productService.getProductById(Long.parseLong(productId));
            Route route = routeService.getRouteById(Long.parseLong(routeId));
            UnitTransporterAbstract unitTransport = transportService.getUnitTransporterById(Long.parseLong(transportUnitId));


            UnitTransporterAbstract transport = new UnitTransporterAbstract();
            transport.setProduct(product);
            transport.setRoute(route);
            transport.setUnitTransport(unitTransport);


            transportService.registerNewTransport(transport);
            System.out.println("Transporte registrado con éxito");
        }
    }

}
