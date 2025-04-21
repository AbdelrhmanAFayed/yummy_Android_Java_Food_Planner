package com.example.yummy.model.network.meal;

import com.example.yummy.model.Meal;

import java.util.List;

public class MealResponse {
    public List<Meal> meals ;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
