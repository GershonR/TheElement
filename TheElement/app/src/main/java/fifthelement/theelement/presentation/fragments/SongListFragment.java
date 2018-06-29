package fifthelement.theelement.presentation.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.business.Services.SongService;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.services.MusicService;
import fifthelement.theelement.presentation.adapters.SongsListAdapter;

public class SongListFragment extends Fragment {
    private View view;
    private SongService songService;
    private MusicService musicService;
    private SongsListAdapter songListAdapter;
    List<Song> songs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        songService = ((MainActivity)getActivity()).getSongService();
        musicService = ((MainActivity)getActivity()).getMusicService();

        view = inflater.inflate(R.layout.song_list_fragment, container, false);
        ListView listView = (ListView) view.findViewById(R.id.song_list_view);

        songs = songService.getSongs();

        if(songs != null) {
            songListAdapter = new SongsListAdapter(getActivity(), songs);

            Button buttonOrganize = view.findViewById(R.id.button_organize_list);
            buttonOrganize.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    songService.sortSongs(songs);
                    songListAdapter.notifyDataSetChanged();
                }
            });

            listView.setAdapter(songListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    boolean result = musicService.playSongAsync(songs.get(position));
                    if(result) {
                        Toast.makeText(getContext(), "Now Playing: " + songs.get(position).getName(), Toast.LENGTH_SHORT).show();
                        ((MainActivity)getActivity()).startNotificationService(view.findViewById(R.id.toolbar));
                    }
                }
            });

        } else {

        }
        return view;
    }

    @Override
    public void onResume() { // TODO: Try and figure out why the list wont get updated when you add a song
        super.onResume();
        if(songListAdapter != null) {
            songs = songService.getSongs();
            songListAdapter.notifyDataSetChanged();
            Log.e("DEBUG", "onResume of SongListFragment");
        }
    }

}
