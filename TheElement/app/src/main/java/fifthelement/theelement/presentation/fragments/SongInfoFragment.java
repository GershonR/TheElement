package fifthelement.theelement.presentation.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.util.SongUtil;


public class SongInfoFragment extends Fragment {
    private static final String LOG_TAG = "SongsInfoFragment";
    private Song song;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().setTitle("Song Info");
        activity.getSupportActionBar().setSubtitle("");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song_info, container, false);

        ImageView albumArt = (ImageView) view.findViewById(R.id.song_info_albumArt);
        albumArt.setImageBitmap(SongUtil.getSongAlbumArt(getContext(), song));
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

        RatingBar rating = (RatingBar) view.findViewById(R.id.song_info_rating);
        rating.setRating((float)song.getRating());
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ((MainActivity)getActivity()).getSongService().updateSongWithRating(song, ratingBar.getRating());
            }
        });

        FloatingActionButton editButton = view.findViewById(R.id.song_info_edit_btn);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                try{
                    EditSongInfoFragment editSongInfoFragment = EditSongInfoFragment.newInstance();
                    editSongInfoFragment.setSong(song);
                    fragment = (Fragment) editSongInfoFragment;
                }
                catch (Exception e){
                    Log.e(LOG_TAG, e.getMessage());
                }
                Helpers.getFragmentHelper((MainActivity)getActivity()).createFragment(R.id.flContent, fragment, "EditSong");
            }
        });

        Button backButton = view.findViewById(R.id.song_info_back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                Class fragmentClass = SongListFragment.class;
                try{
                    fragment = (Fragment) fragmentClass.newInstance();
                }
                catch (Exception e){
                    Log.e(LOG_TAG, e.getMessage());
                }
                Helpers.getFragmentHelper((MainActivity)getActivity()).createFragment(R.id.flContent, fragment, "SongList");
            }
        });

        return view;
    }


    public static SongInfoFragment newInstance(){
        return new SongInfoFragment();
    }

    public void setSong(Song newSong) {
        song = newSong;
    }


}
