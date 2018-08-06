package fifthelement.theelement.presentation.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.constants.NotificationConstants;
import fifthelement.theelement.presentation.util.SongUtil;

public class NotificationService extends Service {

    Notification status;
    MusicService musicService;
    RemoteViews views;
    RemoteViews bigViews;
    NotificationManager manager;

    private static final String LOG_TAG = "NotificationService";
    
    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        musicService = Services.getMusicService();
        try {
            musicService.setNotificationPlaybackListener(new NotificationPlaybackStartStopListener());
            if (intent.getAction().equals(NotificationConstants.STARTFOREGROUND_ACTION)) {
                buildNotification();
            } else if (intent.getAction().equals(NotificationConstants.PREV_ACTION)) {
                musicService.prev();
                buildNotification();
            } else if (intent.getAction().equals(NotificationConstants.PLAY_ACTION)) {
                if (musicService.isPlaying()) {
                    showPlay();
                    musicService.pause();
                } else {
                    showPause();
                    musicService.start();
                }
            } else if (intent.getAction().equals(NotificationConstants.NEXT_ACTION)) {
                musicService.skip();
                buildNotification();
            } else if (intent.getAction().equals(NotificationConstants.STOPFOREGROUND_ACTION)) {
                Services.getMusicService().pause();
                stopForeground(true);
                stopSelf();
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return START_STICKY;
    }

    private void buildNotification() {
        musicService = Services.getMusicService();
        // Using RemoteViews to bind custom layouts into Notification
        views = new RemoteViews(getPackageName(),
                R.layout.status_bar);
        bigViews = new RemoteViews(getPackageName(),
                R.layout.status_bar_expanded);

        // showing album image
        views.setImageViewBitmap(R.id.status_bar_album_art, SongUtil.getSongAlbumArt(this, musicService.getCurrentSongPlaying()));
        bigViews.setImageViewBitmap(R.id.status_bar_album_art, SongUtil.getSongAlbumArt(this, musicService.getCurrentSongPlaying()));

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(NotificationConstants.MAIN_ACTION + System.currentTimeMillis());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent previousIntent = new Intent(this, NotificationService.class);
        previousIntent.setAction(NotificationConstants.PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Intent playIntent = new Intent(this, NotificationService.class);
        playIntent.setAction(NotificationConstants.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, NotificationService.class);
        nextIntent.setAction(NotificationConstants.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent closeIntent = new Intent(this, NotificationService.class);
        closeIntent.setAction(NotificationConstants.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, 0);

        views.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);

        views.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);

        views.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);

        views.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);

        views.setImageViewResource(R.id.status_bar_play,
                R.drawable.ic_pause);
        bigViews.setImageViewResource(R.id.status_bar_play,
                R.drawable.ic_pause);

        createNotificationDisplay(views, bigViews);
        showNotification(views, bigViews, pendingIntent);
    }

    private void createNotificationDisplay(RemoteViews views, RemoteViews bigViews) {
        if(musicService.getCurrentSongPlaying() != null) {
            views.setTextViewText(R.id.status_bar_track_name, musicService.getCurrentSongPlaying().getName());
            bigViews.setTextViewText(R.id.status_bar_track_name, musicService.getCurrentSongPlaying().getName());
            views.setImageViewBitmap(R.id.status_bar_album_art, SongUtil.getSongAlbumArt(this, musicService.getCurrentSongPlaying()));
            bigViews.setImageViewBitmap(R.id.status_bar_album_art, SongUtil.getSongAlbumArt(this, musicService.getCurrentSongPlaying()));

            if(musicService.getCurrentSongPlaying().getAuthor() != null) {
                views.setTextViewText(R.id.status_bar_artist_name, musicService.getCurrentSongPlaying().getAuthor().getName());
                bigViews.setTextViewText(R.id.status_bar_artist_name, musicService.getCurrentSongPlaying().getAuthor().getName());
            } else {
                views.setTextViewText(R.id.status_bar_artist_name, "");
                bigViews.setTextViewText(R.id.status_bar_artist_name, "");
            }

            if(musicService.getCurrentSongPlaying().getAlbum() != null)
                bigViews.setTextViewText(R.id.status_bar_album_name, musicService.getCurrentSongPlaying().getAlbum().getName());
            else
                bigViews.setTextViewText(R.id.status_bar_album_name, "");
        }
    }

    private void showNotification(RemoteViews views, RemoteViews bigViews, PendingIntent pendingIntent) {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Make this work on Oreo
            NotificationChannel notificationChannel = new NotificationChannel(NotificationConstants.CHANNEL_ID, NotificationConstants.CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            manager.createNotificationChannel(notificationChannel);
            status = new Notification.Builder(this, NotificationConstants.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_theelement)
                    .setContentTitle(getString(R.string.app_name)).build();
        } else {
            status = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher_theelement).build();
        }
        status.contentView = views;
        status.bigContentView = bigViews;
        status.flags = Notification.FLAG_ONGOING_EVENT;
        status.contentIntent = pendingIntent;
        try {
            startForeground(NotificationConstants.NOTIFICATION_ID, status);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }


    public void showPause() {
        if(status != null && manager != null) {
            bigViews.setImageViewResource(R.id.status_bar_play, R.drawable.ic_pause);
            views.setImageViewResource(R.id.status_bar_play, R.drawable.ic_pause);
            manager.notify(NotificationConstants.NOTIFICATION_ID, status);
        }
    }

    public void showPlay() {
        if(status != null && manager != null) {
            bigViews.setImageViewResource(R.id.status_bar_play, R.drawable.ic_play);
            views.setImageViewResource(R.id.status_bar_play, R.drawable.ic_play);
            manager.notify(NotificationConstants.NOTIFICATION_ID, status);
        }
    }

    public class NotificationPlaybackStartStopListener {
        public void onPlaybackStart(){
            showPause();
        }

        public void onPlaybackStop() {
            showPlay();
        }

        public void onSkip() {
            if(status != null && manager != null) {
                try {
                    SkipTask asyncTask = new SkipTask();
                    asyncTask.execute();
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }
        }
    }

    public class SkipTask extends AsyncTask<Object, Object, Object> {

        @Override
        protected Object doInBackground(Object... params) {
            if(status != null && manager != null) {
                buildNotification();
            }
            return null;

        }
    }
}
