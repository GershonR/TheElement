package fifthelement.theelement.persistence.stubs;
/* AuthorStorage (a stub)
 * extends BaseStorage
 * acts as a database. stores Authors
 */

import java.util.List;

import fifthelement.theelement.objects.Author;

public class AuthorStorage extends BaseStorage<Author> {

    public AuthorStorage() {
        super();
        super.storeItem(new Author("00101", "Author1"));
        super.storeItem(new Author("00102", "Author2"));
        super.storeItem(new Author("00103", "Author3"));
        super.storeItem(new Author("00104", "Author4"));
    }

    public AuthorStorage(List<Author> authors) {
        super(authors);
    }

}

