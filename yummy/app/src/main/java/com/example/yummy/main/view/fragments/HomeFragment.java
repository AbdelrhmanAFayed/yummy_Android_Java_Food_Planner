package com.example.yummy.main.view.fragments;

import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.example.yummy.R;
import com.example.yummy.details.view.DetailedMeal;
import com.example.yummy.main.view.fragments.adapters.SubCategoryAdapter;
import com.example.yummy.main.view.fragments.adapters.SubIngredientAdapter;
import com.example.yummy.model.category.CategoryRepository;
import com.example.yummy.model.category.CategoryRepositoryImp;
import com.example.yummy.model.ingredient.IngredientRepository;
import com.example.yummy.model.ingredient.IngredientRepositoryImp;
import com.example.yummy.model.meal.MealRepository;
import com.example.yummy.model.meal.MealRepositoryImp;
import com.example.yummy.model.network.category.CatNetWorkCallBack;
import com.example.yummy.model.network.category.CategoryResponse;
import com.example.yummy.model.network.ingredient.IngNetWorkCallBack;
import com.example.yummy.model.network.ingredient.IngredientResponse;
import com.example.yummy.model.network.meal.MealNetWorkCallBack;
import com.example.yummy.model.network.meal.MealResponse;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements MealNetWorkCallBack, IngNetWorkCallBack, CatNetWorkCallBack {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button btn_ref ;
    ImageView randImg ;

    TextView mealTitle ;
    TextView mealDesc ;

    Button btn_Surprise ;


    MealRepository mealRepository;
    IngredientRepository ingredientRepository ;

    CategoryRepository categoryRepository ;

    RecyclerView ingRecyclerView ;
    RecyclerView catRecyclerView ;

    String json ;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mealRepository = MealRepositoryImp.getInstance();
        ingredientRepository = IngredientRepositoryImp.getInstance();
        categoryRepository = CategoryRepositoryImp.getInstance();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        randImg = view.findViewById(R.id.randImg);

        mealTitle = view.findViewById(R.id.randTitle);

        mealDesc = view.findViewById(R.id.randDesc);

        ingRecyclerView = view.findViewById(R.id.recyclerIng);

        catRecyclerView = view.findViewById(R.id.recyclerCat);

        btn_Surprise =view.findViewById(R.id.btn_Surprise);

        btn_Surprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mealRepository.getRandom(HomeFragment.this);


            }
        });






        mealRepository.getRandom(this);

        ingredientRepository.getIngredients(this);

        categoryRepository.getAll(this);

        randImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), DetailedMeal.class);
                intent.putExtra("meal", json);
                startActivity(intent);

            }
        });




    }

    @Override
    public void onDaySuccessResult(MealResponse mealResponse) {

        mealTitle.setText(mealResponse.meals.get(0).getStrMeal());
        mealDesc.setText(mealResponse.meals.get(0).getStrInstructions().substring(0,50) + ".....");

        Glide.with(getContext()).load(mealResponse.meals.get(0).getStrMealThumb() + "/large")
                .into(randImg);

        Gson gson = new Gson();

        json = gson.toJson(mealResponse.meals.get(0));



    }

    @Override
    public void onDayFailureResult(String error) {

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

    @Override
    public void onSuccessResult(IngredientResponse ingredientResponse) {

        LinearLayoutManager manager  = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SubIngredientAdapter adapter = new SubIngredientAdapter(getContext(), ingredientResponse.getIngredients());
        ingRecyclerView.setAdapter(adapter);
        ingRecyclerView.setLayoutManager(manager);

    }

    @Override
    public void onSuccessResult(CategoryResponse categoryResponse) {
        LinearLayoutManager manager  = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SubCategoryAdapter adapter = new SubCategoryAdapter(getContext(),categoryResponse.getCategories());
        catRecyclerView.setAdapter(adapter);
        catRecyclerView.setLayoutManager(manager);
        Log.d("HomeFragment", "Category count: " + categoryResponse.getCategories().size());



    }

    @Override
    public void onFailureResult(String error) {

    }
}