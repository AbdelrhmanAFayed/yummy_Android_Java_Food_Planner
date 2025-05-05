package com.example.yummy.model.ingredient;

import com.example.yummy.model.network.ingredient.IngNetWorkCallBack;
import com.example.yummy.model.network.ingredient.IngredientRemoteDataSourceImp;

public class IngredientRepositoryImp implements IngredientRepository{

    IngredientRemoteDataSourceImp remoteDataSource ;

    private static IngredientRepositoryImp repo ;


    private IngredientRepositoryImp(IngredientRemoteDataSourceImp remoteDataSource)
    {
        this.remoteDataSource = remoteDataSource ;
    }


    public static IngredientRepositoryImp getInstance() {

        if(repo ==null)
        {
            repo = new IngredientRepositoryImp(IngredientRemoteDataSourceImp.getInstance());
        }

        return repo;
    }


    @Override
    public void getIngredients(IngNetWorkCallBack callBack) {

        remoteDataSource.getAllIng(callBack);

    }
}
