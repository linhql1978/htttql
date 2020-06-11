package com.example.demo.service;

import com.example.demo.entity.CartProduct;
import com.example.demo.repository.CartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartProductService {

    @Autowired
    private CartProductRepository cartProductRepository;

    public void deleteById(Long id) {
        cartProductRepository.deleteById(id);
    }

    public void deleteByEntity(CartProduct cartProduct) {
        cartProductRepository.delete(cartProduct);
    }

    public Optional<CartProduct> findById(Long id) {
        return cartProductRepository.findById(id);
    }

    public CartProduct save(CartProduct cartProduct) {
        return cartProductRepository.save(cartProduct);
    }

    public CartProduct saveAndFlush(CartProduct cartProduct) {
        return cartProductRepository.saveAndFlush(cartProduct);
    }
}
