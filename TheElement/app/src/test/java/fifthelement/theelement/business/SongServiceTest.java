package fifthelement.theelement.business;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.business.exceptions.SongAlreadyExistsException;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.stubs.AlbumPersistenceStub;
import fifthelement.theelement.persistence.stubs.AuthorPersistenceStub;
import fifthelement.theelement.persistence.stubs.PlaylistPersistenceStub;
import fifthelement.theelement.persistence.stubs.SongPersistenceStub;

import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class SongServiceTest {
    private List<Song> songsList;
    private SongService classUnderTest;

    @Before
    public void setup() {

        classUnderTest = new SongService(new SongPersistenceStub(), new AlbumPersistenceStub(), new AuthorPersistenceStub(), new PlaylistPersistenceStub());
        List<Song> songs = classUnderTest.getSongs();
        classUnderTest.getSongs().clear();
        this.songsList = classUnderTest.getSongs();
        songsList.add(new Song( "Pristine", "data/song1"));
        songsList.add(new Song( "This is America", "data/song2"));
        songsList.add(new Song( "Nice For What", "data/song3"));
        songsList.add(new Song( "Geyser", "data/song4"));
        songsList.add(new Song( "Purity", "data/song5"));
    }

    @Test
    public void getAllSongsTest() {
        List<Song> albums = classUnderTest.getSongs(); // Stub creates 3

        Assert.assertTrue("getAllSongsTest: song size != 5", albums.size() == 5);
    }

    @Test
    public void insertSongValidTest() throws SongAlreadyExistsException {
        Song song = new Song("Some song", "Path");
        classUnderTest.insertSong(song);

        Assert.assertTrue("insertSongValidTest: song size != 6", classUnderTest.getSongs().size() == 6);
    }


    @Test(expected = IllegalArgumentException.class)
    public void insertSongInValidTest() throws SongAlreadyExistsException {
        Song song = null;

        classUnderTest.insertSong(song);
    }

    @Test(expected = SongAlreadyExistsException.class)
    public void insertSongDuplicateTest() throws SongAlreadyExistsException{
        Song songOne = new Song("A song", "Path");
        songOne.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Song songTwo = new Song("Other song", "Path");
        songTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        classUnderTest.insertSong(songOne);
        classUnderTest.insertSong(songTwo);
    }

    @Test
    public void updateSongValidTest() throws Exception {
        Song songOne = new Song("A song", "Path");
        songOne.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Song songTwo = new Song("Other song", "Path");
        songTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        boolean insertReturn = classUnderTest.insertSong(songOne);
        boolean updateReturn = classUnderTest.updateSong(songTwo);

        Assert.assertTrue("updateSongValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("updateSongValidTest: updateReturn != true", updateReturn);
        Assert.assertTrue("updateSongValidTest: album size != 6", classUnderTest.getSongs().size() == 6);

        Song song = classUnderTest.getSongByUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Assert.assertTrue("updateSongValidTest: song name != Changed Song Name", "Other song".equals(song.getName()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateSongInValidTest() {
        Song song = null;
        classUnderTest.updateSong(song);
    }

    @Test
    public void updateSongNotExistTest() {
        Song song = new Song("Gold", "Path");
        boolean result = classUnderTest.updateSong(song);
        Assert.assertFalse("updateSongNotExistTest: result != false", result);
        Assert.assertTrue("updateSongNotExistTest: song size != 5", classUnderTest.getSongs().size() == 5);

    }

    @Test
    public void deleteSongValidTest() throws Exception {
        Song songOne = new Song("21", "Path");
        UUID songUUID = UUID.fromString("793410b3-dd0b-4b78-97bf-289f50f6e74f");
        songOne.setUUID(songUUID);
        boolean insertReturn = classUnderTest.insertSong(songOne);

        Assert.assertTrue("deleteSongValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("deleteSongValidTest: song size != 6", classUnderTest.getSongs().size() == 6);

        boolean deleteReturn = classUnderTest.deleteSong(songOne);
        Assert.assertTrue("deleteSongValidTest: deleteReturn != true", deleteReturn);
        Assert.assertTrue("deleteSongValidTest: song size != 5", classUnderTest.getSongs().size() == 5);

        Song deletedSong = classUnderTest.getSongByUUID(songUUID);
        Assert.assertNull("deleteSongValidTest: deletedSong != null", deletedSong);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteSongInValidTest() {
        Song song = null;
        classUnderTest.deleteSong(song);
    }

    @Test
    public void deleteSongNotExistTest() {
        Song song = new Song("Gold", "Path");
        boolean result = classUnderTest.deleteSong(song);
        Assert.assertFalse("deleteSongNotExistTest: result != false", result);
        Assert.assertTrue("deleteSongNotExistTest: album size != 5", classUnderTest.getSongs().size() == 5);
    }

    @Test
    public void sortSongListSize() {
        //sort list
       classUnderTest.sortSongs(songsList);

        Assert.assertTrue("Size of list after sorting != 5", songsList.size() == 5);
    }

    @Test
    public void sortSongOrderTest() {
        //sort list
        classUnderTest.sortSongs(songsList);

        Assert.assertTrue("List after sort doesn't match to the sorted list", songsList.get(0).getName().equals("Geyser"));
        Assert.assertTrue("List after sort doesn't match to the sorted list", songsList.get(1).getName().equals("Nice For What"));
        Assert.assertTrue("List after sort doesn't match to the sorted list", songsList.get(2).getName().equals("Pristine"));
        Assert.assertTrue("List after sort doesn't match to the sorted list", songsList.get(3).getName().equals("Purity"));
        Assert.assertTrue("List after sort doesn't match to the sorted list", songsList.get(4).getName().equals("This is America"));
    }

    @Test
    public void searchTest_normalQuery() {
        List<Song> searchResults;
        String regex = "i";

        searchResults = classUnderTest.search(regex);

        Assert.assertTrue("Normal search, should find 4 songs", searchResults.size() == 4);
    }

    @Test
    public void searchTest_emptyQuery() {
        List<Song> searchResults;
        String regex = "";

        searchResults = classUnderTest.search(regex);

        Assert.assertTrue("Empty search, should be 5 | Actual size = "+searchResults.size(), searchResults.size() == 5);
    }

    @Test
    public void searchTest_specialCharactersRegex() {
        List<Song> searchResults;
        String regex = "[!@#$%&*()_+=|<>?{}\\[\\]~-]";

        searchResults = classUnderTest.search(regex);

        Assert.assertTrue("Invalid Regex string,", searchResults.size()==0);
    }

    @Test
    public void searchTest_invalidRegex() {
        List<Song> searchResults;
        String regex = "[";

        searchResults = classUnderTest.search(regex);

        Assert.assertTrue("Invalid Regex single special character", searchResults.size()==0);
    }
}
