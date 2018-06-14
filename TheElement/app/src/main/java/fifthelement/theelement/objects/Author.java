package fifthelement.theelement.objects;

import java.util.List;
import java.util.UUID;

public class Author {

    private UUID uuid;
    private String name;
    private List<UUID> songList;
    private List<UUID> albumList;


    public Author(String name) {

        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    // getters
    public String getName() {
        return name;
    }

    public UUID getUUID() {
        return uuid;
    }

    public List<UUID> getSongList() {
        return this.songList;
    }

    public List<UUID> getAlbumList() {
        return albumList;
    }

    // setters
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setSongList(List<UUID> songList) {
        this.songList = songList;
    }

    public void setAlbumList(List<UUID> albumList) {
        this.albumList = albumList;
    }

    public void addSong(Song song) {
        this.songList.add(song.getUUID());
    }

    public void addAlbum(Album album) {
        this.albumList.add(album.getUUID());
    }

    public void deleteSong(Song song) {
        this.songList.remove(song.getUUID());
    }

    public void deleteAlbum(Album album) {
        this.albumList.remove(album.getUUID());
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Author && ((Author) object).getUUID() == this.getUUID();
    }
}