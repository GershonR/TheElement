package fifthelement.theelement.business;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.application.Main;
import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.exceptions.SongAlreadyExistsException;
import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.AlbumPersistence;
import fifthelement.theelement.persistence.AuthorPersistence;
import fifthelement.theelement.persistence.PlaylistPersistence;
import fifthelement.theelement.persistence.SongPersistence;
import fifthelement.theelement.persistence.hsqldb.AlbumPersistenceHSQLDB;
import fifthelement.theelement.persistence.hsqldb.AuthorPersistenceHSQLDB;
import fifthelement.theelement.persistence.hsqldb.PlaylistPersistenceHSQLDB;
import fifthelement.theelement.persistence.hsqldb.SongPersistenceHSQLDB;
import fifthelement.theelement.utils.TestDatabaseUtil;

public class SongServiceIT {
    private SongService songService;
    private File tempDB;

    @Before
    public void setUpTestDB() throws IOException {
        this.tempDB = TestDatabaseUtil.copyDB();
        SongPersistence sp = new SongPersistenceHSQLDB(Main.getDBPathName());
        AlbumPersistence alp = new AlbumPersistenceHSQLDB(Main.getDBPathName());
        AuthorPersistence aup = new AuthorPersistenceHSQLDB(Main.getDBPathName());
        PlaylistPersistence pp = new PlaylistPersistenceHSQLDB(Main.getDBPathName());
        songService = new SongService(sp, alp, aup, pp);
    }

    @Test
    public void getAllSongsTest() {
        List<Song> albums = songService.getSongs(); // Should start with 4 songs in db

        Assert.assertTrue("getAllSongsTest: song size != 4", albums.size() == 4);
    }

    @Test
    public void insertSongValidTest() throws SongAlreadyExistsException {
        Song song = new Song("Some song", "Path");
        songService.insertSong(song);

        Assert.assertTrue("insertSongValidTest: song size != 5", songService.getSongs().size() == 5);
    }


    @Test(expected = IllegalArgumentException.class)
    public void insertSongInValidTest() throws SongAlreadyExistsException {
        Song song = null;

        songService.insertSong(song);
    }

    @Test(expected = SongAlreadyExistsException.class)
    public void insertSongDuplicateTest() throws SongAlreadyExistsException{
        Song songOne = new Song("A song", "Path");
        songOne.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Song songTwo = new Song("Other song", "Path");
        songTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        songService.insertSong(songOne);
        songService.insertSong(songTwo);
    }

    @Test
    public void updateSongValidTest() throws Exception {
        Song songOne = new Song("A song", "Path");
        songOne.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Song songTwo = new Song("Other song", "Path");
        songTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        boolean insertReturn = songService.insertSong(songOne);
        boolean updateReturn = songService.updateSong(songTwo);

        Assert.assertTrue("updateSongValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("updateSongValidTest: updateReturn != true", updateReturn);
        Assert.assertTrue("updateSongValidTest: album size != 5", songService.getSongs().size() == 5);

        Song song = songService.getSongByUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Assert.assertTrue("updateSongValidTest: song name != Changed Song Name", "Other song".equals(song.getName()));
    }

    @Test
    public void updateSongWithParametersValidTest() throws Exception{
        Album albumOne = new Album("Album");
        Author authorOne = new Author("Author");
        Song songOne = new Song(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"),"A song", "Path", authorOne, albumOne, "Jazz");
        songService.insertSong(songOne);

        Song songTwo = new Song("Other song", "Path");
        songTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));

        songService.updateSongWithParameters(songTwo, "new song", "new Author", "new Album","Rock");

        Song songTestOne = songService.getSongByUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Assert.assertTrue("Song name not updated", "new song".equals(songTestOne.getName()));
        Assert.assertTrue("Song author not updated", "new Author".equals(songTestOne.getAuthor().getName()));
        Assert.assertTrue("Song album not updated", "new Album".equals(songTestOne.getAlbum().getName()));
        Assert.assertTrue("Song genre not updated", "Rock".equals(songTestOne.getGenre()));

        songService.updateSongWithParameters(songTwo, "new song", "", "","");

        Song songTestTwo = songService.getSongByUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
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
        songService.insertSong(songOne);

        Song songTwo = new Song("Other song", "Path");
        songTwo.setUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        songService.updateSongWithRating(songTwo, 4.5);

        Song song = songService.getSongByUUID(UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f"));
        Assert.assertTrue("Song rating not updated", song.getRating() == 4.5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateSongInValidTest() {
        Song song = null;
        songService.updateSong(song);
    }

    @Test
    public void updateSongNotExistTest() {
        Song song = new Song("Gold", "Path");
        boolean result = songService.updateSong(song);
        Assert.assertFalse("updateSongNotExistTest: result != false", result);
        Assert.assertTrue("updateSongNotExistTest: song size != 4", songService.getSongs().size() == 4);

    }

    @Test
    public void createSongValidTest() throws Exception {
        String path = "google.com";
        String name = "TestSong";
        String artist = "Test";
        String album = "Test Album";
        String genre = "Test Genere";

        songService.createSong(path, name, artist, album, genre);
        Assert.assertTrue("updateSongNotExistTest: song size != 5", songService.getSongs().size() == 5);

    }

    @Test(expected = IllegalArgumentException.class)
    public void createSongInvalidTest() throws Exception {
        String path = null;
        String name = null;
        String artist = null;
        String album = null;
        String genre = null;

        songService.createSong(path, name, artist, album, genre);
        Assert.assertTrue("updateSongNotExistTest: song size != 5", songService.getSongs().size() == 5);

    }

    @Test
    public void deleteSongValidTest() throws Exception {
        Song songOne = new Song("21", "Path");
        UUID songUUID = UUID.fromString("793410b3-dd0b-4b78-97bf-289f50f6e74f");
        songOne.setUUID(songUUID);
        boolean insertReturn = songService.insertSong(songOne);

        Assert.assertTrue("deleteSongValidTest: insertReturn != true", insertReturn);
        Assert.assertTrue("deleteSongValidTest: song size != 5", songService.getSongs().size() == 5);

        boolean deleteReturn = songService.deleteSong(songOne);
        Assert.assertTrue("deleteSongValidTest: deleteReturn != true", deleteReturn);
        Assert.assertTrue("deleteSongValidTest: song size != 4", songService.getSongs().size() == 4);

        Song deletedSong = songService.getSongByUUID(songUUID);
        Assert.assertNull("deleteSongValidTest: deletedSong != null", deletedSong);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteSongInValidTest() {
        Song song = null;
        songService.deleteSong(song);
    }

    @Test
    public void deleteSongNotExistTest() {
        Song song = new Song("Gold", "Path");
        boolean result = songService.deleteSong(song);
        Assert.assertFalse("deleteSongNotExistTest: result != false", result);
        Assert.assertTrue("deleteSongNotExistTest: album size != 4", songService.getSongs().size() == 4);
    }

    @Test
    public void searchTest_normalQuery() {
        List<Song> searchResults;
        String regex = "i";

        searchResults = songService.search(regex);

        Assert.assertTrue("Normal search, should find 3 songs", searchResults.size() == 3);
    }

    @Test
    public void searchTest_emptyQuery() {
        List<Song> searchResults;
        String regex = "";

        searchResults = songService.search(regex);

        Assert.assertTrue("Empty search, should be 4 | Actual size = "+searchResults.size(), searchResults.size() == 4);
    }

    @Test
    public void searchTest_specialCharactersRegex() {
        List<Song> searchResults;
        String regex = "[!@#$%&*()_+=|<>?{}\\[\\]~-]";

        searchResults = songService.search(regex);

        Assert.assertTrue("Invalid Regex string,", searchResults.size()==0);
    }

    @Test
    public void searchTest_invalidRegex() {
        List<Song> searchResults;
        String regex = "[";

        searchResults = songService.search(regex);

        Assert.assertTrue("Invalid Regex single special character", searchResults.size()==0);
    }

    @After
    public void tearDownTestDB() {
        this.tempDB.delete();
        Persistence.resetPersistence();
        Services.resetServices();
        Path script = Paths.get(this.tempDB.getAbsolutePath());
        Path properties = Paths.get(this.tempDB.getAbsolutePath().replace(".script", ".properties"));
        try {
            Files.delete(script);
            Files.delete(properties);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
