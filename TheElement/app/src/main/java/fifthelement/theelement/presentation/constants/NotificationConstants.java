package fifthelement.theelement.presentation.constants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import fifthelement.theelement.R;

public class NotificationConstants {
    public interface ACTION {
        String MAIN_ACTION = "fifthelement.theelement.presentation.action.main";
        String INIT_ACTION = "fifthelement.theelement.presentation.action.init";
        String PREV_ACTION = "fifthelement.theelement.presentation.action.prev";
        String PLAY_ACTION = "fifthelement.theelement.presentation.action.play";
        String NEXT_ACTION = "fifthelement.theelement.presentation.action.next";
        String STARTFOREGROUND_ACTION = "fifthelement.theelement.presentation.action.startforeground";
        String STOPFOREGROUND_ACTION = "fifthelement.theelement.presentation.action.stopforeground";

    }

    public interface NOTIFICATION_ID {
        int FOREGROUND_SERVICE = 101;
    }

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
