package com.example.aadil.bakingapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeContentProvider extends ContentProvider {
    public static final int INGREDIENTS = 100;
    public static final int INGREDIENT_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final String TAG = RecipeContentProvider.class.getName();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(IngredientContract.AUTHORITY, IngredientContract.PATH_INGREDIENTS, INGREDIENTS);
        uriMatcher.addURI(IngredientContract.AUTHORITY, IngredientContract.PATH_INGREDIENTS + "/#",
                INGREDIENT_WITH_ID);
        return uriMatcher;
    }

    private IngredientDbHelper mIngredientDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mIngredientDbHelper = new IngredientDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
                        @Nullable String[] strings1, @Nullable String s1) {
        final SQLiteDatabase db = mIngredientDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor cursor;

        switch (match) {
            case INGREDIENTS:
                cursor = db.query(IngredientContract.IngredientEntry.TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);
                break;
            case INGREDIENT_WITH_ID:
                String id = uri.getPathSegments().get(1);
                cursor = db.query(IngredientContract.IngredientEntry.TABLE_NAME,
                        strings,
                        "_id=?",
                        new String[]{id},
                        null,
                        null,
                        s1);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mIngredientDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case INGREDIENTS:
                long id = db.insert(IngredientContract.IngredientEntry.TABLE_NAME,
                        null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(IngredientContract.IngredientEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = mIngredientDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        int ingredientsDeleted;
        switch (match) {
            case INGREDIENTS:
                ingredientsDeleted = db.delete(IngredientContract.IngredientEntry.TABLE_NAME,
                        null, null);
                break;
            case INGREDIENT_WITH_ID:
                String id = uri.getPathSegments().get(1);
                ingredientsDeleted = db.delete(IngredientContract.IngredientEntry.TABLE_NAME,
                        "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (ingredientsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return ingredientsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s,
                      @Nullable String[] strings) {
        final SQLiteDatabase db = mIngredientDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int ingredientsUpdated;

        switch (match) {
            case INGREDIENTS:
                ingredientsUpdated = db.update(IngredientContract.IngredientEntry.TABLE_NAME,
                        contentValues, s, strings);
                break;
            case INGREDIENT_WITH_ID:
                if (s == null) s = IngredientContract.IngredientEntry._ID + "=?";
                else s += " AND " + IngredientContract.IngredientEntry._ID + "=?";

                String id = uri.getPathSegments().get(1);

                if (strings == null) {
                    strings = new String[]{id};
                }
                else {
                    ArrayList<String> selectionArgsList = new ArrayList<String>();
                    selectionArgsList.addAll(Arrays.asList(strings));
                    selectionArgsList.add(id);
                    strings = selectionArgsList.toArray(new String[selectionArgsList.size()]);
                }
                ingredientsUpdated = db.update(IngredientContract.IngredientEntry.TABLE_NAME, contentValues,
                        s, strings);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (ingredientsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return ingredientsUpdated;
    }
}
