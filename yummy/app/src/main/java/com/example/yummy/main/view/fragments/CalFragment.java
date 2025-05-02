package com.example.yummy.main.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.yummy.R;
import com.example.yummy.main.MainContract;
import com.example.yummy.main.presenter.fragpresenter.CalPresenter;
import com.example.yummy.model.meal.Meal;
import com.example.yummy.model.meal.MealRepositoryImp;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalFragment extends Fragment implements MainContract.CalendarView {
    private CalendarView calendarViewWidget;
    private RecyclerView recycler;
    private MainContract.CalendarPresenter presenter;
    private MealAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cal, container, false);
        calendarViewWidget = root.findViewById(R.id.calendarView);
        recycler           = root.findViewById(R.id.recyclerMealsForDay);

        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MealAdapter(new ArrayList<>());
        recycler.setAdapter(adapter);

        presenter = new CalPresenter(this, MealRepositoryImp.getInstance(requireContext()));

        // Initial load for today
        presenter.loadMealsForDay(new Date(calendarViewWidget.getDate()));

        calendarViewWidget.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, dayOfMonth, 0, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            presenter.loadMealsForDay(cal.getTime());
        });

        return root;
    }

    @Override
    public void showMealsForDay(List<Meal> meals) {
        adapter.update(meals);
    }

    @Override
    public void showCalendarError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
