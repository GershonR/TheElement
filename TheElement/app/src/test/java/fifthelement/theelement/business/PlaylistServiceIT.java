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
import fifthelement.theelement.business.exceptions.SongAlreadyExistsException;
import fifthelement.theelement.business.services.AlbumService;
import fifthelement.theelement.business.services.AuthorService;
import fifthelement.theelement.business.services.PlaylistService;
import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.PlaylistPersistence;
import fifthelement.theelement.persistence.SongPersistence;
import fifthelement.theelement.persistence.hsqldb.PlaylistPersistenceHSQLDB;
import fifthelement.theelement.persistence.hsqldb.SongPersistenceHSQLDB;
import fifthelement.theelement.persistence.stubs.AlbumPersistenceStub;
import fifthelement.theelement.persistence.stubs.AuthorPersistenceStub;
import fifthelement.theelement.persistence.stubs.PlaylistPersistenceStub;
import fifthelement.theelement.utils.TestDatabaseUtil;

public class PlaylistServiceIT {
    private PlaylistService playlistService;
    private File tempDB;

    @Before
    public void setUpTestDB() throws IOException {
        this.tempDB = TestDatabaseUtil.copyDB();
        PlaylistPersistence pp = new PlaylistPersistenceHSQLDB(Main.getDBPathName());
        SongPersistence sp = new SongPersistenceHSQLDB(Main.getDBPathName());
        playlistService = new PlaylistService(pp, sp, new SongService(sp,
                new AlbumPersistenceStub(), new AuthorPersistenceStub(), pp));
        try {
            playlistService.insertPlaylist(new Playlist("Test Playlist"));
            playlistService.insertPlaylist(new Playlist("Another Test Playlist"));
            playlistService.insertPlaylist(new Playlist("Some Other Test Playlist"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getAllPlaylistsTest() {
        List<Playlist> playlists = playlistService.getAllPlaylists(); // Database has 3

        Assert.assertTrue("getAllPlaylistsTest: playlist size != 3", playlists.size() == 3);
    }

    @Test
    public void insertPlaylistValidTest() {
        Playlist playlist = new Playlist("Bad");
        playlistService.insertPlaylist(playlist);

        Assert.assertTrue("insertAuthorValidTest: playlist size != 4", playlistService.getAllPlaylists().size() == 4);
    }


    @Test(expected = IllegalArgumentException.class)
    public void insertPlaylistInValidTest() {
        Playlist playlist = null;
        playlistService.insertPlaylist(playlist);
    }

    @Test
    public void insertPlaylistMultipleValidTest() {
        Playlist playlistOne = new Playlist("21");
        playlistOne.setId(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Playlist playlistTwo = new Playlist("Gold");
        playlistTwo.setId(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        boolean insertReturn = playlistService.insertPlaylist(playlistOne);

        Assert.assertTrue("updatePlaylistValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("updatePlaylistValidTest: playlist size != 4", playlistService.getAllPlaylists().size() == 4);

        Playlist playlist = playlistService.getPlaylistByUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
    }

    @Test
    public void deletePlaylistValidTest() {
        Playlist playlistOne = new Playlist("21");
        UUID playlistUUID = UUID.fromString("793410b3-dd0b-4b78-97bf-289f50f6e74f");
        playlistOne.setId(playlistUUID);

        boolean insertReturn = playlistService.insertPlaylist(playlistOne);
        Assert.assertTrue("deletePlaylistValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("deletePlaylistValidTest: song size != 4", playlistService.getAllPlaylists().size() == 4);

        boolean deleteReturn = playlistService.deletePlaylist(playlistOne);
        Assert.assertTrue("deletePlaylistValidTest: deleteReturn != true", deleteReturn);
        Assert.assertTrue("deletePlaylistValidTest: song size != 3", playlistService.getAllPlaylists().size() == 3);

        Playlist deletedPlaylist = playlistService.getPlaylistByUUID(playlistUUID);
        Assert.assertNull("deletePlaylistValidTest: deletedPlaylist != null", deletedPlaylist);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deletePlaylistInValidTest() {
        Playlist playlist = null;
        playlistService.deletePlaylist(playlist);
    }

    @Test
    public void deletePlaylistNotExistTest() {
        Playlist playlist = new Playlist("Led Zepplin");
        boolean result = playlistService.deletePlaylist(playlist);
        Assert.assertFalse("deletePlaylistNotExistTest: result != false", result);
        Assert.assertTrue("deletePlaylistNotExistTest: playlist size != 3", playlistService.getAllPlaylists().size() == 3);
    }

    @Test
    public void updatePlaylistValidTest() {
        Playlist playlist = new Playlist("Led Zepplin");
        playlist.setId(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        UUID playlistUUID = playlist.getUUID();

        boolean insertReturn = playlistService.insertPlaylist(playlist);
        Assert.assertTrue("updatePlaylistValidTest: insertReturn != true", insertReturn);

        boolean result = playlistService.updatePlaylist(playlist, "Barney");

        Playlist playlistFound = playlistService.getPlaylistByUUID(playlistUUID);

        Assert.assertTrue("updatePlaylistValidTest: result != true", result);
        Assert.assertTrue("updatePlaylistValidTest name != Barney", playlistFound.getName().equals("Barney"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updatePlaylistInValidTest() {
        Playlist playlist = null;

        boolean result = playlistService.updatePlaylist(playlist, "Fred");
    }

    @Test
    public void insertSongForPlaylistValidTest() {
        Playlist playlist = new Playlist("Led Zepplin");
        playlist.setId(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Song song = new Song("Test", "");

        try {
            Services.getSongService().insertSong(song);
        } catch (SongAlreadyExistsException e) {
            e.printStackTrace();
        }

        boolean insertReturn = playlistService.insertPlaylist(playlist);
        Assert.assertTrue("insertSongForPlaylistValidTest: insertReturn != true", insertReturn);

        boolean result = playlistService.insertSongForPlaylist(playlist, song);
        Assert.assertTrue("insertSongForPlaylistValidTest: result != true", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertSongForPlaylistInValidTest() {
        Playlist playlist = null;
        Song song = null;

        boolean result = playlistService.insertSongForPlaylist(playlist, song);
    }

    @After
    public void tearDownTestDB() {
        TestDatabaseUtil.killDB(tempDB);
    }
}
