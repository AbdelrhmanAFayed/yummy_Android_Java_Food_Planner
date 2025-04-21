package com.example.yummy.model.network.category;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("json/v1/1/categories.php")
    Call<CategoryResponse> getCategories();




}
