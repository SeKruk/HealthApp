package com.example.healthapp.user;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WaterFacade {

    private double dailyWaterNeed = 0;
    private double totalWaterIntake = 0;

    public Map<String, Object> calculateWaterNeed(double weight) {
        if (weight > 0) {
            dailyWaterNeed = weight * 35;
        }

        double remainingWater = Math.max(0, dailyWaterNeed - totalWaterIntake);

        Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("waterNeed", dailyWaterNeed);
        response.put("remainingWater", remainingWater);
        return response;
    }

    public Map<String, Object> addWaterIntake(double waterIntake) {
        if (waterIntake > 0) {
            totalWaterIntake += waterIntake;
        }

        double remainingWater = Math.max(0, dailyWaterNeed - totalWaterIntake);

        Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("totalWater", totalWaterIntake);
        response.put("remainingWater", remainingWater);
        return response;
    }
}