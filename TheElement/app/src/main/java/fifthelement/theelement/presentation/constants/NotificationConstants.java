package fifthelement.theelement.presentation.constants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import fifthelement.theelement.R;

public class NotificationConstants {
    public static final int NOTIFICATION_ID = 101;
    public static final String CHANNEL_ID = "THE_ELEMENT_01";
    public static final String MAIN_ACTION = "fifthelement.theelement.presentation.action.main";
    public static final String INIT_ACTION = "fifthelement.theelement.presentation.action.init";
    public static final String PREV_ACTION = "fifthelement.theelement.presentation.action.prev";
    public static final String PLAY_ACTION = "fifthelement.theelement.presentation.action.play";
    public static final String NEXT_ACTION = "fifthelement.theelement.presentation.action.next";
    public static final String STARTFOREGROUND_ACTION = "fifthelement.theelement.presentation.action.startforeground";
    public static final String STOPFOREGROUND_ACTION = "fifthelement.theelement.presentation.action.stopforeground";

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
