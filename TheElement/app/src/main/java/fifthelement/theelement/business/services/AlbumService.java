package fifthelement.theelement.business.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.persistence.AlbumPersistence;
import fifthelement.theelement.persistence.SongPersistence;


public class AlbumService {

    private AlbumPersistence albumPersistence;
    private SongPersistence songPersistence;

    public AlbumService() {
        albumPersistence = Persistence.getAlbumPersistence();
        songPersistence = Persistence.getSongPersistence();
    }

    public AlbumService(AlbumPersistence albumPersistence, SongPersistence songPersistence) {
        this.albumPersistence = albumPersistence;
        this.songPersistence = songPersistence;
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
        if(album == null)
            throw new IllegalArgumentException();
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
        if( albumList != null ) {
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
                    return Integer.compare(album.getNumPlayed(), t1.getNumPlayed());
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
