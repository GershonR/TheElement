package fifthelement.theelement.business.services;

import java.util.Collections;
import java.util.Comparator;
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

    public Album getMostPlayedAlbum() {
        Album album = null;
        List<Album> albumList = getSortedAlbumListByMostPlayed();
        if( albumList != null && albumList.size() > 0 ) {
            album = albumList.get(0);
        }
        return album;
    }

    public List<Album> getSortedAlbumListByMostPlayed() {
        List<Album> albumList = this.getAlbums();
        if( albumList != null ) {
            Collections.sort(albumList, new Comparator<Album>() {
                @Override
                public int compare(Album album, Album t1) {
                    return Integer.compare(Integer.valueOf(t1.getNumPlayed()), Integer.valueOf(album.getNumPlayed()));
                }
            });
        }
        return albumList;
    }

    public int getTotalAlbumPlays() {
        List<Album> albumList = this.getAlbums();
        int totalPlays = 0;
        for( Album album : albumList ) {
            totalPlays += album.getNumPlayed();
        }
        return totalPlays;
    }


}
