package fifthelement.theelement.presentation;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.ArrayList;

import fifthelement.theelement.R;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.objects.SongsListAdapter;

public class SongListFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Im in a SongList Fragment Page");

        view = inflater.inflate(R.layout.song_list_fragment, container, false);

        String[] songName = {"Nice For What", "God's Plan", "This Is America", "Yes Indeed", "No Tears Left To Cry"};
        String[] authorNames = {"Drake", "Drake", "Childish Gambino", "Lil Baby & Drake", "Ariana Grande" };
        ArrayList<Song> songs = new ArrayList<Song>();
        for(int i = 0; i < 30; i++) {
            Song song = new Song(i, songName[i%5], "test");
            song.addAlbum(new Album(i, songName[i%5]));
            song.addAuthor(new Author(i, authorNames[i%5]));
            songs.add(song);
        }

        ListView listView = (ListView) view.findViewById(R.id.song_list_view);
        SongsListAdapter songListAdapter = new SongsListAdapter(getActivity(), songs);
        listView.setAdapter(songListAdapter);
        return view;
    }

}