package com.example.demo.entity;

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
public class ProductCategorize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productCategorizeName;

    @OneToMany(mappedBy = "productCategorize",cascade = CascadeType.ALL)
    private Set<Product> products;

}
