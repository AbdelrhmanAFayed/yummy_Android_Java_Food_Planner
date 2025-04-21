package com.example.yummy.model.network.ingredient;

import com.example.yummy.model.Ingredient;

import java.util.List;

public class IngredientResponse {

    List<Ingredient> ingredients ;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
