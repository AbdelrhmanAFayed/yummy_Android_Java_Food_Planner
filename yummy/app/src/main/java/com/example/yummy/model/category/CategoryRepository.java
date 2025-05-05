package com.example.yummy.model.category;

import com.example.yummy.model.network.category.CatNetWorkCallBack;

public interface CategoryRepository {


    void getAll(CatNetWorkCallBack callBack);
}
