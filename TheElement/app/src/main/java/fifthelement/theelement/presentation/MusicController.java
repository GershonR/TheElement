package fifthelement.theelement.presentation;

import android.content.Context;
import android.widget.MediaController;

/**
 * Created by ADAM on 6/9/2018.
 */

public class MusicController extends MediaController {

    public MusicController(Context c){
        super(c);
    }

    public void hide(){} //Stops automatic hiding of playback controls by overriding
}
