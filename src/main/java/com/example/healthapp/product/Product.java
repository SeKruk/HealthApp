package com.example.healthapp.product;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double kcal;
    private double protein;
    private double fat;
    private double carb;

    public Product(String name, double kcal, double protein, double fat, double carb) {
        this.name = name;
        this.kcal = kcal;
        this.protein = protein;
        this.fat = fat;
        this.carb = carb;
    }
    public Product() {
    }

}
