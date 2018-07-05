package fifthelement.theelement.persistence.stubs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.PlaylistPersistence;

public class PlaylistPersistenceStub implements PlaylistPersistence {

    private List<Playlist> playlists;

    public PlaylistPersistenceStub() {
        this.playlists = new ArrayList<>();

        Song song1 = new Song("This Is America", "android.resource://fifthelement.theelement/raw/childish_gambino_this_is_america");
        song1.setAuthor(new Author("Childish Gambino"));
        Song song2 = new Song("Classical Music", "android.resource://fifthelement.theelement/raw/classical_music");
        Song song3 = new Song("Adventure of a Lifetime", "android.resource://fifthelement.theelement/raw/coldplay_adventure_of_a_lifetime");
        song3.setAuthor(new Author("Coldplay"));


        Playlist playlist1 = new Playlist("Actual Playlist");
        playlist1.addSong(song1);
        playlist1.addSong(song2);
        playlist1.addSong(song3);
        this.storePlaylist(playlist1);

        Playlist playlist2 = new Playlist("Second Playlist");
        playlist2.addSong(song1);
        this.storePlaylist(playlist2);

        this.storePlaylist(new Playlist("Empty Playlist"));

        Playlist playlist3 = new Playlist("Duplicate songs");
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        playlist3.addSong(song1);
        playlist3.addSong(song3);
        this.storePlaylist(playlist3);

    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlists;
    }

    @Override
    public Playlist getPlaylistByUUID(UUID uuid) {
        for( Playlist p : playlists) {
            if( p.getUUID() == uuid ) {
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean storePlaylist(Playlist playList) {
        if( playlistExists(playList.getUUID()) ) {
            throw new ArrayStoreException();
        }
        playlists.add(playList);
        return true;
    }

    @Override
    public boolean updatePlaylist(Playlist playlist, String newName) {
        if( newName == null || playlist == null) {
            throw new IllegalArgumentException("Cannot update a playlist with null string");
        }
        playlist.setName(newName);
        return true;
    }

    @Override
    public boolean deletePlaylist(Playlist playList) throws IllegalArgumentException {
        if(playList == null)
            throw new IllegalArgumentException("Cannot delete a null playlist");
        return this.deletePlaylist(playList.getUUID());
    }

    @Override
    public boolean deletePlaylist(UUID uuid) throws IllegalArgumentException {
        boolean removed = false;
        if(uuid == null)
            throw new IllegalArgumentException("Cannot delete a null author");
        for(int index = 0; index < playlists.size(); index++) {
            if(playlists.get(index).getUUID().compareTo(uuid) == 0) {
                this.playlists.remove(index);
                removed = true;
            }
        }
        return removed;
    }


    public boolean playlistExists(Playlist playList) {
        return this.playlistExists(playList.getUUID());
    }

    @Override
    public boolean playlistExists(UUID uuid) {
        return getPlaylistByUUID(uuid) != null;
    }

    @Override
    public List<Song> getAllSongsByPlaylist(UUID uuid) {
        Playlist playlist = getPlaylistByUUID(uuid);
        return playlist.getSongs();
    }

}
