package fifthelement.theelement.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.business.services.PlaylistService;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.adapters.PlaylistListAdapter;

public class PlaylistListFragment extends Fragment {
    private View view;
    private ListView playlistListView;
    List<Playlist> playlists;
    private PlaylistService playlistService;

    private static final String LOG_TAG = "PlaylistListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().setTitle("Playlists");
        playlistService = ((MainActivity)getActivity()).getPlaylistService();
        try {
            playlists = playlistService.getAllPlaylists();
        } catch (PersistenceException p) {
            Log.e(LOG_TAG, p.getMessage());
            Helpers.getToastHelper(getContext()).sendToast("Could not retrieve playlists", "RED");
        }
        displayView(inflater, container);
        return view;
    }

    private void displayView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_playlist_list, container, false);
        playlistListView = view.findViewById(R.id.playlist_list_view);
        playlistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity)getActivity()).openPlaylistSongs(playlistService.getAllPlaylists().get(position));
            }
        });

        refreshAdapter();
    }

    public void refreshAdapter() {
        PlaylistListAdapter playlistListAdapter = new PlaylistListAdapter(getActivity(), playlistService.getAllPlaylists());
        playlistListView.setAdapter(playlistListAdapter);
    }
}