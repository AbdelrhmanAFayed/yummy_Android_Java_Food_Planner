package com.example.yummy.details.view;

import static android.widget.Toast.LENGTH_LONG;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummy.R;
import com.example.yummy.details.DetailedContract;
import com.example.yummy.main.view.fragments.adapters.SubIngredientAdapter;
import com.example.yummy.model.meal.Meal;
import com.google.gson.Gson;

public class DetailedMeal extends AppCompatActivity implements DetailedContract.View {

    Button btn_add ;
    ImageView imageView ;

    TextView textTitle ;
    TextView textArea ;

    TextView textSubIns ;

    WebView webView ;
    RecyclerView ingRecyclerView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_meal);

        String json = getIntent().getStringExtra("meal");

        Meal meal = new Gson().fromJson(json, Meal.class);

        textTitle = findViewById(R.id.textTitle);
        textSubIns = findViewById(R.id.textSubIns);
        textArea = findViewById(R.id.textArea);

        imageView = findViewById(R.id.mainImg);

        webView = findViewById(R.id.webview);

        ingRecyclerView = findViewById(R.id.recyclerIng);



        textTitle.setText(meal.getStrMeal());
        textArea.setText(meal.getStrArea());

        textSubIns.setText(meal.getStrInstructions());

        Glide.with(this).load(meal.getStrMealThumb() + "/large")
                .into(imageView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());



        String watchUrl = meal.getStrYoutube();
        String embedUrl = watchUrl.replace("watch?v=", "embed/");

        String html = "<html><body style='margin:0;padding:0'>" +
                "<iframe width='100%' height='100%' src='" + embedUrl + "' " +
                "frameborder='0' allow='autoplay; encrypted-media' allowfullscreen>" +
                "</iframe></body></html>";

        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);

        LinearLayoutManager manager  = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        MealIngredientAdapter adapter = new MealIngredientAdapter(this, meal.getIngredientsWithMeasurements());
        ingRecyclerView.setAdapter(adapter);
        ingRecyclerView.setLayoutManager(manager);



    }


    @Override
    public void showMealDetails(Meal meal) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void updateFavoriteState(boolean isFav) {

    }


}