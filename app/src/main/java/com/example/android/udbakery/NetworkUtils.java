package com.example.android.udbakery;

import android.util.Log;

import com.example.android.udbakery.Model.BakeryModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by SSubra27 on 7/6/17.
 */

public class NetworkUtils  {


    private static final String API_URL = "https://d17h27t6h515a5.cloudfront.net";
    private static final String TAG = NetworkUtils.class.getSimpleName() ;


    private Call<List<BakeryModel.Bakery>> call;

    private List<BakeryModel.Bakery> mBakeryModel;

    private List<BakeryModel.Bakery> items;



    public NetworkUtils()
    {

    }
    public List<BakeryModel.Bakery> start()
    {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(API_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

        BakeryService.BakeryItemAPI bakeryItemAPI = retrofit.create(BakeryService.BakeryItemAPI.class);

        call = bakeryItemAPI.loadIngredients();
        call.enqueue(new Callback<List<BakeryModel.Bakery>>() {
            @Override
            public void onResponse(Call<List<BakeryModel.Bakery>> call, Response<List<BakeryModel.Bakery>> response) {
                mBakeryModel = response.body();
                Log.i(TAG, String.valueOf(response.body()));
            }

            @Override
            public void onFailure(Call<List<BakeryModel.Bakery>> call, Throwable t) {

            }
        });

        return mBakeryModel;
    }

//    https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json



    public static class BakeryService
    {
        public interface BakeryItemAPI
        {
            @GET("/topher/2017/May/59121517_baking/baking.json")
            Call<List<BakeryModel.Bakery>> loadIngredients();

        }
    }
}
