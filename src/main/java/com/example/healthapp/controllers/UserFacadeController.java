package com.example.healthapp.controllers;

import com.example.healthapp.user.User;
import com.example.healthapp.user.UserFacade;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class UserFacadeController {

    private final UserFacade userFacade;

    @Autowired
    public UserFacadeController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    // Strona logowania
    @GetMapping("/homeLogin")
    public String showLoginForm() {
        return "homeLogin";  // Wyświetlenie formularza logowania
    }

    @PostMapping("/homeLogin")
    public String processLogin(@RequestParam String email, @RequestParam String password, HttpSession session) {
        // Logika logowania
        if (userFacade.authenticateUser(email, password)) {
            User user = userFacade.findUserByEmail(email);
            session.setAttribute("user", user);  // Zapisanie całego użytkownika w sesji
            return "redirect:/homeProfile";  // Po zalogowaniu przekierowanie do profilu
        }
        return "homeLogin";  // Jeśli logowanie się nie powiodło, ponownie wyświetl formularz
    }

    @GetMapping("/homeProfile")
    public String showHomeProfile(Model model, HttpSession session) {
        // Pobieramy obiekt użytkownika z sesji
        User user = (User) session.getAttribute("user");

        // Jeśli użytkownik nie jest zalogowany, przekierowujemy go do strony logowania
        if (user == null) {
            model.addAttribute("error", "Nie jesteś zalogowany");
            return "redirect:/homeLogin";  // Zmieniono z "/login" na "/homeLogin"
        }

        // Przekazujemy model do widoku homeProfile
        model.addAttribute("user", user);
        return "homeProfile";
    }

    // Wylogowanie użytkownika
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Usuwa wszystkie dane z sesji, w tym dane o użytkowniku
        return "redirect:/homeLogin";  // Po wylogowaniu przekierowanie do strony logowania
    }

    // Metoda do rejestracji użytkownika
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "passwords.not.match", "Hasła nie pasują do siebie");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "homeLogin";  // Jeśli są błędy, wracamy do formularza logowania
        }

        userFacade.addUser(
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                user.getConfirmPassword(),
                70.0,  // Domyślna waga
                175    // Domyślny wzrost
        );

        return "redirect:/homeLogin";  // Po rejestracji przekierowanie na stronę logowania
    }


    // 🟢 POST - Dodawanie użytkownika (np. admin)
    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
        userFacade.addUser(user.getName(), user.getSurname(), user.getEmail(), user.getPassword(),user.getConfirmPassword(), user.getWeight(), user.getHeight());
        return "redirect:/homeLogin";  // Po dodaniu użytkownika przekierowanie na stronę logowania
    }

    // 🟢 DELETE - Usuwanie użytkownika
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        boolean removed = userFacade.removeUser(id);
        return removed ? "redirect:/homeLogin" : "redirect:/homeProfile";  // Po usunięciu przekierowanie
    }

    // 🟢 GET - Obliczanie BMI
    @GetMapping("/calculateBMI")
    public String calculateBMI(@RequestParam double weight, @RequestParam int height, Model model) {
        try {
            double bmi = userFacade.calculateBMI(weight, height);
            model.addAttribute("bmi", bmi);  // Przekazanie wyniku do widoku
            return "bmiResult";  // Widok z wynikiem BMI
        } catch (Exception e) {
            model.addAttribute("error", "Błąd obliczeń");
            return "error";  // Widok błędu
        }
    }

    // 🟢 POST - Obliczanie BMR (kalorii)
    @PostMapping("/calculateBMR")
    public String calculateBMR(@RequestParam("gender") String gender,
                               @RequestParam("weight") double weight,
                               @RequestParam("height") double height,
                               @RequestParam("age") int age,
                               @RequestParam("activityLevel") double activityLevel,
                               @RequestParam("goal") String goal,
                               Model model) {

        try {
            double basicCalories = userFacade.calculateBMR(gender, weight, height, age, activityLevel, goal);
            double goalCalories = calculateGoalCalories(basicCalories, goal);

            model.addAttribute("basicCalories", basicCalories);
            model.addAttribute("goalCalories", goalCalories);
            return "bmrResult";  // Widok z wynikiem BMR
        } catch (Exception e) {
            model.addAttribute("error", "Błąd obliczeń");
            return "error";  // Widok błędu
        }
    }

    private double calculateGoalCalories(double basicCalories, String goal) {
        switch (goal) {
            case "lose_weight":
                return basicCalories - 500; // Schudnięcie
            case "gain_weight":
                return basicCalories + 500; // Przytycie
            case "maintain_weight":
            default:
                return basicCalories; // Utrzymanie wagi
        }
    }
}










