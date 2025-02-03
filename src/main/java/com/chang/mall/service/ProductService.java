package com.chang.mall.service;

import com.chang.mall.constants.ProductCategory;
import com.chang.mall.dto.ProductRequest;
import com.chang.mall.entity.Product;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {

    Page<Product> getProducts(String search, ProductCategory productCategory, String orderBy, String sortDirection, Integer page, Integer size);

    Optional<Product> getProductById(Integer id);

    Product createProduct(ProductRequest productRequest);

    Product updateProduct(Product product,ProductRequest productRequest );

    void deleteProduct(Integer productId);
}
