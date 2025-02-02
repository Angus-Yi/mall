package com.chang.mall.service;

import com.chang.mall.constants.ProductCategory;
import com.chang.mall.dto.ProductRequest;
import com.chang.mall.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getProducts(String search, ProductCategory productCategory, String orderBy, String sortDirection);

    Optional<Product> getProductById(Integer id);

    Product createProduct(ProductRequest productRequest);

    Product updateProduct(Product product,ProductRequest productRequest );

    void deleteProduct(Integer productId);
}
