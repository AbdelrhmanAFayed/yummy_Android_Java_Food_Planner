package com.example.yummy.model.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;



@Entity(
        tableName = "meal_plan",
        primaryKeys = { "mealId", "day" }
)
public class MealPlan {

    @NonNull
    public String mealId;
    public long day;

    public MealPlan(@NonNull String mealId, long day) {
        this.mealId = mealId;
        this.day    = day;
    }
}

