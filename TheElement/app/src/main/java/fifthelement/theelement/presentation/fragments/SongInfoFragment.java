package fifthelement.theelement.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fifthelement.theelement.R;
import fifthelement.theelement.objects.Song;


public class SongInfoFragment extends Fragment {
    Song song;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song_info, container, false);

        TextView songName = (TextView) view.findViewById(R.id.song_info_name);
        songName.setText(song.getName());

        TextView artistName = (TextView) view.findViewById(R.id.song_info_artist);
        if(song.getAuthor() != null) {
            artistName.setText(song.getAuthor().getName());
        } else {
            artistName.setText("");
        }

        TextView albumName = (TextView) view.findViewById(R.id.song_info_album);
        if(song.getAlbum() != null) {
            albumName.setText(song.getAlbum().getName());
        } else {
            albumName.setText("");
        }

        TextView genre = (TextView) view.findViewById(R.id.song_info_genre);
        if(song.getGenre() != null) {
            genre.setText(song.getGenre());
        } else {
            genre.setText("");
        }

        return view;
    }


    public static SongInfoFragment newInstance(){
        return new SongInfoFragment();
    }

    public void setSong(Song newSong) {
        song = newSong;
    }
}
