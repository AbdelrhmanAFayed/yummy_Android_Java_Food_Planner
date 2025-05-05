package com.example.yummy.model.network.mealshort;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealShortService {


    @GET("json/v1/1/filter.php")
    Call<MealShortResponse> getMealsByCategory(@Query("c") String category);

    @GET("json/v1/1/filter.php")
    Call<MealShortResponse> getMealsByIng(@Query("i") String ing);

    @GET("json/v1/1/filter.php")
    Call<MealShortResponse> getMealsByArea(@Query("a") String area);


}
