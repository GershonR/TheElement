package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;

// TODO: implement proper stuff here
public class PlayList {
    private int listId;
    private String listName;
    private List<Song> songs;

    public PlayList(int id, String name) {
        this.listId = id;
        this.listName = name;
        songs = new ArrayList<>();
    }

    public String getName() {
        return listName;
    }

    public int getId() {
        return listId;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setName(String newName) {
        this.listName = newName;
    }

    public void setId(int newId) {
        this.listId = newId;
    }

    public void setSongs(List<Song> songList) {
        this.songs = songList;
    }
    //OR

    public void addSong(Song newSong) {
        this.songs.add(newSong);
    }
}