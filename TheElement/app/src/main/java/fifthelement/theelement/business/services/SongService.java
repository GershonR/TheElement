package fifthelement.theelement.business.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.exceptions.SongAlreadyExistsException;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.AlbumPersistence;
import fifthelement.theelement.persistence.AuthorPersistence;
import fifthelement.theelement.persistence.PlaylistPersistence;
import fifthelement.theelement.persistence.SongPersistence;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;

public class SongService {

    private SongPersistence songPersistence;
    private AlbumPersistence albumPersistence;
    private AuthorPersistence authorPersistence;
    private PlaylistPersistence playlistPersistence;
    private AuthorService authorService;
    private AlbumService albumService;

    public SongService() {
        songPersistence = Persistence.getSongPersistence();
        albumPersistence = Persistence.getAlbumPersistence();
        authorPersistence = Persistence.getAuthorPersistence();
        playlistPersistence = Persistence.getPlaylistPersistence();
        authorService = Services.getAuthorService();
        albumService = Services.getAlbumService();
    }

    public SongService(SongPersistence songPersistence, AlbumPersistence albumPersistence, AuthorPersistence authorPersistence, PlaylistPersistence playlistPersistence) {
        this.songPersistence = songPersistence;
        this.albumPersistence = albumPersistence;
        this.authorPersistence = authorPersistence;
        this.playlistPersistence = playlistPersistence;
        this.albumService = new AlbumService(albumPersistence, songPersistence);
        this.authorService = new AuthorService(authorPersistence);

    }

    public Song getSongByUUID(UUID uuid) {
        return songPersistence.getSongByUUID(uuid);
    }

    public List<Song> getSongs() throws PersistenceException {
        List<Song> songs = songPersistence.getAllSongs();

        if(songs != null) {
            for(Song song : songs) {
                if(song.getAuthor() != null)
                    song.setAuthor(authorPersistence.getAuthorByUUID(song.getAuthor().getUUID()));
                if(song.getAlbum() != null) {
                    song.setAlbum(albumPersistence.getAlbumByUUID(song.getAlbum().getUUID()));
                }
            }
        }

        return songs;
    }

    public void createSong(String realPath, String songName, String songArtist, String songAlbum, String songGenre) throws PersistenceException, IllegalArgumentException, SongAlreadyExistsException {
        if(songName == null || realPath == null)
            throw new IllegalArgumentException();

        Author author = null;
        Album album = null;
        Song song = new Song(songName, realPath);
        if(songArtist != null) {
            author = new Author(songArtist);
            song.setAuthor(author);
            authorService.insertAuthor(author);
        }

        if(songAlbum != null) {
            album = new Album(songAlbum);
            if(author != null)
                album.setAuthor(author);
            else
                album.setAuthor(null);
            song.setAlbum(album);
            albumService.insertAlbum(album);
        }

        if(songGenre != null)
            song.setGenre(songGenre);
        insertSong(song);
    }

    public boolean insertSong(Song song) throws PersistenceException, IllegalArgumentException, SongAlreadyExistsException {
        if(song == null)
            throw new IllegalArgumentException();
        if(pathExists(song.getPath()))
            throw new SongAlreadyExistsException(song.getPath());
        return songPersistence.storeSong(song);
    }

    public boolean updateSong(Song song) throws PersistenceException, IllegalArgumentException {
        if(song == null)
            throw new IllegalArgumentException();

        return songPersistence.updateSong(song);
    }

    public boolean updateSongWithParameters(Song song, String songName, String author, String album, String genre) {

        if(!songName.equals("")){
            song.setName(songName);
        }

        Author newAuthor = new Author(author);
        if(!author.equals("")) { // TODO: Seperate Method For This?
            song.setAuthor(newAuthor);
            authorService.insertAuthor(newAuthor);
        }else {
            song.setAuthor(null);
        }

        if(!album.equals("")) { // TODO: Seperate Method For This?
            Album newAlbum = new Album(album);
            if(!author.equals(""))
                newAlbum.setAuthor(newAuthor);
            else
                newAlbum.setAuthor(null);
            song.setAlbum(newAlbum);
            albumService.insertAlbum(newAlbum);
        } else {
            song.setAlbum(null);
        }

        if(genre.equals("")) {
            song.setGenre(null);
        } else {
            song.setGenre(genre);
        }
        return updateSong(song);
    }

    public boolean updateSongWithRating(Song song, double rating){
        song.setRating(rating);
        return updateSong(song);
    }


    public boolean deleteSong(Song songToRemove) throws PersistenceException, IllegalArgumentException {
        if(songToRemove == null)
            throw new IllegalArgumentException();
        Song song = songPersistence.getSongByUUID(songToRemove.getUUID());

        if( song != null ) {

            // deletes songs from existing PlayList if it's there
            // implementation for this hasn't been fully decided. this is a STUB
            //for( PlayList p : playlistPersistence.getAllPlayLists() ) {
            //    if( p.contains(song) ) {
            //        p.removeSong(song);
            //        playlistPersistence.updatePlayList(p);
            //    }
            //}

            songPersistence.deleteSong(song);
            return true;
        }
        return false;
    }

    // Method checks if any song already has the same path
    // and returns true if a songs exists with the same path
    public boolean pathExists(String path) {
        List<Song> songs = getSongs();
        boolean toReturn = false;

        for(Song song : songs) {
            if(song.getPath().equals(path)) {
                toReturn = true;
                break;
            }
        }
        return toReturn;
    }

    public List<Song> search(String query) {
        List<Song> allSongs = getSongs();
        ArrayList<Song> matchesList = new ArrayList<>();
        Matcher matcher;

        if (validateRegex(query)){
            String regex = query;
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

            for (Song s: allSongs) {
                matcher = pattern.matcher(s.getName());
                if ( matcher.find()){
                    matchesList.add(s);
                }
            }
        }
        return matchesList;
    }

    // Making sure the regex pattern doesn't contain special
    // characters, which screws up regex compiling
    private boolean validateRegex(String regexPattern){
        boolean result = false;
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher matcher = special.matcher(regexPattern);
        if (matcher.find() == false)
            result = true;
        return result;
    }

    public Song getMostPlayedSong() {
        List<Song> songList = getSortedSongListByMostPlayed();
        Song song = null;
        if( songList != null && songList.size() > 0 ) {
            song = songList.get(0);
        }
        return song;
    }

    public List<Song> getSortedSongListByMostPlayed() {
        List<Song> songList = this.getSongs();
        if( songList != null ) {
            Collections.sort(songList, new Comparator<Song>() {
                @Override
                public int compare(Song song, Song t1) {
                    return Integer.compare(t1.getNumPlayed(), song.getNumPlayed());
                }
            });
        }
        return  songList;
    }

    public int getTotalSongPlays() {
        List<Song> songList = this.getSongs();
        int totalPlays = 0;
        for( Song song : songList ) {
            totalPlays += song.getNumPlayed();
        }
        return totalPlays;
    }

    // called every time a song is skipped.
    public void songIsPlayed(UUID songId) {
        Song song = this.getSongByUUID(songId);
        Author author = null;
        Album album = null;
        if( song != null ) {
            song.incrNumPlayed();
            if( song.getAuthor() != null ) {
                author = Services.getAuthorService().getAuthorByUUID(song.getAuthor().getUUID());
                author.incrNumPlayed();
                Services.getAuthorService().updateAuthor(author);
            }
            if( song.getAlbum() != null ) {
                album = Services.getAlbumService().getAlbumByUUID(song.getAlbum().getUUID());
                album.incrNumPlayed();
                Services.getAlbumService().updateAlbum(album);
            }
        }
        this.updateSong(song);
    }

    // if the song is skipped then we shouldn't count it as a played
    public void songIsSkipped(UUID songId) {
        Song song = this.getSongByUUID(songId);
        Author author = null;
        Album album = null;
        if( song != null ) {
            song.decrNumPlayed();
            if( song.getAuthor() != null ) {
                author = Services.getAuthorService().getAuthorByUUID(song.getAuthor().getUUID());
                author.decrNumPlayed();
                Services.getAuthorService().updateAuthor(author);
            }
            if( song.getAlbum() != null ) {
                album = Services.getAlbumService().getAlbumByUUID(song.getAlbum().getUUID());
                album.decrNumPlayed();
                Services.getAlbumService().updateAlbum(album);
            }
        }
        this.updateSong(song);
    }
}
