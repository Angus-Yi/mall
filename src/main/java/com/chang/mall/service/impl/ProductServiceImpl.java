package com.chang.mall.service.impl;

import com.chang.mall.Repository.ProductRepository;
import com.chang.mall.constants.ProductCategory;
import com.chang.mall.dto.ProductRequest;
import com.chang.mall.entity.Product;
import com.chang.mall.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<Product> getProducts(String search,
                                      ProductCategory productCategory,
                                      String orderBy,
                                      String sortDirection) {

        // 根據[商品條件]執行查詢
        Specification<Product> specification = (root, query, criteriaBuilder) -> {

            List<Predicate> predicateList = new ArrayList<>();

            // 根據[名稱]查詢
            if (search != null && !search.trim().isEmpty()) {

                predicateList.add(criteriaBuilder.like(root.get("productName"), "%" + search + "%"));
            }

            // 根據[類別]查詢
            if (productCategory != null) {
                predicateList.add(criteriaBuilder.equal(root.get("category"), productCategory));
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };

        // 排序商品
        Sort sort = Sort.unsorted(); // 預設不排序
        if (orderBy != null && !orderBy.trim().isEmpty()) {
            sort = Sort.by(Sort.Direction.fromString(sortDirection), orderBy);
        }

        return productRepository.findAll(specification, sort);
    }

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
    public Product updateProduct(Product product, ProductRequest productRequest) {

        BeanUtils.copyProperties(productRequest, product);
        product.setLastModifiedDate(new Date());

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer productId) {

        productRepository.deleteById(productId);
    }
}

