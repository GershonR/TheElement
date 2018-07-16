package fifthelement.theelement.presentation.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;

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
}
