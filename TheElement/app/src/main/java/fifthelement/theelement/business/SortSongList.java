package fifthelement.theelement.business;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fifthelement.theelement.objects.Song;

public class SortSongList {
    List<Song> songs;
    public SortSongList(List<Song> songs)
    {
        this.songs = songs;
    }

    public void sortListByName() {
        Collections.sort(songs, new Comparator<Song>() {
            @Override
            public int compare(Song one, Song two) {
                return one.getName().compareTo(two.getName());
            }
        });
    }
}
