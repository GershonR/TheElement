package fifthelement.theelement.presentation.services;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.services.SongListService;
import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.activities.Delagate;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.constants.NotificationConstants;
import fifthelement.theelement.presentation.fragments.NowPlaying;
import fifthelement.theelement.presentation.fragments.SeekerFragment;


// This MusicService will allow for a MediaPlayer instance to
// play music in the background of the app and be controlled
// by various UI elements in the app.
public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private MainActivity mainActivity;
    private MediaPlayer player;
    private boolean playerPrepared;
    private SongListService songListService;
    private Song currentSongPlaying;
    private final IBinder musicBind = new MusicBinder();
    private SeekerFragment.SeekerPlaybackStartStopListener seekerPlaybackListener;
    private NotificationService.NotificationPlaybackStartStopListener notificationPlaybackListener;
    private SongService songService;

    private static final String LOG_TAG = "MusicService";

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
        songListService = Services.getSongListService();
        songService = Services.getSongService();
        player = new MediaPlayer();
        initMusicPlayer();
        mainActivity = Delagate.mainActivity;
    }

    //This function acts as a callback that occurs when the private MediaPlayer
    // instance completes playback of a music file.
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        //We stop playback on completion
        if(seekerPlaybackListener != null){
            seekerPlaybackListener.onPlaybackStop(true);
        }
        if(notificationPlaybackListener != null){
            notificationPlaybackListener.onPlaybackStop();
        }
        //If autoplay is on, we "skip" to next song on completion
        if(songListService.getAutoplayEnabled()){
            skip();
        }
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
            playerPrepared = true;
        }
    }

    public void playSongAsync() {
        if (songListService.getSongAtIndex(0) != null)
            playSongAsync( songListService.getSongAtIndex(0));
    }

    // This function will attempt to set the media player up asynchronously and play the media.
    public boolean playSongAsync(Song song) {
        reset();
        if(song != null) {
            Uri uri = Uri.parse(song.getPath());
            try {
                player.setDataSource(getApplication(), uri);
                player.prepareAsync();
                currentSongPlaying = song;
            } catch (Exception e) {
                Helpers.getToastHelper(getApplicationContext()).sendToast("Invalid Song!", "RED");
                Log.e(LOG_TAG, e.getMessage());
                return false;
            }
            playerPrepared = false;

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer player) {
                    playerPrepared = true;
                    start();
                    songService.songIsPlayed(currentSongPlaying.getUUID());
                    Helpers.getToastHelper(getApplicationContext()).sendToast("Now Playing: " + currentSongPlaying.getName());
                    if(notificationPlaybackListener != null){
                        notificationPlaybackListener.onSkip();
                    } else {
                        startNotificationService();
                    }
                }

            });
            return true;
        }
        return false;
    }

    // Start the notification service if it did not exist
    public void startNotificationService() {
        Intent serviceIntent = new Intent(getApplicationContext(), NotificationService.class);
        serviceIntent.setAction(NotificationConstants.STARTFOREGROUND_ACTION);
        startService(serviceIntent);
    }

    public void stopNotificationService() {
        Intent serviceIntent = new Intent(getApplicationContext(), NotificationService.class);
        serviceIntent.setAction(NotificationConstants.STARTFOREGROUND_ACTION);
        stopService(serviceIntent);
    }

    // This function will reset the MediaPlayer instance and reset seekbar UI positions to start.
    public void reset() {
        if(seekerPlaybackListener != null){
            seekerPlaybackListener.onPlaybackStop(true);
        }
        if(notificationPlaybackListener != null){
            notificationPlaybackListener.onPlaybackStop();
        }
        player.reset();
        playerPrepared = false;
    }

    // This function will start the private MediaPlayer instance (equivalent to 'Play').
    public void start() {
        if(playerPrepared && !player.isPlaying()) {
            if(seekerPlaybackListener != null){
                seekerPlaybackListener.onPlaybackStart();
            }
            if(notificationPlaybackListener != null){
                notificationPlaybackListener.onPlaybackStart();
            }
            player.start();
        } else if(!playerPrepared) {
            initMusicPlayer();
            start(); // retry starting
        }
    }

    // This function pauses the playback of the private MediaPlayer instance.
    public void pause() {
        if(playerPrepared && player.isPlaying()){
            if(seekerPlaybackListener != null){
                seekerPlaybackListener.onPlaybackStop(false);
            }
            if(notificationPlaybackListener != null){
                notificationPlaybackListener.onPlaybackStop();
            }
            player.pause();
        }
    }

    // Skips to the next song in the list
    public void skip() {
        Song currentSong = songListService.getSongAtIndex(songListService.getCurrentSongPlayingIndex());
        if(currentSong != null) {
            songService.songIsSkipped(currentSong.getUUID());
            playSongAsync(songListService.skipToNextSong());
        } else {
            playSongAsync(songListService.getSongAtIndex(0));
        }
        updateSong();
    }

    // Skips to the previous song in the list
    public void prev() {
        Song currentSong = songListService.getSongAtIndex(songListService.getCurrentSongPlayingIndex());
        if(currentSong != null) {
            songService.songIsSkipped(currentSong.getUUID());
            playSongAsync(songListService.goToPrevSong());
        } else {
            playSongAsync(songListService.getSongAtIndex(0));
        }
        updateSong();
    }

    // Update the notificaction & NowPlaying
    private void updateSong() {
        if (notificationPlaybackListener != null) {
            notificationPlaybackListener.onSkip();
        }

        updateFragment();
    }


    // If on NowPlaying page, update the page when a skip happens
    private void updateFragment() {
        if(mainActivity == null)
            return;
        NowPlaying nowPlaying = (NowPlaying) mainActivity.getSupportFragmentManager().findFragmentByTag("NowPlaying");
        if (nowPlaying != null && nowPlaying.isVisible()) {
            NowPlaying newNowPlaying = new NowPlaying();
            Helpers.getFragmentHelper(mainActivity).createFragment(R.id.flContent, newNowPlaying, "NowPlaying");
        }
    }

    public void shuffle() {
       songListService.shuffle();
       playSongAsync(songListService.skipToNextSong());
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
        if(playerPrepared && player != null) {
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

    public Song getCurrentSongPlaying() {
        return this.currentSongPlaying;
    }

    public void setSeekerPlaybackListener(SeekerFragment.SeekerPlaybackStartStopListener listener){
        seekerPlaybackListener = listener;
    }

    public void setNotificationPlaybackListener(NotificationService.NotificationPlaybackStartStopListener listener){
        notificationPlaybackListener = listener;
    }

    //Public helper class for binding this service to an activity
    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
