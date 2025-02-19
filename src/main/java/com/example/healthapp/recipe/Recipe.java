package com.example.healthapp.recipe;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "recipe")
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

    public Recipe(int id, String title, String description, String category, String imageUrl, String ingredients, String source, int calories){
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.source = source;
        this.calories = calories;
    }

    public Recipe(){

    }
    public int getId() {
        return this.id;
    }
    public String getTitle(){return this.title;}
    public String getDescription(){ return  this.description;}
    public  String getCategory(){ return  this.category;}
    public String getImageUrl(){return  this.imageUrl;}
    public String getIngredients() {return this.ingredients;}
    public String getSource(){ return  this.source;}
    public int getCalories(){return  this.calories;}
}
