package fifthelement.theelement.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.services.SongListService;
import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.adapters.SongsListAdapter;
import fifthelement.theelement.presentation.services.MusicService;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {
    private SearchView mSearchView;
    private RecyclerView recyclerView;
    private View view;
    private SongService songService;
    private SongListService songListService;
    private MusicService musicService;
    private SongsListAdapter songsListAdapter;
    private SearchView.OnQueryTextListener onQueryTextListener;
    private List<Song> prevSongList;
    private List<Song> currentSearchResults;

    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(onQueryTextListener);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().setTitle("Search");
        activity.getSupportActionBar().setSubtitle("");

        songService = Services.getSongService();
        songListService = Services.getSongListService();
        musicService = Services.getMusicService();

        prevSongList = songListService.getCurrentSongsList();
        currentSearchResults = songService.getSongs();

        view = inflater.inflate(R.layout.search_fragment, container, false);
        RecyclerView listView = view.findViewById(R.id.search_song_list_view_item);
        mSearchView = view.findViewById(R.id.search_view_item);

        ImageView closeButton = (ImageView) this.mSearchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSearchViewResults();
            }
        });
        recyclerView = view.findViewById(R.id.search_song_list_view_item);

        onQueryTextListener = createNewOnQueryTextListener();

        List<Song> songs = songService.getSongs();
        songListService.sortSongs(songs);
        songsListAdapter = new SongsListAdapter(getActivity(), songs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(songsListAdapter);

        setupSearchView();

        return view;
    }

    private void clearSearchViewResults(){
        mSearchView.setQuery("", false);
        mSearchView.clearFocus();
        List<Song> songs = songService.getSongs();
        songListService.sortSongs(songs);
        songsListAdapter = new SongsListAdapter(getActivity(), songs);
        recyclerView.setAdapter(songsListAdapter);
        songsListAdapter.notifyDataSetChanged();
    }

    // These methods override the below two, but the below two
    // must be present to satisfy the implement requirements
    private SearchView.OnQueryTextListener createNewOnQueryTextListener(){
        return new SearchView.OnQueryTextListener() {
            @Override
            // Search on submit button
            public boolean onQueryTextSubmit(String query) {
                currentSearchResults = songService.search(query);
                songsListAdapter = new SongsListAdapter(getActivity(), currentSearchResults);
                recyclerView.setAdapter(songsListAdapter);
                songsListAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearchResults = songService.search(newText);
                songsListAdapter = new SongsListAdapter(getActivity(), currentSearchResults);
                recyclerView.setAdapter(songsListAdapter);
                songsListAdapter.notifyDataSetChanged();
                return false;
            }
        };
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onPause(){
        super.onPause();
        songListService.setCurrentSongsList(prevSongList);
    }

    @Override
    public void onResume(){
        super.onResume();
        prevSongList = songListService.getCurrentSongsList();
    }
}
