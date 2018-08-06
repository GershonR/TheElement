package fifthelement.theelement.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.helpers.BackHelper;

public class EditSongInfoFragment extends Fragment {
    private static final String LOG_TAG = "EditSongsInfoFragment";
    private Song song;
    private EditText songName;
    private EditText artistName;
    private EditText albumName;
    private EditText genre;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().setTitle("Edit Song");
        activity.getSupportActionBar().setSubtitle("");

        SongInfoFragment songInfoFragment = new SongInfoFragment();
        songInfoFragment.setSong(song);

        BackHelper.setupBack(activity, songInfoFragment, "SongInfo");

        View view = inflater.inflate(R.layout.fragment_edit_song_info, container, false);

        songName = (EditText) view.findViewById(R.id.edit_song_info_name);
        artistName = (EditText) view.findViewById(R.id.edit_song_info_artist);
        albumName = (EditText) view.findViewById(R.id.edit_song_info_album);
        genre = (EditText) view.findViewById(R.id.edit_song_info_genre);
        setInitialText();

        Button doneButton = view.findViewById(R.id.song_info_editDone_btn);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update song
                updateSong();
                songInfoFragment.setSong(song);
                Helpers.getFragmentHelper(activity).createFragment(R.id.flContent, songInfoFragment, "SongInfo");
            }
        });

        return view;
    }

    public void setInitialText(){

        songName.setText(song.getName());

        if(song.getAuthor() != null) {
            artistName.setText(song.getAuthor().getName());
        } else {
            artistName.setText("");
        }

        if(song.getAlbum() != null) {
            albumName.setText(song.getAlbum().getName());
        } else {
            albumName.setText("");
        }

        if(song.getGenre() != null) {
            genre.setText(song.getGenre());
        } else {
            genre.setText("");
        }
    }

    public void updateSong(){

        if(song != null) {
            ((MainActivity)getActivity()).getSongService().updateSongWithParameters(song, songName.getText().toString(), artistName.getText().toString(), albumName.getText().toString(), genre.getText().toString());
        }
    }

    public static EditSongInfoFragment newInstance(){
        return new EditSongInfoFragment();
    }

    public void setSong(Song newSong) {
        song = newSong;
    }

}
