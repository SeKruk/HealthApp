package com.example.healthapp.controllers;

import com.example.healthapp.user.User;
import com.example.healthapp.user.UserFacade;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserFacadeController {

    private final UserFacade userFacade;

    @Autowired
    public UserFacadeController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/homeLogin")
    public String showLoginForm() {
        return "homeLogin";
    }

    @PostMapping("/Login")
    public String processLogin(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        if (userFacade.authenticateUser(email, password)) {
            User user = userFacade.findUserByEmail(email);
            session.setAttribute("user", user);
            return "redirect:/homeProfile";
        }
        model.addAttribute("errorMessage", "Nieprawidłowe dane");

        return "homeLogin";
    }

    @GetMapping("/homeProfile")
    public String showHomeProfile(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            model.addAttribute("error", "Nie jesteś zalogowany");
            return "redirect:/homeLogin";
        }

        model.addAttribute("user", user);
        return "homeProfile";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/homeLogin";
    }


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "passwords.not.match", "Hasła nie pasują do siebie");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "homeLogin";
        }

        userFacade.addUser(
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                user.getConfirmPassword(),
                70.0,
                175,
                "Opis... "
        );
        return "redirect:/homeLogin";
    }

    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
        userFacade.addUser(user.getName(), user.getSurname(), user.getEmail(), user.getPassword(),user.getConfirmPassword(), user.getWeight(), user.getHeight(), user.getDescription());
        return "redirect:/homeLogin";
    }

    @GetMapping("/calculateBMI")
    public ResponseEntity<String> calculateBMI(@RequestParam double weight, @RequestParam int height) {
        try {
            double bmi = userFacade.calculateBMI(weight, height);
            return ResponseEntity.ok(String.format("Twoje BMI wynosi: %.2f", bmi));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Wystąpił błąd podczas obliczania BMI.");
        }
    }

    @PostMapping("/calculateBMR")
    @ResponseBody
    public Map<String, Object> calculateBMR(@RequestParam("gender") String gender,
                                            @RequestParam("weight") double weight,
                                            @RequestParam("height") double height,
                                            @RequestParam("age") int age,
                                            @RequestParam("activityLevel") double activityLevel,
                                            @RequestParam("goal") String goal) {

        Map<String, Object> response = new HashMap<>();

        try {
            double basicCalories = userFacade.calculateBMR(gender, weight, height, age, activityLevel,goal);

            double goalCalories = userFacade.calculateGoalCalories(basicCalories, goal);

            basicCalories = Math.round(basicCalories);
            goalCalories = Math.round(goalCalories);

            response.put("basicCalories", basicCalories);
            response.put("goalCalories", goalCalories);
        } catch (Exception e) {
            response.put("error", "Błąd obliczeń");
        }

        return response;
    }
    @PostMapping("/updateWeight")
    public String updateWeight(@RequestParam("weight") double weight, HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("user");

        userFacade.updateWeight(sessionUser, weight);

        return "redirect:/homeProfile";
    }
    @PostMapping("/updateDescription")
    public String updateDescription(@RequestParam("profileDescription") String Description, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");

        sessionUser.setDescription(Description);

        userFacade.updateUser(sessionUser);

        return "redirect:/homeProfile";
    }

}










