package com.example.yummy.main.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.yummy.R;
import com.example.yummy.details.view.DetailedMeal;
import com.example.yummy.main.MainContract;
import com.example.yummy.main.OnCalendarItemClickListener;
import com.example.yummy.main.presenter.fragpresenter.CalPresenter;
import com.example.yummy.main.view.fragments.adapters.CalAdapter;
import com.example.yummy.model.meal.Meal;
import com.example.yummy.model.meal.MealRepositoryImp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalFragment extends Fragment implements MainContract.CalendarView , OnCalendarItemClickListener {
    private CalendarView calendarViewWidget;
    private RecyclerView recycler;
    private MainContract.CalendarPresenter presenter;
    private CalAdapter adapter;

    private long selectedDayMillis;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarViewWidget = view.findViewById(R.id.calendarView);
        recycler = view.findViewById(R.id.recyclerMealsForDay);




        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CalAdapter(requireContext(),new ArrayList<>(),this);
        recycler.setAdapter(adapter);


        Calendar todayCal = Calendar.getInstance();
        todayCal.set(Calendar.HOUR_OF_DAY, 0);
        todayCal.set(Calendar.MINUTE,      0);
        todayCal.set(Calendar.SECOND,      0);
        todayCal.set(Calendar.MILLISECOND, 0);
        long todayMillis = todayCal.getTimeInMillis();
        selectedDayMillis = todayMillis ;


        calendarViewWidget.setDate(todayMillis,false,true);
        Date today = new Date(todayMillis);
        presenter = new CalPresenter(this, MealRepositoryImp.getInstance(requireContext()));
        presenter.loadMealsForDay(today);

        calendarViewWidget.setOnDateChangeListener((calendarViewWidget, year, month, dayOfMonth) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, dayOfMonth, 0, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            selectedDayMillis = cal.getTimeInMillis();
            presenter.loadMealsForDay(cal.getTime());
        });



    }


    @Override
    public void showMealsForDay(List<Meal> meals) {
        adapter.update(meals);
    }

    @Override
    public void showCalendarError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealClicked(Meal meal) {
        Intent intent = new Intent(requireContext(), DetailedMeal.class);
        intent.putExtra("meal_id", meal.getIdMeal());
        startActivity(intent);

    }

    @Override
    public void onDeleteMeal(Meal meal) {
        presenter.removeMealFromDay(meal, selectedDayMillis);
    }
}
