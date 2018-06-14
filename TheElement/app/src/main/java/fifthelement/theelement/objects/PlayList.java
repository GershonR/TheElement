package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayList {
    private UUID uuid;
    private String listName;
    private List<UUID> songList;

    public PlayList(String name) {
        this.uuid = UUID.randomUUID();
        this.listName = name;
        this.songList = new ArrayList<>();
    }

    public PlayList(String name, List<UUID> songList) {
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

    public List<UUID> getSongs() {
        return songList;
    }

    // setters
    public void setName(String newName) {
        this.listName = newName;
    }

    public void setId(UUID uuid) {
        this.uuid = uuid;
    }

    public void setSongs(List<UUID> songList) {
        this.songList = songList;
    }

    public void addSong(Song newSong) {
        this.songList.add(newSong.getUUID());
    }

    public void removeSong(Song song) {
        this.songList.remove(song.getUUID());
    }

    public boolean contains(Song song) {
        return songList.contains(song.getUUID());
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof PlayList && ((PlayList) object).getUUID() == this.getUUID();
    }
}