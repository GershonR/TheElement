package fifthelement.theelement.business;

import java.util.ArrayList;
import java.util.List;

import fifthelement.theelement.application.Services;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.AlbumPersistence;
import fifthelement.theelement.persistence.AuthorPersistence;
import fifthelement.theelement.persistence.PlayListPersistence;
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

        Song song = songPersistence.getSongById(songToRemove.getId());
        if( song == null ) {
            throw new IllegalArgumentException("Cannot delete unknown song");
        }

        List<Album> albumList = song.getAlbums();
        List<Author> authorList = song.getAuthors();

        for( Album a : song.getAlbums() ) {
            a.deleteSong(song);
        }

        for( Author a : song.getAuthors() ) {
            a.deleteSong(song);
        }

        songPersistence.deleteSong(song);
        validateAlbumList(song.getAlbums());
        validateAuthorList(song.getAuthors());
    }

    private void validateAlbumList(List<Album> albumList) {
        for( Album a : albumList ) {
            if( a.getSongs().size() == 0 ) {
                albumPersistence.deleteAlbum(a);
            }
        }
    }

    private void validateAuthorList(List<Author> authorList) {
        for( Author a : authorList ) {
            if( a.getSongs().size() == 0 ) {
                authorPersistence.deleteAuthor(a);
            }
        }
    }

    public void deleteAuthorAndSongs(Author authorToRemove) {

        Author author = authorPersistence.getAuthorById(authorToRemove.getId());
        if( author == null ) {
            throw new IllegalArgumentException("Cannot delete null author");
        }

        List<Album> albumList = new ArrayList<>();
        List<Author> authorList = new ArrayList<>();

        for( Song s : author.getSongs() ) {
            for( Album a : s.getAlbums() ) {
                a.deleteSong(s);
                albumList.add(a);
            }
            for( Author a : s.getAuthors() ) {
                a.deleteSong(s);
                authorList.add(a);
            }
            songPersistence.deleteSong(s);
        }

        validateAlbumList(albumList);
        validateAuthorList(authorList);
    }

    public void deleteAlbumAndSongs(Album albumToRemove) {

        Album album = albumPersistence.getAlbumById(albumToRemove.getId());
        if( album == null ) {
            throw new IllegalArgumentException("Unknown album");
        }

        List<Album> albumList = new ArrayList<>();
        List<Author> authorList = new ArrayList<>();

        for( Song s : album.getSongs() ) {
            for( Album a : s.getAlbums() ) {
                a.deleteSong(s);
                albumList.add(a);
            }
            for( Author a : s.getAuthors() ) {
                a.deleteSong(s);
                authorList.add(a);
            }
            songPersistence.deleteSong(s);
        }

        validateAlbumList(albumList);
        validateAuthorList(authorList);
    }
}

