package com.example.yummy.main.view.fragments.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummy.R;
import com.example.yummy.main.OnFavClickListener;
import com.example.yummy.model.meal.Meal;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    private final List<Meal> meals;
    private final Context context;
    private final OnFavClickListener listener;

    public FavAdapter(Context context, List<Meal> meals, OnFavClickListener listener) {
        this.context = context;
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.fav_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        Meal meal = meals.get(pos);
        holder.title.setText(meal.getStrMeal());

        byte[] imageData = meal.getImageData();
        if (imageData != null && imageData.length > 0) {
            Log.i("ImageStored","Stored");
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            holder.image.setImageBitmap(bitmap);
        } else {
            Glide.with(holder.image.getContext()).load(meal.getStrMealThumb()).into(holder.image);
        }


        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(meal);
            }
        });

        holder.btnRemove.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRemove(meal);
            }
        });
    }

    public void updateList(List<Meal> newMeals) {
        meals.clear();
        meals.addAll(newMeals);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        View btnRemove;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageMeal);
            title = itemView.findViewById(R.id.textMealName);
            btnRemove = itemView.findViewById(R.id.btnRemoveFavorite);
        }
    }
}
