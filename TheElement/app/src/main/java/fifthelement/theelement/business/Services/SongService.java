package fifthelement.theelement.business.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fifthelement.theelement.application.Services;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.SongPersistence;


// TODO: Our MainActivity Initializes This - Fragments Will Call
//       these Methods!
// TODO: TESTS!
public class SongService {

    private SongPersistence songPersistence;

    public SongService() {
        songPersistence = Services.getSongPersistence();
    }

    public List<Song> getSongs() {
        return songPersistence.getAllSongs();
    }

    public boolean insertSong(Song song) {
        return songPersistence.storeSong(song);
    }

    public boolean updateSong(Song song) {
        return songPersistence.updateSong(song);
    }

    public boolean deleteSong(Song song) {
        return songPersistence.deleteSong(song.getUUID());
    }

    /**
     * This method checks if any song already has the same path
     * @param path  The path to check
     * @return  True if there exists a song with the same path
     */
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

    /*
        Tristans Delete Song Stuff Here
     */
}
