package com.example.yummy.main.presenter.fragpresenter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.yummy.main.MainContract;
import com.example.yummy.model.meal.Meal;
import com.example.yummy.model.meal.MealRepository;

import java.util.List;

public class FavPresenter implements MainContract.FavoritesPresenter {

    private final MainContract.FavoritesView view;
    private final MealRepository repository;
    private LiveData<List<Meal>> currentLiveData;



    public FavPresenter(MainContract.FavoritesView view, MealRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadAllFavorites() {
        if (currentLiveData != null) currentLiveData.removeObservers((androidx.lifecycle.LifecycleOwner) view);
        currentLiveData = repository.getAllMealsLocal();
        currentLiveData.observe((androidx.lifecycle.LifecycleOwner) view, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                if (meals == null || meals.isEmpty()) {
                    view.showFavoritesError("No favorites yet.");
                } else {
                    view.showFavorites(meals);
                }
            }
        });
    }

    @Override
    public void searchFavorites(String query) {
        if (currentLiveData != null) currentLiveData.removeObservers((androidx.lifecycle.LifecycleOwner) view);
        currentLiveData = repository.getMealsByNameLocal("%" + query + "%");
        currentLiveData.observe((androidx.lifecycle.LifecycleOwner) view, meals -> {
            if (meals == null || meals.isEmpty()) {
                view.showFavoritesError("No matching favorites.");
            } else {
                view.showFavorites(meals);
            }
        });
    }

    @Override
    public void removeFavorite(Meal meal) {
        repository.deleteMealLocal(meal);
    }
}
