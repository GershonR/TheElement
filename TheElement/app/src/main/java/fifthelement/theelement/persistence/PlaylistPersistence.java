package fifthelement.theelement.persistence;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;

public interface PlaylistPersistence {

    List<Playlist> getAllPlaylists() throws PersistenceException;

    List<Song> getAllSongsByPlaylist(UUID uuid) throws PersistenceException, IllegalArgumentException;

    Playlist getPlaylistByUUID(UUID uuid) throws PersistenceException, IllegalArgumentException;

    // using boolean since its a stub. would make changes when implementing db anyway
    boolean storePlaylist(Playlist playList) throws PersistenceException, IllegalArgumentException;

    boolean storeSongForPlaylist(Playlist playList, Song song) throws PersistenceException, IllegalArgumentException;

    boolean updatePlaylist(Playlist playlist, String newName) throws PersistenceException, IllegalArgumentException;

    boolean deletePlaylist(Playlist playList) throws PersistenceException, IllegalArgumentException;

    boolean deletePlaylist(UUID uuid) throws PersistenceException, IllegalArgumentException;

    boolean playlistExists(Playlist playList) throws PersistenceException, IllegalArgumentException;

    boolean playlistExists(UUID uuid) throws PersistenceException, IllegalArgumentException;
}
