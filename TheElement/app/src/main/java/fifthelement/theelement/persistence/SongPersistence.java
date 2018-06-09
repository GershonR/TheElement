package fifthelement.theelement.persistence;

import java.util.List;

import fifthelement.theelement.objects.Song;

public interface SongPersistence {

    List<Song> getAllSongs(); // some unordered list.

    Song getSongByID(int ID); // get a song by ID

    Song storeSong(Song song); // checks & ignores duplicates

    Song updateSong(Song song); // replaces old song with new one

    boolean deleteSong(Song song); // delete's using ID

}