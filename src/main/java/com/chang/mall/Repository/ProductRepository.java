package com.chang.mall.Repository;

import com.chang.mall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
//    Product findByProductId(Integer id);
//
//    @Transactional
//    @Modifying
//    @Query("DELETE FROM Product p WHERE p.productId = :productId")
//    void deleteByProductId(@Param("productId") Integer productId);
}
