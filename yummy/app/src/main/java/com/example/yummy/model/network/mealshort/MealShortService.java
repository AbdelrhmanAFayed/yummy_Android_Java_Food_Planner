package com.example.yummy.model.network.mealshort;

import com.example.yummy.model.network.meal.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MealShortService {


    @GET("api/json/v1/1/filter.php?c={cat}")
    Call<MealShortResponse> getMealsByCategory(@Path("cat") String category);

    @GET("api/json/v1/1/filter.php?i={ing}")
    Call<MealShortResponse> getMealsByIng(@Path("ing") String ing);

    @GET("api/json/v1/1/filter.php?a={area}")
    Call<MealShortResponse> getMealsByArea(@Path("area") String area);


}
