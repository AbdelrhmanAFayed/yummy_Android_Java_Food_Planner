package com.example.yummy.main.view;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.yummy.R;
import com.example.yummy.model.network.mealshort.MealShortNetWorkCallBack;
import com.example.yummy.model.network.mealshort.MealShortRemoteDataSource;
import com.example.yummy.model.network.mealshort.MealShortRemoteDataSourceImp;
import com.example.yummy.model.network.mealshort.MealShortResponse;

public class MainActivity extends AppCompatActivity implements MealShortNetWorkCallBack {

    MealShortRemoteDataSource source ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        source = MealShortRemoteDataSourceImp.getInstance();

        source.getMealsByCat("Seafood",this);


    }

    @Override
    public void onCatSuccessResult(MealShortResponse mealShortResponse) {


    }

    @Override
    public void onCatFailureResult(String error) {

    }

    @Override
    public void onIngSuccessResult(MealShortResponse mealShortResponse) {

    }

    @Override
    public void onIngFailureResult(String error) {

    }

    @Override
    public void onAreaSuccessResult(MealShortResponse mealShortResponse) {

    }

    @Override
    public void onAreaFailureResult(String error) {

    }
}