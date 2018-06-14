package fifthelement.theelement.business;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.Services.AuthorService;
import fifthelement.theelement.business.Services.SongService;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.AuthorPersistence;
import fifthelement.theelement.persistence.stubs.AuthorPersistenceStub;

@RunWith(JUnit4.class)
public class AuthorServiceTest {
    AuthorService classUnderTest;

    @Before
    public void setup() {
        classUnderTest = new AuthorService();
        classUnderTest.getAuthors().clear();

        classUnderTest.insertAuthor(new Author("Bob Marley"));
        classUnderTest.insertAuthor(new Author("Led Zepplin"));
        classUnderTest.insertAuthor(new Author("Justin Bieber"));
    }


    @Test
    public void getAllAuthorsTest() {
        List<Author> authors = classUnderTest.getAuthors(); // Stub creates 3

        Assert.assertTrue("getAllAuthorsTest: song size != 3", authors.size() == 3);
    }

    @Test
    public void insertAuthorValidTest() {
        Author author = new Author("Jim Bob");
        classUnderTest.insertAuthor(author);

        Assert.assertTrue("insertAuthorValidTest: song size != 4", classUnderTest.getAuthors().size() == 4);
    }


    @Test(expected = IllegalArgumentException.class)
    public void insertAuthorInValidTest() {
        Author author = null;
        classUnderTest.insertAuthor(author);
    }

    @Test(expected = ArrayStoreException.class)
    public void insertAuthorDuplicateTest() {
        Author authorOne = new Author("Jim Bob");
        authorOne.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Author authorTwo = new Author("Bob Jim");
        authorTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        classUnderTest.insertAuthor(authorOne);
        classUnderTest.insertAuthor(authorTwo);
    }

    @Test
    public void updateAuthorValidTest() {
        Author authorOne = new Author("Jim Bob");
        authorOne.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Author authorTwo = new Author("Bob Jim");
        authorTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        boolean insertReturn = classUnderTest.insertAuthor(authorOne);
        boolean updateReturn =classUnderTest.updateAuthor(authorTwo);

        Assert.assertTrue("updateAuthorValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("updateAuthorValidTest: updateReturn != true", updateReturn);
        Assert.assertTrue("updateAuthorValidTest: song size != 4", classUnderTest.getAuthors().size() == 4);

        Author author = classUnderTest.getAuthorByUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Assert.assertTrue("updateAuthorValidTest: song name != Changed Song Name", "Bob Jim".equals(author.getName()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateAuthorInValidTest() {
        Author author = null;
        classUnderTest.updateAuthor(author);
    }

}
