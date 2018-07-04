package fifthelement.theelement.presentation.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.services.MusicService;
import fifthelement.theelement.presentation.adapters.SongsListAdapter;

public class SongListFragment extends Fragment {
    private View view;
    private ListView listView;
    private SongService songService;
    private MusicService musicService;
    private SongsListAdapter songListAdapter;
    List<Song> songs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        songService = ((MainActivity)getActivity()).getSongService();
        musicService = Services.getMusicService();
        songs = songService.getSongs();
        displayView(inflater, container);
        return view;
    }

    private void displayView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.song_list_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.song_list_view);

        refreshAdapter();

        sortSongsButton();
        autoPlaySwitch();
        playSong(listView);
    }

    private void autoPlaySwitch() {
        Switch autoplaySwitch = view.findViewById(R.id.autoplaySwitch);
        autoplaySwitch.setChecked(musicService.getAutoplayEnabled());
        autoplaySwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                musicService.setAutoplayEnabled(isChecked);
            }
        });
    }

    private void refreshAdapter() {
        songListAdapter = new SongsListAdapter(getActivity(), songs);
        listView.setAdapter(songListAdapter);
    }

    private void sortSongsButton() {
        Button buttonOrganize = view.findViewById(R.id.button_organize_list);
        buttonOrganize.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                songService.sortSongs(songs);
                musicService.setSongs(songs);
                refreshAdapter();
            }
        });
    }

    private void playSong(ListView listView) {
        if(songs != null) {
            final SongsListAdapter songListAdapter = new SongsListAdapter(getActivity(), songs);
            listView.setAdapter(songListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    boolean result = musicService.playSongAsync(songs.get(position), position);
                    if (result) {
                        ((MainActivity) getActivity()).startNotificationService(view.findViewById(R.id.toolbar));
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(songListAdapter != null) {
            songs = songService.getSongs();
            musicService.setSongs(songs);
            refreshAdapter();
        }
    }

}
