package com.example.demo.service;

import com.example.demo.entity.OrderDetail;
import com.example.demo.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public Optional<OrderDetail> findById(Long id) {
        return orderDetailRepository.findById(id);
    }

    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    public OrderDetail saveAndFlush(OrderDetail orderDetail) {
        return orderDetailRepository.saveAndFlush(orderDetail);
    }

    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    public void deleteByEntity(OrderDetail orderDetail) {
        orderDetailRepository.delete(orderDetail);
    }
}
