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
import android.widget.Toast;

import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;
import fifthelement.theelement.presentation.activities.MainActivity;

public class SongsListAdapter extends BaseAdapter {
    Context context;
    List<Song> songs;
    LayoutInflater inflater;

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
        view = inflater.inflate(R.layout.fragment_song_list_item, null);
        TextView songName = (TextView) view.findViewById(R.id.song_name_list);
        TextView authorName = (TextView) view.findViewById(R.id.author_name_list);
        final Song printSong = songs.get(i);
        Author author = printSong.getAuthor();
        String authors = "";
        if(author != null) {
            authors += author.getName();
        }
        songName.setText(printSong.getName());
        authorName.setText(authors);
        AppCompatImageButton button = view.findViewById(R.id.popup_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v, Gravity.LEFT);
                Activity act = (MainActivity)context;
                activity.getMenuInflater().inflate(R.menu.song_list_item_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        try { // TODO: Possible code smell?
                            Services.getToastService(context).sendToast("Deleted " + printSong.getName());
                            activity.getSongService().deleteSong(printSong);
                            songs.remove(printSong);
                            notifyDataSetChanged();
                        } catch(PersistenceException p) {
                            Services.getToastService(context).sendToast("Could not delete " + printSong.getName());
                            Log.e("SongListAdapter", p.getMessage());
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
        return view;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
