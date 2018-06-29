package fifthelement.theelement.business.Services;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.persistence.AlbumPersistence;
import fifthelement.theelement.persistence.AuthorPersistence;


public class AuthorService {

    private AuthorPersistence authorPersistence;

    public AuthorService() {
        authorPersistence = Persistence.getAuthorPersistence();
    }

    public AuthorService(AuthorPersistence authorPersistence) {
        this.authorPersistence = authorPersistence;
    }

    public Author getAuthorByUUID(UUID uuid) {
        return authorPersistence.getAuthorByUUID(uuid);
    }

    public List<Author> getAuthors() {
        return authorPersistence.getAllAuthors();
    }

    public boolean insertAuthor(Author author) throws ArrayStoreException, IllegalArgumentException {
        if(author == null)
            throw new IllegalArgumentException();
        return authorPersistence.storeAuthor(author);
    }

    public boolean updateAuthor(Author author) throws  IllegalArgumentException {
        if(author == null)
            throw new IllegalArgumentException();
        return authorPersistence.updateAuthor(author);
    }

    public boolean deleteAuthor(Author author) throws IllegalArgumentException {
        if(author == null)
            throw new IllegalArgumentException();
        return authorPersistence.deleteAuthor(author.getUUID());
    }

}
