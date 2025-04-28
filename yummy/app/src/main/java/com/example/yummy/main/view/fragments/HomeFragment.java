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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yummy.R;
import com.example.yummy.details.view.DetailedMeal;
import com.example.yummy.main.MainContract;
import com.example.yummy.main.presenter.fragpresenter.HomePresenter;
import com.example.yummy.main.view.fragments.adapters.SubCategoryAdapter;
import com.example.yummy.main.view.fragments.adapters.SubIngredientAdapter;
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
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class HomeFragment extends Fragment implements MainContract.HomeView {


    private MainContract.HomePresenter presenter;

    private ImageView randImg;
    private TextView mealTitle, mealDesc;
    private RecyclerView ingRecyclerView, catRecyclerView;
    private MaterialButton btnSurprise;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new HomePresenter(
                this,
                MealRepositoryImp.getInstance(getContext()),
                IngredientRepositoryImp.getInstance(),
                CategoryRepositoryImp.getInstance()
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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(loadMeal() == null)
        {
            presenter.getRandomMeal();
        }
        else
        {
            showRandomMeal(loadMeal());
        }



        presenter.getIngredients();
        presenter.getCategories();


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
        SubIngredientAdapter adapter = new SubIngredientAdapter(requireContext(), ingredientResponse.getIngredients());
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        ingRecyclerView.setLayoutManager(layoutManager);
        ingRecyclerView.setAdapter(adapter);

    }

    @Override
    public void showCategories(CategoryResponse categoryResponse) {
        SubCategoryAdapter adapter = new SubCategoryAdapter(requireContext(), categoryResponse.getCategories());
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        catRecyclerView.setLayoutManager(layoutManager);
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
    public Meal loadMeal() {
        SharedPreferences prefs = requireContext().getSharedPreferences("MealPrefs", Context.MODE_PRIVATE);
        String mealJson = prefs.getString("meal_json", null);
        if (mealJson != null) {
            return new Gson().fromJson(mealJson, Meal.class);
        }
        return null;
    }
}