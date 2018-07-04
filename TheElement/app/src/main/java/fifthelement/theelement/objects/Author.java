package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Author {

    private UUID uuid;
    private String name;
    private List<Song> songList;
    private List<Album> albumList;


    public Author(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.songList = new ArrayList<>();
        this.albumList = new ArrayList<>();
    }

    public Author(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.songList = new ArrayList<>();
        this.albumList = new ArrayList<>();
    }

    public Author(String name, List<Song> songList, List<Album> albumList) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.songList = songList;
        this.albumList = albumList;
    }

    // getters
    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public List<Song> getSongList() {
        return this.songList;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }

    // setters
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }

    public void addSong(Song song) {
        this.songList.add(song);
    }

    public void addAlbum(Album album) {
        this.albumList.add(album);
    }

    public void deleteSong(Song song) {
        this.songList.remove(song);
    }

    public void deleteAlbum(Album album) {
        this.albumList.remove(album);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Author && ((Author) object).getUUID() == this.getUUID();
    }
}