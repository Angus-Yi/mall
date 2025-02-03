package com.chang.mall.service.impl;

import com.chang.mall.Repository.ProductRepository;
import com.chang.mall.constants.ProductCategory;
import com.chang.mall.dto.ProductRequest;
import com.chang.mall.entity.Product;
import com.chang.mall.service.ProductService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Product> getProducts(String search,
                                     ProductCategory productCategory,
                                     String orderBy,
                                     String sortDirection,
                                     Integer page,
                                     Integer size) {

        // 根據[查詢條件]執行查詢
        Specification<Product> specification = filterBy(search, productCategory);

        // 排序商品
        Sort sort = Sort.unsorted(); // 預設不排序
        if (orderBy != null && !orderBy.trim().isEmpty()) {
            sort = Sort.by(Sort.Direction.fromString(sortDirection), orderBy);
        }

        // 分頁
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(specification, pageable);
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


    private Specification<Product> filterBy(String search, ProductCategory productCategory) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // 根據 [名稱] 查詢
            if (search != null && !search.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("productName"), "%" + search + "%"));
            }

            // 根據 [類別] 查詢
            if (productCategory != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"), productCategory));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}

