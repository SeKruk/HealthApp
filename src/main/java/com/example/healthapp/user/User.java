package com.example.healthapp.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_app")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String surname;
    private String email;
    private int phone;
    private String password;
    private String confirmPassword;
    private double weight;
    private int height;
    public User() {
    }
    public User(int id,String name, String surname, String email, String password, String confirmPassword, double weight, int height) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.weight = weight;
        this.height = height;
    }
    public User(String name, String surname, String email, String password, String confirmPassword, double weight, int height) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.weight = weight;
        this.height = height;
    }
    public User(String name, String surname, String email, String password, double weight, int height) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.weight = weight;
        this.height = height;
    }
    public String getConfirmPassword(){return this.confirmPassword;}
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getSurname(){
        return this.surname;
    }
    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }
    public double getWeight() { return  this.weight;}
    public int getHeight() { return this.height;}
}