package fifthelement.theelement.presentation.tasks;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import fifthelement.theelement.R;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.activities.Delagate;

public class SongAlbumArtTask extends AsyncTask<Object, Object, Object>  {
    Context context;
    ImageView albumArt;
    Song song;

    public SongAlbumArtTask(Context context,  ImageView albumArt, Song song) {
        this.context = context;
        this.albumArt = albumArt;
        this.song = song;
    }
    @Override
    protected Object doInBackground(Object... params) {
        if(albumArt != null && song != null) {
            Cursor cursor = getCursor();
            if (cursor != null && cursor.moveToFirst()) {
                Uri imgUri = getUri(cursor);
                Delagate.mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Glide.with(context)
                                .load(imgUri)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.default_album_art)
                                        .error(R.drawable.default_album_art)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .dontAnimate()
                                        .dontTransform())
                                .into(albumArt);
                    }
                });
            }
        }
        return null;

    }

    private Cursor getCursor() {
        Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.DATA + "=? ";
        String[] projection = new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ALBUM_ID};
        return context.getContentResolver().query(
                audioUri,
                projection,
                selection,
                new String[]{song.getPath()}, null);
    }

    private Uri getUri(Cursor cursor) {
        long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
        Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
        return ContentUris.withAppendedId(sArtworkUri,albumId);
    }
}
