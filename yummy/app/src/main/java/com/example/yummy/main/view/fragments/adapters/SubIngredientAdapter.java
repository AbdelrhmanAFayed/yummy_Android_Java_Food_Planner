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
import com.example.yummy.model.ingredient.Ingredient;

import java.util.List;


public class SubIngredientAdapter extends RecyclerView.Adapter<SubIngredientAdapter.ViewHolder> {

    private final Context context;
    private final List<Ingredient> items;



    public SubIngredientAdapter(Context context, List<Ingredient> items) {
        this.context = context;
        this.items = items;




    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title ;
        ImageView img ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.subTitle);
            img = itemView.findViewById(R.id.sunImg);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal, parent, false); // Replace with your actual layout
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load("https://www.themealdb.com/images/ingredients/" +items.get(position).getStrIngredient() +".png").circleCrop().into(holder.img);

        holder.title.setText(items.get(position).getStrIngredient());

    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }
}