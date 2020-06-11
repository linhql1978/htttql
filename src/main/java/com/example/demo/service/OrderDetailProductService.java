package com.example.demo.service;

import com.example.demo.entity.OrderDetailProduct;
import com.example.demo.repository.OrderDetailProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDetailProductService {

    @Autowired
    private OrderDetailProductRepository orderDetailProductRepository;

    public Optional<OrderDetailProduct> findById(Long id) {
        return orderDetailProductRepository.findById(id);
    }

    public OrderDetailProduct save(OrderDetailProduct orderDetailProduct) {
        return orderDetailProductRepository.save(orderDetailProduct);
    }

    public OrderDetailProduct saveAndFlush(OrderDetailProduct orderDetailProduct) {
        return orderDetailProductRepository.saveAndFlush(orderDetailProduct);
    }

    public void deleteById(Long id) {
        orderDetailProductRepository.deleteById(id);
    }

    public void deleteByEntity(OrderDetailProduct orderDetailProduct) {
        orderDetailProductRepository.delete(orderDetailProduct);
    }
}
