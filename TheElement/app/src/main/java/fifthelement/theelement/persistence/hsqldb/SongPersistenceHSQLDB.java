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

    private Connection c;

    public SongPersistenceHSQLDB(final String dbPath) {
        try {
            this.c = DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath, "SA", "");
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    private Song fromResultSet(final ResultSet rs) throws SQLException {
        final UUID songUUID = UUID.fromString(rs.getString("songUUID"));
        final String songName = rs.getString("songName");
        final String songPath = rs.getString("songPath");
        final String authorUUID = rs.getString("authorUUID");
        final String albumUUID = rs.getString("albumUUID");
        Author songAuthor = null;
        Album songAlbum = null;
        if(authorUUID != null && authorUUID.length() == 36)
            songAuthor = new Author(UUID.fromString(authorUUID), "");
        if(albumUUID != null && albumUUID.length() == 36)
            songAlbum = new Album(UUID.fromString(albumUUID), "");
        final String songGenre = rs.getString("songGenre");
        final int numPlayed = rs.getInt("numPlayed");
        final double songRating = rs.getDouble("songRating");
        return new Song(songUUID, songName, songPath, songAuthor, songAlbum, songGenre, numPlayed, songRating);
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
            final PreparedStatement st = c.prepareStatement("SELECT * FROM songs WHERE songUUID = ?");
            st.setString(1, uuid.toString());

            Song song = null;
            final ResultSet rs = st.executeQuery();
            if(rs.next()) {
                song = fromResultSet(rs);
            }
            rs.close();
            st.close();

            return song;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public List<Song> getSongsByAuthorUUID(final UUID uuid) {

        final List<Song> songs = new ArrayList<>();

        try
        {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM songs WHERE albumUUID = ?");
            st.setString(1, uuid.toString());
            final ResultSet rs = st.executeQuery();
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
    public List<Song> getSongsByAlbumUUID(final UUID uuid) {

        final List<Song> songs = new ArrayList<>();

        try
        {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM songs WHERE authorUUID = ?");
            st.setString(1, uuid.toString());
            final ResultSet rs = st.executeQuery();
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
    public boolean storeSong(Song song) throws PersistenceException {
        try {
            final PreparedStatement st = c.prepareStatement("INSERT INTO songs VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1, song.getUUID().toString());
            st.setString(2, song.getName());
            st.setString(3, song.getPath());
            if(song.getAuthor() != null)
                st.setString(4, song.getAuthor().getUUID().toString());
            else
                st.setString(4, null);
            if(song.getAlbum() != null)
                st.setString(5, song.getAlbum().getUUID().toString());
            else
                st.setString(5, null);
            st.setString(6, song.getGenre());
            st.setInt(7, song.getNumPlayed());
            st.setDouble(8, song.getRating());

            st.executeUpdate();

            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public boolean updateSong(Song song) throws IllegalArgumentException {
        try {
            if(songExists(song)) {
                final PreparedStatement st = c.prepareStatement("UPDATE songs SET songName = ?, songPath = ?, authorUUID = ?, albumUUID = ?, songGenre = ?, numPlayed = ?, songRating = ? WHERE songUUID = ?");
                st.setString(1, song.getName());
                st.setString(2, song.getPath());
                if (song.getAuthor() != null) {
                    st.setString(3, song.getAuthor().getUUID().toString());
                } else {
                    st.setString(3, null);
                }

                if (song.getAlbum() != null) {
                    st.setString(4, song.getAlbum().getUUID().toString());
                } else {
                    st.setString(4, null);
                }

                st.setString(5, song.getGenre());
                st.setInt(6, song.getNumPlayed());
                st.setDouble(7, song.getRating());
                st.setString(8, song.getUUID().toString());

                st.executeUpdate();

                return true;
            }
            return false;
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
            removed = true;
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
