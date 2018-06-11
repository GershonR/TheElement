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
        this.songs = new ArrayList<>();
    }

    public PlayList(int id, String name, List<Song> songs) {
        this.listId = id;
        this.listName = name;
        this.songs = songs;
    }

    // getters
    public String getName() {
        return listName;
    }

    public int getId() {
        return listId;
    }

    public List<Song> getSongs() {
        return songs;
    }

    // setters
    public void setName(String newName) {
        this.listName = newName;
    }

    public void setId(int newId) {
        this.listId = newId;
    }

    public void setSongs(List<Song> songList) {
        this.songs = songList;
    }

    public void addSong(Song newSong) {
        this.songs.add(newSong);
    }
}