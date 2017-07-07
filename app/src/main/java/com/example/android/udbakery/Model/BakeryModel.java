package com.example.android.udbakery.Model;

/**
 * Created by SSubra27 on 7/3/17.
 */

public class BakeryModel  {

    private static final String TAG = BakeryModel.class.getSimpleName();
    public BakeryModel()
    {

    }

    public String mRecipeName;
    public String mRecipeURl;
    public String mIngridients;


    public String getRecipeName() {
        return mRecipeName;
    }

    public void setRecipeName(String recipeName) {
        mRecipeName = recipeName;
    }

    public String getRecipeURl() {
        return mRecipeURl;
    }

    public void setRecipeURl(String recipeURl) {
        mRecipeURl = recipeURl;
    }

    public String getIngridients() {
        return mIngridients;
    }

    public void setIngridients(String ingridients) {
        mIngridients = ingridients;
    }


}
