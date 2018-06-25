package fifthelement.theelement.presentation.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fifthelement.theelement.application.Services;
import fifthelement.theelement.presentation.fragments.SeekerFragment;


// This MusicService will allow for a MediaPlayer instance to
// play music in the background of the app and be controlled
// by various UI elements in the app.
public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private MediaPlayer player;
    private boolean playerPrepared;
    private final IBinder musicBind = new MusicBinder();
    private SeekerFragment.PlaybackStartStopListener playbackListener;

    //This function is called when the service is bound, it will return a MusicBinder instance
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }


    //This function is called when the service is unbound, it will clean up the MediaPlayer
    @Override
    public boolean onUnbind(Intent intent) {
        player.stop();
        player.release();
        return false;
    }

    //This function is called when the service is created, it will initialize the MediaPlayer
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        initMusicPlayer();
    }

    //This function acts as a callback that occurs when the private MediaPlayer
    // instance completes playback of a music file.
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    // This function acts as a callback that occurs when the private MediaPlayer
    // instance runs into an error
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        return false;
    }

    // This function acts as a callback that occurs when the private MediaPlayer
    // instance has fully prepared.
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        //Code to run when media player is prepared to play
        playerPrepared = true;
    }

    // This function will initialize the private MediaPlayer member to play music in the background
    // and sets up the 3 listener methods above.
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

    // This function will attempt to set the media player up asynchronously and play the media.
    public boolean playSongAsync(String songPath) {
        reset();
        Uri uri = Uri.parse(songPath);

        try {
            player.setDataSource(getApplication(), uri);
            player.prepareAsync();
        } catch(Exception e) {
            Services.getToastService(getApplicationContext()).sendToast("Invalid Song!", "RED");
            e.printStackTrace();
            return false;
        }
        playerPrepared = false;

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer player) {
                playerPrepared = true;
                start();
            }

        });
        return true;
    }

    // This function will reset the MediaPlayer instance and reset seekbar UI positions to start.
    public void reset() {
        player.reset();
    }

    // This function will start the private MediaPlayer instance (equivalent to 'Play').
    public void start() {
        if(playerPrepared && !player.isPlaying()) {
            if(playbackListener != null){
                playbackListener.onPlaybackStart();
            }
            player.start();
        } else if(!playerPrepared) {
            Services.getToastService(getApplicationContext()).sendToast("No Song Selected!", "RED");
        }
    }

    // This function pauses the playback of the private MediaPlayer instance.
    public void pause() {
        if(playerPrepared && player.isPlaying()){
            if(playbackListener != null){
                playbackListener.onPlaybackStop();
            }
            player.pause();
        }
    }

    // This function will return the duration of the currently loaded music file.
    public int getDuration() {
        if(playerPrepared) {
            return player.getDuration();
        }
        else {
            return 0;
        }
    }

    // This function will return the current position of playback in ms.
    public int getCurrentPosition() {
        if(playerPrepared) {
            return player.getCurrentPosition();
        }
        else {
            return 0;
        }
    }

    // This function will have the MediaPlayer seek to a position of playback in ms.
    public void seekTo(int pos) {
        if(playerPrepared) {
            player.seekTo(pos);
        }
    }

    // This function will return a boolean indicating if playback is currently going on.
    public boolean isPlaying() {
        return player.isPlaying();
    }

    public void setPlaybackListener(SeekerFragment.PlaybackStartStopListener listener){
        playbackListener = listener;
    }

    //Public helper class for binding this service to an activity
    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
