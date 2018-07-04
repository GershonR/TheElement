package fifthelement.theelement.persistence.stubs;

import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Author;
import fifthelement.theelement.persistence.AuthorPersistence;

public class AuthorPersistenceStub implements AuthorPersistence {

    private List<Author> authorList;

    public AuthorPersistenceStub() {
        this.authorList = new ArrayList<>();

        this.authorList.add(new Author("Bob Marley"));
        this.authorList.add(new Author("Led Zepplin"));
        this.authorList.add(new Author("Justin Bieber"));
        this.authorList.add(new Author("Jeremy The Goat"));

    }


    @Override
    public List<Author> getAllAuthors() {
        return authorList;
    }

    @Override
    public Author getAuthorByUUID(UUID uuid) {
        for(Author a : this.authorList)
            if(a.getUUID().compareTo(uuid) == 0)
                return a;
        return null;
    }

    @Override
    public boolean storeAuthor(Author author) throws ArrayStoreException, IllegalArgumentException {
        if(author == null)
            throw new IllegalArgumentException("Cannot insert a null author");
        if(authorExists(author.getUUID()))
            throw new ArrayStoreException();
        this.authorList.add(author);
        return true;
    }

    @Override
    public boolean updateAuthor(Author author) throws IllegalArgumentException {
        boolean removed = false;
        if(author == null)
            throw new IllegalArgumentException("Cannot update a null author");
        for(int index = 0; index < authorList.size(); index++) {
            if(authorList.get(index).getUUID().compareTo(author.getUUID()) == 0) {
                this.authorList.set(index, author);
                removed = true;
                break;
            }
        }
        return removed;
    }

    @Override
    public boolean deleteAuthor(Author author) throws IllegalArgumentException {
        if (author == null)
            throw new IllegalArgumentException("Cannot delete a null author");
        return this.deleteAuthor(author.getUUID());
    }

    @Override
    public boolean deleteAuthor(UUID uuid) throws IllegalArgumentException {
        boolean removed = false;
        if(uuid == null)
            throw new IllegalArgumentException("Cannot delete a null author");
        for(int index = 0; index < authorList.size(); index++) {
            if(authorList.get(index).getUUID().compareTo(uuid) == 0) {
                this.authorList.remove(index);
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean authorExists(Author author) {
        return this.authorExists(author.getUUID());
    }

    @Override
    public boolean authorExists(UUID uuid) {
        boolean exists = false;
        for(Author a : this.authorList)
            if(a.getUUID().compareTo(uuid) == 0) {
                exists = true;
                break;
            }
        return exists;
    }
}
