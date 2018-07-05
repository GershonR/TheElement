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
        final double songRating = rs.getDouble("songRating");
        return new Song(songUUID, songName, songPath, songAuthor, songAlbum, songGenre, songRating);
    }


    @Override
    public List<Song> getAllSongs() throws PersistenceException {

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
    public Song getSongByUUID(final UUID uuid) throws PersistenceException {
        if(uuid == null)
            throw new IllegalArgumentException("Cannot get song with a null UUID");
        try {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM songs WHERE songUUID = ?");
            st.setString(1, uuid.toString());

            final ResultSet rs = st.executeQuery();
            rs.next();
            final Song song = fromResultSet(rs);
            rs.close();
            st.close();

            return song;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public List<Song> getSongsByAlbumUUID(final UUID uuid) throws PersistenceException {
        if(uuid == null)
            throw new IllegalArgumentException("Cannot get song with a null album UUID");

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
    public boolean storeSong(Song song) throws PersistenceException, IllegalArgumentException {
        if(song == null)
            throw new IllegalArgumentException("Cant store a song with null Song");
        if(songExists(song.getUUID()))
            throw new IllegalArgumentException("Cant store a song with existing UUID");
        try {
            final PreparedStatement st = c.prepareStatement("INSERT INTO songs VALUES(?, ?, ?, ?, ?, ?, ?)");
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
            st.setDouble(7, song.getRating());

            st.executeUpdate();

            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public boolean updateSong(Song song) throws IllegalArgumentException, PersistenceException {
        if(song == null)
            throw new IllegalArgumentException("Cannot update a null song");
        try {
            final PreparedStatement st = c.prepareStatement("UPDATE songs SET songName = ?, songPath = ?, authorUUID = ?, albumUUID = ?, songGenre = ?, songRating = ? WHERE songUUID = ?");
            st.setString(1, song.getName());
            st.setString(2, song.getPath());
            if(song.getAuthor() != null){
                st.setString(3, song.getAuthor().getUUID().toString() );
            } else {
                st.setString(3,null);
            }

            if(song.getAlbum() != null) {
                st.setString(4, song.getAlbum().getUUID().toString());
            } else {
                st.setString(4, null);
            }

            st.setString(5, song.getGenre());

            st.setDouble(6, song.getRating());

            st.setString(7, song.getUUID().toString());

            st.executeUpdate();

            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public boolean deleteSong(Song song) throws IllegalArgumentException, PersistenceException {
        if(song == null)
            throw new IllegalArgumentException("Cannot delete song with a null Song");
        if(song == null)
            throw new IllegalArgumentException();
        return deleteSong(song.getUUID());
    }

    @Override
    public boolean deleteSong(UUID uuid) throws IllegalArgumentException, PersistenceException {
        boolean removed = false;
        if(uuid == null)
            throw new IllegalArgumentException("Cannot delete song with a null UUID");
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
    public boolean songExists(Song song) throws PersistenceException, IllegalArgumentException {
        if(song == null)
            throw new IllegalArgumentException("Cannot check exists with a null Song");
        return songExists(song.getUUID());
    }

    @Override
    public boolean songExists(UUID uuid) throws PersistenceException, IllegalArgumentException {
        if(uuid == null)
            throw new IllegalArgumentException("Cannot check exists with a null UUID");
        Song song = getSongByUUID(uuid);
        return song != null;
    }
}
