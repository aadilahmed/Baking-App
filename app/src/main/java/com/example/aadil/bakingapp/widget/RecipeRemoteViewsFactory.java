package com.example.aadil.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.aadil.bakingapp.R;
import com.example.aadil.bakingapp.model.Ingredient;

import java.util.ArrayList;

public class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private Intent intent;
    private ArrayList<Ingredient> ingredientList;

    public RecipeRemoteViewsFactory (Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onCreate() {
        /*Bundle bundle = intent.getExtras();

        if(bundle != null) {
            ingredientList = bundle.getBundle("bundle").getParcelableArrayList("ingredientList");
        }*/
    }

    @Override
    public void onDataSetChanged() {
        Bundle bundle = intent.getExtras();

        if(bundle != null) {
            ingredientList = bundle.getBundle("bundle").getParcelableArrayList("ingredientList");
        }
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_item);

        Ingredient ingredient = ingredientList.get(i);

        String quantity = Double.toString(ingredient.getQuantity());
        String measure = ingredient.getMeasure();
        String ingredientName = ingredient.getIngredient();

        String text = quantity + " " + measure.toLowerCase() + " " + ingredientName.toLowerCase();

        views.setTextViewText(R.id.ingredient_widget_item, text);

        Intent fillInIntent = new Intent();
        views.setOnClickFillInIntent(R.id.ingredient_widget_item, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
