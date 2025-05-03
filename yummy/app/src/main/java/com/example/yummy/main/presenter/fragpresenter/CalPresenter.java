package com.example.yummy.main.presenter.fragpresenter;

import androidx.lifecycle.Observer;

import com.example.yummy.main.MainContract;
import com.example.yummy.model.db.MealPlan;
import com.example.yummy.model.meal.Meal;
import com.example.yummy.model.meal.MealRepository;

import java.util.Date;
import java.util.List;

public class CalPresenter implements MainContract.CalendarPresenter {

    private final MainContract.CalendarView view;
    private final MealRepository repo;

    public CalPresenter(MainContract.CalendarView view, MealRepository repo) {
        this.view = view;
        this.repo = repo;
    }


    @Override
    public void loadMealsForDay(Date date) {
        long dayStartMillis = date.getTime();
        repo.getMealsForDate(dayStartMillis).observeForever(new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                view.showMealsForDay(meals);
            }
        });
    }

    @Override
    public void removeMealFromDay(Meal meal , long day) {
        repo.removeMealFromPlan(meal,day);
    }
}
