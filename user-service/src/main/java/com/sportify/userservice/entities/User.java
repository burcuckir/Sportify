package com.sportify.userservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
@Table(name = "\"user\"")
@Entity
public class User extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private Date birthDate;
    private String phone;
}

