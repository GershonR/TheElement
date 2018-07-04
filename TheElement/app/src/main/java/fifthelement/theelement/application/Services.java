package fifthelement.theelement.application;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import fifthelement.theelement.business.services.AlbumService;
import fifthelement.theelement.business.services.AuthorService;
import fifthelement.theelement.presentation.services.DrawerService;
import fifthelement.theelement.presentation.services.MusicService;

public class Services {
    private static DrawerService drawerService = null;
    private static MusicService musicService = null;
    private static AuthorService authorService = null;
    private static AlbumService albumService = null;

    public static synchronized DrawerService getDrawerService(AppCompatActivity appCompatActivity) {

        if(drawerService == null || appCompatActivity.hashCode() != drawerService.getApplicationHashCode())
             drawerService = new DrawerService(appCompatActivity);

        return drawerService;
    }

    public static synchronized AuthorService getAuthorService() {

        if( authorService == null ) {
            authorService = new AuthorService();
        }

        return authorService;
    }

    public static synchronized AlbumService getAlbumService() {

        if( albumService == null ) {
            albumService = new AlbumService();
        }

        return albumService;
    }

    public static synchronized MusicService getMusicService() {

        return musicService;
    }

    public static synchronized void setMusicService(MusicService musicServiceToSet) {

        musicService = musicServiceToSet;
    }

}