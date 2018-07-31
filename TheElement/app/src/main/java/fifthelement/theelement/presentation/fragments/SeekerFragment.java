package fifthelement.theelement.presentation.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.helpers.FragmentHelper;
import fifthelement.theelement.presentation.services.MusicService;
import fifthelement.theelement.presentation.util.SongUtil;

public class SeekerFragment extends Fragment {

    public static final int PLAYBACK_POSITION_REFRESH_INTERVAL_MS = 500;

    private MainActivity mainActivity;
    private SeekBar mSeekbarAudio;
    private MusicService musicService;
    private View view;
    private boolean mUserIsSeeking = false;
    private ScheduledExecutorService mExecutor;
    private Runnable mSeekbarPositionUpdateTask;
    private Runnable songDurationUpdateTask;
    private TextView currDurr;
    private Handler timerHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();

        view = inflater.inflate(R.layout.seeker_fragment, container, false);
        currDurr = view.findViewById(R.id.song_curr_duration);

        timerHandler = new Handler();
        musicService = Services.getMusicService();
        initializeUI();
        initializeSeekbar();
        return view;
    }

    private void initializeUI() {
        final ImageButton mPlayButton = view.findViewById(R.id.button_play_pause);
        final ImageButton mSkipButton = view.findViewById(R.id.button_next);
        final ImageButton mPrevButton = view.findViewById(R.id.button_prev);
        mSeekbarAudio = view.findViewById(R.id.seekbar_audio);

        if(!musicService.isPlaying()) {
            mPlayButton.setImageResource(R.drawable.ic_play_button);
        } else {
            mPlayButton.setImageResource(R.drawable.ic_pause_button);
        }

        mPlayButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!musicService.isPlaying()) {
                            mPlayButton.setImageResource(R.drawable.ic_play_button);
                            startUpdatingCallbackWithPosition();
                            musicService.start();
                        } else {
                            mPlayButton.setImageResource(R.drawable.ic_pause_button);
                            stopUpdatingCallbackWithPosition(false);
                            musicService.pause();
                        }
                    }
                });

        mSkipButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RxView.clicks(view).throttleFirst(300, TimeUnit.MILLISECONDS).subscribe(empty -> {
                            musicService.skip();
                        });
                    }
                });

        mPrevButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RxView.clicks(view).throttleFirst(300, TimeUnit.MILLISECONDS).subscribe(empty -> {
                            musicService.prev();
                        });
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
                        currDurr.setText(SongUtil.getTimeString(userSelectedPosition));
                    }
                });

        //Set up new playback listener which will initiate seekbar updating if playback starts somehow
        musicService.setSeekerPlaybackListener(new SeekerPlaybackStartStopListener());

        //Update the seekbar with current playback position and duration
        int currentPosition = musicService.getCurrentPosition();
        final int duration = musicService.getDuration();
        if (mSeekbarAudio.getMax() != duration){
            mSeekbarAudio.setMax(duration);
        }
        mSeekbarAudio.setProgress(currentPosition);

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
        updateSongPositionTask();
    }

    private void updateSongPositionTask() {
        songDurationUpdateTask = new Runnable() {
            @Override
            public void run() {
                long currentPosition = musicService.getCurrentPosition();
                currDurr.setText(SongUtil.getTimeString(currentPosition));
                timerHandler.postDelayed(songDurationUpdateTask,1000);
            }
        };
        timerHandler.post(songDurationUpdateTask);
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
                currDurr.setText(" 0:00");
            }
        }
        if(songDurationUpdateTask != null) {
            timerHandler.removeCallbacks(songDurationUpdateTask);
            songDurationUpdateTask = null;
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
            //currDurr.setText(SongUtil.getTimeString(currentPosition));
        }
    }

    private void setDisplayDuration() {
        if (musicService != null && musicService.getCurrentSongPlaying() != null) {
            TextView songDuration = view.findViewById(R.id.song_duration);
            songDuration.setText(SongUtil.getSongDuration(musicService.getCurrentSongPlaying()));
        }
    }

    public class SeekerPlaybackStartStopListener {
        public void onPlaybackStart(){
            ImageButton mPlayButton = view.findViewById(R.id.button_play_pause);
            mPlayButton.setImageResource(R.drawable.ic_pause_button);
            startUpdatingCallbackWithPosition();
            setDisplayDuration();
        }

        public void onPlaybackStop(boolean resetUIPlaybackPosition){
            ImageButton mPlayButton = view.findViewById(R.id.button_play_pause);
            mPlayButton.setImageResource(R.drawable.ic_play_button);
            stopUpdatingCallbackWithPosition(resetUIPlaybackPosition);
        }
    }

}
