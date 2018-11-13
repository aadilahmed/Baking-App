package com.example.aadil.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aadil.bakingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder>{
    private ArrayList<Step> stepList;
    private Context context;
    private RecipeStepFragment stepFragment;
    private FragmentManager fragmentManager;

    public StepAdapter(ArrayList<Step> mStepList, RecipeStepFragment stepFragment,
                       FragmentManager fragmentManager) {
        this.stepList = mStepList;
        this.stepFragment = stepFragment;
        this.fragmentManager = fragmentManager;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_description_tv)
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    @NonNull
    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View stepItem = LayoutInflater.from(context)
                .inflate(R.layout.step_item, viewGroup, false);

        return new StepAdapter.ViewHolder(stepItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepAdapter.ViewHolder viewHolder, int i) {
        final Step step = stepList.get(i);

        String shortDescription = step.getShortDescription();

        viewHolder.mTextView.setText(shortDescription);

        final Bundle bundle = new Bundle();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putParcelable("step", step);
                stepFragment.setArguments(bundle);
                /*Intent intent = new Intent(context, StepDetailActivity.class);
                intent.putExtra("step", step);
                context.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        if(stepList == null) {
            return 0;
        }
        return stepList.size();
    }
}
