package com.example.android.udbakery;

import com.example.android.udbakery.Model.BakeryPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by SSubra27 on 7/6/17.
 */

public interface BakeryItemAPI
    {
        @GET("topher/2017/May/59121517_baking/baking.json")
        Call<List<BakeryPojo>> loadIngredients();

    }

