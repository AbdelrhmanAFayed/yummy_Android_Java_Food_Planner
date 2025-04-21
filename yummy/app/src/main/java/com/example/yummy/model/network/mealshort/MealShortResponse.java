package com.example.yummy.model.network.mealshort;

import com.example.yummy.model.MealShort;

import java.util.List;

public class MealShortResponse {

    List<MealShort> mealShorts ;

    public List<MealShort> getMealShorts() {
        return mealShorts;
    }

    public void setMealShorts(List<MealShort> mealShorts) {
        this.mealShorts = mealShorts;
    }
}
