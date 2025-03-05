package com.example.healthapp.meal;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class MealSummary {

    private LocalDate mealDate;
    private Map<MealType, List<MealProduct>> mealsByType;
    private double totalCalories;
    private double totalProtein;
    private double totalFat;
    private double totalCarbs;
}