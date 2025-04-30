package com.example.yummy.model.network.area;

import com.example.yummy.model.area.Area;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaResponse {

    @SerializedName("meals")
    List<Area> areas ;

    public List<Area> getAreas() {
        return areas;
    }
}
