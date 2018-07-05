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

        classUnderTest = new SongListService();
        songsList = new ArrayList<Song>();
        songsList.add(new Song( "Pristine", "data/song1"));
        songsList.add(new Song( "This is America", "data/song2"));
        songsList.add(new Song( "Nice For What", "data/song3"));
        songsList.add(new Song( "Geyser", "data/song4"));
        songsList.add(new Song( "Purity", "data/song5"));
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
}
