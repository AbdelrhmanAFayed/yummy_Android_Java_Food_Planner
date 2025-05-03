package com.example.yummy.main.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yummy.R;
import com.example.yummy.details.view.DetailedMeal;
import com.example.yummy.main.MainContract;
import com.example.yummy.main.OnFavClickListener;
import com.example.yummy.main.presenter.fragpresenter.FavPresenter;
import com.example.yummy.main.view.fragments.adapters.FavAdapter;
import com.example.yummy.main.OnSearchItemClickListener;
import com.example.yummy.model.meal.Meal;
import com.example.yummy.model.meal.MealRepositoryImp;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class FavFragment extends Fragment implements MainContract.FavoritesView, OnFavClickListener {

    private MainContract.FavoritesPresenter presenter;
    private RecyclerView recycler;
    private FavAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FavPresenter(this, MealRepositoryImp.getInstance(requireContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recyclerFav);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new FavAdapter(requireContext(), new ArrayList<>(), this);
        recycler.setAdapter(adapter);

        presenter.loadAllFavorites();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadAllFavorites();
    }

    @Override
    public void showFavorites(List<Meal> favorites) {
        adapter.updateList(favorites);
    }

    @Override
    public void showFavoritesError(String message) {
    }

    @Override
    public void onItemClick(Meal meal) {
        Intent intent = new Intent(requireContext(), DetailedMeal.class);
        intent.putExtra("meal_id", meal.getIdMeal());
        startActivity(intent);
    }

    @Override
    public void onRemove(Meal meal) {
        presenter.removeFavorite(meal);
    }
}
