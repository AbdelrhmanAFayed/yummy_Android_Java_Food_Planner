package com.example.yummy.model.meal;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;

import com.bumptech.glide.Glide;
import com.example.yummy.model.db.MealLocalDataSource;
import com.example.yummy.model.db.MealLocalDataSourceImp;
import com.example.yummy.model.db.MealPlan;
import com.example.yummy.model.db.MealPlanLocalDataSource;
import com.example.yummy.model.db.MealPlanLocalDataSourceImp;
import com.example.yummy.model.network.meal.MealNetWorkCallBack;
import com.example.yummy.model.network.meal.MealRemoteDataSource;
import com.example.yummy.model.network.meal.MealRemoteDataSourceImp;
import com.example.yummy.model.network.meal.MealResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class MealRepositoryImp implements MealRepository , MealNetWorkCallBack{


    private static final String PREFS_NAME = "meal_prefs";
    private static final String KEY_RANDOM_JSON = "random_meal_json";
    private static final String KEY_RANDOM_DATE = "random_meal_date";

    private final MealRemoteDataSource remoteDataSource;
    private final MealLocalDataSource localDataSource;

    private final MealPlanLocalDataSource planDataSource;

    private final SharedPreferences prefs;
    private final Gson gson = new Gson();

    private Context context = null ;

    private MealNetWorkCallBack   callerCallback;

    private static MealRepositoryImp repo = null;


    public static MealRepositoryImp getInstance(Context context) {
        if (repo == null) {
            repo = new MealRepositoryImp(
                    MealRemoteDataSourceImp.getInstance(),
                    MealLocalDataSourceImp.getInstance(context),
                    context
            );
        }
        return repo;
    }


    private MealRepositoryImp(MealRemoteDataSource remoteDataSource,
                              MealLocalDataSource localDataSource,
                              Context context) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.context = context ;
        this.planDataSource = MealPlanLocalDataSourceImp.getInstance(context);
    }


    @Override
    public void getRandom(MealNetWorkCallBack cb) {
        this.callerCallback = cb;
        remoteDataSource.getMealOfTheDay(this);
    }

    public void getMealOfTheDay(MealNetWorkCallBack cb) {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.US)
                .format(new Date());

        String storedDate = prefs.getString(KEY_RANDOM_DATE, null);
        String storedJson = prefs.getString(KEY_RANDOM_JSON, null);

        if (today.equals(storedDate) && storedJson != null) {
            MealResponse resp = new MealResponse();
            resp.meals = List.of(gson.fromJson(storedJson, Meal.class));
            cb.onDaySuccessResult(resp);
        } else {
            this.callerCallback = cb;
            remoteDataSource.getMealOfTheDay(this);
        }
    }

    @Override
    public void getByName(String name, MealNetWorkCallBack callBack) {

        remoteDataSource.getMealByName(name,callBack);
    }

    @Override
    public void getByID(String ID, MealNetWorkCallBack callBack) {

        remoteDataSource.getMealByID(ID , callBack);

    }

    @Override
    public void getByFirstLetter(String letter, MealNetWorkCallBack callBack) {

        remoteDataSource.getMealByFirstLetter(letter,callBack);

    }

    @Override
    public LiveData<List<Meal>> getAllMealsLocal() {
        return localDataSource.getAllMeals();    }

    @Override
    public LiveData<List<Meal>> getMealsByNameLocal(String name) {
        return localDataSource.getMealsByName(name);
    }

    @Override
    public LiveData<Meal> getMealByIDLocal(String ID) {
        return localDataSource.getMealByID(ID);
    }

    @Override
    public void insertMealLocal(final Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Bitmap bmp = Glide.with(context)
                            .asBitmap()
                            .load(meal.getStrMealThumb())
                            .submit()
                            .get();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] pngBytes = baos.toByteArray();

                    meal.setImageData(pngBytes);

                    localDataSource.insertMeal(meal);
                    addMealIdToFirebase(meal.getIdMeal());
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();
    }

    private void addMealIdToFirebase(String mealId) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            String uid = auth.getCurrentUser().getUid();
            DatabaseReference mealIdsRef = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(uid)
                    .child("mealIds");
            mealIdsRef.child(mealId).setValue(true);
        }
    }

    private void removeMealIdFromFirebase(String mealId) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            String uid = auth.getCurrentUser().getUid();
            DatabaseReference mealIdRef = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(uid)
                    .child("mealIds")
                    .child(mealId);
            mealIdRef.removeValue();
        }
    }

    @Override
    public void deleteMealLocal(final Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                planDataSource.removeAllPlansForMeal(meal.getIdMeal());
                localDataSource.deleteMeal(meal);
                removeMealIdFromFirebase(meal.getIdMeal());
            }
        }).start();
    }

    @Override
    public void restoreMealsFromFirebase() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            String uid = auth.getCurrentUser().getUid();
            DatabaseReference mealIdsRef = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(uid)
                    .child("mealIds");
            mealIdsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> mealIds = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        mealIds.add(snapshot.getKey());
                    }
                    fetchAndInsertMeals(mealIds);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private void fetchAndInsertMeals(List<String> mealIds) {
        for (String mealId : mealIds) {
            remoteDataSource.getMealByID(mealId, new MealNetWorkCallBack() {
                @Override
                public void onDaySuccessResult(MealResponse mealResponse) {

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
                    if (mealResponse.meals != null && !mealResponse.meals.isEmpty()) {
                        Meal meal = mealResponse.meals.get(0);
                        insertMealLocal(meal);
                    }

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
            });
        }
    }


    @Override
    public void addMealToPlan(Meal meal, long date) {
        insertMealLocal(meal);
        planDataSource.addMealToPlan(meal.getIdMeal(), date);
    }

    @Override
    public void removeMealFromPlan(Meal meal, long date) {
        planDataSource.removeMealFromPlan(meal.getIdMeal(), date);

    }

    @Override
    public LiveData<List<Meal>> getMealsForDate(long date) {
        return planDataSource.getPlannedMealsForDate(date);
    }

    @Override
    public void onDaySuccessResult(MealResponse mealResponse) {
        String json = gson.toJson(mealResponse.meals.get(0));
        prefs.edit()
                .putString(KEY_RANDOM_JSON, json)
                .putString(KEY_RANDOM_DATE,
                        new SimpleDateFormat("yyyy-MM-dd", Locale.US)
                                .format(new Date()))
                .apply();

        if (callerCallback != null) {
            callerCallback.onDaySuccessResult(mealResponse);
        }

    }

    @Override
    public void onDayFailureResult(String error) {
        if (callerCallback != null) {
            callerCallback.onDayFailureResult(error);
        }

    }

    @Override
    public void onNameSuccessResult(MealResponse m) {
        callerCallback.onNameSuccessResult(m);
    }
    @Override
    public void onNameFailureResult(String e) {
        callerCallback.onNameFailureResult(e);
    }
    @Override
    public void onIDSuccessResult(MealResponse m) {
        callerCallback.onIDSuccessResult(m);
    }
    @Override
    public void onIDFailureResult(String e) {
        callerCallback.onIDFailureResult(e);
    }
    @Override
    public void onLetterSuccessResult(MealResponse m) {
        callerCallback.onLetterSuccessResult(m);
    }
    @Override
    public void onLetterFailureResult(String e) {
        callerCallback.onLetterFailureResult(e);
    }
}
