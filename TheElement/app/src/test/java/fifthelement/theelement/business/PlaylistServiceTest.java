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
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.SongPersistence;
import fifthelement.theelement.persistence.stubs.PlaylistPersistenceStub;
import fifthelement.theelement.persistence.stubs.SongPersistenceStub;

@RunWith(JUnit4.class)
public class PlaylistServiceTest {
    PlaylistService classUnderTest;
    SongPersistence songPersistence;

    @Before
    public void setup() {
        songPersistence = new SongPersistenceStub();
        classUnderTest = new PlaylistService(new PlaylistPersistenceStub(), songPersistence);
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
    public void insertPlaylistMultipleValidTest() {
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

    @Test
    public void updatePlaylistValidTest() {
        Playlist playlist = new Playlist("Led Zepplin");
        playlist.setId(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        UUID playlistUUID = playlist.getUUID();

        boolean insertReturn = classUnderTest.insertPlaylist(playlist);
        Assert.assertTrue("updatePlaylistValidTest: insertReturn != true", insertReturn);

        boolean result = classUnderTest.updatePlaylist(playlist, "Barney");

        Playlist playlistFound = classUnderTest.getPlaylistByUUID(playlistUUID);

        Assert.assertTrue("updatePlaylistValidTest: result != true", result);
        Assert.assertTrue("updatePlaylistValidTest name != Barney", playlistFound.getName().equals("Barney"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updatePlaylistInValidTest() {
        Playlist playlist = null;

        boolean result = classUnderTest.updatePlaylist(playlist, "Fred");
    }

    @Test
    public void insertSongForPlaylistValidTest() {
        Playlist playlist = new Playlist("Led Zepplin");
        playlist.setId(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Song song = new Song("Test", "");

        songPersistence.storeSong(song); //Song needs to exist in songPersistence for this test to pass

        boolean insertReturn = classUnderTest.insertPlaylist(playlist);
        Assert.assertTrue("insertSongForPlaylistValidTest: insertReturn != true", insertReturn);

        boolean result = classUnderTest.insertSongForPlaylist(playlist, song);
        Assert.assertTrue("insertSongForPlaylistValidTest: result != true", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertSongForPlaylistInValidTest() {
        Playlist playlist = null;
        Song song = null;

        boolean result = classUnderTest.insertSongForPlaylist(playlist, song);
    }

}
