package com.example.yummy.model.network.meal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("json/v1/1/random.php")
    Call<MealResponse> getMealOfTheDay();

    @GET("json/v1/1/search.php")
    Call<MealResponse> getMealByName(@Query("s") String name);

    @GET("json/v1/1/lookup.php")
    Call<MealResponse> getMealByID(@Query("i") String id);

    @GET("json/v1/1/search.php")
    Call<MealResponse> getMealByFirstLetter(@Query("f") String letter);





}
