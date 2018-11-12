package com.example.aadil.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aadil.bakingapp.model.Ingredient;
import com.example.aadil.bakingapp.model.Recipe;
import com.example.aadil.bakingapp.model.Step;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private String name;
    private ImageView recipeBackdropIv;
    private ArrayList<Ingredient> ingredients;
    private RecyclerView mIngredientRV;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Step> steps;
    private RecyclerView mStepRV;
    private RecyclerView.Adapter mStepAdapter;
    private RecyclerView.LayoutManager mStepLayoutManager;

    private RecyclerView.ItemDecoration mStepDividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        Recipe recipe = bundle.getParcelable("recipe");

        ingredients = recipe.getIngredients();

        name = recipe.getName();

        recipeBackdropIv = findViewById(R.id.recipe_backdrop);

        if(name.equals("Nutella Pie")) {
            Glide.with(this).load(R.drawable.no_bake_nutella_pie_text1)
                    .into(recipeBackdropIv);
        }
        else if(name.equals("Brownies")) {
            Glide.with(this).load(R.drawable.chocolate_beetroot_brownies)
                    .into(recipeBackdropIv);
        }
        else if(name.equals("Yellow Cake")) {
            Glide.with(this).load(R.drawable.yellow_cake)
                    .into(recipeBackdropIv);
        }
        else {
            Glide.with(this).load(R.drawable.finished_product_cheesecake)
                    .into(recipeBackdropIv);
        }

        mIngredientRV = findViewById(R.id.rv_ingredient_list);
        mIngredientRV.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mIngredientRV.setLayoutManager(mLayoutManager);

        mAdapter = new IngredientAdapter(ingredients);
        mIngredientRV.setAdapter(mAdapter);


        /*-----------------------------------------------------------------*/
        steps = recipe.getSteps();

        mStepRV = findViewById(R.id.rv_step_list);
        mStepRV.setHasFixedSize(true);

        mStepLayoutManager = new LinearLayoutManager(this);
        mStepRV.setLayoutManager(mStepLayoutManager);

        mStepAdapter = new StepAdapter(steps);
        mStepRV.setAdapter(mStepAdapter);

        mStepDividerItemDecoration = new DividerItemDecoration(mStepRV.getContext(),
                DividerItemDecoration.VERTICAL);
        mStepRV.addItemDecoration(mStepDividerItemDecoration);
    }
}