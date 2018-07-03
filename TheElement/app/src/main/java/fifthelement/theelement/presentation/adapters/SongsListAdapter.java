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
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;
import fifthelement.theelement.presentation.activities.MainActivity;

public class SongsListAdapter extends BaseAdapter {
    Context context;
    List<Song> songs;
    LayoutInflater inflater;
    private static final String LOG_TAG = "SongsListAdapter";

    public SongsListAdapter(Context context, List<Song> songs) {
        this.context = context;
        this.songs = songs;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);
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
        TextView authorName = (TextView) view.findViewById(R.id.secondary_name);
        final Song printSong = songs.get(i);
        Author author = printSong.getAuthor();
        String authors = "";
        if(author != null) {
            authors += author.getName();
        }
        songName.setText(printSong.getName());
        authorName.setText(authors);
        AppCompatImageButton button = view.findViewById(R.id.popup_button);
        songOptions(activity, printSong, button);
        return view;
    }

    private void songOptions(final MainActivity activity, final Song song, AppCompatImageButton button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v, Gravity.LEFT);
                Activity act = (MainActivity)context;
                activity.getMenuInflater().inflate(R.menu.song_list_item_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        deleteSong(song, activity);
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    private void deleteSong(Song song, MainActivity activity) {
        try { // TODO: Possible code smell?
            Helpers.getToastHelper(context).sendToast("Deleted " + song.getName());
            if(Services.getMusicService().getCurrentSongPlaying() != null
                    && Services.getMusicService().getCurrentSongPlaying().getUUID().equals(song.getUUID())) {
                Services.getMusicService().reset();

            }
            activity.getSongService().deleteSong(song);
            songs.remove(song);
            notifyDataSetChanged();
        } catch(PersistenceException p) {
            Helpers.getToastHelper(context).sendToast("Could not delete " + song.getName());
            Log.e(LOG_TAG, p.getMessage());
        }
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
