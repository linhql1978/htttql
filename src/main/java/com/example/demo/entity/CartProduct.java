package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Scope(value = "request")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @PrimaryKeyJoinColumn
    private Product product;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @PrimaryKeyJoinColumn
    private Cart cart;

}
