package fifthelement.theelement.business;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.services.AlbumService;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.utils.TestDatabaseUtil;

public class AlbumServiceIT {
    private AlbumService albumService;
    private File tempDB;

    @Before
    public void setUpTestDB() throws IOException {
        this.tempDB = TestDatabaseUtil.copyDB();
        albumService = Services.getAlbumService();
    }

    @Test
    public void getAllAlbumsTest() {
        List<Album> albums = albumService.getAlbums(); // Database has 1

        Assert.assertTrue("getAllAlbumsTest: album size != 1", albums.size() == 1);
    }

    @Test
    public void insertAlbumValidTest() {
        Album album = new Album("Bad");
        albumService.insertAlbum(album);

        Assert.assertTrue("insertAuthorValidTest: album size != 2", albumService.getAlbums().size() == 2);
    }


    @Test(expected = IllegalArgumentException.class)
    public void insertAlbumInValidTest() {
        Album album = null;
        albumService.insertAlbum(album);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertAlbumDuplicateTest() {
        Album albumOne = new Album("21");
        albumOne.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Album albumTwo = new Album("Gold");
        albumTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        albumService.insertAlbum(albumOne);
        albumService.insertAlbum(albumTwo);
    }

    @Test
    public void updateAlbumValidTest() {
        Album albumOne = new Album("21");
        albumOne.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Album albumTwo = new Album("Gold");
        albumTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        boolean insertReturn = albumService.insertAlbum(albumOne);
        boolean updateReturn = albumService.updateAlbum(albumTwo);

        Assert.assertTrue("updateAlbumValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("updateAlbumValidTest: updateReturn != true", updateReturn);
        Assert.assertTrue("updateAlbumValidTest: album size != 2", albumService.getAlbums().size() == 2);

        Album album = albumService.getAlbumByUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Assert.assertTrue("updateAlbumValidTest: album name != Changed Album Name", "Gold".equals(album.getName()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateAlbumInValidTest() {
        Album album = null;
        albumService.updateAlbum(album);
    }

    @Test
    public void updateAlbumNotExistTest() {
        Album album = new Album("Gold");
        boolean result = albumService.updateAlbum(album);
        Assert.assertFalse("updateAlbumNotExistTest: result != false", result);
        Assert.assertTrue("updateAlbumNotExistTest: song size != 1", albumService.getAlbums().size() == 1);

    }

    @Test
    public void deleteAlbumValidTest() {
        Album albumOne = new Album("21");
        UUID albumUUID = UUID.fromString("793410b3-dd0b-4b78-97bf-289f50f6e74f");
        albumOne.setUUID(albumUUID);

        boolean insertReturn = albumService.insertAlbum(albumOne);
        Assert.assertTrue("deleteAlbumValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("deleteAlbumValidTest: song size != 2", albumService.getAlbums().size() == 2);

        boolean deleteReturn = albumService.deleteAlbum(albumOne);
        Assert.assertTrue("deleteAlbumValidTest: deleteReturn != true", deleteReturn);
        Assert.assertTrue("deleteAlbumValidTest: song size != 1", albumService.getAlbums().size() == 1);

        Album deletedAlbum = albumService.getAlbumByUUID(albumUUID);
        Assert.assertNull("deleteAlbumValidTest: deletedAlbum != null", deletedAlbum);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteAlbumInValidTest() {
        Album album = null;
        albumService.deleteAlbum(album);
    }

    @Test
    public void deleteAlbumNotExistTest() {
        Album album = new Album("Led Zepplin");
        boolean result = albumService.deleteAlbum(album);
        Assert.assertFalse("deleteAlbumNotExistTest: result != false", result);
        Assert.assertTrue("deleteAlbumNotExistTest: album size != 1", albumService.getAlbums().size() == 1);
    }

    @After
    public void tearDownTestDB() {
        TestDatabaseUtil.killDB(tempDB);
        Persistence.resetPersistence();
        Services.resetServices();
    }
}
