package com.example.yummy.model.network.mealshort;

public interface MealShortRemoteDataSource {

    public void getMealsByCat(String cat ,MealShortNetWorkCallBack callBack);
    public void getMealsByIng(String Ing,MealShortNetWorkCallBack callBack);
    public void getMealsByArea(String area ,MealShortNetWorkCallBack callBack);


}
