package com.example.yummy.model.category;

import com.example.yummy.model.network.category.CatNetWorkCallBack;
import com.example.yummy.model.network.category.CategoryRemoteDataSource;
import com.example.yummy.model.network.category.CategoryRemoteDataSourceImp;

public class CategoryRepositoryImp implements CategoryRepository {


    CategoryRemoteDataSource remoteDataSource ;
    private static CategoryRepositoryImp repo = null ;


    private CategoryRepositoryImp(CategoryRemoteDataSource remoteDataSource)
    {
        this.remoteDataSource = remoteDataSource ;
    }

    public static CategoryRepositoryImp getInstance() {

        if(repo == null)
        {
            repo = new CategoryRepositoryImp(CategoryRemoteDataSourceImp.getInstance());
        }
        return repo;
    }




    @Override
    public void getAll(CatNetWorkCallBack callBack) {

        remoteDataSource.getAllCat(callBack);

    }
}
