package com.example.android.udbakery;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.udbakery.Model.BakeryPojo;
import com.google.android.exoplayer2.C;
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
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.udbakery.BakingDetailActivity.mBakeryPojo;


public class BakeryVideoFragment extends Fragment implements ExoPlayer.EventListener {

    private static final String TAG = BakeryVideoFragment.class.getSimpleName();
    private SimpleExoPlayerView mExoPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private TextView mTextView;
    private ImageView mImageView;

    public BakeryPojo mBakingPojo;
    private Button nextButton;
    private Button prevButton;
    static Integer mposId;
    static long position;

    private List<BakeryPojo.Steps> steps = new ArrayList<>();
    private static Bundle myIntent;
    private Uri videoUri;


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


        Configuration configuration = getActivity().getResources().getConfiguration();

        if (configuration.smallestScreenWidthDp >= 600) {
            prevButton.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.INVISIBLE);
        }

        mImageView = v.findViewById(R.id.iv_recipe_image_view);

        mExoPlayerView = v.findViewById(R.id.vv_simple);

        mTextView = v.findViewById(R.id.textView);

        mBakingPojo = Parcels.unwrap(myIntent.getParcelable("BakingPojo"));

        steps = mBakingPojo.getSteps();

        mposId = myIntent.getInt("BakingPosId");

        Log.i(TAG, mposId + "");
        if (savedInstanceState != null) {
            steps = Parcels.unwrap(savedInstanceState.getParcelable("SELECTED_STEPS"));
            position = savedInstanceState.getLong("SELECTED_POSITION");
            mposId = savedInstanceState.getInt("SELECTED_INDEX");
        }

        if (myIntent.getParcelable("BakingPojo") != null) {

            if (!TextUtils.isEmpty(steps.get(mposId).getThumbnailURL())) {
                Picasso.with(getContext()).load(steps.get(mposId).getThumbnailURL()).into(mImageView);
            } else {

                initializeMediaSession();
            }
            if (mBakingPojo.getSteps().get(mposId).getVideoURL().isEmpty()) {

                Bitmap artWork = BitmapFactory.decodeResource(getResources(), R.drawable.default_bakery);

                int nh = (int) (artWork.getHeight() * (512.0 / artWork.getWidth()));

                Bitmap b = Bitmap.createScaledBitmap(artWork, 512, nh, true);

                mExoPlayerView.setDefaultArtwork(b);

                mExoPlayerView.hideController();

                Toast.makeText(getContext(), "No video for this step", Toast.LENGTH_SHORT).show();
            }
            mTextView.setText(steps.get(mposId).getDescription());

            videoUri = Uri.parse(steps.get(mposId).getVideoURL());

            initializePlayer(videoUri);
//        }

        if (mposId < steps.size() - 1) {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent nextVideoIntent = new Intent(getContext(), BakingVideosActivity.class);
                    Parcelable mIdWraper = Parcels.wrap(mBakingPojo);
                    nextVideoIntent.putExtra("BakingPosId", mposId + 1);
                    Log.i(TAG, mposId + "");
                    nextVideoIntent.putExtra("BakingPojo", mIdWraper);
                    getActivity().finish();
                    startActivity(nextVideoIntent);
                }
            });
        } else {
            nextButton.setEnabled(false);
        }
        if (mposId == 0) {
            prevButton.setEnabled(false);
        } else {
            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent prevVideoIntent = new Intent(getContext(), BakingVideosActivity.class);
                    Parcelable mIdWraper = Parcels.wrap(mBakingPojo);
                    prevVideoIntent.putExtra("BakingPosId", mposId - 1);
                    prevVideoIntent.putExtra("BakingPojo", mIdWraper);
                    getActivity().finish();
                    startActivity(prevVideoIntent);
                }
            });
        } }

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
            if(position != C.TIME_UNSET) mExoPlayer.seekTo(position);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("SELECTED_STEPS", Parcels.wrap(steps));
        outState.putInt("SELECTED_INDEX", mposId);
        outState.putString("Title", mBakeryPojo.getName());
        outState.putLong("SELECTED_POSITION", position);

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

    }

    @Override
    public void onPause() {
        super.onPause();
        if(mExoPlayer!=null)
        {
            position = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(videoUri!=null)
        {
            initializePlayer(videoUri);
        }
    }
}
