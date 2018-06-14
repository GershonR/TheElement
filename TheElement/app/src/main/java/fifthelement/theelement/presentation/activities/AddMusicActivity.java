package fifthelement.theelement.presentation.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.net.URI;
import java.net.URISyntaxException;

import fifthelement.theelement.R;
import fifthelement.theelement.business.Services.SongService;
import fifthelement.theelement.objects.Album;
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

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

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
            System.out.println(e.getMessage());

        }

        if(!songService.pathExists(path.getPath())) {
            String realPath = "";
            try {
                realPath = getPath(getApplicationContext(), path);
                System.out.println("Real Path? " + realPath);
                System.out.println(path.getPath());
            } catch(Exception e) {

            }

            Song song = new Song(songName, realPath);
            song.addAuthor(new Author(songArtist));
            song.addAlbum(new Album(songAlbum));
            songService.insertSong(song);
        } else {
            Toast toast = Toast.makeText(this, "This Song Already Exists!", Toast.LENGTH_LONG);
            View view = toast.getView();

            //To change the Background of Toast
            view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            toast.show();
        }
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{ split[1] };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
