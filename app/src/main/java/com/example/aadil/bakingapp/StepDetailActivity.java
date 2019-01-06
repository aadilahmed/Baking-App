package com.example.aadil.bakingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aadil.bakingapp.model.Step;

import java.util.ArrayList;

public class StepDetailActivity extends FragmentActivity {

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