package fifthelement.theelement.business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.business.services.PlaylistService;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.persistence.stubs.PlaylistPersistenceStub;

@RunWith(JUnit4.class)
public class PlaylistServiceTest {
    PlaylistService classUnderTest;

    @Before
    public void setup() {
        classUnderTest = new PlaylistService(new PlaylistPersistenceStub());
        classUnderTest.getAllPlaylists().clear();

        classUnderTest.insertPlaylist(new Playlist("Thriller"));
        classUnderTest.insertPlaylist(new Playlist("The Wall"));
        classUnderTest.insertPlaylist(new Playlist("Hotel California"));
    }


    @Test
    public void getAllPlaylistsTest() {
        List<Playlist> playlists = classUnderTest.getAllPlaylists(); // Stub creates 3

        Assert.assertTrue("getAllPlaylistsTest: playlist size != 3", playlists.size() == 3);
    }

    @Test
    public void insertPlaylistValidTest() {
        Playlist playlist = new Playlist("Bad");
        classUnderTest.insertPlaylist(playlist);

        Assert.assertTrue("insertAuthorValidTest: playlist size != 4", classUnderTest.getAllPlaylists().size() == 4);
    }


    @Test(expected = IllegalArgumentException.class)
    public void insertPlaylistInValidTest() {
        Playlist playlist = null;
        classUnderTest.insertPlaylist(playlist);
    }

    @Test
    public void updatePlaylistValidTest() {
        Playlist playlistOne = new Playlist("21");
        playlistOne.setId(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Playlist playlistTwo = new Playlist("Gold");
        playlistTwo.setId(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        boolean insertReturn = classUnderTest.insertPlaylist(playlistOne);

        Assert.assertTrue("updatePlaylistValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("updatePlaylistValidTest: playlist size != 4", classUnderTest.getAllPlaylists().size() == 4);

        Playlist playlist = classUnderTest.getPlaylistByUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
    }

    @Test
    public void deletePlaylistValidTest() {
        Playlist playlistOne = new Playlist("21");
        UUID playlistUUID = UUID.fromString("793410b3-dd0b-4b78-97bf-289f50f6e74f");
        playlistOne.setId(playlistUUID);

        boolean insertReturn = classUnderTest.insertPlaylist(playlistOne);
        Assert.assertTrue("deletePlaylistValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("deletePlaylistValidTest: song size != 4", classUnderTest.getAllPlaylists().size() == 4);

        boolean deleteReturn = classUnderTest.deletePlaylist(playlistOne);
        Assert.assertTrue("deletePlaylistValidTest: deleteReturn != true", deleteReturn);
        Assert.assertTrue("deletePlaylistValidTest: song size != 3", classUnderTest.getAllPlaylists().size() == 3);

        Playlist deletedPlaylist = classUnderTest.getPlaylistByUUID(playlistUUID);
        Assert.assertNull("deletePlaylistValidTest: deletedPlaylist != null", deletedPlaylist);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deletePlaylistInValidTest() {
        Playlist playlist = null;
        classUnderTest.deletePlaylist(playlist);
    }

    @Test
    public void deletePlaylistNotExistTest() {
        Playlist playlist = new Playlist("Led Zepplin");
        boolean result = classUnderTest.deletePlaylist(playlist);
        Assert.assertFalse("deletePlaylistNotExistTest: result != false", result);
        Assert.assertTrue("deletePlaylistNotExistTest: playlist size != 3", classUnderTest.getAllPlaylists().size() == 3);
    }

}
