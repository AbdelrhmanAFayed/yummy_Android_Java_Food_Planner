package com.example.yummy.meals.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.R;
import com.example.yummy.main.view.fragments.adapters.SearchAdapter;
import com.example.yummy.meals.MealContract;
import com.example.yummy.meals.OnSearchItemClickListener;
import com.example.yummy.meals.presenter.MealPresenter;
import com.example.yummy.model.mealshort.MealShort;
import com.example.yummy.model.mealshort.MealShortRepositoryImp;
import com.example.yummy.model.network.mealshort.MealShortRemoteDataSource;
import com.example.yummy.model.network.mealshort.MealShortRemoteDataSourceImp;

import java.util.ArrayList;
import java.util.List;

public class MealActivity extends AppCompatActivity implements MealContract.View , OnSearchItemClickListener {

    public static final String EXTRA_MEAL_SOURCE_TYPE = "meal_source_type";
    public static final String EXTRA_MEAL_SOURCE_VALUE = "meal_source_value";


    public static final String SOURCE_TYPE_INGREDIENT = "ingredient";
    public static final String SOURCE_TYPE_AREA = "area";
    public static final String SOURCE_TYPE_CATEGORY = "category";

    private MealContract.Presenter presenter;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private SearchAdapter.Mode currentMode;
    private List<MealShort> fullList = new ArrayList<>();
    private List<MealShort> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        Intent intent = getIntent();
        String sourceType = intent.getStringExtra(EXTRA_MEAL_SOURCE_TYPE);
        String sourceValue = intent.getStringExtra(EXTRA_MEAL_SOURCE_VALUE);



        MealShortRepositoryImp repository = MealShortRepositoryImp.getInstance();

        presenter = new MealPresenter(this, repository);

        loadData();
        initViews();
        setupRecyclerView();
        setupSearchView();



    }

    private void initViews() {
        searchView = findViewById(R.id.meal_search_view);
        recyclerView = findViewById(R.id.meal_recycler_view);
    }

    private void setupRecyclerView() {
        adapter = new SearchAdapter(this, filteredList,SearchAdapter.Mode.MEAL_SHORT,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(fullList);
        } else {
            for (MealShort meal : fullList) {
                if (meal.getStrMeal().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(meal);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void loadData() {
        String sourceType = getIntent().getStringExtra(EXTRA_MEAL_SOURCE_TYPE);
        String sourceValue = getIntent().getStringExtra(EXTRA_MEAL_SOURCE_VALUE);

        if (sourceType == null || sourceValue == null) {
            Toast.makeText(this, "No data received", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (sourceType) {
            case SOURCE_TYPE_INGREDIENT:
                currentMode = SearchAdapter.Mode.INGREDIENT;
                presenter.loadMealsByIngredient(sourceValue);
                break;
            case SOURCE_TYPE_AREA:
                currentMode = SearchAdapter.Mode.COUNTRY;
                presenter.loadMealsByArea(sourceValue);
                break;
            case SOURCE_TYPE_CATEGORY:
                currentMode = SearchAdapter.Mode.CATEGORY;
                presenter.loadMealsByCategory(sourceValue);
                break;
            default:
                Toast.makeText(this, "Unknown source type", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void showMeals(List<MealShort> meals) {
        fullList.clear();
        fullList.addAll(meals);
        filteredList.clear();
        filteredList.addAll(meals);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void showError(String message) {
       // Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchItemClick(String sourceType, String value) {

    }
}