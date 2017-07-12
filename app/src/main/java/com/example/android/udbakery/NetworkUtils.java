package com.example.android.udbakery;

import android.util.Log;

import com.example.android.udbakery.Model.BMBakeryModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by SSubra27 on 7/6/17.
 */

public class NetworkUtils implements Callback<BMBakeryModel> {


    private static final String API_URL = "https://d17h27t6h515a5.cloudfront.net";
    private static final String TAG = NetworkUtils.class.getSimpleName() ;

    public NetworkUtils()
    {

    }
    public void start()
    {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(API_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

        BakeryService.BakeryItemAPI bakeryItemAPI = retrofit.create(BakeryService.BakeryItemAPI.class);

        Call<BMBakeryModel> call = bakeryItemAPI.loadIngredients();
        call.enqueue(this);
    }

//    https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json

    @Override
    public void onResponse(Call<BMBakeryModel> call, Response<BMBakeryModel> response) {
    if (response.isSuccessful()) {

        Log.i(TAG, String.valueOf(response.body()));
    } else {
        System.out.println(response.errorBody());
    }
}


    @Override
    public void onFailure(Call<BMBakeryModel> call, Throwable t) {

    }

    public static class BakeryService
    {
        public interface BakeryItemAPI
        {
            @GET("/topher/2017/May/59121517_baking/baking.json")
            Call<BMBakeryModel> loadIngredients();

        }
    }
}
