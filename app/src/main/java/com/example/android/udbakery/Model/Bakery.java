package com.example.android.udbakery.Model;

/**
 * Created by SSubra27 on 7/3/17.
 */

public class Bakery

    {
        String id;
        String name;
        String mRecipeURl;
        String ingredients;
        String mSteps;

    public String getId() {
        return id;
    }

    public void setId(String iD) {
        this.id = iD;
    }

    public String getSteps() {
        return mSteps;
    }

    public void setSteps(String steps) {
        mSteps = steps;
    }

    public String getRecipeName() {
        return name;
    }

    public void setRecipeName(String recipeName) {
        name = recipeName;
    }

    public String getRecipeURl() {
        return mRecipeURl;
    }

    public void setRecipeURl(String recipeURl) {
        mRecipeURl = recipeURl;
    }

    public String getIngridients() {
        return ingredients;
    }

    public void setIngridients(String ingridients) {
        ingredients = ingridients;
    }
}


