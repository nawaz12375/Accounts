package org.practice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Customer extends BaseEntity {

    @Column(name = "customer_Id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobileNumber")
    private String mobileNumber;

}
