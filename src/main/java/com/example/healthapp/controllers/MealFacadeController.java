package com.example.healthapp.controllers;

import com.example.healthapp.meal.Meal;
import com.example.healthapp.meal.MealRepository;
import com.example.healthapp.meal.MealType;
import com.example.healthapp.product.Product;
import com.example.healthapp.product.ProductRepository;
import com.example.healthapp.user.User;
import com.example.healthapp.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MealFacadeController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MealRepository mealRepository;

    @PostMapping("/saveDailyMeals")
    public String saveDailyMeals(
            @RequestParam(name = "mealDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate mealDate,
            @RequestParam(required = false) List<Long> breakfastProductIds,
            @RequestParam(required = false) List<Long> lunchProductIds,
            @RequestParam(required = false) List<Long> secondBreakfastProductIds,
            @RequestParam(required = false) List<Long> snackProductIds,
            @RequestParam(required = false) List<Long> dinnerProductIds,
            HttpSession session, Model model) {

        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            model.addAttribute("errorMessage", "Musisz się zalogować, aby zapisać posiłki.");
            return "homeNutrition";
        }

        User user = userRepository.findById(sessionUser.getId()).orElse(null);
        if (user == null) {
            model.addAttribute("errorMessage", "Błąd: użytkownik nie istnieje w bazie.");
            return "homeNutrition";
        }

        // Pobierz produkty po ID (jeśli lista jest pusta – zwraca pustą listę)
        List<Product> breakfastProducts = findProductsByIds(breakfastProductIds);
        List<Product> lunchProducts = findProductsByIds(lunchProductIds);
        List<Product> secondBreakfastProducts = findProductsByIds(secondBreakfastProductIds);
        List<Product> snackProducts = findProductsByIds(snackProductIds);
        List<Product> dinnerProducts = findProductsByIds(dinnerProductIds);

        // Zapisz posiłki – przypisujemy również użytkownika
        saveMeal(user, MealType.BREAKFAST, breakfastProducts, mealDate);
        saveMeal(user, MealType.LUNCH, lunchProducts, mealDate);
        saveMeal(user, MealType.SECOND_BREAKFAST, secondBreakfastProducts, mealDate);
        saveMeal(user, MealType.SNACK, snackProducts, mealDate);
        saveMeal(user, MealType.DINNER, dinnerProducts, mealDate);

        return "redirect:/homeNutrition";
    }

    private List<Product> findProductsByIds(List<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return List.of();
        }
        return productRepository.findAllById(productIds);
    }

    private void saveMeal(User user, MealType mealType, List<Product> products, LocalDate mealDate) {
        Meal meal = new Meal();
        meal.setMealType(mealType);
        meal.setProducts(products);
        meal.setMealDate(mealDate);
        meal.setUser(user);
        mealRepository.save(meal);
    }

    @GetMapping("/homeNutritions")
    public String showNutritionPage(
            @RequestParam(name = "selectedDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate selectedDate,
            Model model) {

        if (selectedDate == null) {
            selectedDate = LocalDate.now();
        }
        model.addAttribute("selectedDate", selectedDate);

        // Przykład pobierania produktów – dostosuj według logiki aplikacji
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);

        return "homeNutrition";
    }
}