package com.example.yummy.model.mealshort;

import com.example.yummy.model.network.mealshort.MealShortNetWorkCallBack;
import com.example.yummy.model.network.mealshort.MealShortRemoteDataSource;
import com.example.yummy.model.network.mealshort.MealShortRemoteDataSourceImp;

public class MealShortRepositoryImp implements MealShortRepository {

    private MealShortRemoteDataSource remoteDataSource ;


    private static MealShortRepositoryImp repo = null ;

    private MealShortRepositoryImp(MealShortRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }


    public static MealShortRepositoryImp getInstance()
    {
        if(repo == null)
        {
            repo = new MealShortRepositoryImp(MealShortRemoteDataSourceImp.getInstance());
        }

        return repo ;
    }

    @Override
    public void getByCat(String cat, MealShortNetWorkCallBack callBack) {
        remoteDataSource.getMealsByCat(cat,callBack);
    }

    @Override
    public void getByCountry(String country, MealShortNetWorkCallBack callBack) {
        remoteDataSource.getMealsByArea(country, callBack);
    }

    @Override
    public void getByIng(String ing, MealShortNetWorkCallBack callBack) {
        remoteDataSource.getMealsByIng(ing,callBack);
    }
}
