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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This MusicService will allow for a MediaPlayer instance to
 * play music in the background of the app and be controlled
 * by various UI elements in the app.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaController.MediaPlayerControl{

    public static final int PLAYBACK_POSITION_REFRESH_INTERVAL_MS = 500;

    private MediaPlayer player;
    private boolean playerPrepared;
    private final IBinder musicBind = new MusicBinder();
    private PlaybackInfoListener mPlaybackInfoListener;
    private ScheduledExecutorService mExecutor;
    private Runnable mSeekbarPositionUpdateTask;

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
            player.prepare();
        } catch(Exception e) {
            //TODO: Handle exceptions
            e.printStackTrace();
        }

        playerPrepared = false;
        initializeProgressCallback();

    }

    public void reset() {
        player.reset();
        stopUpdatingCallbackWithPosition(true);
    }

    @Override
    public void start() {
        if(playerPrepared && !player.isPlaying()) {
            player.start();
            startUpdatingCallbackWithPosition();
        }
    }

    @Override
    public void pause() {
        if(playerPrepared && player.isPlaying()){
            player.pause();
            stopUpdatingCallbackWithPosition(false);
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

    public void setPlaybackInfoListener(PlaybackInfoListener listener) {
        mPlaybackInfoListener = listener;
    }

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

    // Reports media playback position to mPlaybackProgressCallback.
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

    private void updateProgressCallbackTask() {
        if (player != null && player.isPlaying()) {
            int currentPosition = player.getCurrentPosition();
            if (mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onPositionChanged(currentPosition);
            }
        }
    }

    public void initializeProgressCallback() {
        final int duration = player.getDuration();
        if (mPlaybackInfoListener != null) {
            mPlaybackInfoListener.onDurationChanged(duration);
            mPlaybackInfoListener.onPositionChanged(0);
            System.out.println("onDurationChanged duration: " + duration);
        }
    }

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
}
