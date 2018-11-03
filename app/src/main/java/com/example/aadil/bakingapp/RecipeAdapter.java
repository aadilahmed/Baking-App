package com.example.aadil.bakingapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
    private ArrayList<JSONObject> recipeList;

    public RecipeAdapter(ArrayList<JSONObject> mRecipeList) {
        recipeList = mRecipeList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name) TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView recipeItem = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_item, viewGroup, false);

        return new RecipeAdapter.ViewHolder(recipeItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder viewHolder, int position) {
        String recipeTitle;
        try {
            recipeTitle = recipeList.get(position).getString("name");
            viewHolder.mTextView.setText(recipeTitle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(recipeList == null) {
            return 0;
        }
        return recipeList.size();
    }
}
