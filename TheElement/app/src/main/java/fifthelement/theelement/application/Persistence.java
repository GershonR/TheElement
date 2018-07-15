package fifthelement.theelement.application;

import fifthelement.theelement.persistence.AlbumPersistence;
import fifthelement.theelement.persistence.AuthorPersistence;
import fifthelement.theelement.persistence.PlaylistPersistence;
import fifthelement.theelement.persistence.SongPersistence;
import fifthelement.theelement.persistence.hsqldb.AlbumPersistenceHSQLDB;
import fifthelement.theelement.persistence.hsqldb.AuthorPersistenceHSQLDB;
import fifthelement.theelement.persistence.hsqldb.PlaylistPersistenceHSQLDB;
import fifthelement.theelement.persistence.hsqldb.SongPersistenceHSQLDB;
import fifthelement.theelement.persistence.stubs.PlaylistPersistenceStub;

public class Persistence {
    private static SongPersistence songPersistence = null;
    private static AlbumPersistence albumPersistence = null;
    private static AuthorPersistence authorPersistence = null;
    private static PlaylistPersistence playlistPersistence = null;

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

    public static synchronized PlaylistPersistence getPlaylistPersistence() {

        if( playlistPersistence == null ) {
            playlistPersistence = new PlaylistPersistenceHSQLDB(Main.getDBPathName());
        }

        return playlistPersistence;
    }

    public static synchronized void resetPersistence() {
        songPersistence = null;
        albumPersistence = null;
        authorPersistence = null;
        playlistPersistence = null;
    }
}
