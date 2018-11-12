package com.example.aadil.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aadil.bakingapp.model.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private ArrayList<Ingredient> ingredientList;
    private Context context;

    public IngredientAdapter(ArrayList<Ingredient> mIngredientList) {
        this.ingredientList = mIngredientList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_tv) TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View ingredientItem = LayoutInflater.from(context)
                .inflate(R.layout.ingredient_item, viewGroup, false);

        return new IngredientAdapter.ViewHolder(ingredientItem);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder viewHolder, int i) {
        final Ingredient ingredient = ingredientList.get(i);
        String quantity;
        String measure;
        String ingredientName;

        quantity = Double.toString(ingredient.getQuantity());
        measure = ingredient.getMeasure();
        ingredientName = ingredient.getIngredient();

        viewHolder.mTextView.setText(quantity);
        viewHolder.mTextView.append(" " + measure.toLowerCase());
        viewHolder.mTextView.append(" " + ingredientName.toLowerCase());
    }

    @Override
    public int getItemCount() {
        if(ingredientList == null) {
            return 0;
        }
        return ingredientList.size();
    }
}