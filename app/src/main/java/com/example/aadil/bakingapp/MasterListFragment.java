package com.example.aadil.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aadil.bakingapp.model.Ingredient;
import com.example.aadil.bakingapp.model.Recipe;
import com.example.aadil.bakingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterListFragment extends Fragment{
    private String name;
    @BindView(R.id.recipe_detail_title) TextView recipeTitleTv;
    private ArrayList<Ingredient> ingredients;
    @BindView(R.id.rv_ingredient_list) RecyclerView mIngredientRV;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Step> steps;
    @BindView(R.id.rv_step_list) RecyclerView mStepRV;
    private RecyclerView.Adapter mStepAdapter;
    private RecyclerView.LayoutManager mStepLayoutManager;

    private RecyclerView.ItemDecoration mStepDividerItemDecoration;

    private Boolean mTwoPane;

    public MasterListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        ButterKnife.bind(this, rootView);

        Bundle bundle = getArguments();

        if(bundle != null) {
            mTwoPane = bundle.getBoolean("mTwoPane");
            Recipe recipe = bundle.getParcelable("recipe");

            ingredients = recipe.getIngredients();

            name = recipe.getName();
            recipeTitleTv.setText(name);

            mIngredientRV.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(getContext());
            mIngredientRV.setLayoutManager(mLayoutManager);

            mAdapter = new IngredientAdapter(ingredients);
            mIngredientRV.setAdapter(mAdapter);

            steps = recipe.getSteps();

            mStepRV.setHasFixedSize(true);

            mStepLayoutManager = new LinearLayoutManager(getContext());
            mStepRV.setLayoutManager(mStepLayoutManager);

            mStepAdapter = new StepAdapter(steps, mTwoPane);

            mStepRV.setAdapter(mStepAdapter);

            mStepDividerItemDecoration = new DividerItemDecoration(mStepRV.getContext(),
                    DividerItemDecoration.VERTICAL);
            mStepRV.addItemDecoration(mStepDividerItemDecoration);
        }

    return rootView;
    }
}