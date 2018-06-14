package fifthelement.theelement.persistence;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Author;

public interface AuthorPersistence {

    List<Author> getAllAuthors(); // some unordered list.

    Author getAuthorByUUID(UUID ID); // get a author by UUID

    Author storeAuthor(Author song); // checks & ignores duplicates

    Author updateAuthor(Author song); // replaces old author with new one

    boolean deleteAuthor(Author song); // delete's using UUID

    boolean authorExists(UUID uuid); // sees if a author exists by UUID

}
