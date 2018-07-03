package fifthelement.theelement.business.services;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.persistence.PlaylistPersistence;


public class PlaylistService {

    private PlaylistPersistence playlistPersistence;

    public PlaylistService() {
        playlistPersistence = Persistence.getPlaylistPersistence();
    }

    public PlaylistService(PlaylistPersistence playlistPersistence) {
        this.playlistPersistence = playlistPersistence;
    }

    public Playlist getPlaylistsByUUID(UUID uuid) {
        return playlistPersistence.getPlaylistByUUID(uuid);
    }

    public List<Playlist> getPlaylists() {
        return playlistPersistence.getAllPlaylists();
    }

    public boolean insertPlaylist(Playlist playlist) throws ArrayStoreException, IllegalArgumentException {
        if(playlist == null)
            throw new IllegalArgumentException();
        return playlistPersistence.storePlaylist(playlist);
    }

    public boolean updatePlaylist(Playlist playlist) throws  IllegalArgumentException {
        if(playlist == null)
            throw new IllegalArgumentException();
        return playlistPersistence.updatePlaylist(playlist);
    }

    public boolean deletePlaylist(Author author) throws IllegalArgumentException {
        if(author == null)
            throw new IllegalArgumentException();
        return playlistPersistence.deletePlaylist(author.getUUID());
    }

}
