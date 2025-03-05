package com.example.healthapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFacade {
    @Autowired
    private UserRepository userRepository;

    public void addUser(String name, String surname, String email, String password,String confirmPassword, double weight, int height, String description) {

        User user = new User(name, surname, email, password,confirmPassword, weight, height, description);
        userRepository.save(user);
    }
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);

    }

    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
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

        return Math.round(dailyCalories);
    }
    public double calculateGoalCalories(double basicCalories, String goal) {
        switch (goal.toLowerCase()) {
            case "lose_weight":
                return basicCalories - 300;
            case "lose_weight2":
                return basicCalories - 600;
            case "gain_weight":
                return basicCalories + 300;
            case "gain_weight2":
                return basicCalories + 600;
            case "maintain_weight":
            default:
                return basicCalories;
        }
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }
    public void updateUser(User user) {
        userRepository.save(user);
    }
    public void updateWeight(User user, double weight) {
        user.setWeight(weight);
        userRepository.save(user);
    }
}