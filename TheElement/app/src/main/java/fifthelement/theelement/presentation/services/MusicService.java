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

/**
 * This MusicService will allow for a MediaPlayer instance to
 * play music in the background of the app and be controlled
 * by various UI elements in the app.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    public static final int PLAYBACK_POSITION_REFRESH_INTERVAL_MS = 500;

    private MediaPlayer player;
    private boolean playerPrepared;
    private final IBinder musicBind = new MusicBinder();
    private PlaybackInfoListener mPlaybackInfoListener;
    private ScheduledExecutorService mExecutor;
    private Runnable mSeekbarPositionUpdateTask;

    /**
     * onBind
     *  This function is called when the service is bound, it will return a MusicBinder instance
     */
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    /**
     * onUnbind
     *  This function is called when the service is unbound, it will clean up the MediaPlayer
     */
    @Override
    public boolean onUnbind(Intent intent) {
        player.stop();
        player.release();
        return false;
    }

    /**
     * onCreate
     *  This function is called when the service is created, it will initialize the MediaPlayer
     */
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        initMusicPlayer();
    }

    /**
     * onCompletion
     *  This function acts as a callback that occurs when the private MediaPlayer instance completes
     *  playback of a music file.
     * @param mediaPlayer - MediaPlayer instance that triggered the callback
     */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    /**
     * onError
     *  This function acts as a callback that occurs when the private MediaPlayer instance runs into
     *  an error.
     * @param mediaPlayer - MediaPlayer instance that triggered the callback
     * @param what - Type of error that has occured
     * @param extra - Extra code to specify the error further
     * @return boolean - Should be true if the error was handled, false if not
     */
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        return false;
    }

    /**
     * onPrepared
     *  This function acts as a callback that occurs when the private MediaPlayer instance has fully
     *  prepared.
     * @param mediaPlayer - MediaPlayer instance that triggered the callback
     */
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        //Code to run when media player is prepared to play
        playerPrepared = true;
    }

    /**
     * initMusicPlayer
     *  This function will initialize the private MediaPlayer member to play music in the background
     *  and sets up the 3 listener methods above.
     */
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

    /**
     *  playSongAsynch:
     *  This function will attempt to set the media player up asynchronously and play the media.
     * @param songPath - File path of the music file to play
     * @return boolean - Indicator if the music file path was set successfully
     */
    public boolean playSongAsynch(String songPath) {
        reset();
        Uri uri = Uri.parse(songPath);

        try {
            player.setDataSource(getApplication(), uri);
            player.prepareAsync();
        } catch(Exception e) {
            Toast toast = Toast.makeText(this, "Invalid Song!", Toast.LENGTH_LONG);
            View view = toast.getView();
            view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            toast.show();
            e.printStackTrace();
            return false;
        }
        playerPrepared = false;

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer player) {
                playerPrepared = true;
                initializeProgressCallback();
                start();
            }

        });
        return true;
    }

    /**
     * reset
     *  This function will reset the MediaPlayer instance and reset seekbar UI positions to start.
     */
    public void reset() {
        player.reset();
        stopUpdatingCallbackWithPosition(true);
    }

    /**
     * start
     *  This function will start the private MediaPlayer instance (equivalent to 'Play').
     */
    public void start() {
        if(playerPrepared && !player.isPlaying()) {
            player.start();
            startUpdatingCallbackWithPosition();
        }
    }

    /**
     * pause
     *  This function pauses the playback of the private MediaPlayer instance.
     */
    public void pause() {
        if(playerPrepared && player.isPlaying()){
            player.pause();
            stopUpdatingCallbackWithPosition(false);
        }
    }

    /**
     * getDuration
     *  This function will return the duration of the currently loaded music file.
     * @return int - length of the loaded music file in milliseconds
     */
    public int getDuration() {
        if(playerPrepared) {
            return player.getDuration();
        }
        else {
            return 0;
        }
    }

    /**
     * getCurrentPosition
     *  This function will return the current position of playback in ms.
     * @return int - current position of playback in ms
     */
    public int getCurrentPosition() {
        if(playerPrepared) {
            return player.getCurrentPosition();
        }
        else {
            return 0;
        }
    }

    /**
     * seekTo
     *  This function will have the MediaPlayer seek to a position of playback in ms.
     * @param pos - millisecond position to seek to
     */
    public void seekTo(int pos) {
        if(playerPrepared) {
            player.seekTo(pos);
        }
    }

    /**
     * isPlaying
     *  This function will return a boolean indicating if playback is currently going on.
     * @return boolean - indicator if playback is currently going
     */
    public boolean isPlaying() {
        return player.isPlaying();
    }


    /**
     * setPlaybackInfoListener
     *  This function will set a PlaybackInfoListener for this service, which is used by seekbars in
     *  the UI to update the current position of playback.
     * @param listener - PlaybackInfoListener to use during playback
     */
    public void setPlaybackInfoListener(PlaybackInfoListener listener) {
        mPlaybackInfoListener = listener;
    }

    /**
     * startUpdatingCallbackWithPosition
     *  This function will spawn a scheduled executor to call a UI update function at a set interval.
     */
    private void startUpdatingCallbackWithPosition() {
        if (mExecutor == null) {
            mExecutor = Executors.newSingleThreadScheduledExecutor();
        }
        if (mSeekbarPositionUpdateTask == null) {
            mSeekbarPositionUpdateTask = new Runnable() {
                @Override
                public void run() {
                    updateProgressCallbackTask();
                }
            };
        }
        mExecutor.scheduleAtFixedRate(
                mSeekbarPositionUpdateTask,
                0,
                PLAYBACK_POSITION_REFRESH_INTERVAL_MS,
                TimeUnit.MILLISECONDS
        );
    }


    /**
     * stopUpdatingCallbackWithPosition
     *  This function will stop the scheduled executor from calling UI update function and uses a
     *  boolean parameter to determine if seekbar UI elements reporting playback must reset to 0.
     * @param resetUIPlaybackPosition - indicator of if seekbars should reset to position 0
     */
    private void stopUpdatingCallbackWithPosition(boolean resetUIPlaybackPosition) {
        if (mExecutor != null) {
            mExecutor.shutdownNow();
            mExecutor = null;
            mSeekbarPositionUpdateTask = null;
            if (resetUIPlaybackPosition && mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onPositionChanged(0);
            }
        }
    }

    /**
     * updateProgressCallbackTask
     *  This function will call the onPositionChanged function of the PlaybackInfoListener with the
     *  current position of playback, which will update the seekbars that are using the listener.
     */
    private void updateProgressCallbackTask() {
        if (player != null && player.isPlaying()) {
            int currentPosition = player.getCurrentPosition();
            if (mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onPositionChanged(currentPosition);
            }
        }
    }

    /**
     * initializeProgressCallback
     *  This function will initialize seekbars using the PlaybackInfoListener with current playback
     *  data.
     */
    public void initializeProgressCallback() {
        final int duration = player.getDuration();
        if (mPlaybackInfoListener != null) {
            mPlaybackInfoListener.onDurationChanged(duration);
            mPlaybackInfoListener.onPositionChanged(0);
            System.out.println("onDurationChanged duration: " + duration);
        }
    }

    //Public helper class for binding this service to an activity
    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
