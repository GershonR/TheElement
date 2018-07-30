package fifthelement.theelement.presentation.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.util.SongMetaUtil;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;
import fifthelement.theelement.presentation.activities.Delagate;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.adapters.CompactSongsListAdapter;
import fifthelement.theelement.presentation.adapters.PlaylistListAdapter;
import fifthelement.theelement.presentation.fragments.PlaylistListFragment;

public class PlaylistHelper {

    private static final String LOG_TAG = "PlaylistHelper";

    public void newPlaylistDialog(){
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(Delagate.mainActivity);
        builderSingle.setIcon(R.drawable.ic_playlist_add);
        builderSingle.setTitle("Give your playlist a name:");
        final EditText newNameInput = new EditText(Delagate.mainActivity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        newNameInput.setLayoutParams(lp);
        builderSingle.setView(newNameInput);

        builderSingle.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //take the text and change the name of the playlist
                String newName = newNameInput.getText().toString();
                if ( SongMetaUtil.validName(newName)){
                    Playlist newPlaylist = new Playlist(newName);
                    Services.getPlaylistService().insertPlaylist(newPlaylist);
                    Helpers.getToastHelper(Delagate.mainActivity.getApplicationContext()).sendToast(newName+" created!");
                    // find and refresh the playlist list fragment
                    List<Fragment> allFrags = Delagate.mainActivity.getSupportFragmentManager().getFragments();
                    for (Fragment fragment: allFrags){
                        if (fragment instanceof PlaylistListFragment){
                            ((PlaylistListFragment) fragment).refreshAdapter();
                        }
                    }
                }
                else{
                    Helpers.getToastHelper(Delagate.mainActivity.getApplicationContext()).sendToast(newName+" is an invalid name, try again");
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

    public void addSongsToPlaylist(final Song song) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(Delagate.mainActivity);
        builderSingle.setIcon(R.drawable.ic_add);
        builderSingle.setTitle("Select a Playlist:");

        final PlaylistListAdapter playlistListAdapter = new PlaylistListAdapter(Delagate.mainActivity, Services.getPlaylistService().getAllPlaylists());

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                System.out.println("Dismissed...");
            }
        });

        builderSingle.setAdapter(playlistListAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("Clicked: " + which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(Delagate.mainActivity);
                try {
                    Playlist chosenPlaylist =  Services.getPlaylistService().getAllPlaylists().get(which);
                    chosenPlaylist.addSong(song);
                    Services.getPlaylistService().insertSongForPlaylist(chosenPlaylist, song);
                    builderInner.setTitle("Added to "+chosenPlaylist.getName());
                    builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,int which) {
                            dialog.dismiss();
                        }
                    });
                    builderInner.show();
                } catch (PersistenceException p) {
                    Log.e(LOG_TAG, p.getMessage());
                    Helpers.getToastHelper(Delagate.mainActivity.getApplicationContext()).sendToast("Could not get playlist", "RED");
                }
            }
        });
        builderSingle.show();
    }

    public boolean deletePlaylist(Playlist playlist){
        boolean result = Services.getPlaylistService().deletePlaylist(playlist);
        List<Fragment> allFrags;
        if ( result){
            allFrags = Delagate.mainActivity.getSupportFragmentManager().getFragments();
            for (Fragment fragment: allFrags){
                if (fragment instanceof PlaylistListFragment){
                    ((PlaylistListFragment) fragment).refreshAdapter();
                }
            }
        }
        return result;
    }

    // For choosing to open a single song to play from the playlist
    public void openPlaylistSongs(final Playlist currentPlaylist){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(new ContextThemeWrapper(Delagate.mainActivity, R.style.alert_dialog_custom));
        builderSingle.setIcon(R.drawable.ic_song_list);
        builderSingle.setTitle(currentPlaylist.getName()+" songs:");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final CompactSongsListAdapter compactSongsListAdapter = new CompactSongsListAdapter(Delagate.mainActivity, currentPlaylist.getSongs());
        builderSingle.setAdapter(compactSongsListAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Services.getSongListService().setPlayerCurrentSongs(currentPlaylist);
                Services.getMusicService().playSongAsync(Services.getSongListService().getSongAtIndex(which));
            }
        });

        builderSingle.show();
    }

}
