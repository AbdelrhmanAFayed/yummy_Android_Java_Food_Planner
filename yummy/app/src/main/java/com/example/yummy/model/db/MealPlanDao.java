package com.example.yummy.model.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.yummy.model.meal.Meal;

import java.util.List;

@Dao
public interface MealPlanDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addPlan(MealPlan plan);

    @Query("DELETE FROM meal_plan WHERE mealId = :mealId AND day = :day")
    void removePlan(String mealId, long day);

    @Transaction
    @Query(
            "SELECT m.* " +
                    "FROM meals AS m " +
                    "INNER JOIN meal_plan AS p ON m.idMeal = p.mealId " +
                    "WHERE p.day = :day"
    )
    LiveData<List<Meal>> getMealsForDate(long day);
}
