package com.example.aadil.bakingapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

            Button prevButton = findViewById(R.id.previous_button);

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