package fifthelement.theelement.presentation.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.services.SongListService;
import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.adapters.SongsListAdapter;
import fifthelement.theelement.presentation.services.MusicService;

public class PlaylistSongsFragment extends Fragment {
    private View view;
    ViewGroup viewGroup;
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

        songs = ((MainActivity)getActivity()).getSongListService().getSongList();
        //songs = ((MainActivity)getActivity()).getCurrentPlaylist().getSongs();

        displayView(inflater, container);
        return view;
    }

    private void displayView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.playlist_song_list_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.playlist_list_view);

        playSong(listView);
    }

    private void refreshAdapter() {
        songListAdapter = new SongsListAdapter(getActivity(), songs);
        listView.setAdapter(songListAdapter);

    }

    private void playSong(ListView listView) {
        if(songs != null) {
            final SongsListAdapter songListAdapter = new SongsListAdapter(getActivity(), songs);
            listView.setAdapter(songListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    boolean result = musicService.playSongAsync(songs.get(position));
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
            //musicService.setSongs(songs);
            refreshAdapter();
        }
    }
}