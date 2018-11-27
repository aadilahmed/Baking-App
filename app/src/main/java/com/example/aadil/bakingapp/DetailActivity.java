package com.example.aadil.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailActivity extends AppCompatActivity{
    private Boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(findViewById(R.id.step_detail_fragment) != null) {
            mTwoPane = true;

            Button nextButton = findViewById(R.id.next_button);
            nextButton.setVisibility(View.GONE);

            Button prevButton = findViewById(R.id.previous_button);
            prevButton.setVisibility(View.GONE);

            if(savedInstanceState != null) {
                StepDetailFragment stepFragment = new StepDetailFragment();

                Bundle bundle = getIntent().getExtras();
                stepFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.step_detail_fragment, stepFragment)
                        .commit();
            }
        }
        else {
            mTwoPane = false;
        }

        if (savedInstanceState == null) {
            MasterListFragment masterList = new MasterListFragment();

            Bundle bundle = getIntent().getExtras();
            if(bundle != null) {
                bundle.putBoolean("mTwoPane", mTwoPane);
                masterList.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.master_list_fragment, masterList)
                        .commit();
            }
        }
    }
}