package com.example.healthapp.recipe;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@Entity
@Table(name = "recipe")
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String ingredients;

    @Column(columnDefinition = "TEXT")
    private String source;

    @Column(name = "calories")
    private int calories;

    public Recipe(){

    }
}