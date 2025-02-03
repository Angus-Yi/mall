package com.chang.mall.controller;

import com.chang.mall.constants.ProductCategory;
import com.chang.mall.dto.ProductRequest;
import com.chang.mall.entity.Product;
import com.chang.mall.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // 查詢商品列表
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            // 查詢條件
            @RequestParam(required = false) String search,
            @RequestParam(required = false) ProductCategory productCategory,

            // 排序
            @RequestParam(required = false) String orderBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,

            // 分頁
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "10") @Max(50) @Min(1) Integer size) {

        Page<Product> products = productService.getProducts(search, productCategory, orderBy, sortDirection, page, size);

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    // 查詢單一商品
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {

        Optional<Product> product = productService.getProductById(productId);

        if (product.isPresent()) {

            return ResponseEntity.status(HttpStatus.OK).body(product.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 新增單一商品
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {

        Product product = productService.createProduct(productRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    // 更新單一商品
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

    // 刪除單一商品
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {

        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
