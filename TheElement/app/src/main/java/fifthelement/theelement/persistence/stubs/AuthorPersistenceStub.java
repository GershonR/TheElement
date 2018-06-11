package fifthelement.theelement.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import fifthelement.theelement.objects.Author;
import fifthelement.theelement.persistence.AuthorPersistence;

public class AuthorPersistenceStub implements AuthorPersistence {

    private List<Author> authorList;

    public AuthorPersistenceStub() {
        this.authorList = new ArrayList<>();

        this.authorList.add(new Author(00101, "Author1"));
        this.authorList.add(new Author(00102, "Author2"));
        this.authorList.add(new Author(00103, "Author3"));
        this.authorList.add(new Author(00104, "Author4"));
    }

    public AuthorPersistenceStub(List<Author> authorList) {
        this.authorList = authorList;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorList;
    }

    @Override
    public Author getAuthorById(int Id) {
        for(Author a : this.authorList)
            if( a.getId() == Id )
                return a;
        return null;
    }

    @Override
    public Author storeAuthor(Author author) {
        if(authorExists(author))
            throw new ArrayStoreException();
        this.authorList.add(author);
        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        if(author == null)
            throw new IllegalArgumentException("Cannot update a null song");
        for(int index = 0; index < authorList.size(); index++) {
            if(authorList.get(index).getId() == author.getId()) {
                this.authorList.set(index, author);
                return author;
            }
        }
        return null;
    }

    @Override
    public boolean deleteAuthor(Author author) {
        boolean removed = false;
        if(author == null)
            throw new IllegalArgumentException("Cannot delete a null song");
        for(int index = 0; index < authorList.size(); index++) {
            if(authorList.get(index).getId() == author.getId()) {
                this.authorList.remove(index);
                removed = true;
                break;
            }
        }
        return removed;
    }

    private boolean authorExists(Author author) {
        return getAuthorById(author.getId()) != null;
    }
}
