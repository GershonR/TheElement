package fifthelement.theelement.persistence;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Author;

public interface AuthorPersistence {

    List<Author> getAllAuthors(); // some unordered list.

    Author getAuthorByUUID(UUID uuid); // get a author by UUID

    boolean storeAuthor(Author author); // checks & ignores duplicates

    boolean updateAuthor(Author author); // replaces old author with new one

    boolean deleteAuthor(UUID uuid); // delete's using UUID

    boolean authorExists(UUID uuid); // sees if a author exists by UUID

}
