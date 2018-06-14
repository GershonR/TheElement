package fifthelement.theelement.presentation;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;


import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.business.Services.SongService;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.SongsListAdapter;

public class SongListFragment extends Fragment {
    private View view;
    private SongService songService;
    List<Song> songs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        songService = new SongService();
        view = inflater.inflate(R.layout.song_list_fragment, container, false);
        ListView listView = (ListView) view.findViewById(R.id.song_list_view);

        songs = songService.getSongs();

        if(songs != null) {
            final SongsListAdapter songListAdapter = new SongsListAdapter(getActivity(), songs);

            Button buttonOrganize = view.findViewById(R.id.button_organize_list);
            buttonOrganize.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    songService.sortSongs(songs);
                    songListAdapter.notifyDataSetChanged();
                }
            });

            listView.setAdapter(songListAdapter);

        } else {

        }
        return view;
    }

}
