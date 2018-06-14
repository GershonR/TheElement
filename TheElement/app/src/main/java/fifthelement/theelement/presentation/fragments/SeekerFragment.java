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


import fifthelement.theelement.R;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.services.MusicService;
import fifthelement.theelement.presentation.services.PlaybackInfoListener;

public class SeekerFragment extends Fragment {

    private SeekBar mSeekbarAudio;
    private MusicService musicService;
    private View view;
    private boolean mUserIsSeeking = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.seeker_fragment, container, false);

        musicService = ((MainActivity)getActivity()).getMusicService();
        initializeUI();
        initializeSeekbar();
        initializePlaybackController();
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
                        musicService.pause();
                    }
                });
        mPlayButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        musicService.start();
                    }
                });
    }

    private void initializePlaybackController() {
        musicService.setPlaybackInfoListener(new PlaybackListener());
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
    }

    //Listener that will help seekbars update with the progressing of music playback
    public class PlaybackListener extends PlaybackInfoListener {

        public void onDurationChanged(int duration) {
            mSeekbarAudio.setMax(duration);
        }

        public void onPositionChanged(int position) {
            if (!mUserIsSeeking) {
                mSeekbarAudio.setProgress(position);
            }
        }
    }

}
