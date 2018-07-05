package fifthelement.theelement.persistence.stubs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Author;

@RunWith(JUnit4.class)
public class AuthorPersistenceTest {

    private AuthorPersistenceStub classUnderTest;
    private ArrayList<Author> authorList;

    private static UUID uuidOne;
    private static UUID uuidTwo;
    private static UUID uuidThree;
    private static UUID uuidFour;

    @Before
    public void initClass() {
        authorList = getAuthorList();
        classUnderTest = new AuthorPersistenceStub();
        uuidOne = classUnderTest.getAllAuthors().get(0).getUUID();
        uuidTwo = classUnderTest.getAllAuthors().get(1).getUUID();
        uuidThree = classUnderTest.getAllAuthors().get(2).getUUID();
        uuidFour = classUnderTest.getAllAuthors().get(3).getUUID();
    }

    @Test
    public void testGetAllAuthors() {
        List<Author> authors = classUnderTest.getAllAuthors();
        Assert.assertTrue("testGetAllAuthors: author size == 4, actual size: "+authors.size(), authors.size() == 4);
    }

    @Test
    public void testValidGetAuthorById() {
        Author author = classUnderTest.getAuthorByUUID(uuidOne);
        Assert.assertTrue("testValidGetAuthorById: author id != 1",author.getUUID().compareTo(uuidOne) == 0);
        Assert.assertTrue("testValidGetAuthorById: author name != Bob Marley", "Bob Marley".equals(author.getName()));
    }

    @Test
    public void testInvalidGetAuthorById() {
        UUID genUuid = UUID.randomUUID();
        Author author = classUnderTest.getAuthorByUUID(genUuid);
        Assert.assertTrue("testInvalidGetAuthorById: author != null",author == null);
    }

    @Test
    public void testValidStoreAuthor() {
        Author author = new Author("Inserted Author");
        UUID authorUUID = author.getUUID();
        classUnderTest.storeAuthor(author);

        List<Author> authors = classUnderTest.getAllAuthors();
        Assert.assertTrue("testValidStoreAuthor: author size != 4, actual size: "+authors.size(), authors.size() == 5);

        author = classUnderTest.getAuthorByUUID(authorUUID);
        Assert.assertTrue("testValidStoreAuthor: author id != 4",author.getUUID().compareTo(authorUUID) == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAuthorStore() {
        Author author = new Author("Some Author");
        author.setUUID(uuidOne);
        classUnderTest.storeAuthor(author);
    }

    @Test
    public void testValidUpdateAuthor() {
        Author author = new Author("Changed Author Name");
        author.setUUID(uuidTwo);
        classUnderTest.updateAuthor(author);

        List<Author> authors = classUnderTest.getAllAuthors();
        Assert.assertTrue("testValidUpdateAuthor: author size != 3, actual size: "+authors.size(), authors.size() == 4);

        author = classUnderTest.getAuthorByUUID(uuidTwo);
        Assert.assertTrue("testValidUpdateAuthor: author name != Changed Author Name", "Changed Author Name".equals(author.getName()));
    }

    @Test
    public void testValidUpdateAuthorNotExist() {
        Author author = new Author("This author does not exist");
        author.setUUID(uuidFour);
        classUnderTest.updateAuthor(author);

        List<Author> authors = classUnderTest.getAllAuthors();
        Assert.assertTrue("testValidUpdateAuthorNotExist: author size != 3, actual size: "+authors.size(), authors.size() == 4);

        author = classUnderTest.getAuthorByUUID(uuidFour);
        Assert.assertFalse("testValidUpdateAuthorNotExist: author != null",author == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidUpdateAuthor() {
        Author author = null;
        Boolean b = classUnderTest.updateAuthor(author);
    }

    @Test
    public void testValidDeleteAuthor() {
        boolean result = classUnderTest.deleteAuthor(classUnderTest.getAuthorByUUID(uuidOne));

        Assert.assertTrue("testValidDeleteAuthor: result = false", result);

        List<Author> authors = classUnderTest.getAllAuthors();
        Assert.assertTrue("testValidDeleteAuthor: author size != 2, actual size: "+authors.size(), authors.size() == 3);

        Author author = classUnderTest.getAuthorByUUID(uuidOne);
        Assert.assertTrue("testValidDeleteAuthor: author != null",author == null);
    }

    @Test
    public void testNotFoundDeleteAuthor() {
        boolean result = classUnderTest.deleteAuthor(classUnderTest.getAuthorByUUID(uuidFour));

        Assert.assertTrue("testNotFoundDeleteAuthor: result = true", result);

        List<Author> authors = classUnderTest.getAllAuthors();
        Assert.assertTrue("testNotFoundDeleteAuthor: author size != 3, actual size: "+authors.size(), authors.size() == 3);

        Author author = classUnderTest.getAuthorByUUID(uuidFour);
        Assert.assertTrue("testNotFoundDeleteAuthor: author != null",author == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDeleteAuthor() {
        Author a = null;
        boolean result = classUnderTest.deleteAuthor(a);
    }

    private static ArrayList<Author> getAuthorList() {
        String idOne = "493410b3-dd0b-4b78-97bf-289f50f6e74f";
        String idTwo = "593410b3-dd0b-4b78-97bf-289f50f6e74f";
        String idThree = "693410b3-dd0b-4b78-97bf-289f50f6e74f";
        String idFour = "793410b3-dd0b-4b78-97bf-289f50f6e74f";
        uuidOne = UUID.fromString(idOne);
        uuidTwo = UUID.fromString(idTwo);
        uuidThree = UUID.fromString(idThree);
        uuidFour = UUID.fromString(idFour);

        Author authorOne = new Author("Test Author");
        Author authorTwo = new Author("Another Test Author");
        Author authorThree = new Author("Some Author");

        authorOne.setUUID(uuidOne);
        authorTwo.setUUID(uuidTwo);
        authorThree.setUUID(uuidThree);

        ArrayList<Author> authors = new ArrayList<>();
        authors.add(authorOne);
        authors.add(authorTwo);
        authors.add(authorThree);
        return authors;
    }
}
