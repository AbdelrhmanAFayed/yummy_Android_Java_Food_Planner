package com.example.yummy.model.network.mealshort;

import com.example.yummy.model.network.RemoteDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealShortRemoteDataSourceImp implements MealShortRemoteDataSource{

    private MealShortService service = null ;
    private static MealShortRemoteDataSourceImp client = null ;



    private MealShortRemoteDataSourceImp() {
        service = RemoteDataSource.getRetroInstance().create(MealShortService.class);
    }

    public static MealShortRemoteDataSourceImp getInstance() {

        if(client == null)
        {
            client = new MealShortRemoteDataSourceImp();
        }

        return client ;
    }


    @Override
    public void getMealsByCat(String cat, MealShortNetWorkCallBack callBack) {

        Call<MealShortResponse> call = service.getMealsByCategory(cat);

        call.enqueue(new Callback<MealShortResponse>() {
            @Override
            public void onResponse(Call<MealShortResponse> call, Response<MealShortResponse> response) {
                callBack.onCatSuccessResult(response.body());
            }

            @Override
            public void onFailure(Call<MealShortResponse> call, Throwable t) {
                callBack.onCatFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });

    }

    @Override
    public void getMealsByIng(String Ing, MealShortNetWorkCallBack callBack) {
        Call<MealShortResponse> call = service.getMealsByCategory(Ing);

        call.enqueue(new Callback<MealShortResponse>() {
            @Override
            public void onResponse(Call<MealShortResponse> call, Response<MealShortResponse> response) {
                callBack.onIngSuccessResult(response.body());
            }

            @Override
            public void onFailure(Call<MealShortResponse> call, Throwable t) {
                callBack.onIngFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });


    }

    @Override
    public void getMealsByArea(String area, MealShortNetWorkCallBack callBack) {

        Call<MealShortResponse> call = service.getMealsByCategory(area);

        call.enqueue(new Callback<MealShortResponse>() {
            @Override
            public void onResponse(Call<MealShortResponse> call, Response<MealShortResponse> response) {
                callBack.onCatSuccessResult(response.body());
            }

            @Override
            public void onFailure(Call<MealShortResponse> call, Throwable t) {
                callBack.onCatFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });


    }
}
