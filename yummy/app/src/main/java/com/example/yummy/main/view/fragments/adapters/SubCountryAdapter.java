package com.example.yummy.main.view.fragments.adapters;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.circleCrop;

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
import com.example.yummy.model.area.Area;

import java.util.List;


public class SubCountryAdapter extends RecyclerView.Adapter<SubCountryAdapter.ViewHolder> {

    private Context context;
    private List<Area> areaList;

    public interface OnCountryClickListener {
        void onCountryClick(String countryName);
    }

    public SubCountryAdapter(Context context, List<Area> areaList) {
        this.context = context;
        this.areaList = areaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Area area = areaList.get(position);
        holder.countryName.setText(area.getStrArea());

        Glide.with(context).load("https://flagcdn.com/w2560/" + Area.COUNTRY_TO_CODE.get(area.getStrArea()) + ".png")
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView countryName;
        ImageView img ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.subTitle);
            img = itemView.findViewById(R.id.sunImg);
        }
    }
}
