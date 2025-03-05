package com.example.healthapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {


    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam String email, @RequestParam String message, Model model) {
        System.out.println("Otrzymano wiadomość od: " + email);
        System.out.println("Treść wiadomości: " + message);

        model.addAttribute("successMessage", "Twoja wiadomość została wysłana. Dziękujemy za kontakt!");

        return "homeAboutUs";
    }
}