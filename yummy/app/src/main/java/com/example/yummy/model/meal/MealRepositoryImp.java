package com.example.yummy.model.meal;

import com.example.yummy.model.network.meal.MealNetWorkCallBack;
import com.example.yummy.model.network.meal.MealRemoteDataSource;
import com.example.yummy.model.network.meal.MealRemoteDataSourceImp;


public class MealRepositoryImp implements MealRepository{


    MealRemoteDataSource remoteDataSource ;

    private static MealRepositoryImp repo = null ;


    public static MealRepositoryImp getInstance() {
        if(repo ==null)
        {
            repo = new MealRepositoryImp(MealRemoteDataSourceImp.getInstance());
        }
        return repo;
    }


    private MealRepositoryImp(MealRemoteDataSource remoteDataSource)
    {
        this.remoteDataSource = remoteDataSource ;
    }


    @Override
    public void getRandom(MealNetWorkCallBack callBack) {

        remoteDataSource.getMealOfTheDay(callBack);

    }

    @Override
    public void getByName(String name, MealNetWorkCallBack callBack) {

        remoteDataSource.getMealByName(name,callBack);
    }

    @Override
    public void getByID(String ID, MealNetWorkCallBack callBack) {

        remoteDataSource.getMealByID(ID , callBack);

    }

    @Override
    public void getByFirstLetter(String letter, MealNetWorkCallBack callBack) {

        remoteDataSource.getMealByFirstLetter(letter,callBack);

    }
}
