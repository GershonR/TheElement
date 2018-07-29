package fifthelement.theelement.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.services.MusicService;
import fifthelement.theelement.presentation.util.SongUtil;

public class NowPlaying extends Fragment {

    private MusicService musicService;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.now_playing_fragment, container, false);
        musicService = Services.getMusicService();
        if(musicService != null && musicService.getCurrentSongPlaying() != null) {
            Song currentSong = musicService.getCurrentSongPlaying();

            updateAlbumArt(view, currentSong);
            updateActionBar(currentSong);
        }

        return view;

    }

    private void updateAlbumArt(View view, Song currentSong) {
        ImageView albumArt = view.findViewById(R.id.now_playing_art);
        albumArt.setImageBitmap(SongUtil.getSongAlbumArt(this.getActivity(), currentSong));
    }

    private void updateActionBar(Song currentSong) {
        mainActivity.getSupportActionBar().setTitle(currentSong.getName());
        if(currentSong.getAuthor() != null) {
            mainActivity.getSupportActionBar().setSubtitle(currentSong.getAuthor().getName());
        } else {
            mainActivity.getSupportActionBar().setSubtitle("");
        }
    }

    @Override
    public void onDestroy() {
        //mainActivity.getSupportActionBar().setSubtitle("");
        super.onDestroy();
    }
}
