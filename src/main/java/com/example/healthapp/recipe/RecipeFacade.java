package com.example.healthapp.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RecipeFacade {
    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> getRecipesByCategory(String category) {
        return recipeRepository.findByCategory(category);
    }

    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }
    public Recipe getRecipeById(int id) {
        return recipeRepository.findById(id).orElse(null);
    }

}
