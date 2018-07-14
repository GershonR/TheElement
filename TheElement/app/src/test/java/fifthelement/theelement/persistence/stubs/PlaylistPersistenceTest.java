package fifthelement.theelement.persistence.stubs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Playlist;

@RunWith(JUnit4.class)
public class PlaylistPersistenceTest {

    private PlaylistPersistenceStub classUnderTest;
    private List<Playlist> playlists;

    private static UUID uuidOne;
    private static UUID uuidTwo;
    private static UUID uuidThree;
    private static UUID uuidFour;

    @Before
    public void initClass() {
        classUnderTest = new PlaylistPersistenceStub();
        playlists = classUnderTest.getAllPlaylists();
        uuidOne = classUnderTest.getAllPlaylists().get(0).getUUID();
        uuidTwo = classUnderTest.getAllPlaylists().get(1).getUUID();
        uuidThree = classUnderTest.getAllPlaylists().get(2).getUUID();
        uuidFour = classUnderTest.getAllPlaylists().get(3).getUUID();


    }

    @Test
    public void testGetAllPlaylists() {
        List<Playlist> playlists = classUnderTest.getAllPlaylists();
        Assert.assertTrue("testGetAllPlaylists: playlist size == 4, actual size: "+playlists.size(), playlists.size() == 4);
    }

    @Test
    public void testValidGetPlaylistById() {
        Playlist playlist = classUnderTest.getPlaylistByUUID(uuidOne);
        Assert.assertTrue("testValidGetPlaylistById: playlist id != 1",playlist.getUUID().compareTo(uuidOne) == 0);
        Assert.assertTrue("testValidGetPlaylistById: playlist name != Playlist1", "Actual Playlist".equals(playlist.getName()));
    }

    @Test
    public void testInvalidGetPlaylistById() {
        UUID genUuid = UUID.randomUUID();
        Playlist playlist = classUnderTest.getPlaylistByUUID(genUuid);
        Assert.assertTrue("testInvalidGetPlaylistById: playlist != null",playlist == null);
    }

    @Test
    public void testValidStorePlaylist() {
        Playlist playlist = new Playlist("Inserted Playlist");
        UUID playlistUUID = playlist.getUUID();
        classUnderTest.storePlaylist(playlist);

        List<Playlist> playlists = classUnderTest.getAllPlaylists();
        Assert.assertTrue("testValidStorePlaylist: playlist size != 4, actual size: "+playlists.size(), playlists.size() == 5);

        playlist = classUnderTest.getPlaylistByUUID(playlistUUID);
        Assert.assertTrue("testValidStorePlaylist: playlist id != 4",playlist.getUUID().compareTo(playlistUUID) == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPlaylistStore() {
        Playlist playlist = new Playlist("Some Playlist");
        playlist.setId(uuidOne);
        classUnderTest.storePlaylist(playlist);
    }

    @Test
    public void testValidUpdatePlaylist() {
        Playlist playlist = new Playlist("Changed Playlist Name");
        playlist.setId(uuidTwo);
        String newName = "Nice";
        classUnderTest.updatePlaylist(playlist, newName);

        List<Playlist> playlists = classUnderTest.getAllPlaylists();
        Assert.assertTrue("testValidUpdatePlaylist: playlist size != 3, actual size: "+playlists.size(), playlists.size() == 4);
        Assert.assertTrue("testValidUpdatePlaylist: new name = Nice", playlist.getName().equals(newName));
        playlist = classUnderTest.getPlaylistByUUID(uuidTwo);
        Assert.assertFalse("testValidUpdatePlaylist: playlist name != Changed Playlist Name", "Changed Playlist Name".equals(playlist.getName()));
    }

    @Test
    public void testValidUpdatePlaylistNotExist() {
        Playlist playlist = new Playlist("This playlist does not exist");
        playlist.setId(uuidFour);
        classUnderTest.updatePlaylist(playlist, "Nice");

        List<Playlist> playlists = classUnderTest.getAllPlaylists();
        Assert.assertTrue("testValidUpdatePlaylistNotExist: playlist size != 3, actual size: "+playlists.size(), playlists.size() == 4);
        Assert.assertTrue("testValidUpdatePlaylistNotExist: playlistName != Nice", playlist.getName().equals("Nice"));
        playlist = classUnderTest.getPlaylistByUUID(uuidFour);
        Assert.assertFalse("testValidUpdatePlaylistNotExist: playlist != null",playlist == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidUpdatePlaylist() {
        Playlist playlist = null;
        if( classUnderTest.updatePlaylist(playlist, "Whoa") ) {
            Playlist a = playlist;
        }
    }

    @Test
    public void testValidDeletePlaylist() {
        boolean result = classUnderTest.deletePlaylist(classUnderTest.getPlaylistByUUID(uuidOne));

        Assert.assertTrue("testValidDeletePlaylist: result = false", result);

        List<Playlist> playlists = classUnderTest.getAllPlaylists();
        Assert.assertTrue("testValidDeletePlaylist: playlist size != 2, actual size: "+playlists.size(), playlists.size() == 3);

        Playlist playlist = classUnderTest.getPlaylistByUUID(uuidOne);
        Assert.assertTrue("testValidDeletePlaylist: playlist != null",playlist == null);
    }

    @Test
    public void testNotFoundDeletePlaylist() {
        boolean result = classUnderTest.deletePlaylist(classUnderTest.getPlaylistByUUID(uuidFour));

        Assert.assertTrue("testNotFoundDeletePlaylist: result = true", result);

        List<Playlist> playlists = classUnderTest.getAllPlaylists();
        Assert.assertTrue("testNotFoundDeletePlaylist: playlist size != 3, actual size: "+playlists.size(), playlists.size() == 3);

        Playlist playlist = classUnderTest.getPlaylistByUUID(uuidFour);
        Assert.assertTrue("testNotFoundDeletePlaylist: playlist != null",playlist == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDeletePlaylist() {
        Playlist a = null;
        boolean result = classUnderTest.deletePlaylist(a);
    }

    private static ArrayList<Playlist> getPlaylistList() {
        String idOne = "493410b3-dd0b-4b78-97bf-289f50f6e74f";
        String idTwo = "593410b3-dd0b-4b78-97bf-289f50f6e74f";
        String idThree = "693410b3-dd0b-4b78-97bf-289f50f6e74f";
        String idFour = "793410b3-dd0b-4b78-97bf-289f50f6e74f";
        uuidOne = UUID.fromString(idOne);
        uuidTwo = UUID.fromString(idTwo);
        uuidThree = UUID.fromString(idThree);
        uuidFour = UUID.fromString(idFour);

        Playlist playlistOne = new Playlist("Test Playlist");
        Playlist playlistTwo = new Playlist("Another Test Playlist");
        Playlist playlistThree = new Playlist("Some Playlist");

        playlistOne.setId(uuidOne);
        playlistTwo.setId(uuidTwo);
        playlistThree.setId(uuidThree);

        ArrayList<Playlist> playlists = new ArrayList<>();
        playlists.add(playlistOne);
        playlists.add(playlistTwo);
        playlists.add(playlistThree);
        return playlists;
    }
}
