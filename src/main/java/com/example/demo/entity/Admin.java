package com.example.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Component
@Scope(value = "request")
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User{

    public Admin(Long id, String username, String password, String role) {
        super(id, username, password, role);
    }
}
