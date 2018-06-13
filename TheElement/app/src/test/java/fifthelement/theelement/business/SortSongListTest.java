package fifthelement.theelement.business;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import fifthelement.theelement.objects.Song;

@RunWith(JUnit4.class)
public class SortSongListTest {
    private List<Song> sortedList;
    private List<Song> toBeSortedList;

    @Before
    public void initLists() {
        this.sortedList = new ArrayList<Song>();
        this.toBeSortedList = new ArrayList<Song>();

        //adding songs sorted
        sortedList.add(new Song(1, "Geyser", "Path"));
        sortedList.add(new Song(2, "Nice For What", "Path"));
        sortedList.add(new Song(3, "Pristine", "Path"));
        sortedList.add(new Song(4, "Purity", "Path"));
        sortedList.add(new Song(5, "This is America", "Path"));

        //adding songs unsorted
        toBeSortedList.add(new Song(3, "Pristine", "Path"));
        toBeSortedList.add(new Song(5, "This is America", "Path"));
        toBeSortedList.add(new Song(2, "Nice For What", "Path"));
        toBeSortedList.add(new Song(1, "Geyser", "Path"));
        toBeSortedList.add(new Song(4, "Purity", "Path"));

        SortSongList sortList = new SortSongList(toBeSortedList);
        sortList.sortListByName();
    }

    @Test
    public void checkListSize() {
        Assert.assertTrue("Size of list after sorting != 5", toBeSortedList.size() == 5);
    }

    @Test
    public void checkIfSorted() {
        for (int i = 0; i < toBeSortedList.size(); i++) {
            Assert.assertTrue("List after sort doesn't match to the sorted list", toBeSortedList.get(i).getName().equals(sortedList.get(i).getName()));
        }
    }

}
