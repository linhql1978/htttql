package com.example.demo.service;

import com.example.demo.entity.ProductComment;
import com.example.demo.repository.ProductCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCommentService {

    @Autowired
    private ProductCommentRepository productCommentRepository;

    public Optional<ProductComment> findById(Long id) {
        return productCommentRepository.findById(id);
    }

    public ProductComment save(ProductComment productComment) {
        return productCommentRepository.save(productComment);
    }

    public ProductComment saveAndFlush(ProductComment productComment) {
        return productCommentRepository.saveAndFlush(productComment);
    }

    public void deleteById(Long id) {
        productCommentRepository.deleteById(id);
    }

    public void deleteByEntity(ProductComment productComment) {
        productCommentRepository.delete(productComment);
    }
}
