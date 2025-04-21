package com.example.yummy.model.network.area;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AreaService {

    @GET("/json/v1/1/list.php?a=list")
    Call<AreaResponse> getAllAreas();
}
