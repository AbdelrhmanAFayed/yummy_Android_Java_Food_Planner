package com.example.yummy.model.network.ingredient;

import com.example.yummy.model.network.RemoteDataSource;
import com.example.yummy.model.network.mealshort.MealShortRemoteDataSourceImp;
import com.example.yummy.model.network.mealshort.MealShortService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientRemoteDataSourceImp implements IngredientRemoteDataSource{

    private IngredientService service = null ;
    private static IngredientRemoteDataSourceImp client = null ;


    private IngredientRemoteDataSourceImp() {
        service = RemoteDataSource.getRetroInstance().create(IngredientService.class);
    }

    public static IngredientRemoteDataSourceImp getInstance()
    {
        if(client == null)
        {
            client = new IngredientRemoteDataSourceImp();
        }

        return client ;
    }

    @Override
    public void getAllIng(IngNetWorkCallBack callBack) {
        Call<IngredientResponse> call = service.getAllIng();

        call.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                callBack.onSuccessResult(response.body());
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
            callBack.onFailureResult(t.getMessage());
            t.printStackTrace();
            }
        });

    }
}
