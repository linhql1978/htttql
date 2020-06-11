package com.example.demo.service;

import com.example.demo.entity.Order1;
import com.example.demo.repository.Order1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Order1Service {

    @Autowired
    private Order1Repository order1Repository;

    public Optional<Order1> findById(Long id) {
        return order1Repository.findById(id);
    }

    public Order1 save(Order1 order1) {
        return order1Repository.save(order1);
    }

    public Order1 saveAndFlush(Order1 order1) {
        return order1Repository.saveAndFlush(order1);
    }

    public void deleteById(Long id) {
        order1Repository.deleteById(id);
    }

    public void deleteByEntity(Order1 order1) {
        order1Repository.delete(order1);
    }
}
