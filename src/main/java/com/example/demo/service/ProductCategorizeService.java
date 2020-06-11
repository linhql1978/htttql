package com.example.demo.service;

import com.example.demo.entity.ProductCategorize;
import com.example.demo.repository.ProductCategorizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCategorizeService {

    @Autowired
    private ProductCategorizeRepository productCategorizeRepository;

    public Optional<ProductCategorize> findById(Long id) {
        return productCategorizeRepository.findById(id);
    }

    public Optional<ProductCategorize> findByProductCategorizeName(String productCategorizeName) {
        return productCategorizeRepository.findByProductCategorizeName(productCategorizeName);
    }

    public ProductCategorize save(ProductCategorize productCategorize) {
        return productCategorizeRepository.save(productCategorize);
    }

    public ProductCategorize saveAndFlush(ProductCategorize productCategorize) {
        return productCategorizeRepository.saveAndFlush(productCategorize);
    }

    public void deleteById(Long id) {
        productCategorizeRepository.deleteById(id);
    }

    public void deleteByEntity(ProductCategorize productCategorize) {
        productCategorizeRepository.delete(productCategorize);
    }
}
