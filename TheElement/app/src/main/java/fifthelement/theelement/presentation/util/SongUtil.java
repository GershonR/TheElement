package fifthelement.theelement.presentation.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import fifthelement.theelement.R;
import fifthelement.theelement.objects.Song;

public class SongUtil {

    private static final String LOG_TAG = "SongUtil";

    /**
     * This method will give you the default album art
     * @param context   The context of the app
     * @return          The default album art as a bitmap
     */
    public static Bitmap getDefaultAlbumArt(Context context) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            bm = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.default_album_art, options);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
        return bm;
    }

    /**
     * This method will give you a songs album art
     * @param context   The context of the app
     * @param song      The song to extract the album art
     * @return          The songs album art as a bitmap
     */
    public static Bitmap getSongAlbumArt(Context context, Song song) {
        Bitmap bm = null;
        MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
        try {
            if(song.getPath().contains("android.resource"))
                metaRetriver.setDataSource(context, Uri.parse(song.getPath()));
            else
                 metaRetriver.setDataSource(song.getPath());
            byte[] art = metaRetriver.getEmbeddedPicture();
            if(art != null)
                bm = BitmapFactory.decodeByteArray(art, 0, art.length);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
        if(bm == null)
            bm = getDefaultAlbumArt(context);

        return bm;
    }

    public static Uri getSongURIAlbumArt(Context context, Song song) {
        String[] projections = {MediaStore.Audio.Media.ALBUM_ID};
        Cursor cursor = null;
        Uri toReturn;
        try {
            Uri songURI = Uri.fromFile(new File(song.getPath()));
            System.out.println(songURI.getPath());
            cursor = context.getContentResolver().query(songURI, projections, null, null, null);
            if(cursor == null)
                System.out.println("Cursor is null");
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
            cursor.moveToFirst();

            Uri songCover = Uri.parse("content://media/external/audio/albumart");
            toReturn = ContentUris.withAppendedId(songCover, column_index);
        } finally {
            if(cursor != null){
                cursor.close();
            }
        }
        if(toReturn == null)
            toReturn = Uri.parse("android.resource://"+context.getPackageName()+ R.drawable.default_album_art);
        return toReturn;
    }

    public static Uri getArtUriFromMusicFile(Context context, Song song) {
        final Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        final String[] cursor_cols = { MediaStore.Audio.Media.ALBUM_ID };

        final String where = MediaStore.Audio.Media.IS_MUSIC + "=1 AND " + MediaStore.Audio.Media.DATA + " = '"
                + song.getPath() + "'";
        System.out.println(where);
        final Cursor cursor = context.getApplicationContext().getContentResolver().query(uri, cursor_cols, where, null, null);
        /*
         * If the cusor count is greater than 0 then parse the data and get the art id.
         */
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Long albumId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));

            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId);
            cursor.close();
            return albumArtUri;
        }
        cursor.close();
        return Uri.parse("android.resource://fifthelement.theelement/drawable/default_album_art");
    }

    /**
     * This method will take a song and retrieve its total duration
     * @param song      The song to extract the total duration from
     * @return          The songs total duration, formatted as X.XX
     */
    public static String getSongDuration(Song song) {
        String out = "";
        // load data file
        try {
            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(song.getPath());

            String duration = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long dur = Long.parseLong(duration);

            out = getTimeString(dur);
            metaRetriever.release();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
        return out;
    }

    /**
     * This method takes milliseconds and converts them into a formatted X:XX time string
     * @param millis    milliseconds to convert
     * @return          the converted milliseconds
     */
    public static String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                .append(String.format("%2d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }
}
