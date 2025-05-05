package com.example.yummy.model.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

abstract public class RemoteDataSource {

    public static final String API_BASE_URL = "https://themealdb.com/api/" ;

    private static Retrofit retrofit = null ;

    public static Retrofit getRetroInstance()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;

    }


}
