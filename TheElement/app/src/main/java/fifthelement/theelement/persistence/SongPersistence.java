package fifthelement.theelement.persistence;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Song;

public interface SongPersistence {

    List<Song> getAllSongs(); // some unordered list.

    Song getSongByUUID(UUID ID); // get a song by UUID

    // using boolean since its a stub. would make changes when implementing db anyway
    boolean storeSong(Song song); // checks & ignores duplicates

    boolean updateSong(Song song); // replaces old song with new one

    boolean deleteSong(Song song);

    boolean deleteSong(UUID uuid); // delete's using UUID

    boolean songExists(Song song); // sees if a song exists by UUID

    boolean songExists(UUID uuid); // sees if a song exists by UUID

}