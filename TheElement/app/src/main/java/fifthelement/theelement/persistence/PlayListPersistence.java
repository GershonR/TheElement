package fifthelement.theelement.persistence;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Playlist;

public interface PlaylistPersistence {

    List<Playlist> getAllPlaylists();

    Playlist getPlaylistByUUID(UUID uuid);

    // using boolean since its a stub. would make changes when implementing db anyway
    boolean storePlaylist(Playlist playList);

    boolean updatePlaylist(Playlist playList);

    boolean deletePlaylist(Playlist playList);

    boolean deletePlaylist(UUID uuid);

    boolean playlistExists(Playlist playList);

    boolean playlistExists(UUID uuid);
}
