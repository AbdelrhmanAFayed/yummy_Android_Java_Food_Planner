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
import com.example.yummy.main.OnCalendarItemClickListener;
import com.example.yummy.model.meal.Meal;

import java.util.List;

public class CalAdapter extends RecyclerView.Adapter<CalAdapter.ViewHolder> {



    private final Context context;
    private final List<Meal> items;
    private OnCalendarItemClickListener listener;

    public CalAdapter(Context context,
                       List<Meal> items,
                      OnCalendarItemClickListener listener) {
        this.context  = context;
        this.items    = items;

        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.cal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {

        Glide.with(holder.image.getContext()).clear(holder.image);

        Meal meal = items.get(pos);

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
            listener.onMealClicked(meal);
        });

        holder.deleteButton.setOnClickListener(v -> {
            listener.onDeleteMeal(meal);
        });

    }

    @Override public int getItemCount() { return items.size(); }

    public void update(List<Meal> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        View deleteButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.calendarMealImage);
            title = itemView.findViewById(R.id.calendarMealTitle);
            deleteButton = itemView.findViewById(R.id.btnDeleteFromCalendar);
        }
    }
}

