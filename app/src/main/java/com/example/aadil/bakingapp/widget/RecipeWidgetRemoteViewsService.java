package com.example.aadil.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.aadil.bakingapp.R;
import com.example.aadil.bakingapp.model.Ingredient;
import com.example.aadil.bakingapp.provider.IngredientContract;

import java.util.ArrayList;

import static com.example.aadil.bakingapp.provider.IngredientContract.BASE_CONTENT_URI;
import static com.example.aadil.bakingapp.provider.IngredientContract.PATH_INGREDIENTS;

public class RecipeWidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    Cursor cursor;
    private Intent intent;
    private ArrayList<Ingredient> ingredientList = new ArrayList<>();
    private int appWidgetId;

    public RecipeRemoteViewsFactory (Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        Uri INGREDIENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENTS).build();

        if (cursor != null){
            cursor.close();
        }

        cursor = context.getContentResolver().query(
                INGREDIENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDestroy() {
        cursor.close();
    }

    @Override
    public int getCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToPosition(i);

        int idIndex = cursor.getColumnIndex(IngredientContract.IngredientEntry._ID);
        int quantityIndex = cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_QUANTITY);
        int measureIndex = cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_MEASURE);
        int ingredientIndex = cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_INGREDIENT);

        long ingredientId = cursor.getLong(idIndex);
        String quantity = Double.toString(cursor.getDouble(quantityIndex));
        String measure = cursor.getString(measureIndex);
        String ingredientName = cursor.getString(ingredientIndex);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_item);

        String text = quantity + " " + measure.toLowerCase() + " " + ingredientName.toLowerCase();

        views.setTextViewText(R.id.ingredient_widget_row, text);

        Intent intent = new Intent();
        views.setOnClickFillInIntent(R.id.ingredient_widget_row, intent);

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
