package fifthelement.theelement.presentation;

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

public class SeekerFragment extends Fragment {

    public static final String TAG = "MainActivity";
    public static final String MEDIA_RES_PATH = "android.resource://fifthelement.theelement/raw/jazz_in_paris";

    private SeekBar mSeekbarAudio;
    private MusicService musicService;
    private View view;
    TextView textView;
    private boolean mUserIsSeeking = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.seeker_fragment, container, false);

        musicService = ((MainActivity)getActivity()).getMusicService();
        initializeUI();
        initializeSeekbar();
        initializePlaybackController();
        musicService.setCurrSongPath(MEDIA_RES_PATH);
        Log.d(TAG, "onCreate: finished");
        return view;
    }

    private void initializeUI() {
        Button mPlayButton = (Button) view.findViewById(R.id.button_play);
        Button mPauseButton = (Button) view.findViewById(R.id.button_pause);
        mSeekbarAudio = (SeekBar) view.findViewById(R.id.seekbar_audio);

        mPauseButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("Called Pause Button");
                        musicService.pause();
                    }
                });
        mPlayButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("Called Play Button");
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

    public class PlaybackListener extends PlaybackInfoListener {

        @Override
        public void onDurationChanged(int duration) {
            mSeekbarAudio.setMax(duration);
            System.out.println(String.format("setPlaybackDuration: setMax(%d)", duration));
        }

        @Override
        public void onPositionChanged(int position) {
            if (!mUserIsSeeking) {
                mSeekbarAudio.setProgress(position);
                System.out.println(String.format("setPlaybackPosition: setProgress(%d)", position));
            }
        }
    }

}
