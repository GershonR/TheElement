package fifthelement.theelement.business;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import fifthelement.theelement.business.services.SongListService;
import fifthelement.theelement.objects.Song;

@RunWith(JUnit4.class)
public class SongListServiceTest {
    private List<Song> songsList;
    private SongListService classUnderTest;

    @Before
    public void setup() {
        songsList = new ArrayList<Song>();
        songsList.add(new Song( "Pristine", "data/song1"));
        songsList.add(new Song( "This is America", "data/song2"));
        songsList.add(new Song( "Nice For What", "data/song3"));
        songsList.add(new Song( "Geyser", "data/song4"));
        songsList.add(new Song( "Purity", "data/song5"));
        classUnderTest = new SongListService(songsList);
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
    public void skipSongTest() {
        classUnderTest.getSongAtIndex(0);
        Song nextSong = classUnderTest.skipToNextSong();

        Assert.assertTrue("Skip function did not retrieve the 'next' song in list", nextSong.getName().equals("This is America"));
    }

    @Test
    public void prevSongTest() {
        classUnderTest.getSongAtIndex(1);
        Song prevSong = classUnderTest.goToPrevSong();

        Assert.assertTrue("Prev function did not retrieve the 'prev' song in list", prevSong.getName().equals("Pristine"));
    }

    @Test
    public void skipSongAtListEndTest(){
        classUnderTest.getSongAtIndex(4);
        Song nextSong = classUnderTest.skipToNextSong();

        Assert.assertTrue("Skip function did not wrap around to starting song in list", nextSong.getName().equals("Pristine"));
    }

    @Test
    public void prevSongAtListStartTest(){
        classUnderTest.getSongAtIndex(0);
        Song prevSong = classUnderTest.goToPrevSong();

        Assert.assertTrue("Prev function did not wrap around to ending song in list", prevSong.getName().equals("Purity"));
    }

    @Test
    public void removeSongFromListTest(){
        Song toRemove = classUnderTest.getSongAtIndex(0);
        classUnderTest.removeSongFromList(toRemove);
        Song shouldNotEqual = classUnderTest.getSongAtIndex(0);

        Assert.assertFalse("Remove function did not remove the song from the list", shouldNotEqual.getName().equals("Pristine"));
    }

    @Test
    public void shuffleValidTest() {
        classUnderTest.shuffle();

        Assert.assertTrue("Shuffle not true", classUnderTest.getShuffled());
        Assert.assertTrue("List size is not 5", classUnderTest.getSongList().size() == 5);
    }

    @Test
    public void setSongsListValidTest() {
        classUnderTest.setShuffled(true);
        classUnderTest.setSongList(songsList);


        Assert.assertTrue("Shuffle not true", classUnderTest.getShuffled());
        Assert.assertTrue("List size is not 5", classUnderTest.getSongList().size() == 5);
    }
}
