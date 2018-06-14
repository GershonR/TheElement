package fifthelement.theelement.persistence;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Song;

public interface SongPersistence {

    List<Song> getAllSongs(); // some unordered list.

    Song getSongByUUID(UUID ID); // get a song by UUID

    boolean storeSong(Song song); // checks & ignores duplicates

    boolean updateSong(Song song); // replaces old song with new one

    boolean deleteSong(Song song); // delete's using UUID

    boolean songExists(UUID uuid); // sees if a song exists by UUID

}