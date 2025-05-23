package com.example.yummy.model.network.ingredient;

import com.example.yummy.model.ingredient.Ingredient;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientResponse {

    @SerializedName("meals")
    List<Ingredient> ingredients ;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
