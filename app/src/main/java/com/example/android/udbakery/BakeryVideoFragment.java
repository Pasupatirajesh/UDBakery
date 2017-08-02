package com.example.android.udbakery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.udbakery.Model.BakeryPojo;
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


public class BakeryVideoFragment extends Fragment implements ExoPlayer.EventListener{

    private static final String TAG = BakeryVideoFragment.class.getSimpleName();
    private SimpleExoPlayerView mExoPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private TextView mTextView;

    public  String mBakingPojo;
    private String mBakingdescPojo;
    private BakeryPojo mBakeryPojoObject;

    private Button nextButton;
    private Button prevButton;

    private static Bundle myIntent;

    public BakeryVideoFragment() {
        // Required empty public constructor
    }


    public static BakeryVideoFragment newInstance(String videoUrl) {
        BakeryVideoFragment fragment = new BakeryVideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myIntent = getActivity().getIntent().getExtras();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.fragment_bakery_video, container, false);
        prevButton = v.findViewById(R.id.prev_button);
        nextButton = v.findViewById(R.id.next_button);

        mExoPlayerView = v.findViewById(R.id.vv_simple);
        mTextView = v.findViewById(R.id.textView);

        // Check BakeryStepAdapter class where I explicitly pass the videoURL by using getAdapterPosition() method
        // retrieve the intent extra in the BakeryVideoFragment class to make the videos play in the exoplayer

        // I am currently stuck on how to pass the videourl when user clicks next button.

        // Can you give me suggestions on how to employ this functionality? I am getting errors when I use a similar strategy
        // of passing videourl in a parcel. Hope I am putting my question across in a succinct manner.

        // Error thrown at nextButton.setOnClickListener() implementation.

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

                    Toast.makeText(getContext(), "No video for this step", Toast.LENGTH_SHORT).show();
                }

                mTextView.setText(mBakingdescPojo);

                initializePlayer(Uri.parse(mBakingPojo));
            }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nextVideoIntent = new Intent(getContext(), BakingVideosActivity.class);
                nextVideoIntent.putExtra("BakingPojo", mBakeryPojoObject.getSteps().get(2).getVideoURL());
//                getActivity().finish();
                startActivity(nextVideoIntent);
            }
        });
        return v;
    }

    private void initializePlayer(Uri mediaUri) {
        if(mExoPlayer == null)
        {
            //Create an instance of ExoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector,loadControl);
            mExoPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            // Prepare the media source
            String userAgent = Util.getUserAgent(getContext(), "BakeryVideos");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }
    private void initializeMediaSession() {
        // Instantiate a Media Session
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

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
        mMediaSession.setCallback(new mySessionCallback());

        // start the session
        mMediaSession.setActive(true);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mExoPlayer.release();
    }

}
