package com.example.yummy.model.network.area;

import com.example.yummy.model.network.RemoteDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AreaRemoteDataSourceImp implements AreaRemoteDataSource {

    private AreaService service ;
    private static AreaRemoteDataSourceImp client = null ;


    private AreaRemoteDataSourceImp()
    {
        service = RemoteDataSource.getRetroInstance().create(AreaService.class);
    }

    public static AreaRemoteDataSourceImp getInstance()
    {
        if(client == null)
        {
            client = new AreaRemoteDataSourceImp();
        }

        return client ;
    }



    @Override
    public void getAllAreas(AreaNetWorkCallBack callBack) {

        Call<AreaResponse> call = service.getAllAreas();

        call.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                callBack.onSuccessResult(response.body());
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                callBack.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });


    }
}
