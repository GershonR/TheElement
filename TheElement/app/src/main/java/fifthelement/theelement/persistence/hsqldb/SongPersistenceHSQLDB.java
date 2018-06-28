package fifthelement.theelement.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.SongPersistence;

public class SongPersistenceHSQLDB implements SongPersistence {

    private List<Song> songList;

    private Connection c;

    public SongPersistenceHSQLDB(final String dbPath) {
        try {
            System.out.println("DB PATH: " + dbPath);
            this.c = DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath, "ELEMENT", "secure");
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    private Song fromResultSet(final ResultSet rs) throws SQLException {
        final UUID songUUID = UUID.fromString(rs.getString("songUUID"));
        final String songName = rs.getString("songName");
        final String songPath = rs.getString("songPath");
        final Author songAuthor = new Author(rs.getString("songAuthor"));
        final Album songAlbum = new Album(rs.getString("songAlbum"));
        final String songGenre = rs.getString("songGenre");
        return new Song(songUUID, songName, songPath, songAuthor, songAlbum, songGenre);
    }


    @Override
    public List<Song> getAllSongs() {

        final List<Song> songs = new ArrayList<>();

        try
        {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM songs");
            while (rs.next())
            {
                final Song song = fromResultSet(rs);
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
    public Song getSongByUUID(final UUID uuid) {
        try {
            final PreparedStatement st = c.prepareStatement("SELECT * songs WHERE songUUID = ?");
            st.setString(1, uuid.toString());

            final ResultSet rs = st.executeQuery();
            final Song song = fromResultSet(rs);
            rs.close();
            st.close();

            return song;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public boolean storeSong(Song song) throws ArrayStoreException {
        try {
            final PreparedStatement st = c.prepareStatement("INSERT INTO songs VALUES(?, ?, ?, ?, ?, ?)");
            st.setString(1, song.getUUID().toString());
            st.setString(2, song.getName());
            st.setString(3, song.getPath());
            if(song.getAuthor() != null)
                st.setString(4, song.getAuthor().getName());
            else
                st.setString(4, null);
            if(song.getAlbum() != null)
                st.setString(5, song.getAlbum().getName());
            else
                st.setString(5, null);
            st.setString(6, song.getGenre());

            st.executeUpdate();

            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public boolean updateSong(Song song) throws IllegalArgumentException {
        try {
            final PreparedStatement st = c.prepareStatement("UPDATE songs SET songName = ?, songPath = ? WHERE songUUID = ?");
            st.setString(1, song.getName());
            st.setString(2, song.getPath());
            st.setString(3, song.getUUID().toString());

            st.executeUpdate();

            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public boolean deleteSong(Song song) throws IllegalArgumentException {
        if(song == null)
            throw new IllegalArgumentException();
        return deleteSong(song.getUUID());
    }

    @Override
    public boolean deleteSong(UUID uuid) throws IllegalArgumentException {
        boolean removed = false;
        if(uuid == null)
            throw new IllegalArgumentException("Cannot delete with a null UUID");
        try {
            final PreparedStatement st = c.prepareStatement("DELETE FROM songs WHERE songUUID = ?");
            st.setString(1, uuid.toString());
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return removed;
    }

    @Override
    public boolean songExists(Song song) {
        return songExists(song.getUUID());
    }

    @Override
    public boolean songExists(UUID uuid) {
        Song song = getSongByUUID(uuid);
        return song != null;
    }
}
