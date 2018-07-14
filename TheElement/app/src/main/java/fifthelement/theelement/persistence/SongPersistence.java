package fifthelement.theelement.persistence;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;

public interface SongPersistence {

    List<Song> getAllSongs() throws PersistenceException; // some unordered list.

    Song getSongByUUID(UUID ID) throws PersistenceException, IllegalArgumentException; // get a song by UUID

    List<Song> getSongsByAlbumUUID(UUID ID) throws PersistenceException, IllegalArgumentException; // get a song by UUID

    List<Song> getSongsByAuthorUUID(UUID ID) throws PersistenceException, IllegalArgumentException; // get a song by UUID

    // using boolean since its a stub. would make changes when implementing db anyway
    boolean storeSong(Song song) throws PersistenceException, IllegalArgumentException; // checks & ignores duplicates

    boolean updateSong(Song song) throws PersistenceException, IllegalArgumentException; // replaces old song with new one

    boolean deleteSong(Song song) throws PersistenceException, IllegalArgumentException;

    boolean deleteSong(UUID uuid) throws PersistenceException, IllegalArgumentException; // delete's using UUID

    boolean songExists(Song song) throws PersistenceException, IllegalArgumentException; // sees if a song exists by UUID

    boolean songExists(UUID uuid) throws PersistenceException, IllegalArgumentException; // sees if a song exists by UUID

}