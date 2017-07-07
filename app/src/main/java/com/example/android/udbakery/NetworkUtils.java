package com.example.android.udbakery;

import android.net.Uri;
import android.util.Log;

import com.example.android.udbakery.Model.BakeryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by SSubra27 on 7/6/17.
 */

public class NetworkUtils  {


    private static final String API_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final String TAG = NetworkUtils.class.getSimpleName() ;

    public NetworkUtils()
    {

    }

    // Build the URL

    public static URL buildUrl()
    {
        Uri endpointUri ;
        endpointUri = Uri.parse(API_URL).buildUpon()
                .appendQueryParameter("method", "get")
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .build();

        URL url = null;

        try
        {
            url = new URL(endpointUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    // Get response from the server

    public static String getResponseFromHttpUrl(URL queryUrl) throws IOException
    {
        HttpURLConnection connection = (HttpURLConnection) queryUrl.openConnection();

        try
        {
            InputStream in = connection.getInputStream();

            Scanner sc = new Scanner(in);

            sc.useDelimiter("//A");

            boolean hasInput = sc.hasNext();

            if(hasInput)
            {
                return sc.next();
            } else
            {
                return null;
            }
        } finally {
            connection.disconnect();
        }
    }

    public ArrayList<BakeryModel> fetchItems() throws JSONException, IOException
    {
        ArrayList<BakeryModel> arrayList = new ArrayList<>();

        URL url = buildUrl();
        String responseStringJson =  getResponseFromHttpUrl(url);

        Log.i(TAG, responseStringJson);

        JSONArray jsonArray = new JSONArray(responseStringJson);

        for(int i=0; i<jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            parseItems(arrayList, jsonObject);
            Log.i("jsonResponse", jsonObject+"" );
        }
        return arrayList;
    }

    private void parseItems(ArrayList<BakeryModel> arrayList, JSONObject jsonObject) throws JSONException {

        JSONArray jsonArray = jsonObject.getJSONArray("ingredients");

        for(int i=0 ; i <jsonArray.length(); i++)
        {

            JSONObject bakeryItemObject = jsonArray.getJSONObject((i));

            BakeryModel bakeryModel = new BakeryModel();

            bakeryModel.mRecipeName = bakeryItemObject.getString("ingredient");

            arrayList.add(bakeryModel);

            Log.i(TAG, bakeryModel.mRecipeName);
        }

    }
}
