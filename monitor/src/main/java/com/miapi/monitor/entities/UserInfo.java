package com.miapi.monitor.entities;

/**
 * CREATE TABLE user_info (
 *     id NUMBER PRIMARY KEY,
 *     username VARCHAR2(100),
 *     name VARCHAR2(100),
 *     currency VARCHAR2(3),
 *     amount NUMBER
 * );
 */
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userid;
    private String username;
    private String name;
    private String currency;
    private Double amount;
}
