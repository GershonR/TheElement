package fifthelement.theelement.business.services;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.persistence.PlaylistPersistence;


public class PlaylistService implements PlaylistPersistence {

    private PlaylistPersistence playlistPersistence;

    public PlaylistService() {
        playlistPersistence = Persistence.getPlaylistPersistence();
    }

    public PlaylistService(PlaylistPersistence playlistPersistence) {
        this.playlistPersistence = playlistPersistence;
    }

    public Playlist getPlaylistByUUID(UUID uuid) {
        return playlistPersistence.getPlaylistByUUID(uuid);
    }

    public List<Playlist> getAllPlaylists() {
        return playlistPersistence.getAllPlaylists();
    }

    public boolean insertPlaylist(Playlist playlist) throws ArrayStoreException, IllegalArgumentException {
        if(playlist == null)
            throw new IllegalArgumentException();
        return playlistPersistence.storePlaylist(playlist);
    }

    @Override
    public boolean storePlaylist(Playlist playlist) {
        if(playlist == null)
            throw new IllegalArgumentException();
        return playlistPersistence.storePlaylist(playlist);
    }

    public boolean updatePlaylist(Playlist playlist, String newName) throws  IllegalArgumentException {
        if(playlist == null)
            throw new IllegalArgumentException();
        return playlistPersistence.updatePlaylist(playlist, newName);
    }

    public boolean deletePlaylist(Playlist playlist) throws IllegalArgumentException {
        if(playlist == null)
            throw new IllegalArgumentException();
        return playlistPersistence.deletePlaylist(playlist.getUUID());
    }

    public boolean deletePlaylist(UUID uuid) {
        if (uuid == null)
            throw new IllegalArgumentException();
        return playlistPersistence.deletePlaylist(uuid);
    }

    public boolean playlistExists(Playlist playList) {
        if (playList == null)
            throw new IllegalArgumentException();
        return playlistPersistence.playlistExists(playList.getUUID());
    }

    public boolean playlistExists(UUID uuid) {
        if (uuid == null)
            throw new IllegalArgumentException();
        return playlistPersistence.playlistExists(uuid);
    }
}
