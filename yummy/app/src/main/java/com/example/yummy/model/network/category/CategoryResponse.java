package com.example.yummy.model.network.category;

import com.example.yummy.model.Category;

import java.util.List;

public class CategoryResponse {
    public List<Category> categories ;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
