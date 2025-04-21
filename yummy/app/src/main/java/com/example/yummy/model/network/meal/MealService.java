package com.example.yummy.model.network.meal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MealService {

    @GET("/json/v1/1/random.php")
    Call<MealResponse> getMealOfTheDay();

    @GET("/json/v1/1/search.php?s={name}")
    Call<MealResponse> getMealByName(@Path("name") String name);

    @GET("/json/v1/1/lookup.php?i={id}")
    Call<MealResponse> getMealByID(@Path("id") String id);

    @GET("/json/v1/1/search.php?f={letter}")
    Call<MealResponse> getMealByFirstLetter(@Path("letter") String letter);





}
