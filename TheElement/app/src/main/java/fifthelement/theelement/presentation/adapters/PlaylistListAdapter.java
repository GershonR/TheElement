package fifthelement.theelement.presentation.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.fragments.HomeFragment;
import fifthelement.theelement.presentation.fragments.PlaylistListFragment;
import fifthelement.theelement.presentation.fragments.SearchFragment;
import fifthelement.theelement.presentation.fragments.SongListFragment;
import fifthelement.theelement.presentation.services.MusicService;

public class PlaylistListAdapter extends BaseAdapter {
    Context context;
    List<Playlist> playlists;
    LayoutInflater inflater;
    private static final String LOG_TAG = "SongsListAdapter";

    public PlaylistListAdapter(Context context, List<Playlist> playlists) {
        this.context = context;
        this.playlists = playlists;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return playlists.size();
    }

    @Override
    public Object getItem(int position) {
        return playlists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) getItem(position).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final MainActivity activity = (MainActivity)context;
        view = inflater.inflate(R.layout.fragment_list_item, null);

        TextView songName = (TextView) view.findViewById(R.id.primary_string);
        //TextView authorName = (TextView) view.findViewById(R.id.author_name_list);
        final Playlist playlist = playlists.get(i);
        //Author author = printSong.getAuthor();
        //String authors = "";
        songName.setText(playlist.getName());
        //authorName.setText(authors);
        AppCompatImageButton button = view.findViewById(R.id.popup_button);
        playlistOptions(activity, playlist, button);
        return view;
    }

    private void playlistOptions(final MainActivity activity, final Playlist playlist, AppCompatImageButton button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v, Gravity.LEFT);
                Activity act = (MainActivity)context;
                activity.getMenuInflater().inflate(R.menu.playlist_item_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.delete_playlist:
                                deletePlaylist(playlist, activity);
                                break;
                            case R.id.play_playlist:
                                playPlaylist(playlist, activity);
                                break;
                            case R.id.rename_playlist:
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    private void deletePlaylist(Playlist playlist, MainActivity activity) {
        try { // TODO: Possible code smell?
            Helpers.getToastHelper(context).sendToast("Deleted " + playlist.getName());
            playlists.remove(playlist);
            notifyDataSetChanged();
        } catch(PersistenceException p) {
            Helpers.getToastHelper(context).sendToast("Could not delete " + playlist.getName());
            Log.e(LOG_TAG, p.getMessage());
        }
    }

    private void playPlaylist(Playlist playlist, MainActivity activity) {
        try { // TODO: Possible code smell?
            Helpers.getToastHelper(context).sendToast("Playing " + playlist.getName());

            MusicService musicService = Services.getMusicService();
            musicService.playMultipleSongsAsync(playlist);

            //playlists.remove(playlist);
        } catch(PersistenceException p) {
            Helpers.getToastHelper(context).sendToast("Could not play " + playlist.getName());
            Log.e(LOG_TAG, p.getMessage());
        }
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
