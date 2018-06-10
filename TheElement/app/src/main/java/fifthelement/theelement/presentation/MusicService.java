package fifthelement.theelement.presentation;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.MediaController;

import fifthelement.theelement.objects.Song;

/**
 * This MusicService will allow for a MediaPlayer instance to
 * play music in the background of the app and be controlled
 * by various UI elements in the app.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaController.MediaPlayerControl{

    private MediaPlayer player;
    private boolean playerPrepared;
    private final IBinder musicBind = new MusicBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        player.stop();
        player.release();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        //TODO: This is where we would do autoplay stuff
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        //Code to run when media player is prepared to play
        playerPrepared = true;
    }

    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        initMusicPlayer();
    }

    public void initMusicPlayer() {
        //Sets up the MediaPlayer to continue playing in the background + listeners
        if(player != null) {
            player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setOnPreparedListener(this);
            player.setOnCompletionListener(this);
            player.setOnErrorListener(this);
        }
    }

    public void setCurrSongPath(String songPath) {
        System.out.println("Attempting to set new path: " + songPath);

        player.reset();
        Context context = getBaseContext();
        Uri uri = Uri.parse(songPath);

        try{
            player.setDataSource(context, uri);
        } catch(Exception e) {
            //TODO: Handle exceptions
            e.printStackTrace();
        }

        playerPrepared = false;
        player.prepareAsync();
    }

    public void reset() {
        player.reset();
    }

    @Override
    public void start() {
        if(playerPrepared && !player.isPlaying()) {
            player.start();
        }
    }

    @Override
    public void pause() {
        if(playerPrepared && player.isPlaying()){
            player.pause();
        }
    }

    @Override
    public int getDuration() {
        if(playerPrepared) {
            return player.getDuration();
        }
        else {
            return 0;
        }
    }

    @Override
    public int getCurrentPosition() {
        if(playerPrepared) {
            return player.getCurrentPosition();
        }
        else {
            return 0;
        }
    }

    @Override
    public void seekTo(int pos) {
        if(playerPrepared) {
            player.seekTo(pos);
        }
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }


    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
}
