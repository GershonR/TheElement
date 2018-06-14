package fifthelement.theelement.business.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fifthelement.theelement.application.Services;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.AlbumPersistence;
import fifthelement.theelement.persistence.SongPersistence;


public class AlbumService {

    private AlbumPersistence albumPersistence;

    public AlbumService() {
        albumPersistence = Services.getAlbumPersistence();
    }

    public List<Album> getAlbums() {
        return albumPersistence.getAllAlbums();
    }

    public boolean insertAlbum(Album album) {
        return albumPersistence.storeAlbum(album);
    }

    public boolean updateAlbum(Album album) {
        return albumPersistence.updateAlbum(album);
    }

    public boolean deleteAlbum(Album album) {
        return albumPersistence.deleteAlbum(album.getUUID());
    }

}
