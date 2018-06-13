package fifthelement.theelement.business.Services;

import java.util.Collections;
import java.util.List;

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

    // TODO: Try-Catch
    public boolean insertSong(Song song) {
        return songPersistence.storeSong(song);
    }

    // TODO: Try-Catch
    public boolean updateSong(Song song) {
        return songPersistence.updateSong(song);
    }

    // TODO: Try-Catch
    public boolean deleteSong(Song song) {
        return songPersistence.deleteSong(song);
    }

    public void sortSongs(List<Song> songs) {
        Collections.sort(songs);
    }

    /*
        Tristans Delete Song Stuff Here
     */

    /*
        Jeremys Search Here
     */
}
