package fifthelement.theelement.business;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.business.exceptions.SongAlreadyExistsException;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.SongPersistence;
import fifthelement.theelement.persistence.stubs.AlbumPersistenceStub;
import fifthelement.theelement.persistence.stubs.AuthorPersistenceStub;
import fifthelement.theelement.persistence.stubs.PlaylistPersistenceStub;
import fifthelement.theelement.persistence.stubs.SongPersistenceStub;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

    @Test
    public void updateSongWithParametersValidTest() throws Exception{
        Album albumOne = new Album("Album");
        Author authorOne = new Author("Author");
        Song songOne = new Song(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"),"A song", "Path", authorOne, albumOne, "Jazz");
        classUnderTest.insertSong(songOne);

        Song songTwo = new Song("Other song", "Path");
        songTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        classUnderTest.updateSongWithParameters(songTwo, "new song", "new Author", "new Album","Rock");

        Song songTestOne = classUnderTest.getSongByUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Assert.assertTrue("Song name not updated", "new song".equals(songTestOne.getName()));
        Assert.assertTrue("Song author not updated", "new Author".equals(songTestOne.getAuthor().getName()));
        Assert.assertTrue("Song album not updated", "new Album".equals(songTestOne.getAlbum().getName()));
        Assert.assertTrue("Song genre not updated", "Rock".equals(songTestOne.getGenre()));

        classUnderTest.updateSongWithParameters(songTwo, "new song", "", "","");

        Song songTestTwo = classUnderTest.getSongByUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Assert.assertTrue("Song name not updated", "new song".equals(songTestTwo.getName()));
        Assert.assertTrue("Song author not updated", songTestTwo.getAuthor()== null);
        Assert.assertTrue("Song album not updated", songTestTwo.getAlbum()== null);
        Assert.assertTrue("Song genre not updated", songTestTwo.getGenre() == null);
    }

    @Test
    public void updateSongWithRatingValidTest() throws Exception{
        Song songOne = new Song("A song", "Path");
        songOne.setRating(1.0);
        songOne.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        classUnderTest.insertSong(songOne);

        Song songTwo = new Song("Other song", "Path");
        songTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        classUnderTest.updateSongWithRating(songTwo, 4.5);

        Song song = classUnderTest.getSongByUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Assert.assertTrue("Song rating not updated", song.getRating() == 4.5);
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
    public void createSongValidTest() throws Exception {
        String path = "google.com";
        String name = "TestSong";
        String artist = "Test";
        String album = "Test Album";
        String genre = "Test Genere";

        classUnderTest.createSong(path, name, artist, album, genre);
        Assert.assertTrue("updateSongNotExistTest: song size != 6", classUnderTest.getSongs().size() == 6);

    }

    @Test(expected = IllegalArgumentException.class)
    public void createSongInvalidTest() throws Exception {
        String path = null;
        String name = null;
        String artist = null;
        String album = null;
        String genre = null;

        classUnderTest.createSong(path, name, artist, album, genre);
        Assert.assertTrue("updateSongNotExistTest: song size != 6", classUnderTest.getSongs().size() == 5);

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

    @Test
    public void clearAllSongsTest(){
        //Recreate classUnder test but with a mock SongPersistenceStub
        SongPersistence songPersistenceMock = mock(SongPersistenceStub.class);
        classUnderTest = new SongService(songPersistenceMock, new AlbumPersistenceStub(), new AuthorPersistenceStub(), new PlaylistPersistenceStub());
        classUnderTest.getSongs().clear();
        this.songsList = classUnderTest.getSongs();

        //Re add songs to the song list in stub mock
        Song[] testSongs = new Song[5];
        testSongs[0] = new Song( "Pristine", "data/song1");
        testSongs[1] = new Song( "This is America", "data/song2");
        testSongs[2] = new Song( "Nice For What", "data/song3");
        testSongs[3] = new Song( "Geyser", "data/song4");
        testSongs[4] = new Song( "Purity", "data/song5");
        for(Song song : testSongs){
            songsList.add(song);
        }

        //Call the test
        classUnderTest.clearAllSongs();

        //Verify that all the testSongs were deleted
        for(Song song : testSongs){
            verify(songPersistenceMock).deleteSong(song);
        }
    }
}
