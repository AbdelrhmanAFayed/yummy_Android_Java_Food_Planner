package com.example.yummy.model.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.yummy.model.meal.Meal;

import java.util.List;

@Dao
public interface MealDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Meal meal);

    @Update
    void update(Meal meal);

    @Delete
    void delete(Meal meal);

    @Query("SELECT * FROM meals WHERE idMeal = :idMeal LIMIT 1")
    LiveData<Meal> getMealById(String idMeal);

    @Query("SELECT * FROM meals WHERE strMeal LIKE :mealName")
    LiveData<List<Meal>> getMealsByName(String mealName);

    @Query("SELECT * FROM meals")
    LiveData<List<Meal>> getAllMeals();
}