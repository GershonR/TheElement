package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.UUID;

public class Album{
    private UUID uuid;
    private String albumName;
    private Author author;
    private List<Song> songList;

    public Album(String name){
        this.uuid = UUID.randomUUID();
        this.albumName = name;
        this.songList = new ArrayList<>();
        this.author = new Author("");
    }

    public Album(String name, List<Song> songList, Author author){
        this.uuid = UUID.randomUUID();
        this.albumName = name;
        this.songList = songList;
        this.author = author;
    }

    public Album(UUID uuid, String name, Author author, List<Song> songList){
        this.uuid = uuid;
        this.albumName = name;
        this.songList = songList;
        this.author = author;
    }

    // getters
    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return albumName;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Song> getSongs() {
        return songList;
    }

    // setters
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(String newName) { this.albumName = newName; }

    public void setAuthor(Author author) {
        this.author =  author;
    }

    public void setSongs(List<Song>songList) {
        this.songList = songList;
    }

    public void addSong(Song newSong) {
        this.songList.add(newSong);
    }

    public void deleteSong(Song song) {
        this.songList.remove(song);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Album && ((Album) object).getUUID() == this.getUUID();
    }
}