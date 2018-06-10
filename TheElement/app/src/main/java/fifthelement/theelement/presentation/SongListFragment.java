package fifthelement.theelement.presentation;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.ArrayList;

import fifthelement.theelement.R;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.objects.SongsListAdapter;

public class SongListFragment extends Fragment {

    public static final String TAG = "MainActivity";
    public static final String MEDIA_RES_PATH = "android.resource://fifthelement.theelement/raw/jazz_in_paris";

    private TextView mTextDebug;
    private SeekBar mSeekbarAudio;
    private ScrollView mScrollContainer;
    private MusicService musicService;
    private View view;
    private boolean mUserIsSeeking = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Im in a SongList Fragment Page");

        view = inflater.inflate(R.layout.song_list_fragment, container, false);

        String[] songName = {"Nice For What", "God's Plan", "This Is America", "Yes Indeed", "No Tears Left To Cry"};
        String[] authorNames = {"Drake", "Drake", "Childish Gambino", "Lil Baby & Drake", "Ariana Grande" };
        ArrayList<Song> songs = new ArrayList<Song>();
        for(int i = 0; i < 5; i++) {
            Song song = new Song(i, songName[i%5], "test");
            song.addAlbum(new Album(i, songName[i%5]));
            song.addAuthor(new Author(i, authorNames[i%5]));
            songs.add(song);
        }

        ListView listView = (ListView) view.findViewById(R.id.song_list_view);
        SongsListAdapter songListAdapter = new SongsListAdapter(getActivity(), songs);
        listView.setAdapter(songListAdapter);
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
        //Button mResetButton = (Button) view.findViewById(R.id.button_reset);
        mSeekbarAudio = (SeekBar) view.findViewById(R.id.seekbar_audio);
        //mScrollContainer = (ScrollView) view.findViewById(R.id.scroll_container);

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
//        mResetButton.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        System.out.println("Called Reset Button");
//                        musicService.reset();
//                    }
//                });
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