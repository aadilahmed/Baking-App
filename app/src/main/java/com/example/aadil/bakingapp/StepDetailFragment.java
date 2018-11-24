package com.example.aadil.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.aadil.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;


public class StepDetailFragment extends Fragment{
    private String mediaUrl;
    private TextView stepDescriptionTv;
    private SimpleExoPlayer simpleExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private Boolean playWhenReady = true;
    private int currentWindow = 0;
    private long position = 0;
    private Button nextButton;
    private Button prevButton;
    private int i;

    public StepDetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        stepDescriptionTv = rootView.findViewById(R.id.step_description_tv);
        mPlayerView = rootView.findViewById(R.id.step_player_view);
        nextButton = rootView.findViewById(R.id.next_button);
        prevButton = rootView.findViewById(R.id.previous_button);

        Bundle bundle = getArguments();

        if(bundle != null) {
            Step step = bundle.getParcelable("step");

            ArrayList<Step> stepList = bundle.getParcelableArrayList("stepList");

            int position = bundle.getInt("position");

            mediaUrl = step.getVideoURL();
            stepDescriptionTv.setText(step.getDescription());

            int next = position++;
            int prev = position;

            if(position > 0) {
                prev = --position;
            }

            if(stepList != null) {
                final Step nextStep = stepList.get(next);

                final Step prevStep = stepList.get(prev);

                final Bundle newBundle = new Bundle();

                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StepDetailFragment newFragment = new StepDetailFragment();

                        newBundle.putParcelable("step", nextStep);

                        newFragment.setArguments(newBundle);

                        ((StepDetailActivity) getContext()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.step_detail_fragment, newFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });

                prevButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StepDetailFragment newFragment = new StepDetailFragment();

                        newBundle.putParcelable("step", prevStep);

                        newFragment.setArguments(newBundle);

                        ((StepDetailActivity) getContext()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.step_detail_fragment, newFragment)
                                .addToBackStack(null)
                                .commit();

                    }
                });
            }
        }

        return rootView;
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void initializePlayer() {
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()), new DefaultTrackSelector(), new DefaultLoadControl());

        mPlayerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.setPlayWhenReady(playWhenReady);
        simpleExoPlayer.seekTo(currentWindow, position);

        Uri uri = Uri.parse(mediaUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        simpleExoPlayer.prepare(mediaSource, true, false);
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            position = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }
}
