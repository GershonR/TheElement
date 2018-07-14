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

import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.AlbumPersistence;

public class AlbumPersistenceHSQLDB implements AlbumPersistence {

    private Connection c;

    public AlbumPersistenceHSQLDB(final String dbPath) {
        try {
            this.c = DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath, "SA", "");
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private Album fromResultSet(final ResultSet rs) throws SQLException {
        final UUID albumUUID = UUID.fromString(rs.getString("albumUUID"));
        final String albumName = rs.getString("albumName");
        final String authorUUID = rs.getString("authorUUID");
        Author author = null;
        if(authorUUID != null)
            author = Persistence.getAuthorPersistence().getAuthorByUUID(UUID.fromString(authorUUID));
        final List<Song> songs = null;
        final int numPlayed = rs.getInt("numPlayed");
        return new Album(albumUUID, albumName, author, songs, numPlayed);
    }

    @Override
    public List<Album> getAllAlbums() throws PersistenceException {

        final List<Album> albums = new ArrayList<>();

        try
        {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM albums");
            while (rs.next())
            {
                final Album album = fromResultSet(rs);
                albums.add(album);
            }
            rs.close();
            st.close();

            return albums;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }

    }

    @Override
    public Album getAlbumByUUID(UUID uuid) throws PersistenceException, IllegalArgumentException {
        if(uuid == null)
            throw new IllegalArgumentException("Cannot get album with a null UUID");
        try {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM albums WHERE albumUUID = ?");
            String uuidString = uuid.toString();
            st.setString(1, uuidString);

            final ResultSet rs = st.executeQuery();
            rs.next();
            final Album album = fromResultSet(rs);
            rs.close();
            st.close();

            return album;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public boolean storeAlbum(Album album) throws PersistenceException, IllegalArgumentException {
        if(album == null)
            throw new IllegalArgumentException("Cant store an album with null Album");
//        if(albumExists(album.getUUID()))
//            throw new IllegalArgumentException("Cant store an album with existing UUID");
        try {
            final PreparedStatement st = c.prepareStatement("INSERT INTO albums VALUES(?, ?, ?, ?)");
            st.setString(1, album.getUUID().toString());
            st.setString(2, album.getName());
            if(album.getAuthor() != null)
                st.setString(3, album.getAuthor().getUUID().toString());
            else
                st.setString(3, null);
            st.setInt(4, album.getNumPlayed());

            st.executeUpdate();

            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean updateAlbum(Album album) throws PersistenceException, IllegalArgumentException {
        if(album == null)
            throw new IllegalArgumentException("Cannot update a null album");
        try {
            final PreparedStatement st = c.prepareStatement("UPDATE albums SET albumName = ?, numPlayed = ? WHERE albumUUID = ? ");
            st.setString(1, album.getName());
            st.setInt(2, album.getNumPlayed());
            st.setString(3, album.getUUID().toString());

            st.executeUpdate();
            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean deleteAlbum(Album album) throws PersistenceException, IllegalArgumentException {
        if (album == null)
            throw new IllegalArgumentException("Cannot delete album with a null album");
        return deleteAlbum(album.getUUID());
    }

    @Override
    public boolean deleteAlbum(UUID uuid) throws PersistenceException, IllegalArgumentException {
        boolean removed = false;
        if(uuid == null)
            throw new IllegalArgumentException("Cannot delete album with a null UUID");
        try {
            final PreparedStatement st = c.prepareStatement("DELETE FROM albums WHERE albumUUID = ?");
            st.setString(1, uuid.toString());

            st.executeUpdate();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return  removed;
    }

    @Override
    public boolean albumExists(Album album) throws PersistenceException, IllegalArgumentException {
        if(album == null)
            throw new IllegalArgumentException("Cannot check exists with a null Album");
        return albumExists(album.getUUID());
    }

    @Override
    public boolean albumExists(UUID uuid) throws PersistenceException, IllegalArgumentException {
        if(uuid == null)
            throw new IllegalArgumentException("Cannot check exists with a null UUID");
        Album album = getAlbumByUUID(uuid);
        return album != null;
    }
}
