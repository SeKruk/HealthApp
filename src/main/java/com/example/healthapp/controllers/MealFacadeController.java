package com.example.healthapp.controllers;

import com.example.healthapp.meal.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            @RequestParam("mealDate") LocalDate mealDate,
            @RequestParam(required = false) List<Long> breakfastProductIds,
            @RequestParam(required = false) List<Double> breakfastQuantities,
            @RequestParam(required = false) List<Long> secondBreakfastProductIds,
            @RequestParam(required = false) List<Double> secondBreakfastQuantities,
            @RequestParam(required = false) List<Long> lunchProductIds,
            @RequestParam(required = false) List<Double> lunchQuantities,
            @RequestParam(required = false) List<Long> snackProductIds,
            @RequestParam(required = false) List<Double> snackQuantities,
            @RequestParam(required = false) List<Long> dinnerProductIds,
            @RequestParam(required = false) List<Double> dinnerQuantities,
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

        saveMeal(user, MealType.BREAKFAST, breakfastProductIds, breakfastQuantities, mealDate);
        saveMeal(user, MealType.SECOND_BREAKFAST, secondBreakfastProductIds, secondBreakfastQuantities, mealDate);
        saveMeal(user, MealType.LUNCH, lunchProductIds, lunchQuantities, mealDate);
        saveMeal(user, MealType.SNACK, snackProductIds, snackQuantities, mealDate);
        saveMeal(user, MealType.DINNER, dinnerProductIds, dinnerQuantities, mealDate);

        return "redirect:/homeNutrition";
    }

    private void saveMeal(User user, MealType mealType, List<Long> productIds, List<Double> quantities, LocalDate mealDate) {
        if (productIds == null || quantities == null || productIds.size() != quantities.size()) {
            return;
        }

        List<Product> products = findProductsByIds(productIds);

        Meal meal = new Meal();
        meal.setMealType(mealType);
        meal.setMealDate(mealDate);
        meal.setUser(user);

        List<MealProduct> mealProducts = new ArrayList<>();

        for (int i = 0; i < productIds.size(); i++) {
            Product product = products.get(i);
            Double quantity = quantities.get(i);

            MealProduct mealProduct = new MealProduct();
            mealProduct.setMeal(meal);
            mealProduct.setProduct(product);
            mealProduct.setQuantity(quantity);
            mealProducts.add(mealProduct);
        }

        meal.setMealProducts(mealProducts);
        mealRepository.save(meal);
    }

    private List<Product> findProductsByIds(List<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return List.of();
        }

        List<Product> products = productRepository.findAllById(productIds);

        return productIds.stream()
                .map(id -> products.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null))
                .collect(Collectors.toList());
    }

    @GetMapping("/homeNutritions")
    public String showNutritionPage(
            @RequestParam(name = "selectedDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate selectedDate,
            Model model) {

        if (selectedDate == null) {
            selectedDate = LocalDate.now();
        }
        model.addAttribute("selectedDate", selectedDate);

        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);

        return "homeNutrition";
    }


    @Controller
    @RequestMapping("/homeSummary")
    public class DietSummaryController {

        private final MealFacade mealFacade;
        private final UserRepository userRepository;
        private  MealProduct mealProduct;

        public DietSummaryController(MealFacade mealFacade, UserRepository userRepository) {
            this.mealFacade = mealFacade;
            this.userRepository = userRepository;
        }

        @GetMapping
        public String getDietSummary(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                     @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                     HttpSession session, Model model) {

            User sessionUser = (User) session.getAttribute("user");
            if (sessionUser == null) {
                model.addAttribute("errorMessage", "Musisz się zalogować, aby zobaczyć podsumowanie.");
                return "homeNutrition";
            }

            User user = userRepository.findById(sessionUser.getId()).orElse(null);

            if (startDate.isAfter(endDate)) {
                model.addAttribute("errorMessage", "Data początkowa nie może być po dacie końcowej.");
                return "homeSummary";
            }

            List<MealSummary> summaryData = mealFacade.getMealSummary(user, startDate, endDate);

            model.addAttribute("summaryData", summaryData);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);

            model.addAttribute("mealTranslations", Map.of(
                    MealType.BREAKFAST, "Śniadanie",
                    MealType.SECOND_BREAKFAST, "Drugie śniadanie",
                    MealType.LUNCH, "Obiad",
                    MealType.SNACK, "Podwieczorek",
                    MealType.DINNER, "Kolacja"
            ));

            return "homeSummary";
        }}}