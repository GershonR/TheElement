package fifthelement.theelement.presentation;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.SongsListAdapter;

public class SongListFragment extends Fragment {
    private View view;
    List<Song> songs = new ArrayList<Song>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Im in a SongList Fragment Page");

        view = inflater.inflate(R.layout.song_list_fragment, container, false);
        ListView listView = (ListView) view.findViewById(R.id.song_list_view);
        final SongsListAdapter songListAdapter = new SongsListAdapter(getActivity(), songs);
        String[] songName = {"Nice For What", "God's Plan", "This Is America", "Yes Indeed", "No Tears Left To Cry"};
        String[] authorNames = {"Drake", "Drake", "Childish Gambino", "Lil Baby & Drake", "Ariana Grande" };
        for(int i = 0; i < 50; i++) {
            Song song = new Song(i, songName[i%5], "test");
            song.addAlbum(new Album(i, songName[i%5]));
            song.addAuthor(new Author(i, authorNames[i%5]));
            songs.add(song);
        }


        Button buttonOrganize = view.findViewById(R.id.button_organize_list);
        buttonOrganize.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Collections.sort(songs);
                songService.sort(songs);
                songListAdapter.notifyDataSetChanged();
            }
        });

        listView.setAdapter(songListAdapter);

        return view;
    }

}
