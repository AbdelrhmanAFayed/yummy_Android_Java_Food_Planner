package com.example.yummy.model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.yummy.model.meal.Meal;

@Database(entities = {Meal.class, MealPlan.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase instance = null;

    public abstract MealDAO getMealDao();
    public abstract MealPlanDao   getMealPlanDao();

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, "meal_database").build();
        }
        return instance;
    }
}

