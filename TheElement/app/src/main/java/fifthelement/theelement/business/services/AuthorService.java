package fifthelement.theelement.business.services;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.objects.Author;
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

    public Author getMostPlayedAuthor() {
        Author author = null;
        List<Author> authorList = getSortedAuthorListByMostPlayed();
        if( authorList != null ) {
            author = authorList.get(0);
        }
        return author;
    }

    public List<Author> getSortedAuthorListByMostPlayed() {
       List<Author> authorList = this.getAuthors();
        if( authorList != null ) {
            Collections.sort(authorList, new Comparator<Author>() {
                @Override
                public int compare(Author author, Author t1) {
                    return Integer.compare(Integer.valueOf(t1.getNumPlayed()), Integer.valueOf(author.getNumPlayed()));
                }
            });
        }
       return authorList;
    }

    public int getTotalAuthorPlays() {
        List<Author> authorList = this.getAuthors();
        int totalPlays = 0;
        for( Author author : authorList ) {
            totalPlays += author.getNumPlayed();
        }
        return totalPlays;
    }
}
