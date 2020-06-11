package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Entity
@Component
@Scope(value = "request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date time;

    @JsonSerialize(using = DateSerializer.class)
    public Date getTime() {
        return time;
    }

    @OneToOne(mappedBy = "order1", cascade = CascadeType.ALL)
    private OrderDetail orderDetail;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @PrimaryKeyJoinColumn
    private Customer customer;
}
