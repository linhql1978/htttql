package com.example.demo.repository;

import com.example.demo.entity.Customer;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c where c.username= :username")
    Optional<Customer> findCustomerByName(@Param("username") String username);

    Optional<Customer> findByUsername(String username);
}
