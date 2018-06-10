package fifthelement.theelement.persistence.stubs;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import fifthelement.theelement.objects.Song;

@RunWith(AndroidJUnit4.class)
public class SongPersistenceStubTest {
    private SongPersistenceStub classUnderTest;

    private ArrayList<Song> songList;

    @Before
    public void initClass() {
        songList = getSongList();
        classUnderTest = new SongPersistenceStub(songList);
    }

    @Test
    public void testGetAllSongs() {
        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testGetAllSongs: song size != 3", songs.size() == 3);
    }

    @Test
    public void testValidGetSongById() {
        Song song = classUnderTest.getSongByID(1);
        Assert.assertTrue("testValidGetSongById: song id != 1",song.getId() == 1);
        Assert.assertTrue("testValidGetSongById: song name != Test Song", "Test Song".equals(song.getName()));
    }

    @Test
    public void testInvalidGetSongById() {
        Song song = classUnderTest.getSongByID(50);
        Assert.assertTrue("testInvalidGetSongById: song != null",song == null);
    }

    @Test
    public void testValidStoreSong() {
        Song song = new Song(4, "Inserted Song");
        classUnderTest.storeSong(song);

        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testValidStoreSong: song size != 4", songs.size() == 4);

        song = classUnderTest.getSongByID(4);
        Assert.assertTrue("testValidStoreSong: song id != 4",song.getId() == 4);
    }

    @Test(expected = ArrayStoreException.class)
    public void testInvalidSongStore() {
        Song song = new Song(3, "Some Song");
        classUnderTest.storeSong(song);
    }

    @Test
    public void testValidUpdateSong() {
        Song song = new Song(1, "Changed Song Name");
        classUnderTest.updateSong(song);

        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testValidUpdateSong: song size != 3", songs.size() == 3);

        song = classUnderTest.getSongByID(1);
        Assert.assertTrue("testValidUpdateSong: song name != Changed Song Name", "Changed Song Name".equals(song.getName()));
    }

    @Test
    public void testValidUpdateSongNotExist() {
        Song song = new Song(4, "This song does not exist");
        classUnderTest.updateSong(song);

        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testValidUpdateSongNotExist: song size != 3", songs.size() == 3);

        song = classUnderTest.getSongByID(4);
        Assert.assertTrue("testValidUpdateSongNotExist: song != null",song == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidUpdateSong() {
        Song song = null;
        Song result = classUnderTest.updateSong(song);
    }

    @Test
    public void testValidDeleteSong() {
        Song song = new Song(1, "Test Song");
        boolean result = classUnderTest.deleteSong(song);

        Assert.assertTrue("testValidDeleteSong: result = false", result);

        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testValidDeleteSong: song size != 2", songs.size() == 2);

        song = classUnderTest.getSongByID(1);
        Assert.assertTrue("testValidDeleteSong: song != null",song == null);
    }

    @Test
    public void testNotFoundDeleteSong() {
        Song song = new Song(5, "Test Song");
        boolean result = classUnderTest.deleteSong(song);

        Assert.assertFalse("testNotFoundDeleteSong: result = true", result);

        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testNotFoundDeleteSong: song size != 3", songs.size() == 3);

        song = classUnderTest.getSongByID(5);
        Assert.assertTrue("testNotFoundDeleteSong: song != null",song == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDeleteSong() {
        Song song = null;
        boolean result = classUnderTest.deleteSong(song);
    }

    private static ArrayList<Song> getSongList() {
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song(1, "Test Song"));
        songs.add(new Song(2, "Another Test Song"));
        songs.add(new Song(3, "Some Song"));
        return songs;
    }
}
