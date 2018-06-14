package fifthelement.theelement.business;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fifthelement.theelement.business.Services.SongService;
import fifthelement.theelement.objects.Song;

@RunWith(JUnit4.class)
public class SongServiceTest {
    private List<Song> songsList;
    private SongService classUnderTest;

    @Before
    public void setup() {

        classUnderTest = new SongService();
        this.songsList = new ArrayList<Song>();
        songsList.add(new Song(3, "Pristine", "Path"));
        songsList.add(new Song(5, "This is America", "Path"));
        songsList.add(new Song(2, "Nice For What", "Path"));
        songsList.add(new Song(1, "Geyser", "Path"));
        songsList.add(new Song(4, "Purity", "Path"));
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

        assert(searchResults.size()==4);
    }

    @Test
    public void searchTest_emptyQuery() {
        List<Song> searchResults;
        String regex = "";

        searchResults = classUnderTest.search(regex);

        assert(searchResults.size()==0);
    }

    @Test
    public void searchTest_noSongsInStartingSongList() {
        List<Song> searchResults;
        String regex = "i";
        songsList = new ArrayList<>();

        searchResults = classUnderTest.search(regex);

        assert(songsList.size() == 0);
        assert(searchResults.size()==0);
    }

    @Test
    public void searchTest_specialCharactersRegex() {
        List<Song> searchResults;
        String regex = "[!@#$%&*()_+=|<>?{}\\[\\]~-]";

        searchResults = classUnderTest.search(regex);

        assert(searchResults.size()==0);
    }

    @Test
    public void searchTest_invalidRegex() {
        List<Song> searchResults;
        String regex = "[";

        searchResults = classUnderTest.search(regex);

        assert(searchResults.size()==0);
    }
}
