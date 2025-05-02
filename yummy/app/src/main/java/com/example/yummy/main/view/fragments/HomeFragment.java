package com.example.yummy.main.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yummy.R;
import com.example.yummy.details.view.DetailedMeal;
import com.example.yummy.main.MainContract;
import com.example.yummy.main.presenter.fragpresenter.HomePresenter;
import com.example.yummy.main.view.fragments.adapters.HomeAdapter;
import com.example.yummy.main.OnSearchItemClickListener;
import com.example.yummy.meals.view.MealActivity;
import com.example.yummy.model.area.AreaRepositoryImp;
import com.example.yummy.model.category.CategoryRepositoryImp;
import com.example.yummy.model.ingredient.IngredientRepositoryImp;
import com.example.yummy.model.meal.Meal;
import com.example.yummy.model.meal.MealRepositoryImp;
import com.example.yummy.model.mealshort.MealShortRepositoryImp;
import com.example.yummy.model.network.area.AreaResponse;
import com.example.yummy.model.network.category.CategoryResponse;
import com.example.yummy.model.network.ingredient.IngredientResponse;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class HomeFragment extends Fragment implements MainContract.HomeView, OnSearchItemClickListener {


    private MainContract.HomePresenter presenter;

    private ImageView randImg;
    private TextView mealTitle, mealDesc;
    private RecyclerView ingRecyclerView, catRecyclerView, countryRecyclerView;;
    private MaterialButton btnSurprise;

    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new HomePresenter(
                this,
                MealRepositoryImp.getInstance(getContext())
                ,IngredientRepositoryImp.getInstance()
                ,CategoryRepositoryImp.getInstance()
                ,AreaRepositoryImp.getInstance()
                ,MealShortRepositoryImp.getInstance()
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        randImg = view.findViewById(R.id.randImg);
        mealTitle = view.findViewById(R.id.randTitle);
        mealDesc = view.findViewById(R.id.randDesc);
        ingRecyclerView = view.findViewById(R.id.recyclerIng);
        catRecyclerView = view.findViewById(R.id.recyclerCat);
        btnSurprise = view.findViewById(R.id.btn_Surprise);
        countryRecyclerView = view.findViewById(R.id.recyclerCountry);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.getMealOfDay();


        presenter.getIngredients();
        presenter.getCategories();
        presenter.getCountries();


        btnSurprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getRandomMeal();
            }
        });


        randImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onMealImageClicked();
            }
        });

    }

    @Override
    public void showRandomMeal(Meal meal) {

        mealTitle.setText(meal.getStrMeal());
        mealDesc.setText(meal.getStrInstructions() != null && meal.getStrInstructions().length() > 100
                ? meal.getStrInstructions().substring(0, 100) + "....."
                : meal.getStrInstructions());

        Glide.with(requireContext())
                .load(meal.getStrMealThumb())
                .into(randImg);

    }

    @Override
    public void showIngredients(IngredientResponse ingredientResponse) {
        HomeAdapter adapter = new HomeAdapter(requireContext(), ingredientResponse.getIngredients(), HomeAdapter.Mode.INGREDIENT, this);
        ingRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        ingRecyclerView.setAdapter(adapter);

    }

    @Override
    public void showCategories(CategoryResponse categoryResponse) {
        HomeAdapter adapter = new HomeAdapter(requireContext(), categoryResponse.getCategories(), HomeAdapter.Mode.CATEGORY, this);
        catRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        catRecyclerView.setAdapter(adapter);
    }


    @Override
    public void showHomeError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void openMealDetails(Meal meal) {
        Intent intent = new Intent(requireContext(), DetailedMeal.class);
        intent.putExtra("meal", new Gson().toJson(meal));
        startActivity(intent);

    }

    @Override
    public void saveMeal(Meal meal) {

        SharedPreferences prefs = requireContext().getSharedPreferences("MealPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String mealJson = new Gson().toJson(meal);
        editor.putString("meal_json", mealJson);
        editor.apply();

    }


    @Override
    public void showCountries(AreaResponse areaResponse) {
        HomeAdapter adapter = new HomeAdapter(requireContext(), areaResponse.getAreas(), HomeAdapter.Mode.COUNTRY, this);
        countryRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        countryRecyclerView.setAdapter(adapter);
    }



    @Override
    public void onSearchItemClick(String sourceType, String value) {
        Intent intent = new Intent(requireContext(), MealActivity.class);
        intent.putExtra(MealActivity.EXTRA_MEAL_SOURCE_TYPE, sourceType);
        intent.putExtra(MealActivity.EXTRA_MEAL_SOURCE_VALUE, value);
        startActivity(intent);
    }
}