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

import fifthelement.theelement.objects.Author;
import fifthelement.theelement.persistence.AuthorPersistence;

public class AuthorPersistenceHSQLDB implements AuthorPersistence {
    private Connection c;

    public AuthorPersistenceHSQLDB(final String dbPath) {
        try {
            this.c = DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath, "SA", "");
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private Author fromResultSet(final ResultSet rs) throws SQLException {
        final UUID authorUUID = UUID.fromString(rs.getString("authorUUID"));
        final String authorName = rs.getString("authorName");
        final int numPlayed = rs.getInt("numPlayed");
        return new Author(authorUUID, authorName, numPlayed);
    }

    @Override
    public List<Author> getAllAuthors() throws PersistenceException {

        final List<Author> authors = new ArrayList<>();

        try
        {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM authors");
            while (rs.next())
            {
                final Author author = fromResultSet(rs);
                authors.add(author);
            }
            rs.close();
            st.close();

            return authors;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Author getAuthorByUUID(UUID uuid) throws PersistenceException, IllegalArgumentException {
        if(uuid == null)
            throw new IllegalArgumentException("Cannot get author with a null UUID");
        try {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM authors WHERE authorUUID = ?");
            String uuidString = uuid.toString();
            st.setString(1, uuidString);

            Author author = null;
            final ResultSet rs = st.executeQuery();
            if(rs.next()) {
                author = fromResultSet(rs);
            }
            rs.close();
            st.close();

            return author;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean storeAuthor(Author author) throws PersistenceException, IllegalArgumentException {
        if(author == null)
            throw new IllegalArgumentException("Cannot insert a null author");
//        if(authorExists(author.getUUID()))
//            throw new IllegalArgumentException("Cant store an author with existing UUID");
        try {
            final PreparedStatement st = c.prepareStatement("INSERT INTO authors VALUES(?, ?, ?)");
            st.setString(1, author.getUUID().toString());
            st.setString(2, author.getName());
            st.setInt(3, author.getNumPlayed());

            st.executeUpdate();

            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean updateAuthor(Author author) throws PersistenceException, IllegalArgumentException {
        if(author == null)
            throw new IllegalArgumentException("Cannot update a null author");
        try {
            if(authorExists(author.getUUID())) {
                final PreparedStatement st = c.prepareStatement("UPDATE authors SET authorName = ?, numPlayed = ? WHERE authorUUID = ? ");
                st.setString(1, author.getName());
                st.setInt(2, author.getNumPlayed());
                st.setString(3, author.getUUID().toString());

                st.executeUpdate();

                return true;
            }
            return false;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean deleteAuthor(Author author) throws PersistenceException, IllegalArgumentException  {
        if (author == null)
            throw new IllegalArgumentException("Cannot delete an author with a null author");
        return deleteAuthor(author.getUUID());
    }

    @Override
    public boolean deleteAuthor(UUID uuid) throws PersistenceException, IllegalArgumentException {
        boolean removed = false;
        if(uuid == null)
            throw new IllegalArgumentException("Cannot delete an author with a null UUID");
        try {
            if(getAuthorByUUID(uuid) != null) {
                final PreparedStatement st = c.prepareStatement("DELETE FROM authors WHERE authorUUID = ?");
                st.setString(1, uuid.toString());

                st.executeUpdate();
                removed = true;
            }
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return  removed;
    }

    @Override
    public boolean authorExists(Author author) throws PersistenceException, IllegalArgumentException {
        if(author == null)
            throw new IllegalArgumentException("Cannot check exists with a null Author");
        return authorExists(author.getUUID());
    }

    @Override
    public boolean authorExists(UUID uuid) throws PersistenceException, IllegalArgumentException {
        if(uuid == null)
            throw new IllegalArgumentException("Cannot check exists with a null UUID");
        Author author = getAuthorByUUID(uuid);
        return author != null;
    }
}
