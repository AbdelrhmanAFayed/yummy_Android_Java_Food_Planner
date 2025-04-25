package com.example.yummy.model.category;

import com.example.yummy.model.network.category.CatNetWorkCallBack;

public interface CategoryRepository {


    public void getAll(CatNetWorkCallBack callBack);
}
