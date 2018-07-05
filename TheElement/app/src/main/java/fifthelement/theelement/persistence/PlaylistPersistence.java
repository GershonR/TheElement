package fifthelement.theelement.persistence;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;

public interface PlaylistPersistence {

    List<Playlist> getAllPlaylists();

    List<Song> getAllSongsByPlaylist(UUID uuid);

    Playlist getPlaylistByUUID(UUID uuid);

    // using boolean since its a stub. would make changes when implementing db anyway
    boolean storePlaylist(Playlist playList);

    boolean storeSongForPlaylist(Playlist playList, Song song);

    boolean updatePlaylist(Playlist playlist, String newName);

    boolean deletePlaylist(Playlist playList);

    boolean deletePlaylist(UUID uuid);

    boolean playlistExists(Playlist playList);

    boolean playlistExists(UUID uuid);
}
