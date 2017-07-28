package com.example.android.udbakery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.parceler.Parcels;

public class BakingVideosActivity extends AppCompatActivity implements ExoPlayer.EventListener{

    private static final java.lang.String TAG = BakingVideosActivity.class.getSimpleName();
    private SimpleExoPlayerView mExoPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private TextView mTextView;
    private String mBakingPojo;
    private String mBakingdescPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_videos);
        mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.vv_simple);
        mTextView = (TextView) findViewById(R.id.textView);
        Bundle myIntent = getIntent().getExtras();

        mBakingPojo = Parcels.unwrap(myIntent.getParcelable("BakingPojo"));

        mBakingdescPojo =Parcels.unwrap(myIntent.getParcelable("BakingDescPojo"));


            if(myIntent.getParcelable("BakingPojo")!=null)
            {
                initializeMediaSession();

                if(mBakingPojo.isEmpty())
                {
                    Bitmap artWork = BitmapFactory.decodeResource(getResources(),R.drawable.default_bakery);

                    int nh = (int) (artWork.getHeight() * (512.0/ artWork.getWidth()));

                    Bitmap b = Bitmap.createScaledBitmap(artWork, 512, nh, true);

                    mExoPlayerView.setDefaultArtwork(b);

                    mExoPlayerView.hideController();

                    Toast.makeText(this, "No video for this step", Toast.LENGTH_SHORT).show();
                }


                initializePlayer(Uri.parse(mBakingPojo));
            }

        mTextView.setText(mBakingdescPojo);
    }

    private void initializePlayer(Uri mediaUri) {
        if(mExoPlayer == null)
        {
            //Create an instance of ExoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector,loadControl);
            mExoPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            // Prepare the media source
            String userAgent = Util.getUserAgent(this, "BakeryVideos");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }
    private void initializeMediaSession() {
        // Instantiate a Media Session
        mMediaSession = new MediaSessionCompat(this, TAG);

        // Set the Flags
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Set the optional media control buttons
        mMediaSession.setMediaButtonReceiver(null);

        //Set the available actions and initial state
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        // Set the callbacks
        mMediaSession.setCallback(new BakingVideosActivity.mySessionCallback());

        // start the session
        mMediaSession.setActive(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExoPlayer.release();
    }

    private class mySessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {

            mExoPlayer.setPlayWhenReady(true);

        }

        @Override
        public void onPause() {

            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {

            mExoPlayer.seekTo(0);
        }
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }


}
