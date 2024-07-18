package org.practice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Accounts extends BaseEntity {

    @Column(name = "customer_id")
    private Long customerId;

    @Id
    @Column(name = "accountNumber")
    private Long accountNumber;

    @Column(name = "accountType")
    private String accountType;

    @Column(name = "branchAddress")
    private String branchAddress;

}
