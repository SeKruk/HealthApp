package com.example.healthapp.meal;

import com.example.healthapp.product.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class MealProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private double quantity;

    public double getCaloriesForProduct() {
        return product.getKcal() * quantity / 100;
    }

    public double getProteinForProduct() {
        return product.getProtein() * quantity / 100;
    }

    public double getFatForProduct() {
        return product.getFat() * quantity / 100;
    }

    public double getCarbsForProduct() {
        return product.getCarb() * quantity / 100;
    }
}