package com.cookos.model;

import java.io.Serializable;

import com.cookos.net.ModelType;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "users")
public class User implements Serializable, Model, Identifiable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "login")
    private String login;
    
    @Column(name = "password")
    private byte[] password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {
    }

    public User(int id, String login, byte[] password, UserRole role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @Override
    public ModelType getModelType() {
        return ModelType.User;
    }    
}
