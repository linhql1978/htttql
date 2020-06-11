package com.example.demo.repository;

import com.example.demo.entity.ProductCategorize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategorizeRepository extends JpaRepository<ProductCategorize, Long> {

    @Query("select pc from ProductCategorize pc where pc.productCategorizeName = :productCategorizeName")
    Optional<ProductCategorize> findProductCategorizeByName(@Param("productCategorizeName") String productCategorizeName);

    Optional<ProductCategorize> findByProductCategorizeName(String productCategorizeName);
}
