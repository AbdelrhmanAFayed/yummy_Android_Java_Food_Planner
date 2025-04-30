package com.example.yummy.model.mealshort;

import com.example.yummy.model.network.mealshort.MealShortNetWorkCallBack;

public interface MealShortRepository {

    public void getByCat(String cat , MealShortNetWorkCallBack callBack);

    public void getByCountry(String country, MealShortNetWorkCallBack callBack);

    public void getByIng(String ing , MealShortNetWorkCallBack callBack );
}
