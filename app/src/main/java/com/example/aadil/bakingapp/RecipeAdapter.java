package com.example.aadil.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aadil.bakingapp.model.Ingredient;
import com.example.aadil.bakingapp.model.Recipe;
import com.example.aadil.bakingapp.widget.RecipeWidgetProvider;
import com.example.aadil.bakingapp.widget.RecipeWidgetRemoteViewsService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
    private ArrayList<Recipe> recipeList;
    private Context context;

    public RecipeAdapter(ArrayList<Recipe> mRecipeList) {
        recipeList = mRecipeList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name) TextView mTextView;
        @BindView(R.id.recipe_pic) ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View recipeItem = LayoutInflater.from(context)
                .inflate(R.layout.recipe_item, viewGroup, false);

        return new RecipeAdapter.ViewHolder(recipeItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder viewHolder, int position) {
        final Recipe recipe = recipeList.get(position);
        final ArrayList<Ingredient> ingredients = recipe.getIngredients();
        final Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ingredientList", ingredients);

        String recipeTitle;

        if(position == 0) {
            Glide.with(context).load(R.drawable.no_bake_nutella_pie_text1)
                    .into(viewHolder.imageView);
        }
        else if(position == 1) {
            Glide.with(context).load(R.drawable.chocolate_beetroot_brownies)
                    .into(viewHolder.imageView);
        }
        else if(position == 2) {
            Glide.with(context).load(R.drawable.yellow_cake)
                    .into(viewHolder.imageView);
        }
        else {
            Glide.with(context).load(R.drawable.finished_product_cheesecake)
                    .into(viewHolder.imageView);
        }

        recipeTitle = recipe.getName();
        viewHolder.mTextView.setText(recipeTitle);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("recipe", recipe);

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

                Intent widgetIntent = new Intent(context, RecipeWidgetRemoteViewsService.class);

                widgetIntent.putExtra("bundle", bundle);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidgetProvider.class));
                widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
                widgetIntent.setData(Uri.parse(widgetIntent.toUri(Intent.URI_INTENT_SCHEME)));
                views.setRemoteAdapter(R.id.widget_ingredient_list, widgetIntent);

                PendingIntent pendingIntent = PendingIntent.getService(context, 0, widgetIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                views.setPendingIntentTemplate(R.id.widget_ingredient_list, pendingIntent);

                ComponentName thisWidget = new ComponentName(context, RecipeWidgetProvider.class);
                appWidgetManager.updateAppWidget(thisWidget, views);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredient_list);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(recipeList == null) {
            return 0;
        }
        return recipeList.size();
    }
}