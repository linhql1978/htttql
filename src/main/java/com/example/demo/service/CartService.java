package com.example.demo.service;

import com.example.demo.entity.Cart;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    public Optional<Cart> findByCustomerId(Long id) {
        return cartRepository.findByCustomerId(id);
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart saveAndFlush(Cart cart) {
        return cartRepository.saveAndFlush(cart);
    }

    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }

    public void deleteByEntity(Cart cart) {
        cartRepository.delete(cart);
    }
}
