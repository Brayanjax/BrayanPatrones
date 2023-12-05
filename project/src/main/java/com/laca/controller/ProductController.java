package com.laca.controller;


import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;
import com.laca.entity.Transporter;
import com.laca.entity.concretProduct.Product;
import com.laca.entity.concretUsers.ClientUser;
import com.laca.service.ProductService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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
    public List<Product> getAllProducts() {// se encarga de traer todos las unidades de transporte
        List<Product> product = productService.getAllProducts();
        return product;
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/{product_Id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long ProductId,
            @RequestBody Product updatedProduct) {
        try {
            Product updated = productService.updatedProduct (ProductId, updatedProduct);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating unit Product: " + e.getMessage());
        }
    }

    @GetMapping("/{product_Id}")
    public ResponseEntity<?> getProductId(@PathVariable Long ProductId) {
        try {
            Product product = productService.getProductById(ProductId);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("unit transporter not found: " + e.getMessage());
        }
    }

    @DeleteMapping("/{product_Id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long ProductId) {
        try {
            boolean isDeleted = productService.deleteProduct(ProductId);
            Transporter transporter= new Transporter();
            transporter.setId(ProductId);
            if (isDeleted) {
                return ResponseEntity.ok(transporter);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProductId);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting Product: " + e.getMessage());
        }

    }

}
