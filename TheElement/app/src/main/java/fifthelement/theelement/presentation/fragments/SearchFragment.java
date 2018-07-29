package fifthelement.theelement.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

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
    private ListView mListView;
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
        ListView listView = view.findViewById(R.id.search_song_list_view_item);
        mSearchView = view.findViewById(R.id.search_view_item);
        mListView = view.findViewById(R.id.search_song_list_view_item);

        onQueryTextListener = createNewOnQueryTextListener();

        songsListAdapter = new SongsListAdapter(getActivity(), songService.getSongs());
        mListView.setAdapter(songsListAdapter);

        setupSearchView();

        playSongOnClick(listView);
        return view;
    }

    private void playSongOnClick(ListView listView) {
        if(currentSearchResults != null) {
            final SongsListAdapter songListAdapter = new SongsListAdapter(getActivity(), currentSearchResults);
            listView.setAdapter(songListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    songListService.setCurrentSongsList(currentSearchResults);
                    songListService.setAutoplayEnabled(false);
                    musicService.playSongAsync(songListService.getSongAtIndex(position));
                }
            });

        }
    }

    private void clearSearchViewResults(){
        mSearchView.onActionViewCollapsed();
        mSearchView.setQuery("", false);
        mSearchView.clearFocus();
    }

    // These methods override the below two, but the below two
    // must be present to satisfy the implement requirements
    private OnQueryTextListener createNewOnQueryTextListener(){
        return new OnQueryTextListener() {
            @Override
            // Search on submit button
            public boolean onQueryTextSubmit(String query) {
                currentSearchResults = songService.search(query);
                songsListAdapter = new SongsListAdapter(getActivity(), currentSearchResults);
                mListView.setAdapter(songsListAdapter);
                songsListAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearchResults = songService.search(newText);
                songsListAdapter = new SongsListAdapter(getActivity(), currentSearchResults);
                mListView.setAdapter(songsListAdapter);
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
