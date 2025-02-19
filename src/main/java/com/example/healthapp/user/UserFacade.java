package com.example.healthapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFacade {
    @Autowired
    private UserRepository userRepository;

    public void addUser(String name, String surname, String email, String password,String confirmPassword, double weight, int height) {
        // Tworzymy nowego użytkownika
        User user = new User(name, surname, email, password,confirmPassword, weight, height);
        userRepository.save(user);  // JPA automatycznie generuje ID
    }
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new User();  // Konwersja User na UserDto
        }
        return null;
    }

    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);  // Używamy repozytorium bezpośrednio
        return user != null && user.getPassword().equals(password);
    }

    public boolean removeUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public double calculateBMI(double weight, int heightCm) {
        double heightM = heightCm / 100.0;
        return weight / (heightM * heightM);
    }

    public double calculateBasicBMR(String gender, double weight, double height, int age) {
        if ("male".equalsIgnoreCase(gender)) {
            return 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            return 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
    }

    public double calculateBMR(String gender, double weight, double height, int age, double activityLevel, String goal) {
        double bmr = calculateBasicBMR(gender, weight, height, age);
        double dailyCalories = bmr * activityLevel;

        switch (goal.toLowerCase()) {
            case "lose_weight":
                dailyCalories -= 500;
                break;
            case "gain_weight":
                dailyCalories += 500;
                break;
            case "maintain_weight":
            default:
                break;
        }

        return dailyCalories;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();  // Pobiera wszystkich użytkowników
    }

    public User findUserById(int id) {
        return userRepository.findById(id).orElse(null);  // Pobiera użytkownika po ID
    }
}