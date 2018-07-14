package fifthelement.theelement.presentation.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.net.URISyntaxException;

import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.services.AlbumService;
import fifthelement.theelement.business.services.AuthorService;
import fifthelement.theelement.business.services.SongListService;
import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.business.exceptions.SongAlreadyExistsException;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;
import fifthelement.theelement.presentation.util.PathUtil;
import fifthelement.theelement.presentation.util.SongUtil;


public class AddMusicActivity extends AppCompatActivity {

    private static final int CHOOSE_FILE_REQUESTCODE = 8777;
    private static final int PICKFILE_RESULT_CODE = 8778;
    private static final int PICKFILE_REQUEST_CODE = 1;
    private static final String LOG_TAG = "AddMusicActivity";

    SongService songService;
    SongListService songListService;
    AlbumService albumService;
    AuthorService authorService;

    MediaMetadataRetriever metaRetriver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songService = Services.getSongService();
        songListService = Services.getSongListService();
        authorService = Services.getAuthorService();
        albumService = Services.getAlbumService();

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICKFILE_REQUEST_CODE);
        } else {
            openFileExplorer();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PICKFILE_REQUEST_CODE : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    openFileExplorer();
                    Helpers.getToastHelper(getApplicationContext()).sendToast("Permission Granted");

                } else {
                    Intent intent = new Intent(AddMusicActivity.this, MainActivity.class);
                    AddMusicActivity.this.startActivity(intent);
                    Helpers.getToastHelper(getApplicationContext()).sendToast("Permission Denied", "RED");
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    setupSong(data.getData());
                }
        }
        songListService.setCurrentSongsList(songService.getSongs()); //Reset song list
        songListService.setAllSongsList(songService.getSongs()); //Reset song list
        Intent intent = new Intent(AddMusicActivity.this, MainActivity.class);
        AddMusicActivity.this.startActivity(intent);
    }

    private void openFileExplorer() {
        Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
        fileintent.setType("*/*");
        try {
            startActivityForResult(fileintent, PICKFILE_RESULT_CODE);
        } catch (ActivityNotFoundException e) {
            Log.e(LOG_TAG, "No activity can handle picking a file. Showing alternatives.");
        }
    }


    private void setupSong(Uri path) {
        String stringPath = path.getPath();
        String extension = MimeTypeMap.getFileExtensionFromUrl(stringPath);

        boolean result = SongUtil.supportedAudioFileExtension(extension);

        if ( result){
            metaRetriver = new MediaMetadataRetriever();
            metaRetriver.setDataSource(getApplication(), path);

            String songName = "";
            String songArtist = "";
            String songAlbum = "";
            String songGenre = "";

            try {
                songName = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                if(songName == null)
                    songName = PathUtil.getFileName(getContentResolver(), path); // Use the filename if no metadata
                songAlbum = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                songArtist = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                songGenre = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }

            createSong(path, songName, songArtist, songAlbum, songGenre);
        } else {
            Helpers.getToastHelper(getApplicationContext()).sendToast("That isn't a song, nice try!", "RED");
        }
    }

    private void createSong(Uri path, String songName, String songArtist, String songAlbum, String songGenre) {
        try {
            String realPath = PathUtil.getPath(getApplicationContext(), path);
            songService.createSong(realPath, songName, songArtist, songAlbum, songGenre);
            Helpers.getToastHelper(getApplicationContext()).sendToast("Added " + songName, "GREEN");
        } catch (PersistenceException p) {
            Helpers.getToastHelper(getApplicationContext()).sendToast("Error saving song!", "RED");
            Log.e(LOG_TAG, p.getMessage());
        } catch (SongAlreadyExistsException s) {
            Helpers.getToastHelper(getApplicationContext()).sendToast("Song already exists!", "RED");
            Log.e(LOG_TAG, s.getMessage());
        } catch (URISyntaxException e) {
            Helpers.getToastHelper(getApplicationContext()).sendToast("Could not get the songs path!", "RED");
            Log.e(LOG_TAG, e.getMessage());
        }
    }

}
