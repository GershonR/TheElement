package fifthelement.theelement.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fifthelement.theelement.R;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.services.MusicService;
import fifthelement.theelement.presentation.services.PlaybackInfoListener;

public class SeekerFragment extends Fragment {

    public static final int PLAYBACK_POSITION_REFRESH_INTERVAL_MS = 500;

    private SeekBar mSeekbarAudio;
    private MusicService musicService;
    private View view;
    private boolean mUserIsSeeking = false;
    private ScheduledExecutorService mExecutor;
    private Runnable mSeekbarPositionUpdateTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.seeker_fragment, container, false);

        musicService = ((MainActivity)getActivity()).getMusicService();
        initializeUI();
        initializeSeekbar();
        return view;
    }

    private void initializeUI() {
        Button mPlayButton = view.findViewById(R.id.button_play);
        Button mPauseButton = view.findViewById(R.id.button_pause);
        mSeekbarAudio = view.findViewById(R.id.seekbar_audio);

        mPauseButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stopUpdatingCallbackWithPosition(false);
                        musicService.pause();
                    }
                });
        mPlayButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startUpdatingCallbackWithPosition();
                        musicService.start();
                    }
                });
    }

    private void initializeSeekbar() {
        mSeekbarAudio.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int userSelectedPosition = 0;

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        mUserIsSeeking = true;
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            userSelectedPosition = progress;
                        }
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mUserIsSeeking = false;
                        musicService.seekTo(userSelectedPosition);
                    }
                });

        //Set up new playback listener which will initiate seekbar updating if playback starts somehow
        musicService.setPlaybackListener(new PlaybackStartStopListener());

        //Start updating the seekbar if music is playing
        if(musicService.isPlaying()){
            startUpdatingCallbackWithPosition();
        }
    }

    // This function will spawn a scheduled executor to call a UI update function at a set interval.
    private void startUpdatingCallbackWithPosition() {
        if (mExecutor == null) {
            mExecutor = Executors.newSingleThreadScheduledExecutor();
        }
        if (mSeekbarPositionUpdateTask == null) {
            mSeekbarPositionUpdateTask = new Runnable() {
                @Override
                public void run() {
                    updateProgressCallbackTask();
                }
            };
        }
        mExecutor.scheduleAtFixedRate(
                mSeekbarPositionUpdateTask,
                0,
                PLAYBACK_POSITION_REFRESH_INTERVAL_MS,
                TimeUnit.MILLISECONDS
        );
    }

    // This function will stop the scheduled executor from calling UI update function and uses a
    // boolean parameter to determine if seekbar UI elements reporting playback must reset to 0.
    private void stopUpdatingCallbackWithPosition(boolean resetUIPlaybackPosition) {
        if (mExecutor != null) {
            mExecutor.shutdownNow();
            mExecutor = null;
            mSeekbarPositionUpdateTask = null;
            if (resetUIPlaybackPosition) {
                mSeekbarAudio.setProgress(0);
            }
        }
    }

    // This function will call the setProgress method on the seekbar with the music player's
    // current position, which will update the position of the seekbar during playback
    private void updateProgressCallbackTask() {
        if (musicService != null && musicService.isPlaying()) {
            int currentPosition = musicService.getCurrentPosition();
            final int duration = musicService.getDuration();
            if (mSeekbarAudio.getMax() != duration){
                mSeekbarAudio.setMax(duration);
            }
            if (!mUserIsSeeking) {
                mSeekbarAudio.setProgress(currentPosition);
            }
        }
    }

    public class PlaybackStartStopListener {
        public void onPlaybackStart(){
            startUpdatingCallbackWithPosition();
        }

        public void onPlaybackStop(){
            stopUpdatingCallbackWithPosition(false);
        }
    }

}
