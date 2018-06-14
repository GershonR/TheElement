package fifthelement.theelement.application;

import java.util.logging.Logger;

import fifthelement.theelement.persistence.AlbumPersistence;
import fifthelement.theelement.persistence.AuthorPersistence;
import fifthelement.theelement.persistence.SongPersistence;
import fifthelement.theelement.persistence.stubs.AlbumPersistenceStub;
import fifthelement.theelement.persistence.stubs.AuthorPersistenceStub;
import fifthelement.theelement.persistence.stubs.SongPersistenceStub;

public class Services {
    private static SongPersistence songPersistence = null;
    private static AlbumPersistence albumPersistence = null;
    private static AuthorPersistence authorPersistence = null;

    public static synchronized SongPersistence getSongPersistence() {

        if (songPersistence == null) {
            songPersistence = new SongPersistenceStub();
        }

        return songPersistence;
    }

    public static synchronized AlbumPersistence getAlbumPersistence() {

        if (albumPersistence == null) {
            albumPersistence = new AlbumPersistenceStub();
        }

        return albumPersistence;
    }

    public static synchronized AuthorPersistence getAuthorPersistence() {

        if (authorPersistence == null) {
            authorPersistence = new AuthorPersistenceStub();
        }

        return authorPersistence;
    }

}