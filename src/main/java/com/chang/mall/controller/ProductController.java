package com.chang.mall.controller;

import com.chang.mall.constants.ProductCategory;
import com.chang.mall.dto.ProductRequest;
import com.chang.mall.entity.Product;
import com.chang.mall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts2(@RequestParam(required = false) String search,
                                                      @RequestParam(required = false) ProductCategory productCategory,
                                                      @RequestParam(required = false) String orderBy,
                                                      @RequestParam(defaultValue = "DESC") String sortDirection) {

        List<Product> products = productService.getProducts(search, productCategory, orderBy, sortDirection);

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {

        Optional<Product> product = productService.getProductById(productId);

        if (product.isPresent()) {

            return ResponseEntity.status(HttpStatus.OK).body(product.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {

        Product product = productService.createProduct(productRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {

        Optional<Product> product = productService.getProductById(productId);

        if (product.isPresent()) {

            Product updateProduct = productService.updateProduct(product.get(), productRequest);

            return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {

        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
