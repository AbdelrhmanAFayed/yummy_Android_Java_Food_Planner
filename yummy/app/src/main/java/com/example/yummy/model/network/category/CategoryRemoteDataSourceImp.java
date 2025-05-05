package com.example.yummy.model.network.category;

import com.example.yummy.model.network.RemoteDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRemoteDataSourceImp implements CategoryRemoteDataSource {

    private CategoryService service = null ;
    private static CategoryRemoteDataSourceImp client = null ;


    private CategoryRemoteDataSourceImp()
    {
        service = RemoteDataSource.getRetroInstance().create(CategoryService.class);
    }

    public static CategoryRemoteDataSourceImp getInstance()
    {

        if(client == null)
        {
            client = new CategoryRemoteDataSourceImp();
        }

        return client ;

    }

    @Override
    public void getAllCat(CatNetWorkCallBack callBack) {

        Call<CategoryResponse> call = service.getCategories();

        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                callBack.onSuccessResult(response.body());
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                callBack.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });



    }
}
