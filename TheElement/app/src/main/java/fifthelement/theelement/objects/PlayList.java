package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayList {
    private UUID uuid;
    private String listName;
    private List<Song> songList;

    public PlayList(String name) {
        this.uuid = UUID.randomUUID();
        this.listName = name;
        this.songList = new ArrayList<>();
    }

    public PlayList(String name, List<Song> songList) {
        this.uuid = UUID.randomUUID();
        this.listName = name;
        this.songList = songList;
    }

    // getters
    public String getName() {
        return listName;
    }

    public UUID getUUID() {
        return uuid;
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
        return object instanceof PlayList && ((PlayList) object).getUUID() == this.getUUID();
    }
}