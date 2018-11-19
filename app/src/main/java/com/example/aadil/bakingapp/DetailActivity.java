package com.example.aadil.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity{
    private Boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(findViewById(R.id.step_detail_fragment) != null) {
            mTwoPane = true;

            /*if(savedInstanceState != null) {
                StepDetailFragment stepFragment = new StepDetailFragment();

                Bundle bundle = getIntent().getExtras();
                stepFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.step_detail_fragment, stepFragment)
                        .commit();
            }*/
        }
        else {
            mTwoPane = false;
        }

        if (savedInstanceState == null) {
            MasterListFragment masterList = new MasterListFragment();

            Bundle bundle = getIntent().getExtras();
            bundle.putBoolean("mTwoPane", mTwoPane);
            masterList.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.master_list_fragment, masterList)
                    .commit();
        }
    }
}