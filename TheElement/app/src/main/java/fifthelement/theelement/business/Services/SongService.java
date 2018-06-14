package fifthelement.theelement.business.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fifthelement.theelement.application.Services;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.PlayList;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.AlbumPersistence;
import fifthelement.theelement.persistence.AuthorPersistence;
import fifthelement.theelement.persistence.PlayListPersistence;
import fifthelement.theelement.persistence.SongPersistence;


// TODO: Our MainActivity Initializes This - Fragments Will Call
//       these Methods!
// TODO: TESTS!
public class SongService {

    private SongPersistence songPersistence;
    private AlbumPersistence albumPersistence;
    private AuthorPersistence authorPersistence;
    private PlayListPersistence playListPersistence;

    public SongService() {
        songPersistence = Services.getSongPersistence();
        albumPersistence = Services.getAlbumPersistence();
        authorPersistence = Services.getAuthorPersistence();
        playListPersistence = Services.getPlayListPersistence();
    }

    public List<Song> getSongs() {
        return songPersistence.getAllSongs();
    }

    // TODO: Try-Catch
    public boolean insertSong(Song song) {
        return songPersistence.storeSong(song);
    }

    // TODO: Try-Catch
    public boolean updateSong(Song song) {
        return songPersistence.updateSong(song);
    }

    public void sortSongs(List<Song> songs) {
        Collections.sort(songs);
    }

    public List<Song> search(String query) {
        List<Song> allSongs = songPersistence.getAllSongs();
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

    public boolean deleteSong(Song songToRemove) {
        Song song = songPersistence.getSongByUUID(songToRemove.getUUID());

        if( song != null ) {

            for( Album a : song.getAlbums() ) {
                Album album = albumPersistence.getAlbumByUUID(a.getUUID());
                album.deleteSong(song);
                albumPersistence.updateAlbum(album);
            }

            for( Author a : song.getAuthors() ) {
                Author author = authorPersistence.getAuthorByUUID(a.getUUID());
                author.deleteSong(song);
                authorPersistence.updateAuthor(author);
            }

            // deletes songs from existing PlayList if it's there
            // implementation for this hasn't been fully decided. this is a STUB
            for( PlayList p : playListPersistence.getAllPlayLists() ) {
                if( p.contains(song) ) {
                    p.removeSong(song);
                    playListPersistence.updatePlayList(p);
                }
            }

            validateAlbum(song.getAlbums());
            validateAuthor(song.getAuthors());
            return true;
        }
        return false;
    }

    private void validateAlbum(List<Album> albumList) {
        for( Album a : albumList ) {
            if( albumPersistence.getAlbumByUUID(a.getUUID()).getsongs().size() == 0 ) {
                albumPersistence.deleteAlbum(a);

                // delete album from Author
                Author author = authorPersistence.getAuthorByUUID(a.getUUID());
                author.deleteAlbum(a);
                authorPersistence.updateAuthor(author);
            }
        }
    }

    private void validateAuthor(List<Author> authorList) {
        for( Author a : authorList ) {
            if( authorPersistence.getAuthorByUUID(a.getUUID()).getSongList().size() == 0 ) {
                authorPersistence.deleteAuthor(a);
            }
        }
    }
}
