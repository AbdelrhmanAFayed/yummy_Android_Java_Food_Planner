package com.example.yummy.main;

import com.example.yummy.model.meal.Meal;

public interface OnCalendarItemClickListener {
    void onMealClicked(Meal meal);
    void onDeleteMeal(Meal meal);
}
