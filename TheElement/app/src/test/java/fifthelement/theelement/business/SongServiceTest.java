package fifthelement.theelement.business;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fifthelement.theelement.business.Services.SongService;
import fifthelement.theelement.objects.Song;

@RunWith(JUnit4.class)
public class SongServiceTest {
    private List<Song> toBeSortedList;
    private SongService classUnderTest;

    @Before
    public void setup() {
        classUnderTest = new SongService();
    }
    @Test
    public void sortSongListSize() {
        this.toBeSortedList = new ArrayList<Song>();
        //adding songs unsorted
        toBeSortedList.add(new Song(3, "Pristine", "Path"));
        toBeSortedList.add(new Song(5, "This is America", "Path"));
        toBeSortedList.add(new Song(2, "Nice For What", "Path"));
        toBeSortedList.add(new Song(1, "Geyser", "Path"));
        toBeSortedList.add(new Song(4, "Purity", "Path"));

        //sort list
       classUnderTest.sortSongs(toBeSortedList);

        Assert.assertTrue("Size of list after sorting != 5", toBeSortedList.size() == 5);
    }

    @Test
    public void sortSongOrderTest() {
        this.toBeSortedList = new ArrayList<Song>();
        //adding songs unsorted
        toBeSortedList.add(new Song(3, "Pristine", "Path"));
        toBeSortedList.add(new Song(5, "This is America", "Path"));
        toBeSortedList.add(new Song(2, "Nice For What", "Path"));
        toBeSortedList.add(new Song(1, "Geyser", "Path"));
        toBeSortedList.add(new Song(4, "Purity", "Path"));

        //sort list
        classUnderTest.sortSongs(toBeSortedList);

        Assert.assertTrue("List after sort doesn't match to the sorted list", toBeSortedList.get(0).getName().equals("Geyser"));
        Assert.assertTrue("List after sort doesn't match to the sorted list", toBeSortedList.get(1).getName().equals("Nice For What"));
        Assert.assertTrue("List after sort doesn't match to the sorted list", toBeSortedList.get(2).getName().equals("Pristine"));
        Assert.assertTrue("List after sort doesn't match to the sorted list", toBeSortedList.get(3).getName().equals("Purity"));
        Assert.assertTrue("List after sort doesn't match to the sorted list", toBeSortedList.get(4).getName().equals("This is America"));
    }

}
