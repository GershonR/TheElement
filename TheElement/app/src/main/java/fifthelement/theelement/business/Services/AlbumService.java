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


// TODO: Our MainActivity Initializes This - Fragments Will Call
//       these Methods!
// TODO: TESTS!
public class AlbumService {

    private AlbumPersistence albumPersistence;

    public AlbumService() {
        albumPersistence = Services.getAlbumPersistence();
    }

    public List<Album> getAlbums() {
        return albumPersistence.getAllAlbums();
    }

    // TODO: insertSong Try-Catch
    public boolean insertAlbum(Album album) {
        return albumPersistence.storeAlbum(album);
    }

    // TODO: updateSong Try-Catch
    public boolean updateAlbum(Album album) {
        return albumPersistence.updateAlbum(album);
    }

    // TODO: deleteSong Try-Catch
    public boolean deleteAlbum(Album album) {
        return albumPersistence.deleteAlbum(album.getUUID());
    }

}
