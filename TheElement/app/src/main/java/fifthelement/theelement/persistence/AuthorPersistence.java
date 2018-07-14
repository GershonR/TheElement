package fifthelement.theelement.persistence;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Author;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;

public interface AuthorPersistence {

    List<Author> getAllAuthors() throws PersistenceException; // some unordered list.

    Author getAuthorByUUID(UUID uuid) throws PersistenceException, IllegalArgumentException; // get a author by UUID

    // using boolean since its a stub. would make changes when implementing db anyway
    boolean storeAuthor(Author author) throws PersistenceException, IllegalArgumentException; // checks & ignores duplicates

    boolean updateAuthor(Author author) throws PersistenceException, IllegalArgumentException; // replaces old author with new one

    boolean deleteAuthor(Author author) throws PersistenceException, IllegalArgumentException; // delete's using UUID

    boolean deleteAuthor(UUID uuid) throws PersistenceException, IllegalArgumentException; // delete's using UUID

    boolean authorExists(Author author) throws PersistenceException, IllegalArgumentException; // sees if a author exists by UUID

    boolean authorExists(UUID uuid) throws PersistenceException, IllegalArgumentException; // sees if a author exists by UUID

}
