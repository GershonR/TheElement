package fifthelement.theelement.business;

import java.util.List;

import fifthelement.theelement.application.Services;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.AlbumPersistence;
import fifthelement.theelement.persistence.AuthorPersistence;
import fifthelement.theelement.persistence.SongPersistence;

public class Delete {
    private SongPersistence songPersistence;
    private AlbumPersistence albumPersistence;
    private AuthorPersistence authorPersistence;
    private PlayListPersistence playListPersistence;

    public Delete() {
        this.songPersistence = Services.getSongPersistence();
        this.albumPersistence = Services.getAlbumPersistence();
        this.authorPersistence = Services.getAuthoerPersistence();
        this.playListPersistence = Services.getServicePersistence();
    }

    public void deleteSong(Song songToRemove) {

        Song song = songPersistence.getSongByID(songToRemove.getId());
        if( song == null ) {
            throw new IllegalArgumentException("Cannot delete unknown song");
        }

        // check if there are still songs in the album/s
        for( Album a : song.getAlbums() ) {
            a.deleteSong(song);
            checkAlbumValidity(a);
        }

        // check if there are still songs in the author/s
        for( Author a : song.getAuthors() ) {
            a.deleteSong(song);
            checkAuthorValidity(a);
        }

        songPersistence.deleteSong(song);
    }

    // can't delete when there are songs contained in it. so it is a private method
    private void checkAlbumValidity(Album albumToRemove) {

        Album album = albumPersistence.getAlbumByID(albumToRemove.getId());
        if( album == null ) {
            throw new IllegalArgumentException("Unknown album");
        }

        if( album.getSongs().size() == 0 ) {
            albumPersistence.deleteAlbum(album);
        }
    }

    private void checkAuthorValidity(Author authorToRemove) {

        Author author = authorPersistence.getAuthorByID(authorToRemove.getId());
        if( author == null ) {
            throw new IllegalArgumentException("Unknown author");
        }

        if( author.getSongs().size() == 0 ) {
            authorPersistence.deleteAuthor(author);
        }
    }

    public void deleteAuthorAndSongs(Author authorToRemove) {

        Author author = authorPersistence.getAuthorByID(authorToRemove.getId());
        if( author == null ) {
            throw new IllegalArgumentException("Cannot delete null author");
        }

        // should delete the author and albums in the end
        // redundant and slow. might need some optimizing
        for( Song s : author.getSongs() ) {
            this.deleteSong(s);
        }

    }

    public void deleteAlbumAndSongs(Album albumToRemove) {

        Album album = albumPersistence.getAlbumByID(albumToRemove.getId());
        if( album == null ) {
            throw new IllegalArgumentException("Unknown album");
        }

        for( Song s : album.getSongs() ) {
            this.deleteSong(s);
        }
    }
}

