package fifthelement.theelement.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.services.AlbumService;
import fifthelement.theelement.business.services.AuthorService;
import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.activities.MainActivity;


public class PlayerStatsFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;
    private SongService songService = Services.getSongService();
    private AlbumService albumService = Services.getAlbumService();
    private AuthorService authorService = Services.getAuthorService();
    private static int hey = 0;

    public PlayerStatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = getView(inflater, container);
        refresh(view);
        return view;
    }

    private View getView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_player_statistics, container, false);
    }

    private void refresh(View view) {
        TextView mostPlayedSong = (TextView) view.findViewById(R.id.most_played_song);
        TextView mostPlayedAlbum = (TextView) view.findViewById(R.id.most_played_album);
        TextView mostPlayedAuthor = (TextView) view.findViewById(R.id.most_played_author);
        TextView totalSongPlays = (TextView) view.findViewById(R.id.total_song_plays);

        Song song = songService.getMostPlayedSong();
        Album album = albumService.getMostPlayedAlbum();
        Author author = authorService.getMostPlayedAuthor();

        if( song != null && song.getNumPlayed() != 0 ) {
            String mostPlayedSongString = String.format(Locale.getDefault(), "%s (%d plays)", song.getName(), song.getNumPlayed());
            mostPlayedSong.setText(mostPlayedSongString);
        }

        if( album != null && album.getNumPlayed() != 0 ) {
            String mostPlayedAlbumString = String.format(Locale.getDefault(), "%s (%d plays)", album.getName(), album.getNumPlayed());
            mostPlayedAlbum.setText(mostPlayedAlbumString);
        }
        if( author != null && author.getNumPlayed() != 0 ) {
            String mostPlayedAuthorString = String.format(Locale.getDefault(), "%s (%d plays)", author.getName(), author.getNumPlayed());
            mostPlayedAuthor.setText(mostPlayedAuthorString);
        }

        String totalSongPlaysString = String.format(Locale.getDefault(), "%d total plays", songService.getTotalSongPlays());
        totalSongPlays.setText(totalSongPlaysString);
    }
}
