package com.example.healthapp.meal;

import com.example.healthapp.product.Product;
import com.example.healthapp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {


    List<Meal> findByUserAndMealDate(User user, LocalDate date);
    List<Meal> findByUserAndMealDateBetween(User user, LocalDate startDate, LocalDate endDate);
    List<Meal> findByMealDateAndMealProductsProduct(LocalDate mealDate, Product product);}
