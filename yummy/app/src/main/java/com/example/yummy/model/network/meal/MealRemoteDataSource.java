package com.example.yummy.model.network.meal;

public interface MealRemoteDataSource {

    public void getMealOfTheDay(MealNetWorkCallBack callBack);

    public void getMealByName(String name, MealNetWorkCallBack callBack);

    public void getMealByID(String id, MealNetWorkCallBack callBack);

    public void getMealByFirstLetter(String letter, MealNetWorkCallBack callBack);


}
