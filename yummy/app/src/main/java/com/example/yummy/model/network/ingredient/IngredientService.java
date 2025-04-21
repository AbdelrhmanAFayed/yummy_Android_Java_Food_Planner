package com.example.yummy.model.network.ingredient;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IngredientService {

    @GET("/json/v1/1/list.php?i=list")
    Call<IngredientResponse> getAllIng();

}
