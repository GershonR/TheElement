package fifthelement.theelement.business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.business.services.AuthorService;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.persistence.stubs.AuthorPersistenceStub;

@RunWith(JUnit4.class)
public class AuthorServiceTest {
    AuthorService classUnderTest;

    @Before
    public void setup() {
        classUnderTest = new AuthorService(new AuthorPersistenceStub());
        classUnderTest.getAuthors().clear();

        classUnderTest.insertAuthor(new Author("Bob Marley"));
        classUnderTest.insertAuthor(new Author("Led Zepplin"));
        classUnderTest.insertAuthor(new Author("Justin Bieber"));
    }


    @Test
    public void getAllAuthorsTest() {
        List<Author> authors = classUnderTest.getAuthors(); // Stub creates 3

        Assert.assertTrue("getAllAuthorsTest: author size != 3", authors.size() == 3);
    }

    @Test
    public void insertAuthorValidTest() {
        Author author = new Author("Jim Bob");
        classUnderTest.insertAuthor(author);

        Assert.assertTrue("insertAuthorValidTest: author size != 4", classUnderTest.getAuthors().size() == 4);
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
        boolean updateReturn = classUnderTest.updateAuthor(authorTwo);

        Assert.assertTrue("updateAuthorValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("updateAuthorValidTest: updateReturn != true", updateReturn);
        Assert.assertTrue("updateAuthorValidTest: song size != 4", classUnderTest.getAuthors().size() == 4);

        Author author = classUnderTest.getAuthorByUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Assert.assertTrue("updateAuthorValidTest: song name != Changed Author Name", "Bob Jim".equals(author.getName()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateAuthorInValidTest() {
        Author author = null;
        classUnderTest.updateAuthor(author);
    }

    @Test
    public void updateAuthorNotExistTest() {
        Author author = new Author("Jim Bob");
        boolean result = classUnderTest.updateAuthor(author);
        Assert.assertFalse("updateAuthorNotExistTest: result != false", result);
        Assert.assertTrue("updateAuthorNotExistTest: author size != 3", classUnderTest.getAuthors().size() == 3);

    }

    @Test
    public void deleteAuthorValidTest() {
        Author authorOne = new Author("Santa");
        UUID authorUUID = UUID.fromString("793410b3-dd0b-4b78-97bf-289f50f6e74f");
        authorOne.setUUID(authorUUID);

        boolean insertReturn = classUnderTest.insertAuthor(authorOne);
        Assert.assertTrue("deleteAuthorValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("deleteAuthorValidTest: author size != 4", classUnderTest.getAuthors().size() == 4);

        boolean deleteReturn = classUnderTest.deleteAuthor(authorOne);
        Assert.assertTrue("deleteAuthorValidTest: deleteReturn != true", deleteReturn);
        Assert.assertTrue("deleteAuthorValidTest: author size != 3", classUnderTest.getAuthors().size() == 3);

        Author deletedAuthor = classUnderTest.getAuthorByUUID(authorUUID);
        Assert.assertNull("deleteAuthorValidTest: deletedAuthor != null", deletedAuthor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteAuthorInValidTest() {
        Author author = null;
        classUnderTest.deleteAuthor(author);
    }

    @Test
    public void deleteAuthorNotExistTest() {
        Author author = new Author("Pepe");
        boolean result = classUnderTest.deleteAuthor(author);
        Assert.assertFalse("deleteAuthorNotExistTest: result != false", result);
        Assert.assertTrue("deleteAuthorNotExistTest: author size != 3", classUnderTest.getAuthors().size() == 3);
    }

    @Test
    public void authorStatsTest_SetterGetterTest() {       // initializing them
        List<Author> authorList = classUnderTest.getAuthors();
        int i = 0;
        for( Author author : authorList ) {
            author.setNumPlayed(i);
            Assert.assertEquals(author.getNumPlayed(),i);
            i++;
        }
    }

    @Test
    public void authorStatsTest_incrDecrPlays() {       // initializing them
        List<Author> authorList = classUnderTest.getAuthors();
        for( Author author : authorList ) {
            author.setNumPlayed(0);
        }
        authorList.get(0).incrNumPlayed();
        authorList.get(0).incrNumPlayed();
        authorList.get(0).decrNumPlayed();
        int actual = classUnderTest.getAuthorByUUID(authorList.get(0).getUUID()).getNumPlayed();
        Assert.assertEquals(1, actual);
        authorList.get(1).incrNumPlayed();
        authorList.get(1).decrNumPlayed();
        authorList.get(1).decrNumPlayed();
        actual = classUnderTest.getAuthorByUUID(authorList.get(1).getUUID()).getNumPlayed();
        Assert.assertEquals(0, actual);
    }

    @Test
    public void authorStatsTest_getMostPlayedAuthor() {
        List<Author> authorList = classUnderTest.getAuthors();
        int i = 8;
        for( Author author : authorList ) {
            author.setNumPlayed(i);
            Assert.assertEquals(author.getNumPlayed(),i);
            i--;
        }
        Author mostPlayedExpected = authorList.get(1);
        mostPlayedExpected.setNumPlayed(10);
        Author mostPlayedActual = classUnderTest.getMostPlayedAuthor();
        Assert.assertEquals(mostPlayedExpected, mostPlayedActual);
    }

    @Test
    public void authorStatsTest_getTotalAuthorPlays() {
        List<Author> authorList = classUnderTest.getAuthors();
        int i = 0;
        int expected = 0;
        for( Author author : authorList ) {
            author.setNumPlayed(i);
            expected += i++;
        }
        int actual = classUnderTest.getTotalAuthorPlays();
        Assert.assertEquals(expected, actual);
    }
}
