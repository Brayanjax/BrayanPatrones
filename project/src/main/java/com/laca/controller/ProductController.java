package com.laca.controller;





import com.laca.entity.Transporter;
import com.laca.entity.concretProduct.Product;
import com.laca.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:4200/")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping
    public List<Product> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products;
    }

    @PostMapping
    public Transporter saveTransporter(@RequestBody Transporter transporter) {
        return productService.saveTransporter(transporter);
    }

    @PutMapping("/{transporterId}")
    public ResponseEntity<?> updateTransporter(
            @PathVariable Long transporterId,
            @RequestBody Transporter updatedTransporter) {
        try {
            Transporter updated = transporterService.updateTransporter(transporterId, updatedTransporter);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating transporter: " + e.getMessage());
        }
    }

    @GetMapping("/{transporterId}")
    public ResponseEntity<?> getTransporterById(@PathVariable Long transporterId) {
        try {
            Transporter transporter = transporterService.getTransporterById(transporterId);
            return ResponseEntity.ok(transporter);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transporter not found: " + e.getMessage());
        }
    }

    @DeleteMapping("/{transporterId}")
    public ResponseEntity<?> deleteTransporter(@PathVariable Long transporterId) {
        try {

            boolean isDeleted = transporterService.deleteTransporter(transporterId);
            Transporter transporter= new Transporter();
            transporter.setId(transporterId);
            if (isDeleted) {
                return ResponseEntity.ok(transporter);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transporterId);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting transporter: " + e.getMessage());
        }

    }
}
