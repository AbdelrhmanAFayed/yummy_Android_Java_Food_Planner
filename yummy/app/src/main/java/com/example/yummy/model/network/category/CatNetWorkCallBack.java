package com.example.yummy.model.network.category;

public interface CatNetWorkCallBack {

    public void onSuccessResult(CategoryResponse categoryResponse);
    public void onFailureResult(String error);

}
