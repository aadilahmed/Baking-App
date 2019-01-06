package com.example.aadil.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Recipe implements Parcelable{
    private String name;
    private int servings;
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<Step> steps = new ArrayList<>();

    public Recipe(JSONObject recipeObject) throws JSONException {
        this.name = recipeObject.getString("name");
        this.servings = recipeObject.getInt("servings");
        JSONArray ingredientArray = recipeObject.getJSONArray("ingredients");

        ArrayList<JSONObject> ingredientJSON = new ArrayList<>();

        for(int i = 0; i < ingredientArray.length(); i++) {
            ingredientJSON.add(ingredientArray.getJSONObject(i));
        }

        for(int i = 0; i < ingredientJSON.size(); i++) {
            this.ingredients.add(new Ingredient(ingredientJSON.get(i)));
        }

        JSONArray stepArray = recipeObject.getJSONArray("steps");

        ArrayList<JSONObject> stepJSON = new ArrayList<>();

        for(int i = 0; i < stepArray.length(); i++) {
            stepJSON.add(stepArray.getJSONObject(i));
        }

        for(int i = 0; i < stepJSON.size(); i++) {
            this.steps.add(new Step(stepJSON.get(i)));
        }
    }

    public Recipe(String name, int servings, ArrayList<Ingredient> ingredients,
                   ArrayList<Step> steps) {
        this.name = name;
        this.servings = servings;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    private Recipe(Parcel in) {
        name = in.readString();
        servings = in.readInt();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int i) {
            return new Recipe[i];
        }
    };

    public static ArrayList<Recipe> toRecipe(ArrayList<JSONObject> recipes) throws JSONException {
        ArrayList<Recipe> recipeList = new ArrayList<>();

        for(int i = 0; i < recipes.size(); i++) {
            recipeList.add(new Recipe(recipes.get(i)));
        }

        return recipeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }
}