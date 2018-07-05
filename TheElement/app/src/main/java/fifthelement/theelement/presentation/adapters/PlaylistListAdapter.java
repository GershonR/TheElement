package fifthelement.theelement.presentation.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.business.services.PlaylistService;
import fifthelement.theelement.business.services.SongListService;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;
import fifthelement.theelement.presentation.activities.MainActivity;

import static fifthelement.theelement.application.Services.getMusicService;
import static fifthelement.theelement.application.Services.getSongListService;

public class PlaylistListAdapter extends BaseAdapter {
    Context context;
    List<Playlist> playlists;
    LayoutInflater inflater;
    PlaylistService playlistService;
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
        TextView songCount = (TextView) view.findViewById(R.id.secondary_string);

        final Playlist playlist = playlists.get(i);
        songName.setText(playlist.getName());
        songCount.setText((playlist.getSongs().size())+" Songs");

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
                                renamePlaylist(playlist);
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

    private void renamePlaylist(final Playlist playlist){
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(this.context);
        builderSingle.setIcon(R.drawable.ic_edit);
        builderSingle.setTitle("Rename "+playlist.getName()+" to:");
        final EditText newNameInput = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        newNameInput.setLayoutParams(lp);
        builderSingle.setView(newNameInput);

        builderSingle.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //take the text and change the name of the playlist
                String newName = newNameInput.getText().toString();
                if ( validText(newName)){
                    playlist.setName(newName);
                }
                else{
                    Helpers.getToastHelper(context).sendToast(newName+" is an invalid name, try again");
                }
                dialog.dismiss();
            }
        });

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.show();
    }

    private boolean validText(String text){
        boolean result = false;
        String normalChars = "^[a-zA-Z0-9]+$";
        if (text.matches(normalChars))
            result = true;
        return result;
    }

    private void deletePlaylist(Playlist playlist, MainActivity activity) {
        try { // TODO: Possible code smell?
            Helpers.getToastHelper(context).sendToast("Deleted " + playlist.getName());
            activity.getPlaylistService().deletePlaylist(playlist);
            notifyDataSetChanged();
        } catch(PersistenceException p) {
            Helpers.getToastHelper(context).sendToast("Could not delete " + playlist.getName());
            Log.e(LOG_TAG, p.getMessage());
        }
    }

    private void playPlaylist(Playlist playlist, MainActivity activity) {
        try {
            if (playlist.getSongs().size() == 0)
                Helpers.getToastHelper(context).sendToast("No songs in " + playlist.getName());
            else
                Helpers.getToastHelper(context).sendToast("Playing " + playlist.getName());

            SongListService songListService = getSongListService();
            songListService.setShuffled(true);
            songListService.setCurrentSongsList(playlist.getSongs());
            songListService.setAutoplayEnabled(true);

            getMusicService().start();
            getMusicService().playSongAsync();

        } catch(PersistenceException p) {
            Helpers.getToastHelper(context).sendToast("Could not play " + playlist.getName());
            Log.e(LOG_TAG, p.getMessage());
        }
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
