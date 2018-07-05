package fifthelement.theelement.business.services;

import java.util.ArrayList;
import java.util.Collections;
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
import fifthelement.theelement.persistence.PlayListPersistence;
import fifthelement.theelement.persistence.SongPersistence;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;

public class SongService {

    private SongPersistence songPersistence;
    private AlbumPersistence albumPersistence;
    private AuthorPersistence authorPersistence;
    private PlayListPersistence playListPersistence;

    private List<Song> songs;

    public SongService() {
        songPersistence = Persistence.getSongPersistence();
        albumPersistence = Persistence.getAlbumPersistence();
        authorPersistence = Persistence.getAuthorPersistence();
        playListPersistence = Persistence.getPlayListPersistence();
    }

    public SongService(SongPersistence songPersistence, AlbumPersistence albumPersistence, AuthorPersistence authorPersistence, PlayListPersistence playListPersistence) {
        this.songPersistence = songPersistence;
        this.albumPersistence = albumPersistence;
        this.authorPersistence = authorPersistence;
        this.playListPersistence = playListPersistence;
    }

    public Song getSongByUUID(UUID uuid) {
        return songPersistence.getSongByUUID(uuid);
    }

    public List<Song> getSongs() throws PersistenceException {
        songs = songPersistence.getAllSongs();

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

    public boolean insertSong(Song song) throws PersistenceException, IllegalArgumentException, SongAlreadyExistsException {
        if(song == null)
            throw new IllegalArgumentException();
        if(pathExists(song.getPath()))
            throw new SongAlreadyExistsException(song.getPath());
        return songPersistence.storeSong(song);
    }

    public boolean updateSong(Song song) throws IllegalArgumentException {
        if(song == null)
            throw new IllegalArgumentException();

        return songPersistence.updateSong(song);
    }

    public boolean updateSongWithParameters(Song song, String songName, String author, String album, String genre) {

        if(!songName.equals("")){
            song.setName(songName);
        }
        AuthorService authorService = Services.getAuthorService();
        AlbumService  albumService = Services.getAlbumService();

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
            //for( PlayList p : playListPersistence.getAllPlayLists() ) {
            //    if( p.contains(song) ) {
            //        p.removeSong(song);
            //        playListPersistence.updatePlayList(p);
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

    public void sortSongs(List<Song> songs) {
        Collections.sort(songs);
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
}
