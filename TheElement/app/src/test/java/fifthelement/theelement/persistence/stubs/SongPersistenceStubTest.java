package fifthelement.theelement.persistence.stubs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Song;

@RunWith(JUnit4.class)
public class SongPersistenceStubTest {

    private SongPersistenceStub classUnderTest;
    private ArrayList<Song> songList;

    private static UUID uuidOne;
    private static UUID uuidTwo;
    private static UUID uuidThree;
    private static UUID uuidFour;

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
        Song song = classUnderTest.getSongByUUID(uuidOne);
        Assert.assertTrue("testValidGetSongById: song id != 1",song.getUUID().compareTo(uuidOne) == 0);
        Assert.assertTrue("testValidGetSongById: song name != Test Song", "Test Song".equals(song.getName()));
    }

    @Test
    public void testInvalidGetSongById() {
        Song song = classUnderTest.getSongByUUID(uuidFour);
        Assert.assertTrue("testInvalidGetSongById: song != null",song == null);
    }

    @Test
    public void testValidStoreSong() {
        Song song = new Song("Inserted Song", "");
        UUID songUUID = song.getUUID();
        classUnderTest.storeSong(song);

        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testValidStoreSong: song size != 4", songs.size() == 4);

        song = classUnderTest.getSongByUUID(songUUID);
        Assert.assertTrue("testValidStoreSong: song id != 4",song.getUUID().compareTo(songUUID) == 0);
    }

    @Test(expected = ArrayStoreException.class)
    public void testInvalidSongStore() {
        Song song = new Song("Some Song", "");
        song.setUUID(uuidOne);
        classUnderTest.storeSong(song);
    }

    @Test
    public void testValidUpdateSong() {
        Song song = new Song("Changed Song Name", "");
        song.setUUID(uuidTwo);
        classUnderTest.updateSong(song);

        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testValidUpdateSong: song size != 3", songs.size() == 3);

        song = classUnderTest.getSongByUUID(uuidTwo);
        Assert.assertTrue("testValidUpdateSong: song name != Changed Song Name", "Changed Song Name".equals(song.getName()));
    }

    @Test
    public void testValidUpdateSongNotExist() {
        Song song = new Song("This song does not exist", "");
        song.setUUID(uuidFour);
        classUnderTest.updateSong(song);

        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testValidUpdateSongNotExist: song size != 3", songs.size() == 3);

        song = classUnderTest.getSongByUUID(uuidFour);
        Assert.assertTrue("testValidUpdateSongNotExist: song != null",song == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidUpdateSong() {
        Song song = null;
        boolean result = classUnderTest.updateSong(song);
    }

    @Test
    public void testValidDeleteSong() {
        boolean result = classUnderTest.deleteSong(uuidOne);

        Assert.assertTrue("testValidDeleteSong: result = false", result);

        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testValidDeleteSong: song size != 2", songs.size() == 2);

        Song song = classUnderTest.getSongByUUID(uuidOne);
        Assert.assertTrue("testValidDeleteSong: song != null",song == null);
    }

    @Test
    public void testNotFoundDeleteSong() {
        boolean result = classUnderTest.deleteSong(uuidFour);

        Assert.assertFalse("testNotFoundDeleteSong: result = true", result);

        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testNotFoundDeleteSong: song size != 3", songs.size() == 3);

        Song song = classUnderTest.getSongByUUID(uuidFour);
        Assert.assertTrue("testNotFoundDeleteSong: song != null",song == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDeleteSong() {
        boolean result = classUnderTest.deleteSong(null);
    }

    private static ArrayList<Song> getSongList() {
        String idOne = "493410b3-dd0b-4b78-97bf-289f50f6e74f";
        String idTwo = "593410b3-dd0b-4b78-97bf-289f50f6e74f";
        String idThree = "693410b3-dd0b-4b78-97bf-289f50f6e74f";
        String idFour = "793410b3-dd0b-4b78-97bf-289f50f6e74f";
        uuidOne = UUID.fromString(idOne);
        uuidTwo = UUID.fromString(idTwo);
        uuidThree = UUID.fromString(idThree);
        uuidFour = UUID.fromString(idFour);

        Song songOne = new Song("Test Song", "");
        Song songTwo = new Song("Another Test Song", "");
        Song songThree = new Song("Some Song", "");

        songOne.setUUID(uuidOne);
        songTwo.setUUID(uuidTwo);
        songThree.setUUID(uuidThree);

        ArrayList<Song> songs = new ArrayList<>();
        songs.add(songOne);
        songs.add(songTwo);
        songs.add(songThree);
        return songs;
    }
}
