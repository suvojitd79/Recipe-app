package com.example.bakingapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.Video;
import com.example.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class Fragment2 extends Fragment implements View.OnClickListener {

    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    private TextView textView;
    private Button next, pre;
    private ProgressBar progressBar;
    private Context context;
    private int current = -1;

    private long time = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.details_frag2, container, false);
        playerView = view.findViewById(R.id.video);
        textView = view.findViewById(R.id.description);
        next = view.findViewById(R.id.next);
        pre = view.findViewById(R.id.previous);
        next.setOnClickListener(this);
        pre.setOnClickListener(this);
        progressBar = view.findViewById(R.id.progress);

        //set up the media-player
        initMedia();
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                    if (playbackState == Player.STATE_BUFFERING)
                        progressBar.setVisibility(View.VISIBLE);
                    if (playbackState == Player.STATE_ENDED) {
                        current+=1;
                        render(current);
                    }
                    if (playbackState == Player.STATE_READY)
                        progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });




        if (context instanceof Video)
            render(((Video)context).current);

        return view;

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();


        if (id == R.id.next) {
            current += 1;
            render(current);

        } else if (id == R.id.previous) {
            current -= 1;
            render(current);
        }


    }


    public void render(int position) {

        //update
        current = position;


        //mobile device
        if (context instanceof Video) {

            //Log.d("d99", "render: ");

            Video video = (Video) context;
            Step step = video.getSteps().get(position);
            String des = step.getDescription();

            String uri = step.getVideoURL();

            //current item
            if (!TextUtils.isEmpty(uri)){

                MediaSource mediaSource = getMediaSouce(Uri.parse(uri));
                simpleExoPlayer.prepare(mediaSource);


                simpleExoPlayer.setPlayWhenReady(true);
                simpleExoPlayer.seekTo(0);


            }else
                progressBar.setVisibility(View.VISIBLE);


            if (TextUtils.isEmpty(des))
                textView.setText("No Description has been provided!");
            else
                textView.setText(des);

            //last video
            if (position + 1 == video.getSteps().size()) {
                next.setEnabled(false);
                next.setAlpha(.7f);
            } else {
                next.setEnabled(true);
                next.setAlpha(1f);
            }

            //first video
            if (position == 0) {

                pre.setEnabled(false);
                pre.setAlpha(.7f);
            } else {
                pre.setEnabled(true);
                pre.setAlpha(1f);
            }

        }

    }


    private void initMedia(){

        playerView.setUseController(false);

        simpleExoPlayer = ExoPlayerFactory.
                newSimpleInstance(new DefaultRenderersFactory(context)
                    ,new DefaultTrackSelector(),new DefaultLoadControl());
        playerView.setPlayer(simpleExoPlayer);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
    }


    private MediaSource getMediaSouce(Uri uri){

        return new
                ExtractorMediaSource.
                        Factory(new DefaultHttpDataSourceFactory("media"))
                .createMediaSource(uri);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer!=null){

            time = simpleExoPlayer.getCurrentPosition();

            if (simpleExoPlayer.getPlayWhenReady())
                simpleExoPlayer.setPlayWhenReady(false);

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (simpleExoPlayer!=null){

            if (!simpleExoPlayer.getPlayWhenReady()) {
                simpleExoPlayer.seekTo(time);
                simpleExoPlayer.setPlayWhenReady(true);
            }
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();


        if (simpleExoPlayer!=null){

            simpleExoPlayer.release();
            simpleExoPlayer = null;

        }

    }
}
