package com.example.yummy.main.presenter.fragpresenter;

import com.example.yummy.main.MainContract;
import com.example.yummy.model.category.CategoryRepository;
import com.example.yummy.model.category.CategoryRepositoryImp;
import com.example.yummy.model.ingredient.IngredientRepository;
import com.example.yummy.model.ingredient.IngredientRepositoryImp;
import com.example.yummy.model.meal.Meal;
import com.example.yummy.model.meal.MealRepository;
import com.example.yummy.model.meal.MealRepositoryImp;
import com.example.yummy.model.network.category.CatNetWorkCallBack;
import com.example.yummy.model.network.category.CategoryResponse;
import com.example.yummy.model.network.ingredient.IngNetWorkCallBack;
import com.example.yummy.model.network.ingredient.IngredientResponse;
import com.example.yummy.model.network.meal.MealNetWorkCallBack;
import com.example.yummy.model.network.meal.MealResponse;

public class HomePresenter implements MainContract.HomePresenter, MealNetWorkCallBack, IngNetWorkCallBack, CatNetWorkCallBack {

    private MainContract.HomeView view;
    private MealRepository mealRepository;
    private IngredientRepository ingredientRepository;
    private CategoryRepository categoryRepository;
    private Meal currentMeal;

    public HomePresenter(MainContract.HomeView view,
                         MealRepository mealRepository,
                         IngredientRepository ingredientRepository,
                         CategoryRepository categoryRepository) {
        this.view = view;
        this.mealRepository = mealRepository;
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;

        currentMeal = view.loadMeal();

    }

    @Override
    public void getRandomMeal() {

            mealRepository.getRandom(this);

    }

    @Override
    public void getIngredients() {
        ingredientRepository.getIngredients(this);
    }

    @Override
    public void getCategories() {
        categoryRepository.getAll(this);
    }

    @Override
    public void saveMealToPreferences(Meal meal) {
        view.saveMeal(meal);
    }

    @Override
    public Meal loadMealFromPreferences() {
        return view.loadMeal();
    }

    @Override
    public void onMealImageClicked() {
        if (currentMeal != null) {
            view.openMealDetails(currentMeal);
        } else {
            view.showHomeError("No meal available to show details.");
        }
    }

    @Override
    public void onSuccessResult(CategoryResponse categoryResponse) {
        view.showCategories(categoryResponse);
    }

    @Override
    public void onSuccessResult(IngredientResponse ingredientResponse) {
        view.showIngredients(ingredientResponse);
    }

    @Override
    public void onFailureResult(String error) {
        view.showHomeError(error);
    }

    @Override
    public void onDaySuccessResult(MealResponse mealResponse) {
        if (mealResponse.meals != null && !mealResponse.meals.isEmpty()) {
            currentMeal = mealResponse.meals.get(0);
            view.showRandomMeal(currentMeal);
            saveMealToPreferences(currentMeal);
        }
    }

    @Override
    public void onDayFailureResult(String error) {
        view.showHomeError(error);
    }

    @Override
    public void onNameSuccessResult(MealResponse mealResponse) {

    }

    @Override
    public void onNameFailureResult(String error) {

    }

    @Override
    public void onIDSuccessResult(MealResponse mealResponse) {

    }

    @Override
    public void onIDFailureResult(String error) {

    }

    @Override
    public void onLetterSuccessResult(MealResponse mealResponse) {

    }

    @Override
    public void onLetterFailureResult(String error) {

    }
}
