package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Author {

    private UUID uuid;
    private String name;
    private List<Song> songList;
    private List<Album> albumList;
    private int numPlayed;


    public Author(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.songList = new ArrayList<>();
        this.albumList = new ArrayList<>();
        this.numPlayed = 0;
    }

    public Author(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.songList = new ArrayList<>();
        this.albumList = new ArrayList<>();
        this.numPlayed = 0;
    }

    public Author(UUID uuid, String name, int numPlayed) {
        this.uuid = uuid;
        this.name = name;
        this.songList = new ArrayList<>();
        this.albumList = new ArrayList<>();
        this.numPlayed = numPlayed;
    }

    public Author(String name, List<Song> songList, List<Album> albumList) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.songList = songList;
        this.albumList = albumList;
        this.numPlayed = 0;
    }

    public Author(String name, List<Song> songList, List<Album> albumList, int numPlayed) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.songList = songList;
        this.albumList = albumList;
        this.numPlayed = numPlayed;
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

    public int getNumPlayed() {
        return numPlayed;
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

    public void setNumPlayed(int numPlayed) {
        this.numPlayed = numPlayed;
    }

    public void incrNumPlayed() {
        this.numPlayed++;
    }

    public void decrNumPlayed() {
        if( this.numPlayed > 0 ) {
            this.numPlayed--;
        }
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Author && ((Author) object).getUUID() == this.getUUID();
    }
}