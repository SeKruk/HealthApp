package com.example.healthapp.meal;

import com.example.healthapp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealFacade {

    private final MealRepository mealRepository;

    // Konstruktor do wstrzykiwania zależności (zalecana praktyka)
    public MealFacade(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    // Metoda zapisująca posiłki dla użytkownika w danym dniu
    public void saveDailyMeals(User user, LocalDate date, List<Meal> meals) {

        for (Meal meal : meals) {
            meal.setUser(user);
            meal.setMealDate(date);
            System.out.println("Saving meal: " + meal.getId());
            mealRepository.save(meal);
        }
    }

}