package fifthelement.theelement.presentation.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.Toast;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.constants.NotificationConstants;
import fifthelement.theelement.presentation.util.SongUtil;

public class NotificationService extends Service {

    Notification status;
    MusicService musicService;

    private static final String LOG_TAG = "NotificationService";

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        musicService = Services.getMusicService();
        if (intent.getAction().equals(NotificationConstants.STARTFOREGROUND_ACTION)) {
            buildNotification();
        } else if (intent.getAction().equals(NotificationConstants.PREV_ACTION)) {
            Toast.makeText(this, "Clicked Previous", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Previous");
        } else if (intent.getAction().equals(NotificationConstants.PLAY_ACTION)) {
            if (musicService.isPlaying()) {
                showPlay();
                musicService.pause();
            } else {
                showPause();
                musicService.start();
            }
        } else if (intent.getAction().equals(NotificationConstants.NEXT_ACTION)) {
            Toast.makeText(this, "Clicked Next", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Next");
        } else if (intent.getAction().equals(NotificationConstants.STOPFOREGROUND_ACTION)) {
            Services.getMusicService().pause();
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    private void buildNotification() {
        musicService = Services.getMusicService();
        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews views = new RemoteViews(getPackageName(),
                R.layout.status_bar);
        RemoteViews bigViews = new RemoteViews(getPackageName(),
                R.layout.status_bar_expanded);

        // showing default album image
        views.setViewVisibility(R.id.status_bar_icon, View.VISIBLE);
        views.setViewVisibility(R.id.status_bar_album_art, View.GONE);
        bigViews.setImageViewBitmap(R.id.status_bar_album_art,
                SongUtil.getDefaultAlbumArt(this));

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

            if(musicService.getCurrentSongPlaying().getAuthor() != null) {
                views.setTextViewText(R.id.status_bar_artist_name, musicService.getCurrentSongPlaying().getAuthor().getName());
                bigViews.setTextViewText(R.id.status_bar_artist_name, musicService.getCurrentSongPlaying().getAuthor().getName());
            }

            if(musicService.getCurrentSongPlaying().getAlbum() != null)
                bigViews.setTextViewText(R.id.status_bar_album_name, musicService.getCurrentSongPlaying().getAlbum().getName());
        }
    }

    private void showNotification(RemoteViews views, RemoteViews bigViews, PendingIntent pendingIntent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Make this work on Oreo
            NotificationChannel notificationChannel = new NotificationChannel(NotificationConstants.CHANNEL_ID, NotificationConstants.CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
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
        startForeground(NotificationConstants.NOTIFICATION_ID, status);
    }


    public void showPause() {
        RemoteViews views = new RemoteViews(getPackageName(),
                R.layout.status_bar);
        RemoteViews bigViews = new RemoteViews(getPackageName(),
                R.layout.status_bar_expanded);

        views.setImageViewResource(R.id.status_bar_play,
                R.drawable.ic_pause);
        bigViews.setImageViewResource(R.id.status_bar_play,
                R.drawable.ic_pause);
        status.contentView = views;
        status.bigContentView = bigViews;
        startForeground(NotificationConstants.NOTIFICATION_ID, status);
    }

    public void showPlay() {
        RemoteViews views = new RemoteViews(getPackageName(),
                R.layout.status_bar);
        RemoteViews bigViews = new RemoteViews(getPackageName(),
                R.layout.status_bar_expanded);

        views.setImageViewResource(R.id.status_bar_play,
                R.drawable.ic_play);
        bigViews.setImageViewResource(R.id.status_bar_play,
                R.drawable.ic_play);
        status.contentView = views;
        status.bigContentView = bigViews;
        startForeground(NotificationConstants.NOTIFICATION_ID, status);
    }

}
