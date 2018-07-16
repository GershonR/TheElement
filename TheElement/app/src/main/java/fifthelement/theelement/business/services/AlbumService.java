package fifthelement.theelement.business.services;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.persistence.AlbumPersistence;
import fifthelement.theelement.persistence.AuthorPersistence;
import fifthelement.theelement.persistence.SongPersistence;


public class AlbumService {

    private AlbumPersistence albumPersistence;
    private SongPersistence songPersistence;
    private AuthorPersistence authorPersistence;

    public AlbumService() {
        albumPersistence = Persistence.getAlbumPersistence();
        songPersistence = Persistence.getSongPersistence();
        authorPersistence = Persistence.getAuthorPersistence();
    }

    public AlbumService(AlbumPersistence albumPersistence, SongPersistence songPersistence, AuthorPersistence authorPersistence) {
        this.albumPersistence = albumPersistence;
        this.songPersistence = songPersistence;
        this.authorPersistence = authorPersistence;
    }

    public Album getAlbumByUUID(UUID uuid) {
        return albumPersistence.getAlbumByUUID(uuid);
    }

    public List<Album> getAlbums() {
        List<Album> albums = albumPersistence.getAllAlbums();
        for(Album album : albums) {
            if(album.getSongs() == null) {
                album.setSongs(songPersistence.getSongsByAlbumUUID(album.getUUID()));
            }
        }
        return albumPersistence.getAllAlbums();
    }

    public boolean insertAlbum(Album album) throws ArrayStoreException, IllegalArgumentException {
        if(album == null || getAlbumByUUID(album.getUUID()) != null)
            throw new IllegalArgumentException();

        if(album.getAuthor() != null) {
            Author author = album.getAuthor();
            if(authorPersistence.getAuthorByUUID(author.getUUID()) == null) {
                authorPersistence.storeAuthor(author);
            }
        }

        return albumPersistence.storeAlbum(album);
    }

    public boolean updateAlbum(Album album) throws IllegalArgumentException {
        if(album == null)
            throw new IllegalArgumentException();
        return albumPersistence.updateAlbum(album);
    }

    public boolean deleteAlbum(Album album) throws IllegalArgumentException {
        if(album == null)
            throw new IllegalArgumentException();
        return albumPersistence.deleteAlbum(album.getUUID());
    }

}
