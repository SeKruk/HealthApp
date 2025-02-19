package com.example.healthapp.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Getter
    private double kcal;
    @Getter
    private double protein;
    @Getter
    private double fat;
    @Getter
    private double carb;

    public Product(String name, double kcal,double protein, double fat, double carb){
        this.name = name;
        this.kcal = kcal;
        this.protein = protein;
        this.fat = fat;
        this.carb = carb;
    }
    public Product(){

    }

}
