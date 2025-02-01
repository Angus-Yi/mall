package com.chang.mall.service;

import com.chang.mall.dto.ProductRequest;
import com.chang.mall.entity.Product;

import java.util.Optional;

public interface ProductService {
    Optional<Product> getProductById(Integer id);

    Product createProduct(ProductRequest productRequest);

    Product updateProduct(Integer productId,ProductRequest productRequest );

    void deleteProduct(Integer productId);
}
