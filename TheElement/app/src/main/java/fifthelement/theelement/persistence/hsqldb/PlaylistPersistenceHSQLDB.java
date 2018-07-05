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
    public List<Playlist> getAllPlaylists() {

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

    public List<Song> getAllSongsByPlaylist(UUID uuid) {

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
    public Playlist getPlaylistByUUID(UUID uuid) {
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
    public boolean storePlaylist(Playlist playList) {
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
    public boolean updatePlaylist(Playlist playlist, String newName) {
        try {
            final PreparedStatement st = c.prepareStatement("UPDATE playlists SET playlistName = ? WHERE playlistUUID = ?");
            st.setString(1, newName);
            st.setString(2, playlist.getUUID().toString());

            st.executeUpdate();

            return true;
        } catch (final SQLException e) {
            Log.e("PLAYLISTS", e.getMessage());
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean deletePlaylist(Playlist playList) {
        if(playList == null)
            throw new IllegalArgumentException();
        return deletePlaylist(playList.getUUID());
    }

    @Override
    public boolean deletePlaylist(UUID uuid) {
        boolean removed = false;
        if(uuid == null)
            throw new IllegalArgumentException("Cannot delete with a null UUID");
        try {
            final PreparedStatement st1 = c.prepareStatement("DELETE FROM playlistsongs WHERE playlistUUID = ?");
            st1.setString(1, uuid.toString());
            st1.executeUpdate();

            final PreparedStatement st2 = c.prepareStatement("DELETE FROM playlists WHERE playlistUUID = ?");
            st2.setString(1, uuid.toString());
            st2.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return removed;
    }

    @Override
    public boolean playlistExists(Playlist playList){ return playlistExists(playList.getUUID()); }

    @Override
    public boolean playlistExists(UUID uuid) {
        Playlist playlist = getPlaylistByUUID(uuid);
        return playlist != null;
    }
}
