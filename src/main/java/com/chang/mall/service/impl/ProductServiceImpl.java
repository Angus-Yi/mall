package com.chang.mall.service.impl;

import com.chang.mall.Repository.ProductRepository;
import com.chang.mall.dto.ProductRequest;
import com.chang.mall.entity.Product;
import com.chang.mall.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> getProductById(Integer id) {

        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(ProductRequest productRequest) {

        Date now = new Date();
        Product product = new Product();
        BeanUtils.copyProperties(productRequest, product);
        product.setCreateDate(now);
        product.setLastModifiedDate(now);

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Integer productId, ProductRequest productRequest) {
        return productRepository.findById(productId)
                .map(product -> {

                    BeanUtils.copyProperties(productRequest, product);

                    product.setLastModifiedDate(new Date());

                    return productRepository.save(product);
                })
                .orElseThrow(() -> new EntityNotFoundException("找不到 ID 為 " + productId + " 的產品資訊"));
    }

    @Override
    public void deleteProduct(Integer productId) {

        productRepository.deleteById(productId);
    }
}

