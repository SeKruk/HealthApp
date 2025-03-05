package com.example.healthapp.controllers;

import com.example.healthapp.product.Product;
import com.example.healthapp.product.ProductFacade;
import com.example.healthapp.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ProductFacadeController {
    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private ProductRepository productRepository;
    @PostMapping("/products")
    public String addProduct(@RequestParam String name,
                             @RequestParam double kcal,
                             @RequestParam double protein,
                             @RequestParam double fat,
                             @RequestParam double carb,
                             Model model) {
        Product product = new Product(name, kcal, protein, fat, carb);
        productFacade.addProduct(product);
        model.addAttribute("successMessage", "Produkt został pomyślnie dodany!!!");
        return "homeNutrition";

    }

}
