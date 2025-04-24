package com.example.yummy.model.network.meal;

import com.example.yummy.model.network.RemoteDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRemoteDataSourceImp implements MealRemoteDataSource{

    private MealService service = null ;
    private static MealRemoteDataSourceImp client = null ;


    private MealRemoteDataSourceImp() {
        service = RemoteDataSource.getRetroInstance().create(MealService.class);
    }

    public static MealRemoteDataSourceImp getInstance()
    {
        if(client == null)
        {
            client = new MealRemoteDataSourceImp();
        }

        return client ;
    }

    @Override
    public void getMealOfTheDay(MealNetWorkCallBack callBack) {

        Call<MealResponse> call = service.getMealOfTheDay();

        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                callBack.onDaySuccessResult(response.body());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
            callBack.onDayFailureResult(t.getMessage());
            t.printStackTrace();
            }
        });

    }

    @Override
    public void getMealByName(String name, MealNetWorkCallBack callBack) {

        Call<MealResponse> call = service.getMealByName(name);

        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                callBack.onNameSuccessResult(response.body());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callBack.onNameFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });



    }

    @Override
    public void getMealByID(String id, MealNetWorkCallBack callBack) {

        Call<MealResponse> call = service.getMealByID(id);

        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                callBack.onIDSuccessResult(response.body());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callBack.onIDFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });

    }

    @Override
    public void getMealByFirstLetter(String letter, MealNetWorkCallBack callBack) {

        Call<MealResponse> call = service.getMealByFirstLetter(letter);

        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                callBack.onLetterSuccessResult(response.body());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callBack.onLetterFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });

    }
}
