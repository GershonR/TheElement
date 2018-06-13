package fifthelement.theelement.business;


import org.junit.Assert;
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

    @Test
    public void checkListSize() {
        this.toBeSortedList = new ArrayList<Song>();
        //adding songs unsorted
        toBeSortedList.add(new Song(3, "Pristine", "Path"));
        toBeSortedList.add(new Song(5, "This is America", "Path"));
        toBeSortedList.add(new Song(2, "Nice For What", "Path"));
        toBeSortedList.add(new Song(1, "Geyser", "Path"));
        toBeSortedList.add(new Song(4, "Purity", "Path"));

        //sort list
        SortSongList sortList = new SortSongList(toBeSortedList);
        sortList.sortListByName();

        Assert.assertTrue("Size of list after sorting != 5", toBeSortedList.size() == 5);
    }

    @Test
    public void checkIfSorted() {
        this.toBeSortedList = new ArrayList<Song>();
        //adding songs unsorted
        toBeSortedList.add(new Song(3, "Pristine", "Path"));
        toBeSortedList.add(new Song(5, "This is America", "Path"));
        toBeSortedList.add(new Song(2, "Nice For What", "Path"));
        toBeSortedList.add(new Song(1, "Geyser", "Path"));
        toBeSortedList.add(new Song(4, "Purity", "Path"));

        //sort list
        SortSongList sortList = new SortSongList(toBeSortedList);
        sortList.sortListByName();

        Assert.assertTrue("List after sort doesn't match to the sorted list", toBeSortedList.get(0).getName().equals("Geyser"));
        Assert.assertTrue("List after sort doesn't match to the sorted list", toBeSortedList.get(1).getName().equals("Nice For What"));
        Assert.assertTrue("List after sort doesn't match to the sorted list", toBeSortedList.get(2).getName().equals("Pristine"));
        Assert.assertTrue("List after sort doesn't match to the sorted list", toBeSortedList.get(3).getName().equals("Purity"));
        Assert.assertTrue("List after sort doesn't match to the sorted list", toBeSortedList.get(4).getName().equals("This is America"));
    }

}
