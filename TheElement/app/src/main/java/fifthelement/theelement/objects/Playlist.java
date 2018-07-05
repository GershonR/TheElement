package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import java.util.function.Consumer;

public class Playlist {
    private UUID uuid;
    private String listName;
    private List<Song> songList;
    private int numPlayed;

    public Playlist(String name) {
        this.uuid = UUID.randomUUID();
        this.listName = name;
        this.songList = new ArrayList<>();
        this.numPlayed = 0;
    }

    public Playlist(String name, List<Song> songList) {
        this.uuid = UUID.randomUUID();
        this.listName = name;
        this.songList = songList;
        this.numPlayed = 0;
    }

    public Playlist(UUID uuid, String name, List<Song> songList) {
        this.uuid = uuid;
        this.listName = name;
        this.songList = songList;
    }

    // getters
    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return listName;
    }

    public List<Song> getSongs() {
        return songList;
    }

    public int getNumPlayed() {
        return numPlayed;
    }

    // setters
    public void setName(String newName) {
        this.listName = newName;
    }

    public void setId(UUID uuid) {
        this.uuid = uuid;
    }

    public void setSongs(List<Song> songList) {
        this.songList = songList;
    }

    public void addSong(Song newSong) {
        this.songList.add(newSong);
    }

    public void removeSong(Song song) {
        this.songList.remove(song);
    }

    public boolean contains(Song song) {
        return songList.contains(song);
    }

    public void setNumPlayed(int numPlayed) {
        this.numPlayed = numPlayed;
    }

    public void incrNumPlayed() {
        this.numPlayed++;
    }

    public void decrNumPlayed() {
        this.numPlayed--;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Playlist && ((Playlist) object).getUUID() == this.getUUID();
    }
}