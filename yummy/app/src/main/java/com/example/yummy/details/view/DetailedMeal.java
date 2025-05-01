package com.example.yummy.details.view;

import static android.widget.Toast.LENGTH_LONG;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummy.R;
import com.example.yummy.details.DetailedContract;
import com.example.yummy.details.presenter.DetailedPresenter;
import com.example.yummy.main.view.MainActivity;
import com.example.yummy.model.meal.Meal;
import com.example.yummy.model.meal.MealRepositoryImp;
import com.example.yummy.onboarding.view.OnBoarding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class DetailedMeal extends AppCompatActivity implements DetailedContract.View {

    FloatingActionButton btn_add ;
    ImageView imageView ;

    TextView textTitle ;
    TextView textArea ;

    TextView textSubIns ;


    WebView webView ;
    RecyclerView ingRecyclerView ;

    private Meal currentMeal = null;
    private boolean isFavorite = false;
    private boolean userClicked = false;

    private DetailedContract.Presenter presenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_meal);

        presenter = new DetailedPresenter(this, MealRepositoryImp.getInstance(this));



        textTitle = findViewById(R.id.textTitle);
        textSubIns = findViewById(R.id.textSubIns);
        textArea = findViewById(R.id.textArea);

        imageView = findViewById(R.id.mainImg);

        webView = findViewById(R.id.webview);

        btn_add = findViewById(R.id.btn_add);

        ingRecyclerView = findViewById(R.id.recyclerIng);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((FirebaseAuth.getInstance().getCurrentUser()).isAnonymous())
                {
                    View dialogView = LayoutInflater.from(DetailedMeal.this).inflate(R.layout.boarding, null);

                    AlertDialog dialog = new AlertDialog.Builder(DetailedMeal.this)
                            .setView(dialogView)
                            .setCancelable(true)
                            .create();

                    Button loginButton = dialogView.findViewById(R.id.btn_login);
                    Button signUpButton = dialogView.findViewById(R.id.btn_signup);

                    loginButton.setOnClickListener(vi -> {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(DetailedMeal.this, OnBoarding.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        dialog.dismiss();

                    });

                    signUpButton.setOnClickListener(vi -> {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(DetailedMeal.this, OnBoarding.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        dialog.dismiss();

                    });

                    dialog.show();
                }
                else {
                    if (currentMeal == null) return;

                    userClicked = true;

                    if (isFavorite) {
                        presenter.removeFromFavorites(currentMeal);
                    } else {
                        presenter.addToFavorites(currentMeal);
                    }
                }

            }
        });

        String json = getIntent().getStringExtra("meal");

        String mealId = getIntent().getStringExtra("meal_id");

        if (json != null) {

            presenter.loadMealFromIntent(json);
        } else if (mealId != null) {

            presenter.getMealDetails(mealId);
        } else {
            Toast.makeText(this, "No meal data provided", Toast.LENGTH_SHORT).show();
            finish();
        }



    }


    @Override
    public void showMealDetails(Meal meal) {
        this.currentMeal = meal;

        textTitle.setText(meal.getStrMeal());
        textArea.setText(meal.getStrArea());
        textSubIns.setText(meal.getStrInstructions());

        Glide.with(this).load(meal.getStrMealThumb() + "/large").into(imageView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        String embedUrl = meal.getStrYoutube().replace("watch?v=", "embed/");
        String html = "<html><body style='margin:0;padding:0'>" +
                "<iframe width='100%' height='100%' src='" + embedUrl + "' " +
                "frameborder='0' allow='autoplay; encrypted-media' allowfullscreen>" +
                "</iframe></body></html>";

        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        MealIngredientAdapter adapter = new MealIngredientAdapter(this, meal.getIngredientsWithMeasurements());
        ingRecyclerView.setLayoutManager(manager);
        ingRecyclerView.setAdapter(adapter);
    }


    @Override
    public void showError(String message) {
        Toast.makeText(this, message, LENGTH_LONG).show();

    }

    @Override
    public void updateFavoriteState(boolean isFav) {
        isFavorite = isFav;

        if (isFav) {
            btn_add.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
            if (userClicked) {
                Toast.makeText(DetailedMeal.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
            }
        } else {
            btn_add.setImageResource(android.R.drawable.ic_input_add);
            if (userClicked) {
                Toast.makeText(DetailedMeal.this, "Removed from Favourites", Toast.LENGTH_SHORT).show();
            }
        }

    }




}