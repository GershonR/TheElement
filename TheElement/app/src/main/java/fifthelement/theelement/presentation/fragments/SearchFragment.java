package fifthelement.theelement.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.services.SongListService;
import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.services.MusicService;
import fifthelement.theelement.presentation.adapters.SongsListAdapter;

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
        songService = Services.getSongService();
        songListService = Services.getSongListService();
        musicService = Services.getMusicService();

        prevSongList = songListService.getSongList(); //Get previous list of songs so we can restore it after
        List<Song> songs = songService.getSongs();
        songListService.setSongList(songs);

        view = inflater.inflate(R.layout.search_fragment, container, false);
        ListView listView = view.findViewById(R.id.search_song_list_view_item);
        mSearchView = view.findViewById(R.id.search_view_item);
        mListView = view.findViewById(R.id.search_song_list_view_item);

        onQueryTextListener = createNewOnQueryTextListener();

        songsListAdapter = new SongsListAdapter(getActivity(), songService.getSongs());
        mListView.setAdapter(songsListAdapter);

        //mListView.setTextFilterEnabled(true);
        setupSearchView();

        playSongOnClick(listView);
        return view;
    }

    private void playSongOnClick(ListView listView) {
        List<Song> songs = songListService.getSongList();
        if(songs != null) {
            final SongsListAdapter songListAdapter = new SongsListAdapter(getActivity(), songs);
            listView.setAdapter(songListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    boolean result = musicService.playSongAsync(songListService.getSongAtIndex(position));
                    if(result) {
                        ((MainActivity)getActivity()).startNotificationService(view.findViewById(R.id.toolbar));
                    }
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
            //TODO implement different type of search when search submitted?
            @Override
            // Search on submit button
            public boolean onQueryTextSubmit(String query) {
                List<Song> songs = songService.search(query);
                songListService.setSongList(songs);
                songsListAdapter = new SongsListAdapter(getActivity(), songs);
                mListView.setAdapter(songsListAdapter);
                songsListAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Song> songs = songService.search(newText);
                songListService.setSongList(songs);
                songsListAdapter = new SongsListAdapter(getActivity(), songs);
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
        songListService.setSongList(prevSongList);
    }

    @Override
    public void onResume(){
        super.onResume();
        prevSongList = songListService.getSongList();
    }
}
