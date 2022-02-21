package com.scottish.power.code.test.smartmeterAPI.DTOs;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name="ACCOUNT_ID")
    private long id;
}
