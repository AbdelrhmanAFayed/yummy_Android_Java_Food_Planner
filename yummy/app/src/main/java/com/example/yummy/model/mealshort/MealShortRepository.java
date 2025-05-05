package com.example.yummy.model.mealshort;

import com.example.yummy.model.network.mealshort.MealShortNetWorkCallBack;

public interface MealShortRepository {

    void getByCat(String cat , MealShortNetWorkCallBack callBack);

    void getByCountry(String country, MealShortNetWorkCallBack callBack);

    void getByIng(String ing , MealShortNetWorkCallBack callBack );
}
