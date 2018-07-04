package fifthelement.theelement.presentation.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import fifthelement.theelement.R;

public class SongUtil {

    public static Bitmap getDefaultAlbumArt(Context context) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            bm = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.default_album_art, options);
        } catch (Error ee) {
        } catch (Exception e) {
        }
        return bm;
    }

}
