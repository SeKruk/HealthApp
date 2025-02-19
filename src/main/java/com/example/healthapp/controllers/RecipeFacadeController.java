package com.example.healthapp.controllers;

import com.example.healthapp.recipe.Recipe;
import com.example.healthapp.recipe.RecipeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RecipeFacadeController {

    @Autowired
    private RecipeFacade recipeFacade;

    @PostMapping("/recipes")
    public String addRecipe(@ModelAttribute Recipe recipe) {
        recipeFacade.saveRecipe(recipe);
        String redirectUrl = "/home" + recipe.getCategory().substring(0, 1).toUpperCase() + recipe.getCategory().substring(1);
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/recipes/{category}")
    public String getRecipesByCategory(@PathVariable String category, Model model) {
        List<Recipe> recipes = recipeFacade.getRecipesByCategory(category);
        System.out.println("Fetched recipes for category: " + category); // Debugging line
        model.addAttribute("recipes", recipes);
        model.addAttribute("category", category);
        return "home" + category.substring(0, 1).toUpperCase() + category.substring(1);
    }
    @GetMapping("/recipe/{id}")
    public String getRecipeDetails(@PathVariable int id, Model model) {
        Recipe recipe = recipeFacade.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "recipeDetails"; // Zakładając, że masz szablon recipeDetails.html
    }
}