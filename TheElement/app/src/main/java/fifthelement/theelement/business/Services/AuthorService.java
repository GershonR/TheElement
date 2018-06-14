package fifthelement.theelement.business.Services;

import java.util.List;

import fifthelement.theelement.application.Services;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.persistence.AlbumPersistence;
import fifthelement.theelement.persistence.AuthorPersistence;


// TODO: Our MainActivity Initializes This - Fragments Will Call
//       these Methods!
// TODO: TESTS!
public class AuthorService {

    private AuthorPersistence authorPersistence;

    public AuthorService() {
        authorPersistence = Services.getAuthorPersistence();
    }

    public List<Author> getAuthors() {
        return authorPersistence.getAllAuthors();
    }

    // TODO: insertSong Try-Catch
    public boolean insertAuthor(Author author) {
        return authorPersistence.storeAuthor(author);
    }

    // TODO: updateSong Try-Catch
    public boolean updateAlbum(Author author) {
        return authorPersistence.updateAuthor(author);
    }

    // TODO: deleteSong Try-Catch
    public boolean deleteAlbum(Author author) {
        return authorPersistence.deleteAuthor(author.getUUID());
    }

}
