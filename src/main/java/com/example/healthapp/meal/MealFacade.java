package com.example.healthapp.meal;

import com.example.healthapp.user.User;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MealFacade {

    private final MealRepository mealRepository;

    public MealFacade(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public void saveDailyMeals(User user, LocalDate date, List<Meal> meals) {
        for (Meal meal : meals) {
            meal.setUser(user);
            meal.setMealDate(date);
            System.out.println("Saving meal: " + meal.getId());
            mealRepository.save(meal);
        }
    }

    public List<MealSummary> getMealSummary(User user, LocalDate startDate, LocalDate endDate) {
        List<Meal> meals = mealRepository.findByUserAndMealDateBetween(user, startDate, endDate);
        Map<LocalDate, List<Meal>> mealsByDate = meals.stream()
                .collect(Collectors.groupingBy(Meal::getMealDate));

        List<MealSummary> summaries = new ArrayList<>();

        mealsByDate.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    LocalDate date = entry.getKey();
                    List<Meal> mealsForDate = entry.getValue();
                    Map<MealType, List<MealProduct>> mealsByType = new LinkedHashMap<>();

                    mealsForDate.forEach(meal -> {
                        List<MealProduct> mealProducts = meal.getMealProducts();

                        if (!mealProducts.isEmpty()) {
                            mealsByType.computeIfAbsent(meal.getMealType(), k -> new ArrayList<>())
                                    .addAll(mealProducts);
                        }
                    });

                    double totalCalories = mealsForDate.stream()
                            .flatMap(meal -> meal.getMealProducts().stream())
                            .mapToDouble(mealProduct -> mealProduct.getCaloriesForProduct())
                            .sum();
                    totalCalories = Math.round(totalCalories * 10.0) / 10.0;

                    double totalProtein = mealsForDate.stream()
                            .flatMap(meal -> meal.getMealProducts().stream())
                            .mapToDouble(mealProduct -> mealProduct.getProteinForProduct())
                            .sum();
                    totalProtein = Math.round(totalProtein * 10.0) / 10.0;

                    double totalFat = mealsForDate.stream()
                            .flatMap(meal -> meal.getMealProducts().stream())
                            .mapToDouble(mealProduct -> mealProduct.getFatForProduct())
                            .sum();
                    totalFat = Math.round(totalFat * 10.0) / 10.0;

                    double totalCarbs = mealsForDate.stream()
                            .flatMap(meal -> meal.getMealProducts().stream())
                            .mapToDouble(mealProduct -> mealProduct.getCarbsForProduct())
                            .sum();
                    totalCarbs = Math.round(totalCarbs * 10.0) / 10.0;

                    summaries.add(new MealSummary(date, mealsByType, totalCalories, totalProtein, totalFat, totalCarbs));
                });

        return summaries;
    }
}