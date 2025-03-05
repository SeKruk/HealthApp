package com.example.healthapp.controllers;

import com.example.healthapp.user.WaterFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class WaterController {

    public final WaterFacade waterFacade;
    @Autowired
    public WaterController(WaterFacade waterService) {
        this.waterFacade = waterService;
    }
    @PostMapping("/homeWater")
    @ResponseBody
    public Map<String, Object> calculateWaterNeed(@RequestParam double weight) {
        return waterFacade.calculateWaterNeed(weight);
    }

    @PostMapping("/addWater")
    @ResponseBody
    public Map<String, Object> addWaterIntake(@RequestParam double waterIntake) {
        return waterFacade.addWaterIntake(waterIntake);
    }
}