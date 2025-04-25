package com.example.yummy.model.area;

import com.example.yummy.model.network.area.AreaNetWorkCallBack;
import com.example.yummy.model.network.area.AreaRemoteDataSource;
import com.example.yummy.model.network.area.AreaRemoteDataSourceImp;

public class AreaRepositoryImp implements AreaRepository {

    AreaRemoteDataSource remoteDataSource ;
    private static AreaRepositoryImp repo = null ;

    private AreaRepositoryImp(AreaRemoteDataSource remoteDataSource)
    {
        this.remoteDataSource = remoteDataSource ;

    }

    public static AreaRepositoryImp getInstance() {

        if(repo == null)
        {
            repo = new AreaRepositoryImp(AreaRemoteDataSourceImp.getInstance());
        }

        return repo;
    }

    @Override
    public void getAllAreas(AreaNetWorkCallBack callBack) {

        remoteDataSource.getAllAreas(callBack);


    }
}
