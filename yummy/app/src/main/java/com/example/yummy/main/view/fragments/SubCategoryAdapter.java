package com.example.yummy.main.view.fragments;

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
import com.example.yummy.model.category.Category;
import com.example.yummy.model.ingredient.Ingredient;

import java.util.List;


public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    private final Context context;
    private final List<Category> items;



    public SubCategoryAdapter(Context context, List<Category> items) {
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

        Glide.with(context).load(items.get(position).getStrCategoryThumb()).circleCrop().into(holder.img);

        holder.title.setText(items.get(position).getStrCategory());

    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }
}