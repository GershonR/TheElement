package fifthelement.theelement.presentation.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.fragments.SongInfoFragment;
import fifthelement.theelement.presentation.tasks.SongAlbumArtTask;

public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView albumArt;
        public TextView songName;
        public TextView authorName;
        public ImageButton songOptions;
        public Song song;

        // Constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            albumArt = itemView.findViewById(R.id.song_list_album_art);
            songName = itemView.findViewById(R.id.primary_string);
            authorName = itemView.findViewById(R.id.secondary_string);
            songOptions = itemView.findViewById(R.id.popup_button);

            //set method to handle click events
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            boolean result = Services.getMusicService().playSongAsync(song);
            if (result) {
               Services.getSongListService().setShuffled(false);
               List<Song> songs = Services.getSongService().getSongs();
               Services.getSongListService().sortSongs(songs);
               Services.getSongListService().setCurrentSongsList(songs);
               Services.getSongListService().setCurrentSongPlayingIndex(this.getLayoutPosition());
            }
        }

    }


    Context context;
    List<Song> songs;
    LayoutInflater inflater;
    private static final String LOG_TAG = "SongsListAdapter";


    // Pass in the song array into the constructor
    public SongsListAdapter(Context context, List<Song> songs) {
        this.context = context;
        this.songs = songs;
    }

    // Inflate the layout from XML and returning the holder
    @NonNull
    @Override
    public SongsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View songListView = inflater.inflate(R.layout.fragment_list_item, parent, false);


        // Return a new holder instance
        return new ViewHolder(songListView);
    }

    // Populate data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull SongsListAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Song song = songs.get(position);

        // Set item views based on your views and data model
        ImageView albumArt = viewHolder.albumArt;
        TextView songName = viewHolder.songName;
        TextView authorName = viewHolder.authorName;
        ImageButton songOptions = viewHolder.songOptions;
        viewHolder.song = song;

        songName.setSelected(true);
        Author author = song.getAuthor();
        String authors = "";
        if(author != null) {
            authors += author.getName();
        }
        songName.setText(song.getName());
        authorName.setText(authors);
        songOptions(song, songOptions);
        AsyncTask task = new SongAlbumArtTask(context, albumArt, song);
        task.execute();
    }

    private void songOptions(final Song song, ImageButton button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v, Gravity.LEFT);
                final MainActivity act = (MainActivity)context;
                act.getMenuInflater().inflate(R.menu.song_list_item_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.delete_song:
                                deleteSong(song, act);
                                break;
                            case R.id.song_info:
                                Fragment fragment = null;
                                try{
                                    SongInfoFragment songInfoFragment = SongInfoFragment.newInstance();
                                    songInfoFragment.setSong(song);
                                    fragment = (Fragment) songInfoFragment;
                                }
                                catch (Exception e){
                                    Log.e(LOG_TAG, e.getMessage());
                                }
                                Helpers.getFragmentHelper(act).createFragment(R.id.flContent, fragment, "SongInfo");
                                break;
                            case R.id.add_to_playlist:
                                Helpers.getPlaylistHelper().addSongsToPlaylist(song);
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    private void deleteSong(Song song, MainActivity activity) {
        try {
            Helpers.getToastHelper(context).sendToast("Deleted " + song.getName());
            if(Services.getMusicService().getCurrentSongPlaying() != null
                    && Services.getMusicService().getCurrentSongPlaying().getUUID().equals(song.getUUID())) {
                Services.getMusicService().reset();
            }
            activity.getSongService().deleteSong(song);
            songs.remove(song);
            Services.getSongListService().removeSongFromList(song);
            notifyDataSetChanged();
        } catch(PersistenceException p) {
            Helpers.getToastHelper(context).sendToast("Could not delete " + song.getName());
            Log.e(LOG_TAG, p.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
