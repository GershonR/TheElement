package fifthelement.theelement.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.activities.MainActivity;

public class CompactSongsListAdapter extends BaseAdapter {
    Context context;
    List<Song> songs;
    LayoutInflater inflater;
    private static final String LOG_TAG = "SongsListAdapter";

    public CompactSongsListAdapter(Context context, List<Song> songs) {
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
        view = inflater.inflate(R.layout.compact_fragment_list_item, null);
        TextView songName = (TextView) view.findViewById(R.id.primary_string);
        TextView authorName = (TextView) view.findViewById(R.id.secondary_string);
        final Song printSong = songs.get(i);
        Author author = printSong.getAuthor();
        String authors = "";
        if(author != null) {
            authors += author.getName();
        }
        songName.setText(printSong.getName());
        authorName.setText(authors);
        //AppCompatImageButton button = view.findViewById(R.id.popup_button);
        /*
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clicked a song in the Compact songs list");
            }
        });*/
        return view;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
