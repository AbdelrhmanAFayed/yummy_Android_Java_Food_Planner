package com.example.yummy.main.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.yummy.R;
import com.example.yummy.details.view.DetailedMeal;
import com.example.yummy.main.MainContract;
import com.example.yummy.main.presenter.fragpresenter.SearchPresenter;
import com.example.yummy.main.view.fragments.adapters.SearchAdapter;
import com.example.yummy.main.OnSearchItemClickListener;
import com.example.yummy.meals.view.MealActivity;
import com.example.yummy.model.area.Area;
import com.example.yummy.model.area.AreaRepositoryImp;
import com.example.yummy.model.category.Category;
import com.example.yummy.model.category.CategoryRepositoryImp;
import com.example.yummy.model.ingredient.Ingredient;
import com.example.yummy.model.ingredient.IngredientRepositoryImp;
import com.example.yummy.model.meal.Meal;
import com.example.yummy.model.meal.MealRepositoryImp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SearchFragment extends Fragment implements MainContract.SearchView , OnSearchItemClickListener {

    private SearchView searchView;
    private RadioGroup tabs;
    private RecyclerView recycler;
    private MainContract.SearchPresenter presenter;
    private SearchAdapter adapter ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.searchView);
        tabs = view.findViewById(R.id.tabGroup);
        recycler = view.findViewById(R.id.recyclerViewSearch);

        presenter = new SearchPresenter(
                this,
                MealRepositoryImp.getInstance(requireContext()),
                AreaRepositoryImp.getInstance(),
                CategoryRepositoryImp.getInstance(),
                IngredientRepositoryImp.getInstance()
        );

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                doSearch(query.trim());
                return true;
            }
            @Override public boolean onQueryTextChange(String newText) {
                // live filtering as you type:
                doSearch(newText.trim());
                return true;
            }
        });
        tabs.check(R.id.tab_meals);

        tabs.setOnCheckedChangeListener((group, checkedId) -> {
            searchView.setQuery("", false);

            if (checkedId == R.id.tab_meals) {
                adapter = new SearchAdapter(
                        requireContext(),
                        Collections.emptyList(),
                        SearchAdapter.Mode.MEAL,
                        this
                );
                recycler.setAdapter(adapter);

            } else if (checkedId == R.id.tab_countries) {
                presenter.searchCountries("");

            } else if (checkedId == R.id.tab_categories) {
                presenter.searchCategories("");

            } else if (checkedId == R.id.tab_ingredients) {
                presenter.searchIngredients("");
            }
        });
    }
    private void doSearch(String query) {

        int id = tabs.getCheckedRadioButtonId();
        if (id == R.id.tab_meals) {
            presenter.searchMealsByName(query);
        } else if (id == R.id.tab_countries) {
            presenter.searchCountries(query);
        } else if (id == R.id.tab_categories) {
            presenter.searchCategories(query);
        } else if (id == R.id.tab_ingredients) {
            presenter.searchIngredients(query);
        }
    }



    @Override
    public void showMealSearchResults(List<Meal> meals) {

        if(meals == null )
        {
            meals = new ArrayList<Meal>();

        }
            adapter = new SearchAdapter(
                    requireContext(),
                    meals,
                    SearchAdapter.Mode.MEAL, this

            );

        recycler.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recycler.setAdapter(adapter);
    }

    @Override
    public void showCountrySearchResults(List<Area> filteredAreas) {
        if (!isAdded()) return;
        adapter = new SearchAdapter(
                requireContext(),
                filteredAreas,
                SearchAdapter.Mode.COUNTRY,
                this
        );

        recycler.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        recycler.setAdapter(adapter);
    }

    @Override
    public void showCategorySearchResults(List<Category> filteredCategories) {
        if (!isAdded()) return;
        adapter = new SearchAdapter(
                requireContext(),
                filteredCategories,
                SearchAdapter.Mode.CATEGORY,
                this
        );
        recycler.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recycler.setAdapter(adapter);
    }

    @Override
    public void showIngredientSearchResults(List<Ingredient> filteredIngredients) {
        if (!isAdded()) return;
        adapter = new SearchAdapter(
                requireContext(),
                filteredIngredients,
                SearchAdapter.Mode.INGREDIENT,
                this
        );
        recycler.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recycler.setAdapter(adapter);
    }

    @Override
    public void showSearchError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchItemClick(String sourceType, String value) {
        if(sourceType == "Meal")
        {
            Intent intent = new Intent(requireContext(), DetailedMeal.class);
            intent.putExtra("meal_id", value);
            startActivity(intent);

        }
        else {
            Intent intent = new Intent(requireContext(), MealActivity.class);
            intent.putExtra(MealActivity.EXTRA_MEAL_SOURCE_TYPE, sourceType);
            intent.putExtra(MealActivity.EXTRA_MEAL_SOURCE_VALUE, value);
            startActivity(intent);
        }
    }
}