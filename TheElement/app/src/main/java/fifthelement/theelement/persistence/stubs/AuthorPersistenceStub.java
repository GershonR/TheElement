package fifthelement.theelement.persistence.stubs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Author;
import fifthelement.theelement.persistence.AuthorPersistence;

public class AuthorPersistenceStub implements AuthorPersistence {

    private List<Author> authorList;

    public AuthorPersistenceStub() {
        this.authorList = new ArrayList<>();

        this.authorList.add(new Author("Author1"));
        this.authorList.add(new Author("Author2"));
        this.authorList.add(new Author("Author3"));
        this.authorList.add(new Author("Author4"));
    }

    public AuthorPersistenceStub(List<Author> authorList) {
        this.authorList = authorList;
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
    public Author storeAuthor(Author author) {
        if(authorExists(author.getUUID()))
            throw new ArrayStoreException();
        this.authorList.add(author);
        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        if(author == null)
            throw new IllegalArgumentException("Cannot update a null song");
        for(int index = 0; index < authorList.size(); index++) {
            if(authorList.get(index).getUUID().compareTo(author.getUUID()) == 0) {
                this.authorList.set(index, author);
            }
        }
        return author;
    }

    @Override
    public boolean deleteAuthor(Author author) {
        boolean removed = false;
        if(author == null)
            throw new IllegalArgumentException("Cannot delete a null song");
        for(int index = 0; index < authorList.size(); index++) {
            if(authorList.get(index).getUUID().compareTo(author.getUUID()) == 0) {
                this.authorList.remove(index);
                removed = true;
            }
        }
        return removed;
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
