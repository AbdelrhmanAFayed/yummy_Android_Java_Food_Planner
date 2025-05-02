package com.example.yummy.main.view.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummy.R;
import com.example.yummy.main.OnSearchItemClickListener;
import com.example.yummy.meals.view.MealActivity;
import com.example.yummy.model.area.Area;
import com.example.yummy.model.category.Category;
import com.example.yummy.model.ingredient.Ingredient;
import com.example.yummy.model.meal.Meal;

import java.util.List;

public class CalAdapter extends RecyclerView.Adapter<CalAdapter.ViewHolder> {

    public enum Mode { MEAL, COUNTRY, CATEGORY, INGREDIENT }


    private final Context context;
    private final List<?> items;
    private final Mode         mode;
    private OnSearchItemClickListener listener;

    public CalAdapter(Context context,
                       List<?> items,
                       Mode mode,
                       OnSearchItemClickListener listener) {
        this.context  = context;
        this.items    = items;
        this.mode     = mode;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // assumes you have a square-card layout: item_search.xml
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        Object obj = items.get(pos);
        String title = "";
        String imageUrl = null;

        switch(mode) {
            case MEAL:
                Meal m = (Meal) obj;
                title    = m.getStrMeal();
                imageUrl = m.getStrMealThumb();
                break;
            case COUNTRY:
                Area a = (Area) obj;
                title  = a.getStrArea();
                String code = Area.COUNTRY_TO_CODE.get(a.getStrArea());

                imageUrl = "https://flagcdn.com/w2560/" + code + ".png";

                break;
            case CATEGORY:
                Category c = (Category) obj;
                title    = c.getStrCategory();
                imageUrl = c.getStrCategoryThumb();
                break;
            case INGREDIENT:
                Ingredient i = (Ingredient) obj;
                title    = i.getStrIngredient();
                imageUrl = "https://www.themealdb.com/images/ingredients/" +i.getStrIngredient() +"-large.png";
                break;
        }

        holder.title.setText(title);
        if (imageUrl != null) {
            Glide.with(context)
                    .load(imageUrl)
                    .centerCrop()
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.ic_search_black_24dp);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    String sourceType = "";
                    String value = "";

                    switch (mode) {
                        case COUNTRY:
                            sourceType = MealActivity.SOURCE_TYPE_AREA;
                            value = ((Area) obj).getStrArea();
                            break;
                        case CATEGORY:
                            sourceType = MealActivity.SOURCE_TYPE_CATEGORY;
                            value = ((Category) obj).getStrCategory();
                            break;
                        case INGREDIENT:
                            sourceType = MealActivity.SOURCE_TYPE_INGREDIENT;
                            value = ((Ingredient) obj).getStrIngredient();
                            break;
                    }

                    listener.onSearchItemClick(sourceType, value);
                }
            }
        });
    }

    @Override public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.sunImg);
            title = itemView.findViewById(R.id.subTitle);
        }
    }
}

