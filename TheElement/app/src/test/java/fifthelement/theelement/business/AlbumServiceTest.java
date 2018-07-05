package fifthelement.theelement.business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.business.services.AlbumService;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.persistence.stubs.AlbumPersistenceStub;
import fifthelement.theelement.persistence.stubs.SongPersistenceStub;
import fifthelement.theelement.presentation.util.SongUtil;

@RunWith(JUnit4.class)
public class AlbumServiceTest {
    AlbumService classUnderTest;

    @Before
    public void setup() {
        classUnderTest = new AlbumService(new AlbumPersistenceStub(), new SongPersistenceStub());
        classUnderTest.getAlbums().clear();

        classUnderTest.insertAlbum(new Album("Thriller"));
        classUnderTest.insertAlbum(new Album("The Wall"));
        classUnderTest.insertAlbum(new Album("Hotel California"));
    }


    @Test
    public void getAllAlbumsTest() {
        List<Album> albums = classUnderTest.getAlbums(); // Stub creates 3

        Assert.assertTrue("getAllAlbumsTest: album size != 3", albums.size() == 3);
    }

    @Test
    public void insertAlbumValidTest() {
        Album album = new Album("Bad");
        classUnderTest.insertAlbum(album);

        Assert.assertTrue("insertAuthorValidTest: album size != 4", classUnderTest.getAlbums().size() == 4);
    }


    @Test(expected = IllegalArgumentException.class)
    public void insertAlbumInValidTest() {
        Album album = null;
        classUnderTest.insertAlbum(album);
    }

    @Test(expected = ArrayStoreException.class)
    public void insertAlbumDuplicateTest() {
        Album albumOne = new Album("21");
        albumOne.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Album albumTwo = new Album("Gold");
        albumTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        classUnderTest.insertAlbum(albumOne);
        classUnderTest.insertAlbum(albumTwo);
    }

    @Test
    public void updateAlbumValidTest() {
        Album albumOne = new Album("21");
        albumOne.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Album albumTwo = new Album("Gold");
        albumTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        boolean insertReturn = classUnderTest.insertAlbum(albumOne);
        boolean updateReturn = classUnderTest.updateAlbum(albumTwo);

        Assert.assertTrue("updateAlbumValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("updateAlbumValidTest: updateReturn != true", updateReturn);
        Assert.assertTrue("updateAlbumValidTest: album size != 4", classUnderTest.getAlbums().size() == 4);

        Album album = classUnderTest.getAlbumByUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Assert.assertTrue("updateAlbumValidTest: album name != Changed Album Name", "Gold".equals(album.getName()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateAlbumInValidTest() {
        Album album = null;
        classUnderTest.updateAlbum(album);
    }

    @Test
    public void updateAlbumNotExistTest() {
        Album album = new Album("Gold");
        boolean result = classUnderTest.updateAlbum(album);
        Assert.assertFalse("updateAlbumNotExistTest: result != false", result);
        Assert.assertTrue("updateAlbumNotExistTest: song size != 3", classUnderTest.getAlbums().size() == 3);

    }

    @Test
    public void deleteAlbumValidTest() {
        Album albumOne = new Album("21");
        UUID albumUUID = UUID.fromString("793410b3-dd0b-4b78-97bf-289f50f6e74f");
        albumOne.setUUID(albumUUID);

        boolean insertReturn = classUnderTest.insertAlbum(albumOne);
        Assert.assertTrue("deleteAlbumValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("deleteAlbumValidTest: song size != 4", classUnderTest.getAlbums().size() == 4);

        boolean deleteReturn = classUnderTest.deleteAlbum(albumOne);
        Assert.assertTrue("deleteAlbumValidTest: deleteReturn != true", deleteReturn);
        Assert.assertTrue("deleteAlbumValidTest: song size != 3", classUnderTest.getAlbums().size() == 3);

        Album deletedAlbum = classUnderTest.getAlbumByUUID(albumUUID);
        Assert.assertNull("deleteAlbumValidTest: deletedAlbum != null", deletedAlbum);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteAlbumInValidTest() {
        Album album = null;
        classUnderTest.deleteAlbum(album);
    }

    @Test
    public void deleteAlbumNotExistTest() {
        Album album = new Album("Led Zepplin");
        boolean result = classUnderTest.deleteAlbum(album);
        Assert.assertFalse("deleteAlbumNotExistTest: result != false", result);
        Assert.assertTrue("deleteAlbumNotExistTest: album size != 3", classUnderTest.getAlbums().size() == 3);
    }

    @Test
    public void albumStatsTest_SetterGetterTest() {       // initializing them
        List<Album> albumList = classUnderTest.getAlbums();
        int i = 0;
        for( Album album : albumList ) {
            album.setNumPlayed(i);
            Assert.assertEquals(album.getNumPlayed(),i);
            i++;
        }
    }

    @Test
    public void albumStatsTest_getMostPlayedAlbum() {
        List<Album> albumList = classUnderTest.getAlbums();
        int i = 8;
        for( Album album : albumList ) {
            album.setNumPlayed(i);
            Assert.assertEquals(album.getNumPlayed(),i);
            i--;
        }
        Album mostPlayedExpected = albumList.get(1);
        mostPlayedExpected.setNumPlayed(10);
        Album mostPlayedActual = classUnderTest.getMostPlayedAlbum();
        System.out.print(mostPlayedExpected.getNumPlayed() + " " + mostPlayedActual.getNumPlayed());
        Assert.assertEquals(mostPlayedExpected.getNumPlayed(), mostPlayedActual.getNumPlayed());
    }

    @Test
    public void albumStatsTest_getTotalAlbumPlays() {
        List<Album> albumList = classUnderTest.getAlbums();
        int i = 0;
        int expected = 0;
        for( Album album : albumList ) {
            album.setNumPlayed(i);
            expected += i++;
        }
        int actual = classUnderTest.getTotalAlbumPlays();
        Assert.assertEquals(expected, actual);
    }

}
