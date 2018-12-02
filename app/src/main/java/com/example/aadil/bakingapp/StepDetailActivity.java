package com.example.aadil.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        if(savedInstanceState == null) {
            StepDetailFragment stepFragment = new StepDetailFragment();

            Bundle bundle = getIntent().getExtras();
            stepFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_fragment, stepFragment)
                    .commit();
        }
    }
}