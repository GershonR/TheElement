package fifthelement.theelement.persistence.stubs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Album;

@RunWith(JUnit4.class)
public class AlbumPersistenceStubTest {

    private AlbumPersistenceStub classUnderTest;
    private ArrayList<Album> albumList;

    private static UUID uuidOne;
    private static UUID uuidTwo;
    private static UUID uuidThree;
    private static UUID uuidFour;

    @Before
    public void initClass() {
        albumList = getAlbumList();
        classUnderTest = new AlbumPersistenceStub();
        uuidOne = classUnderTest.getAllAlbums().get(0).getUUID();
        uuidTwo = classUnderTest.getAllAlbums().get(1).getUUID();
        uuidThree = classUnderTest.getAllAlbums().get(2).getUUID();
        uuidFour = classUnderTest.getAllAlbums().get(3).getUUID();


    }

    @Test
    public void testGetAllAlbums() {
        List<Album> albums = classUnderTest.getAllAlbums();
        Assert.assertTrue("testGetAllAlbums: album size == 4, actual size: "+albums.size(), albums.size() == 4);
    }

    @Test
    public void testValidGetAlbumById() {
        Album album = classUnderTest.getAlbumByUUID(uuidOne);
        Assert.assertTrue("testValidGetAlbumById: album id != 1",album.getUUID().compareTo(uuidOne) == 0);
        Assert.assertTrue("testValidGetAlbumById: album name != Album1", "Album1".equals(album.getName()));
    }

    @Test
    public void testInvalidGetAlbumById() {
        UUID genUuid = UUID.randomUUID();
        Album album = classUnderTest.getAlbumByUUID(genUuid);
        Assert.assertTrue("testInvalidGetAlbumById: album != null",album == null);
    }

    @Test
    public void testValidStoreAlbum() {
        Album album = new Album("Inserted Album");
        UUID albumUUID = album.getUUID();
        classUnderTest.storeAlbum(album);

        List<Album> albums = classUnderTest.getAllAlbums();
        Assert.assertTrue("testValidStoreAlbum: album size != 4, actual size: "+albums.size(), albums.size() == 5);

        album = classUnderTest.getAlbumByUUID(albumUUID);
        Assert.assertTrue("testValidStoreAlbum: album id != 4",album.getUUID().compareTo(albumUUID) == 0);
    }

    @Test(expected = ArrayStoreException.class)
    public void testInvalidAlbumStore() {
        Album album = new Album("Some Album");
        album.setUUID(uuidOne);
        classUnderTest.storeAlbum(album);
    }

    @Test
    public void testValidUpdateAlbum() {
        Album album = new Album("Changed Album Name");
        album.setUUID(uuidTwo);
        classUnderTest.updateAlbum(album);

        List<Album> albums = classUnderTest.getAllAlbums();
        Assert.assertTrue("testValidUpdateAlbum: album size != 3, actual size: "+albums.size(), albums.size() == 4);

        album = classUnderTest.getAlbumByUUID(uuidTwo);
        Assert.assertTrue("testValidUpdateAlbum: album name != Changed Album Name", "Changed Album Name".equals(album.getName()));
    }

    @Test
    public void testValidUpdateAlbumNotExist() {
        Album album = new Album("This album does not exist");
        album.setUUID(uuidFour);
        classUnderTest.updateAlbum(album);

        List<Album> albums = classUnderTest.getAllAlbums();
        Assert.assertTrue("testValidUpdateAlbumNotExist: album size != 3, actual size: "+albums.size(), albums.size() == 4);

        album = classUnderTest.getAlbumByUUID(uuidFour);
        Assert.assertFalse("testValidUpdateAlbumNotExist: album != null",album == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidUpdateAlbum() {
        Album album = null;
        Album a = classUnderTest.updateAlbum(album);
    }

    @Test
    public void testValidDeleteAlbum() {
        boolean result = classUnderTest.deleteAlbum(classUnderTest.getAlbumByUUID(uuidOne));

        Assert.assertTrue("testValidDeleteAlbum: result = false", result);

        List<Album> albums = classUnderTest.getAllAlbums();
        Assert.assertTrue("testValidDeleteAlbum: album size != 2, actual size: "+albums.size(), albums.size() == 3);

        Album album = classUnderTest.getAlbumByUUID(uuidOne);
        Assert.assertTrue("testValidDeleteAlbum: album != null",album == null);
    }

    @Test
    public void testNotFoundDeleteAlbum() {
        boolean result = classUnderTest.deleteAlbum(classUnderTest.getAlbumByUUID(uuidFour));

        Assert.assertTrue("testNotFoundDeleteAlbum: result = true", result);

        List<Album> albums = classUnderTest.getAllAlbums();
        Assert.assertTrue("testNotFoundDeleteAlbum: album size != 3, actual size: "+albums.size(), albums.size() == 3);

        Album album = classUnderTest.getAlbumByUUID(uuidFour);
        Assert.assertTrue("testNotFoundDeleteAlbum: album != null",album == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDeleteAlbum() {
        boolean result = classUnderTest.deleteAlbum(null);
    }

    private static ArrayList<Album> getAlbumList() {
        String idOne = "493410b3-dd0b-4b78-97bf-289f50f6e74f";
        String idTwo = "593410b3-dd0b-4b78-97bf-289f50f6e74f";
        String idThree = "693410b3-dd0b-4b78-97bf-289f50f6e74f";
        String idFour = "793410b3-dd0b-4b78-97bf-289f50f6e74f";
        uuidOne = UUID.fromString(idOne);
        uuidTwo = UUID.fromString(idTwo);
        uuidThree = UUID.fromString(idThree);
        uuidFour = UUID.fromString(idFour);

        Album albumOne = new Album("Test Album");
        Album albumTwo = new Album("Another Test Album");
        Album albumThree = new Album("Some Album");

        albumOne.setUUID(uuidOne);
        albumTwo.setUUID(uuidTwo);
        albumThree.setUUID(uuidThree);

        ArrayList<Album> albums = new ArrayList<>();
        albums.add(albumOne);
        albums.add(albumTwo);
        albums.add(albumThree);
        return albums;
    }
}
