package com.example.healthapp;
import com.example.healthapp.user.UserFacade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class UserFacadeTests {

    @Test public void testCalculateBMI() {
       //given
        UserFacade bmiCalculator = new UserFacade();

        //when
        double result = bmiCalculator.calculateBMI(85, 180);

        //then
        assertEquals(26.23, result, 0.01);
    }
}
