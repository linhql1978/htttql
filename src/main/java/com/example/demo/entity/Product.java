package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;

@Entity
@Component
@Scope(value = "request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private Double price;

    private String fontCamera;

    private String backCamera;

    private String ram;

    private String rom;

    private String chip;

    private String screen;

    private String image;

    private String productCategorizeName;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @PrimaryKeyJoinColumn
    private ProductCategorize productCategorize;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductComment> productComments;

    @JsonIgnore
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private Set<CartProduct> cartProducts;

    @JsonIgnore
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private Set<OrderDetailProduct> orderDetailProducts;
}
