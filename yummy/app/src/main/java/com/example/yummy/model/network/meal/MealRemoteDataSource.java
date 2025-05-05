package com.example.yummy.model.network.meal;

public interface MealRemoteDataSource {

    void getMealOfTheDay(MealNetWorkCallBack callBack);

    void getMealByName(String name, MealNetWorkCallBack callBack);

    void getMealByID(String id, MealNetWorkCallBack callBack);

    void getMealByFirstLetter(String letter, MealNetWorkCallBack callBack);


}
