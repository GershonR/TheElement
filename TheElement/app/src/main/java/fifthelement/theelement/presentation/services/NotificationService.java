package fifthelement.theelement.presentation.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
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

public class NotificationService extends Service {

    Notification status;
    MusicService musicService;
    private final String LOG_TAG = "NotificationService";

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
        if (intent.getAction().equals(NotificationConstants.ACTION.STARTFOREGROUND_ACTION)) {
            showNotification();
        } else if (intent.getAction().equals(NotificationConstants.ACTION.PREV_ACTION)) {
            Toast.makeText(this, "Clicked Previous", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Previous");
        } else if (intent.getAction().equals(NotificationConstants.ACTION.PLAY_ACTION)) {
            if (musicService.isPlaying()) {
                showPlay();
                musicService.pause();
            } else {
                showPause();
                musicService.start();
            }
        } else if (intent.getAction().equals(NotificationConstants.ACTION.NEXT_ACTION)) {
            Toast.makeText(this, "Clicked Next", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Next");
        } else if (intent.getAction().equals(NotificationConstants.ACTION.STOPFOREGROUND_ACTION)) {
            Services.getMusicService().pause();
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    private void showNotification() {
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
                NotificationConstants.getDefaultAlbumArt(this));

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(NotificationConstants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent previousIntent = new Intent(this, NotificationService.class);
        previousIntent.setAction(NotificationConstants.ACTION.PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Intent playIntent = new Intent(this, NotificationService.class);
        playIntent.setAction(NotificationConstants.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, NotificationService.class);
        nextIntent.setAction(NotificationConstants.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent closeIntent = new Intent(this, NotificationService.class);
        closeIntent.setAction(NotificationConstants.ACTION.STOPFOREGROUND_ACTION);
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

        if(musicService.getCurrentSongPlaying() != null) {
            views.setTextViewText(R.id.status_bar_track_name, musicService.getCurrentSongPlaying().getName());
            bigViews.setTextViewText(R.id.status_bar_track_name, musicService.getCurrentSongPlaying().getName());

            if(musicService.getCurrentSongPlaying().getAuthors().size() > 1) {
                views.setTextViewText(R.id.status_bar_artist_name, musicService.getCurrentSongPlaying().getAuthors().get(0).getName());
                bigViews.setTextViewText(R.id.status_bar_artist_name, musicService.getCurrentSongPlaying().getAuthors().get(0).getName());
            }

            if(musicService.getCurrentSongPlaying().getAlbums().size() > 1)
                bigViews.setTextViewText(R.id.status_bar_album_name, musicService.getCurrentSongPlaying().getAlbums().get(0).getName());
        }
        status = new Notification.Builder(this).build();
        status.contentView = views;
        status.bigContentView = bigViews;
        status.flags = Notification.FLAG_ONGOING_EVENT;
        status.icon = R.drawable.ic_launcher_theelement;
        status.contentIntent = pendingIntent;
        startForeground(NotificationConstants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
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
        startForeground(NotificationConstants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
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
        startForeground(NotificationConstants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
    }

}
