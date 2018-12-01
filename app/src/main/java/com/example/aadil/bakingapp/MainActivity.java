package com.example.aadil.bakingapp;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.aadil.bakingapp.model.Recipe;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aadil.bakingapp.provider.IngredientContract.BASE_CONTENT_URI;
import static com.example.aadil.bakingapp.provider.IngredientContract.PATH_INGREDIENTS;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String RECIPE_URL =
            "https://d17h27t6h515a5.cloudfront.net";
    private static final String JSON_PATH = "topher/2017/May/59121517_baking/baking.json";
    private static final String NO_CONNECTION_TOAST = "No network connectivity. Please connect to the"
            + " internet and restart the app.";
    @BindView(R.id.rv_recipe_list) RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<JSONObject> recipes = new ArrayList<>();
    private ArrayList<Recipe> recipeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, this.getResources().getInteger(R.integer.numColumns));
        mRecyclerView.setLayoutManager(mLayoutManager);

        new RecipeQueryTask().execute();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        Uri INGREDIENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENTS).build();
        return new CursorLoader(this, INGREDIENT_URI, null,
                null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    public class RecipeQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            Uri uri = Uri.parse(RECIPE_URL).buildUpon().appendPath(JSON_PATH).build();

            if(!checkInternet()) {
                cancel(true);
            }

            try {
                URL url = new URL(uri.toString());
                return NetworkUtils.getResponseFromHttpUrl(url);
            } catch (java.io.IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                recipes = NetworkUtils.parseRecipeJson(s);
                recipeList = Recipe.toRecipe(recipes);
                mAdapter = new RecipeAdapter(recipeList);
                mRecyclerView.setAdapter(mAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            Toast toast = Toast.makeText(getApplicationContext(), NO_CONNECTION_TOAST, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /* Citation: https://stackoverflow.com/questions
                     /1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out*/
    public boolean checkInternet(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}