package fifthelement.theelement.persistence;

import java.util.List;

import fifthelement.theelement.objects.Author;

public interface AuthorPersistence {

    List<Author> getAllAuthors(); // some unordered list.

    Author getAuthorByID(int ID); // get a author by ID

    Author storeAuthor(Author song); // checks & ignores duplicates

    Author updateAuthor(Author song); // replaces old author with new one

    boolean deleteAuthor(Author song); // delete's using ID

}
