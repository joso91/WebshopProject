package webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.model.Product;
import webshop.service.ProductService;

import javax.validation.Valid;

@RestController
@RequestMapping("/webshop/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/create-product")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(productService.createUpdate(product), HttpStatus.CREATED);
    }

    @PutMapping("/update-product/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product, @PathVariable Long id) {
        if (productService.read(id) != null) {
            product.setId(id);
            return new ResponseEntity<>(productService.createUpdate(product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read-product/{id}")
    public ResponseEntity<Product> readProduct(@PathVariable Long id) {
        Product product = productService.read(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
