package fifthelement.theelement.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.PlaylistPersistence;


public class PlaylistService {

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

        List<Playlist> playlists = playlistPersistence.getAllPlaylists();

        if(playlists != null) {
            for(Playlist playlist : playlists) {
                List<Song> songs = playlistPersistence.getAllSongsByPlaylist(playlist.getUUID());
                List<Song> updatedSongs = new ArrayList<>();
                if(songs != null) {
                    for(Song song : songs) {
                        song = Persistence.getSongPersistence().getSongByUUID(song.getUUID());
                        updatedSongs.add(song);
                    }
                }
                playlist.setSongs(updatedSongs);
            }
        }

        return playlists;
    }


    public boolean insertPlaylist(Playlist playlist) throws ArrayStoreException, IllegalArgumentException {
        if(playlist == null)
            throw new IllegalArgumentException();
        return playlistPersistence.storePlaylist(playlist);
    }

    public boolean insertSongForPlaylist(Playlist playlist, Song song) throws IllegalArgumentException {
        if(playlist == null || song == null)
            throw new IllegalArgumentException();
        return playlistPersistence.storeSongForPlaylist(playlist, song);
    }

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
