package com.example.aadil.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
    private Boolean twoPane;
    private Context context;

    public StepAdapter(ArrayList<Step> mStepList, Boolean mTwoPane) {
        this.stepList = mStepList;
        this.twoPane = mTwoPane;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
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
    public void onBindViewHolder(@NonNull final StepAdapter.ViewHolder viewHolder, final int i) {
        final Step step = stepList.get(i);
        final Step nextStep;
        final Step prevStep;

        int next = i + 1;
        int prev = i - 1;

        if(next < stepList.size()) {
            nextStep = stepList.get(next);
        }
        else {
            nextStep = stepList.get(i);
        }

        if(prev > 0) {
            prevStep = stepList.get(prev);
        }
        else {
            prevStep = stepList.get(i);
        }

        String shortDescription = step.getShortDescription();

        viewHolder.mTextView.setText(shortDescription);

        Bundle bundle = new Bundle();

        bundle.putParcelable("step", step);
        bundle.putParcelable("nextStep", nextStep);
        bundle.putParcelable("prevStep", prevStep);
        bundle.putParcelableArrayList("stepList", stepList);
        bundle.putInt("position", i);

        final StepDetailFragment stepFragment = new StepDetailFragment();

        stepFragment.setArguments(bundle);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(twoPane) {
                    ((DetailActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.step_detail_fragment, stepFragment)
                            .addToBackStack(null)
                            .commit();
                }
                else{
                    Intent intent = new Intent(context, StepDetailActivity.class);
                    intent.putExtra("step", step);
                    intent.putExtra("nextStep", nextStep);
                    intent.putExtra("prevStep", prevStep);
                    intent.putParcelableArrayListExtra("stepList", stepList);
                    intent.putExtra("position", i);
                    context.startActivity(intent);
                }
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
