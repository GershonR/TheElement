package fifthelement.theelement.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.PlaylistPersistence;

public class PlaylistPersistenceHSQLDB implements PlaylistPersistence {

    private Connection c;

    public PlaylistPersistenceHSQLDB(final String dbPath) {
        try {
            this.c = DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath, "SA", "");
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private Playlist fromResultSet(final ResultSet rs) throws SQLException {
        final UUID playlistUUID = UUID.fromString(rs.getString("playlistUUID"));
        final String playListName = rs.getString("playlistName");
        final List<Song> songs = null;
        return new Playlist(playlistUUID, playListName, songs);
    }

    private Song fromSongResultSet(final ResultSet rs)  throws SQLException {
        final UUID songUUID = UUID.fromString(rs.getString("songUUID"));
        return new Song(songUUID, "", "");
    }

    @Override
    public List<Playlist> getAllPlaylists() throws PersistenceException {

        final List<Playlist> playlists = new ArrayList<>();

        try
        {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM playlists");
            while (rs.next())
            {
                final Playlist playlist = fromResultSet(rs);
                playlists.add(playlist);
            }
            rs.close();
            st.close();

            return playlists;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    public List<Song> getAllSongsByPlaylist(UUID uuid) throws PersistenceException {
        if(uuid == null)
            throw new IllegalArgumentException("Cannot get songs from playlist with a null UUID");

        final List<Song> songs = new ArrayList<>();

        try
        {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM playlistsongs WHERE playlistUUID = ?");
            st.setString(1, uuid.toString());

            final ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                final Song song = fromSongResultSet(rs);
                songs.add(song);
            }
            rs.close();
            st.close();

            return songs;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Playlist getPlaylistByUUID(UUID uuid) throws PersistenceException {
        if(uuid == null)
            throw new IllegalArgumentException("Cannot get playlist with a null UUID");
        try {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM playlists WHERE playlistUUID = ?");
            st.setString(1, uuid.toString());

            final ResultSet rs = st.executeQuery();
            rs.next();
            final Playlist playlist = fromResultSet(rs);
            rs.close();
            st.close();

            return playlist;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean storePlaylist(Playlist playList) throws PersistenceException {
        if(playList == null)
            throw new IllegalArgumentException("Cant store a playlist with null Playlist");
//        if(playlistExists(playList.getUUID()))
//            throw new IllegalArgumentException("Cant store a playlist with existing UUID");
        try {
            final PreparedStatement st = c.prepareStatement("INSERT INTO playlists VALUES(?, ?)");
            st.setString(1, playList.getUUID().toString());
            st.setString(2, playList.getName());

            st.executeUpdate();

            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean storeSongForPlaylist(Playlist playList, Song song) throws PersistenceException {
        if(playList == null){
            throw new IllegalArgumentException("Cannot add song to a playlist with null Playlist");
        }
        if( song == null ) {
            throw new IllegalArgumentException("Cannot add null Song to playlist");
        }
        try {
            final PreparedStatement st = c.prepareStatement("INSERT INTO playlistsongs VALUES(?, ?)");
            st.setString(1, playList.getUUID().toString());
            st.setString(2, song.getUUID().toString());

            st.executeUpdate();

            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean updatePlaylist(Playlist playlist, String newName) throws PersistenceException {
        if(playlist == null){
            throw new IllegalArgumentException("Cannot update a playlist with null Playlist");
        }
        if( newName == null ) {
            throw new IllegalArgumentException("Cannot update a playlist with null name");
        }
        try {
            final PreparedStatement st = c.prepareStatement("UPDATE playlists SET playlistName = ? WHERE playlistUUID = ?");
            st.setString(1, newName);
            st.setString(2, playlist.getUUID().toString());

            st.executeUpdate();

            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean deletePlaylist(Playlist playList) throws PersistenceException {
        if(playList == null)
            throw new IllegalArgumentException("Cannot delete a playlist with a null Playlist");
        return deletePlaylist(playList.getUUID());
    }

    @Override
    public boolean deletePlaylist(UUID uuid) throws PersistenceException {
        boolean removed = false;
        if(uuid == null)
            throw new IllegalArgumentException("Cannot delete a playlist with a null UUID");
        try {
            final PreparedStatement st1 = c.prepareStatement("DELETE FROM playlistsongs WHERE playlistUUID = ?");
            st1.setString(1, uuid.toString());
            st1.executeUpdate();

            final PreparedStatement st2 = c.prepareStatement("DELETE FROM playlists WHERE playlistUUID = ?");
            st2.setString(1, uuid.toString());
            st2.executeUpdate();
            removed = true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return removed;
    }

    @Override
    public boolean playlistExists(Playlist playList) throws PersistenceException {
        if(playList == null)
            throw new IllegalArgumentException("Cannot check exists with a null Playlist");
        return playlistExists(playList.getUUID());
    }

    @Override
    public boolean playlistExists(UUID uuid) throws PersistenceException {
        if(uuid == null)
            throw new IllegalArgumentException("Cannot check exists with a null UUID");
        Playlist playlist = getPlaylistByUUID(uuid);
        return playlist != null;
    }
}
