package fifthelement.theelement.presentation.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.services.SongListService;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.fragments.SeekerFragment;


// This MusicService will allow for a MediaPlayer instance to
// play music in the background of the app and be controlled
// by various UI elements in the app.
public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private MediaPlayer player;
    private boolean playerPrepared;
    private SongListService songListService;
    private Song currentSongPlaying;
    private final IBinder musicBind = new MusicBinder();
    private SeekerFragment.SeekerPlaybackStartStopListener seekerPlaybackListener;
    private NotificationService.NotificationPlaybackStartStopListener notificationPlaybackListener;

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
        player = new MediaPlayer();
        initMusicPlayer();
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
                    Helpers.getToastHelper(getApplicationContext()).sendToast("Now Playing: " + currentSongPlaying.getName());
                }

            });
            return true;
        }
        return false;
    }
/*
    public boolean playMultipleSongsAsync(Playlist playlist) {
        songs = playlist.getSongs();
        currentSongPlayingIndex = 0;
        lastSongPlayedIndex = 0;

        //start playing the first song
        if (songs.size() > 0)
            playSongAsync(songs.get(currentSongPlayingIndex), currentSongPlayingIndex);
        else
            Helpers.getToastHelper(getApplicationContext()).sendToast("No songs in playlist", "PINK");


        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try{
                    // if modified, we skipped or prev'd
                    if (!currentSongModified){
                        // listened to the song completely, behave normally
                        if (currentSongPlayingIndex == lastSongPlayedIndex)
                            currentSongPlayingIndex++;
                            //We skipped a song (increased currentSongPlayingIndex, and don't want to do it again
                        else if (currentSongPlayingIndex - lastSongPlayedIndex > 1)
                            currentSongPlayingIndex = lastSongPlayedIndex+1;
                        else
                            currentSongPlayingIndex = 0;

                        Song nextSong = songs.get(currentSongPlayingIndex);
                        playSongAsync(nextSong, currentSongPlayingIndex);
                    }
                    else
                        currentSongModified = false;

                    lastSongPlayedIndex = currentSongPlayingIndex;
                }
                catch(Exception e) {
                    Helpers.getToastHelper(getApplicationContext()).sendToast("Finished Playlist", "LIGHT BLUE");
                    Log.e(LOG_TAG, e.getMessage());
                }
            }
        });


        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return true;
            }
        });

        return true;
    }
*/
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
            //Helpers.getToastHelper(getApplicationContext()).sendToast("No Song Selected!", "RED");
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
        playSongAsync(songListService.skipToNextSong());
        if(notificationPlaybackListener != null){
            notificationPlaybackListener.onSkip();
        }
    }

    // Skips to the previous song in the list
    public void prev() {
        playSongAsync(songListService.goToPrevSong());
        if(notificationPlaybackListener != null){
            notificationPlaybackListener.onSkip();
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
