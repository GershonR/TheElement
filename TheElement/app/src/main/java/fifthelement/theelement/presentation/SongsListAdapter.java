package fifthelement.theelement.presentation;

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
        return songs.get(position).getUUID().getMostSignificantBits();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.fragment_song_list_item, null);
        TextView songName = (TextView) view.findViewById(R.id.song_name_list);
        TextView authorName = (TextView) view.findViewById(R.id.author_name_list);
        Song printSong = songs.get(i);
        List<Author> author = printSong.getAuthors();
        String authors = "";
       if(author != null) {
           for(int j = 0; j < author.size(); j++){
               authors += author.get(j).getName();
               if(j < author.size()-1) {
                   authors += ", ";
               }
           }
       }
        songName.setText(printSong.getName());
        authorName.setText(authors);
        return view;
    }

    public void filterResults() {
        //grab all the songs
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
