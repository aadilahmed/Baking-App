package com.example.aadil.bakingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aadil.bakingapp.model.Step;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        StepDetailFragment stepFragment = new StepDetailFragment();

        Bundle bundle = getIntent().getExtras();

            /*ArrayList<Step> stepList = bundle.getParcelableArrayList("stepList");
            mViewPager = findViewById(R.id.view_pager);
            mPagerAdapter = new StepDetailPagerAdapter(getSupportFragmentManager(), stepList);
            mViewPager.setAdapter(mPagerAdapter);*/

            stepFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.step_detail_fragment, stepFragment)
                .commit();
    }

    private class StepDetailPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Step> mStepList;
        public StepDetailPagerAdapter(FragmentManager fm, ArrayList<Step> stepList) {
            super(fm);
            mStepList = stepList;
        }

        @Override
        public Fragment getItem(int position) {
            return StepDetailFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mStepList.size();
        }
    }
}