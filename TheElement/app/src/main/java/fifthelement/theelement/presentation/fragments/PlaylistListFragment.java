package fifthelement.theelement.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.services.PlaylistService;
import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.adapters.PlaylistListAdapter;
import fifthelement.theelement.presentation.adapters.SongsListAdapter;
import fifthelement.theelement.presentation.services.MusicService;

public class PlaylistListFragment extends Fragment {
    private View view;
    //private RecyclerView recyclerView;
    private ListView playlistListView;
    private SongService songService;
    private MusicService musicService;
    private PlaylistService playlistService;
    private PlaylistListAdapter playlistListAdapter;
    private SongsListAdapter songListAdapter;
    List<Playlist> playlists;

    private static final String LOG_TAG = "PlaylistListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //songService = ((MainActivity)getActivity()).getSongService();
        //musicService = Services.getMusicService();
        playlistService = Services.getPlaylistService();
        playlists = playlistService.getPlaylists();
        displayView(inflater, container);
        return view;
    }

    private void displayView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_playlist_list, container, false);
        playlistListView = view.findViewById(R.id.playlist_list_view);
        refreshAdapter();
    }

    private void playPlaylist(ListView listView) {
        if(playlists != null) {
            final PlaylistListAdapter playlistListAdapter = new PlaylistListAdapter(getActivity(), playlists);
            listView.setAdapter(playlistListAdapter);
            //final SongsListAdapter songListAdapter = new SongsListAdapter(getActivity(), songs);
            //listView.setAdapter(songListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    boolean result = musicService.playMultipleSongsAsync(playlists.get(position));
                    if (result) {
                        ((MainActivity) getActivity()).startNotificationService(view.findViewById(R.id.toolbar));
                    }
                }
            });
        }
    }

    private void refreshAdapter() {
        playlistListAdapter = new PlaylistListAdapter(getActivity(), playlists);
        playlistListView.setAdapter(playlistListAdapter);
    }
}