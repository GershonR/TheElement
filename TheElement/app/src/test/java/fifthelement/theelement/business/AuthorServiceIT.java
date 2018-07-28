package fifthelement.theelement.business;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.application.Main;
import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.services.AlbumService;
import fifthelement.theelement.business.services.AuthorService;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.AuthorPersistence;
import fifthelement.theelement.persistence.hsqldb.AuthorPersistenceHSQLDB;
import fifthelement.theelement.utils.TestDatabaseUtil;

public class AuthorServiceIT {
    private AuthorService authorService;
    private File tempDB;

    @Before
    public void setUpTestDB() throws IOException {
        this.tempDB = TestDatabaseUtil.copyDB();
        AuthorPersistence ap = new AuthorPersistenceHSQLDB(Main.getDBPathName());
        authorService = new AuthorService(ap);
        try {
            authorService.insertAuthor(new Author("Coldplay"));
            authorService.insertAuthor(new Author("Childish Gambino"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void getAllAuthorsTest() {
        List<Author> authors = authorService.getAuthors(); // Database has 2

        Assert.assertTrue("getAllAuthorsTest: author size != 2", authors.size() == 2);
    }

    @Test
    public void insertAuthorValidTest() {
        Author author = new Author("Jim Bob");
        authorService.insertAuthor(author);

        Assert.assertTrue("insertAuthorValidTest: author size != 3", authorService.getAuthors().size() == 3);
    }


    @Test(expected = IllegalArgumentException.class)
    public void insertAuthorInValidTest() {
        Author author = null;
        authorService.insertAuthor(author);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertAuthorDuplicateTest() {
        Author authorOne = new Author("Jim Bob");
        authorOne.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Author authorTwo = new Author("Bob Jim");
        authorTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        authorService.insertAuthor(authorOne);
        authorService.insertAuthor(authorTwo);
    }

    @Test
    public void updateAuthorValidTest() {
        Author authorOne = new Author("Jim Bob");
        authorOne.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Author authorTwo = new Author("Bob Jim");
        authorTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        boolean insertReturn = authorService.insertAuthor(authorOne);
        boolean updateReturn = authorService.updateAuthor(authorTwo);

        Assert.assertTrue("updateAuthorValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("updateAuthorValidTest: updateReturn != true", updateReturn);
        Assert.assertTrue("updateAuthorValidTest: author list size != 3", authorService.getAuthors().size() == 3);

        Author author = authorService.getAuthorByUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Assert.assertTrue("updateAuthorValidTest: author name != Changed Author Name", "Bob Jim".equals(author.getName()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateAuthorInValidTest() {
        Author author = null;
        authorService.updateAuthor(author);
    }

    @Test
    public void updateAuthorNotExistTest() {
        Author author = new Author("Jim Bob");
        boolean result = authorService.updateAuthor(author);
        Assert.assertFalse("updateAuthorNotExistTest: result != false", result);
        Assert.assertTrue("updateAuthorNotExistTest: author size != 2", authorService.getAuthors().size() == 2);

    }

    @Test
    public void deleteAuthorValidTest() {
        Author authorOne = new Author("Santa");
        UUID authorUUID = UUID.fromString("793410b3-dd0b-4b78-97bf-289f50f6e74f");
        authorOne.setUUID(authorUUID);

        boolean insertReturn = authorService.insertAuthor(authorOne);
        Assert.assertTrue("deleteAuthorValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("deleteAuthorValidTest: author size != 3", authorService.getAuthors().size() == 3);

        boolean deleteReturn = authorService.deleteAuthor(authorOne);
        Assert.assertTrue("deleteAuthorValidTest: deleteReturn != true", deleteReturn);
        Assert.assertTrue("deleteAuthorValidTest: author size != 2", authorService.getAuthors().size() == 2);

        Author deletedAuthor = authorService.getAuthorByUUID(authorUUID);
        Assert.assertNull("deleteAuthorValidTest: deletedAuthor != null", deletedAuthor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteAuthorInValidTest() {
        Author author = null;
        authorService.deleteAuthor(author);
    }

    @Test
    public void deleteAuthorNotExistTest() {
        Author author = new Author("Pepe");
        boolean result = authorService.deleteAuthor(author);
        Assert.assertFalse("deleteAuthorNotExistTest: result != false", result);
        Assert.assertTrue("deleteAuthorNotExistTest: author size != 2", authorService.getAuthors().size() == 2);
    }

    @After
    public void tearDownTestDB() {
        TestDatabaseUtil.killDB(tempDB);
    }
}
