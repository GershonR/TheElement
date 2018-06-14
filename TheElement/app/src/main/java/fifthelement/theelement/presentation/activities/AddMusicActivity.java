package fifthelement.theelement.presentation.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.net.URI;

import fifthelement.theelement.R;
import fifthelement.theelement.business.Services.SongService;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.MainActivity;


public class AddMusicActivity extends AppCompatActivity {

    private static final int CHOOSE_FILE_REQUESTCODE = 8777;
    private static final int PICKFILE_RESULT_CODE = 8778;

    SongService songService;
    MediaMetadataRetriever metaRetriver;
    byte[] art;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songService = new SongService();

        Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
        fileintent.setType("*/*");
        try {
            startActivityForResult(fileintent, PICKFILE_RESULT_CODE);
        } catch (ActivityNotFoundException e) {
            Log.e("tag", "No activity can handle picking a file. Showing alternatives.");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    String FilePath = data.getData().getPath();
                    System.out.println(FilePath);

                    getMusicData(data.getData());
                }
        }
        Intent intent = new Intent(AddMusicActivity.this, MainActivity.class);
        AddMusicActivity.this.startActivity(intent);
    }

    private void getMusicData(Uri path) {
        metaRetriver = new MediaMetadataRetriever();
        System.out.println(path.getPath());
        metaRetriver.setDataSource(getApplication(), path);

        String songName = "";
        String songArtist = "";
        String songAlbum = "";
        String songGenre = "";

        System.out.println("Set the Data Source");
        try {
            //art = metaRetriver.getEmbeddedPicture();
            //Bitmap songImage = BitmapFactory
             //       .decodeByteArray(art, 0, art.length);
            songName = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            songAlbum = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            songArtist = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            songGenre = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
        } catch (Exception e) {
            System.out.println("Exception???");
            System.out.println(e.getMessage());

        }

        System.out.println(songName);
        System.out.println(songAlbum);
        System.out.println(songArtist);
        System.out.println(songGenre);
        int id = (int)(Math.random() * 1000 + 1);
        Song song = new Song(id, songName, path.getPath());
        song.addAuthor(new Author(id, songArtist));
        songService.insertSong(song);
    }
}
