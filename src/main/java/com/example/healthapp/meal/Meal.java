package com.example.healthapp.meal;

import com.example.healthapp.product.Product;
import com.example.healthapp.user.User;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MealType mealType;  // Typ posiłku (np. śniadanie, obiad)

    private LocalDate mealDate;

    @ManyToMany
    @JoinTable(
            name = "meal_product",
            joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;  // Lista produktów w danym posiłku

    @ManyToOne
    @JoinColumn(name = "user_id")  // Relacja z użytkownikiem
    private User user;  // Użytkownik, który dodał posiłek

    @ManyToOne
    @JoinColumn(name = "meal_plan_id")
    private MealPlan mealPlan;

    // Getters i Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Dodaj metodę setMealDate
    public void setMealDate(LocalDate mealDate) {
        this.mealDate = mealDate;
    }

    // Możesz dodać metodę getMealDate, jeśli chcesz również pobierać datę
    public LocalDate getMealDate() {
        return this.mealDate;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}