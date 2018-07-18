package fifthelement.theelement.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.services.SongListService;
import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.adapters.SongsListAdapter;
import fifthelement.theelement.presentation.services.MusicService;

public class SongListFragment extends Fragment {
    private View view;
    private ListView listView;
    private SongListService songListService;
    private SongService songService;
    private MusicService musicService;
    private SongsListAdapter songListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        songListService = Services.getSongListService();
        musicService = Services.getMusicService();
        songService = Services.getSongService();
        displayView(inflater, container);
        return view;
    }

    private void displayView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.song_list_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.song_list_view);

        refreshAdapter();

        autoPlaySwitch();
        shuffleSwitch();
        playSong(listView);
    }

    private void autoPlaySwitch() {
        Switch autoplaySwitch = view.findViewById(R.id.autoplaySwitch);
        autoplaySwitch.setChecked(songListService.getAutoplayEnabled());
        autoplaySwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                songListService.setAutoplayEnabled(isChecked);
            }
        });
    }

    private void shuffleSwitch() {
        Button shuffle = view.findViewById(R.id.shuffle);
        shuffle.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                musicService.shuffle();
            }
        });
    }

    private void refreshAdapter() {
        List<Song> songs = songService.getSongs();
        songListService.sortSongs(songs);
        songListAdapter = new SongsListAdapter(getActivity(), songs);
        listView.setAdapter(songListAdapter);
    }

    private void playSong(ListView listView) {
        List<Song> songs = songService.getSongs();
        if(songs != null) {
            final SongsListAdapter songListAdapter = new SongsListAdapter(getActivity(), songs);
            listView.setAdapter(songListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    List<Song> songs = songService.getSongs();
                    songListService.sortSongs(songs);
                    songListService.setCurrentSongsList(songs);
                    boolean result = musicService.playSongAsync(songListService.getSongAtIndex(position));
                    if (result) {
                        songListService.setShuffleEnabled(false);
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshAdapter();
    }

}
