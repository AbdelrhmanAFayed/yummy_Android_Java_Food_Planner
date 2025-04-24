package com.example.yummy.model.network.mealshort;

import com.example.yummy.model.mealshort.MealShort;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealShortResponse {

    @SerializedName("meals")
    List<MealShort> mealShorts ;

    public List<MealShort> getMealShorts() {
        return mealShorts;
    }

    public void setMealShorts(List<MealShort> mealShorts) {
        this.mealShorts = mealShorts;
    }
}
