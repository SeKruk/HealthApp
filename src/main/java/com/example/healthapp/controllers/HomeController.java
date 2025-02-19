package com.example.healthapp.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

@GetMapping("/search")
public String Summary(){return "homeSummary";}
    @GetMapping("/homeWater")
    public String home() {
        return "homeWater";
    }

    @GetMapping("/homeBMR")
    public String BMR() {
        return "homeBMR";
    }


    @GetMapping("/homeBMI")
    public String BMI() {
        return "homeBMI";
    }
    @GetMapping("/recipes")
    public String recipes() {
        return "recipes";
    }
    @GetMapping("/homeRecipe")
    public String Recipe() {
        return "homeRecipe";
    }
    @GetMapping("/homeBreakfast")
    public String Breakfast() {
        return "homeBreakfast";
    }
    @GetMapping("/homeLunch")
    public String Lunch() {
        return "homeLunch";
    }
    @GetMapping("/homeDinner")
    public String Dinner() {
        return "homeDinner";
    }
    @GetMapping("/homeDessert")
    public String Dessert() {
        return "homeDessert";
    }
    @GetMapping("/homeAboutUs")
    public String AboutUs() {
        return "homeAboutUs";
    }
    @GetMapping("/homeNutrition")
    public String Nutrition() {
        return "homeNutrition";
    }
    @GetMapping("/products")
    public String Products(){ return "homeNutrition";}
    @GetMapping("/Profile")
    public String Profile(){ return "homeProfile";}

}
