package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private int albumId;
    private String albumName;
    private List<Author> authors;
    private List<Song> songs;

    public Album(int id, String name) {
        this.albumId = id;
        this.albumName = name;
        this.authors = new ArrayList<>();
        this.songs = new ArrayList<>();
    }

    public Album(int id, String name, List<Author> authors, List<Song> songs) {
        this.albumId = id;
        this.albumName = name;
        this.authors = authors;
        this.songs = songs;
    }

    // getters
    public String getName(){
        return albumName;
    }

    public int getId(){
        return albumId;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<Song> getSongs() {
        return songs;
    }

    // setters
    public void setName(String newName) {
        this.albumName = newName;
    }

    public void setId(int newId) {
        this.albumId = newId;
    }

    public void setAuthors(List<Author> authorList) {
        this.authors =  authorList;
    }

    public void addAuthor(Author newAuthor){//to add a single author
        this.authors.add(newAuthor);
    }

    public void setSongs(List<Song> songList) {
        this.songs = songList;
    }

    public void addSong(Song newSong){
        this.songs.add(newSong);
    }
}