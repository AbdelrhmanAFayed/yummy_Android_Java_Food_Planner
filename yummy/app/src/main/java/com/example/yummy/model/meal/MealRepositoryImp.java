package com.example.yummy.model.meal;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.yummy.model.db.MealLocalDataSource;
import com.example.yummy.model.db.MealLocalDataSourceImp;
import com.example.yummy.model.network.meal.MealNetWorkCallBack;
import com.example.yummy.model.network.meal.MealRemoteDataSource;
import com.example.yummy.model.network.meal.MealRemoteDataSourceImp;

import java.util.List;


public class MealRepositoryImp implements MealRepository{


    private MealRemoteDataSource remoteDataSource;
    private MealLocalDataSource localDataSource;




    private static MealRepositoryImp repo = null ;


    public static MealRepositoryImp getInstance(Context context) {
        if (repo == null) {
            repo = new MealRepositoryImp(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(context));
        }
        return repo;
    }


    private MealRepositoryImp(MealRemoteDataSource remoteDataSource, MealLocalDataSource mealLocalDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = mealLocalDataSource;
    }


    @Override
    public void getRandom(MealNetWorkCallBack callBack) {

        remoteDataSource.getMealOfTheDay(callBack);

    }

    @Override
    public void getByName(String name, MealNetWorkCallBack callBack) {

        remoteDataSource.getMealByName(name,callBack);
    }

    @Override
    public void getByID(String ID, MealNetWorkCallBack callBack) {

        remoteDataSource.getMealByID(ID , callBack);

    }

    @Override
    public void getByFirstLetter(String letter, MealNetWorkCallBack callBack) {

        remoteDataSource.getMealByFirstLetter(letter,callBack);

    }

    @Override
    public LiveData<List<Meal>> getAllMealsLocal() {
        return localDataSource.getAllMeals();    }

    @Override
    public LiveData<List<Meal>> getMealsByNameLocal(String name) {
        return localDataSource.getMealsByName(name);
    }

    @Override
    public Meal getMealByIDLocal(String ID) {
        return localDataSource.getMealByID(ID);
    }

    @Override
    public void insertMealLocal(Meal meal) {
        localDataSource.insertMeal(meal);
    }

    @Override
    public void deleteMealLocal(Meal meal) {
        localDataSource.deleteMeal(meal);
    }
}
