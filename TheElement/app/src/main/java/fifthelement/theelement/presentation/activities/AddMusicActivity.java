package fifthelement.theelement.presentation.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.Services.SongService;
import fifthelement.theelement.business.exceptions.SongAlreadyExistsException;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;
import fifthelement.theelement.presentation.util.PathUtil;


public class AddMusicActivity extends AppCompatActivity {

    private static final int CHOOSE_FILE_REQUESTCODE = 8777;
    private static final int PICKFILE_RESULT_CODE = 8778;
    private static final int PICKFILE_REQUEST_CODE = 1;

    SongService songService;
    MediaMetadataRetriever metaRetriver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songService = new SongService();

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
                    Services.getToastService(getApplicationContext()).sendToast("Permission Granted");

                } else {
                    Intent intent = new Intent(AddMusicActivity.this, MainActivity.class);
                    AddMusicActivity.this.startActivity(intent);
                    Services.getToastService(getApplicationContext()).sendToast("Permission Denied", "RED");
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
                    String FilePath = data.getData().getPath();
                    getMusicData(data.getData());
                }
        }
        Intent intent = new Intent(AddMusicActivity.this, MainActivity.class);
        AddMusicActivity.this.startActivity(intent);
    }

    private void openFileExplorer() {
        Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
        fileintent.setType("*/*");
        try {
            startActivityForResult(fileintent, PICKFILE_RESULT_CODE);
        } catch (ActivityNotFoundException e) {
            Log.e("tag", "No activity can handle picking a file. Showing alternatives.");
        }
    }


    private void getMusicData(Uri path) {
        metaRetriver = new MediaMetadataRetriever();
        metaRetriver.setDataSource(getApplication(), path);

        String songName = "";
        String songArtist = "";
        String songAlbum = "";
        String songGenre = "";

        try {
            //art = metaRetriver.getEmbeddedPicture();
            //Bitmap songImage = BitmapFactory
            //       .decodeByteArray(art, 0, art.length);
            songName = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            if(songName == null)
                    songName = PathUtil.getFileName(getContentResolver(), path); // Use the filename if no metadata
            songAlbum = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            songArtist = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            songGenre = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            String realPath = PathUtil.getPath(getApplicationContext(), path);

            Song song = new Song(songName, realPath);
            if(songArtist != null)
                song.setAuthor(new Author(songArtist));
            if(songAlbum != null)
                song.setAlbum(new Album(songAlbum));
            if(songGenre != null)
                song.setGenre(songGenre);
            songService.insertSong(song);
        } catch (PersistenceException p) {
            Services.getToastService(getApplicationContext()).sendToast("Error saving song!", "RED");
        } catch (SongAlreadyExistsException s) {
            Services.getToastService(getApplicationContext()).sendToast("Song already exists!", "RED");
        } catch (Exception e) {
            Services.getToastService(getApplicationContext()).sendToast("Could not get the songs path!", "RED");
        }
    }

}
