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

    public Playlist(String name) {
        this.uuid = UUID.randomUUID();
        this.listName = name;
        this.songList = new ArrayList<>();
    }

    public Playlist(String name, List<Song> songList) {
        this.uuid = UUID.randomUUID();
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

    @Override
    public boolean equals(Object object) {
        return object instanceof Playlist && ((Playlist) object).getUUID() == this.getUUID();
    }
}