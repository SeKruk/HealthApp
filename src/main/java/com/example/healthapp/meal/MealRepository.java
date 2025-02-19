package com.example.healthapp.meal;

import com.example.healthapp.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    // Znajdź posiłki dla konkretnego użytkownika na dany dzień

    List<Meal> findByUserAndMealDate(User user, LocalDate date);
}