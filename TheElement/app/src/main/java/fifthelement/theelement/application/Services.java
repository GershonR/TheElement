package fifthelement.theelement.application;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.logging.Logger;
import fifthelement.theelement.objects.PlayList;
import fifthelement.theelement.persistence.AlbumPersistence;
import fifthelement.theelement.persistence.AuthorPersistence;
import fifthelement.theelement.persistence.PlayListPersistence;
import fifthelement.theelement.persistence.SongPersistence;
import fifthelement.theelement.persistence.hsqldb.AlbumPersistenceHSQLDB;
import fifthelement.theelement.persistence.hsqldb.AuthorPersistenceHSQLDB;
import fifthelement.theelement.persistence.hsqldb.SongPersistenceHSQLDB;
import fifthelement.theelement.persistence.stubs.AlbumPersistenceStub;
import fifthelement.theelement.persistence.stubs.AuthorPersistenceStub;
import fifthelement.theelement.persistence.stubs.PlayListPersistenceStub;
import fifthelement.theelement.persistence.stubs.SongPersistenceStub;
import fifthelement.theelement.presentation.services.DrawerService;
import fifthelement.theelement.presentation.services.FragmentService;
import fifthelement.theelement.presentation.services.MusicService;
import fifthelement.theelement.presentation.services.ToastService;

public class Services {
    private static SongPersistence songPersistence = null;
    private static AlbumPersistence albumPersistence = null;
    private static AuthorPersistence authorPersistence = null;
    private static PlayListPersistence playListPersistence = null;

    private static ToastService toastService = null;
    private static DrawerService drawerService = null;
    private static FragmentService fragmentService = null;
    private static MusicService musicService = null;

    public static synchronized SongPersistence getSongPersistence() {

        if (songPersistence == null) {
            songPersistence = new SongPersistenceHSQLDB(Main.getDBPathName());
        }

        return songPersistence;
    }

    public static synchronized AlbumPersistence getAlbumPersistence() {

        if (albumPersistence == null) {
            albumPersistence = new AlbumPersistenceHSQLDB(Main.getDBPathName());
        }

        return albumPersistence;
    }

    public static synchronized AuthorPersistence getAuthorPersistence() {

        if (authorPersistence == null) {
            authorPersistence = new AuthorPersistenceHSQLDB(Main.getDBPathName());
        }

        return authorPersistence;
    }

    public static synchronized ToastService getToastService(Context context) {

        if (toastService == null) {
            toastService = new ToastService(context);
        }

        return toastService;
    }

    public static synchronized DrawerService getDrawerService(AppCompatActivity appCompatActivity) {

        if(drawerService == null || appCompatActivity.hashCode() != drawerService.getApplicationHashCode())
             drawerService = new DrawerService(appCompatActivity);

        return drawerService;
    }

    public static synchronized FragmentService getFragmentService(AppCompatActivity appCompatActivity) {

        if(fragmentService == null || appCompatActivity.hashCode() != fragmentService.getApplicationHashCode())
                fragmentService = new FragmentService(appCompatActivity);

        return fragmentService;
    }



    public static synchronized PlayListPersistence getPlayListPersistence() {

        if( playListPersistence == null ) {
            playListPersistence = new PlayListPersistenceStub();
        }

        return playListPersistence;
    }

    public static synchronized MusicService getMusicService() {

        return musicService;
    }

    public static synchronized void setMusicService(MusicService musicServiceToSet) {

        musicService = musicServiceToSet;
    }

}