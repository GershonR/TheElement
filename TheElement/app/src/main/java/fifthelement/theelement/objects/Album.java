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
    private int numPlayed;

    public Album(String name){
        this.uuid = UUID.randomUUID();
        this.albumName = name;
        this.songList = new ArrayList<>();
        this.author = new Author("");
        this.numPlayed = 0;
    }

    public Album(UUID uuid, String name){
        this.uuid = uuid;
        this.albumName = name;
        this.songList = new ArrayList<>();
        this.author = new Author("");
        this.numPlayed = 0;
    }

    public Album(String name, List<Song> songList, Author author){
        this.uuid = UUID.randomUUID();
        this.albumName = name;
        this.songList = songList;
        this.author = author;
        this.numPlayed = 0;
    }

    public Album(UUID uuid, String name, Author author, List<Song> songList){
        this.uuid = uuid;
        this.albumName = name;
        this.songList = songList;
        this.author = author;
        this.numPlayed = 0;
    }

    public Album(UUID uuid, String name, Author author, List<Song> songList, int numPlayed){
        this.uuid = uuid;
        this.albumName = name;
        this.songList = songList;
        this.author = author;
        this.numPlayed = numPlayed;
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

    public int getNumPlayed() {
        return numPlayed;
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
        return object instanceof Album && ((Album) object).getUUID() == this.getUUID();
    }
}