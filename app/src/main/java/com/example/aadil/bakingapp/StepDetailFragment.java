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

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepDetailFragment extends Fragment{
    private String mediaUrl;
    @BindView(R.id.step_description_tv) TextView stepDescriptionTv;
    private SimpleExoPlayer simpleExoPlayer;
    @BindView(R.id.step_player_view) SimpleExoPlayerView mPlayerView;
    private Boolean playWhenReady = true;
    private int currentWindow = 0;
    private long position = 0;
    @BindView(R.id.next_button) Button nextButton;
    @BindView(R.id.previous_button) Button prevButton;
    private int i;

    public StepDetailFragment() {}

    public static StepDetailFragment newInstance(int position) {
        StepDetailFragment stepFragment = new StepDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        stepFragment.setArguments(bundle);

        return stepFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, rootView);

        Bundle bundle = getArguments();

        if(bundle != null) {
            final Step step = bundle.getParcelable("step");
            final Step nextStep = bundle.getParcelable("nextStep");
            final Step prevStep = bundle.getParcelable("prevStep");
            final ArrayList<Step> stepList = bundle.getParcelableArrayList("stepList");
            final int position = bundle.getInt("position");
            int x;
            int y;

            if(position < stepList.size() - 2) {
                x = bundle.getInt("position") + 2;
            }
            else {
                x = 0;
            }

            final int nextPosition = x;

            if(position > 2) {
                y = bundle.getInt("position") - 2;
            }
            else {
                y = position;
            }

            final int prevPosition = y;

            mediaUrl = step.getVideoURL();
            stepDescriptionTv.setText(step.getDescription());

            final Bundle newBundle = new Bundle();

            final StepDetailFragment newFragment = new StepDetailFragment();

            nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        newBundle.putParcelable("step", nextStep);
                        newBundle.putParcelable("nextStep", stepList.get(nextPosition));
                        newBundle.putParcelable("prevStep", step);
                        newBundle.putParcelableArrayList("stepList", stepList);
                        newBundle.putInt("position", position + 1);

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
                        newBundle.putParcelable("step", prevStep);
                        newBundle.putParcelable("nextStep", step);
                        newBundle.putParcelable("prevStep", stepList.get(prevPosition));
                        newBundle.putParcelableArrayList("stepList", stepList);
                        newBundle.putInt("position", position - 1);

                        newFragment.setArguments(newBundle);

                        ((StepDetailActivity) getContext()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.step_detail_fragment, newFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });
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
