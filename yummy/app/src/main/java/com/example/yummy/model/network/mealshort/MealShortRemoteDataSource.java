package com.example.yummy.model.network.mealshort;

public interface MealShortRemoteDataSource {

    void getMealsByCat(String cat, MealShortNetWorkCallBack callBack);
    void getMealsByIng(String Ing, MealShortNetWorkCallBack callBack);
    void getMealsByArea(String area, MealShortNetWorkCallBack callBack);


}
