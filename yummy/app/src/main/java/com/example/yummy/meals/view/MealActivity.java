package com.example.yummy.meals.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.yummy.R;

public class MealActivity extends AppCompatActivity {

    public static final String EXTRA_MEAL_SOURCE_TYPE = "meal_source_type";
    public static final String EXTRA_MEAL_SOURCE_VALUE = "meal_source_value";


    public static final String SOURCE_TYPE_INGREDIENT = "ingredient";
    public static final String SOURCE_TYPE_AREA = "area";
    public static final String SOURCE_TYPE_CATEGORY = "category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        Intent intent = getIntent();
        String sourceType = intent.getStringExtra(EXTRA_MEAL_SOURCE_TYPE);
        String sourceValue = intent.getStringExtra(EXTRA_MEAL_SOURCE_VALUE);

        if (sourceType != null && sourceValue != null) {
            Toast.makeText(this,
                    "Type: " + sourceType + "\nValue: " + sourceValue,
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No data received", Toast.LENGTH_SHORT).show();
        }

    }
}