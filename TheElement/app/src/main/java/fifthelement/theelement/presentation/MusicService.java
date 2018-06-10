package fifthelement.theelement.presentation;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

import fifthelement.theelement.objects.Song;

/**
 * This MusicService will allow for a MediaPlayer instance to
 * play music in the background of the app and be controlled
 * by various UI elements in the app.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{

    private MediaPlayer player;
    private Song currSong;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
        //Start playing song
        mediaPlayer.start();
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

    public void setCurrSong(Song song) {
        currSong = song;
    }

    public void playSong() {
        //Try to open current song from path and prepare to play
        player.reset();
        String path = currSong.getPath();

        try{
            player.setDataSource(path);
        } catch(Exception e) {
            //TODO: Handle exceptions
        }

        player.prepareAsync();
    }

    public int getSongPosition(){
        return player.getCurrentPosition();
    }

    public int getSongDuration(){
        return player.getDuration();
    }

    public boolean isPlaying(){
        return player.isPlaying();
    }

    public void pausePlayer(){
        if(player.isPlaying()) {
            player.pause();
        }
    }

    public void seekTo(int posn){
        player.seekTo(posn);
    }

    public void resumePlayer(){
        if(!player.isPlaying()) {
            player.start();
        }
    }
}
