package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private int songId;
    private String songName;
    private List<Author> authors;
    private List<Album> albums;
    private String path;

    public Song(int id, String name, String path) {
        this.songId = id;
        this.songName = name;
        this.path = path;
        this.authors = new ArrayList<>();
        this.albums = new ArrayList<>();
    }

    public Song(int id, String name, String path, List<Author> authors, List<Album> albums) {
        this.songId = id;
        this.songName = name;
        this.path = path;
        this.authors = authors;
        this.albums = albums;
    }

    // getters
    public String getName(){
        return songName;
    }

    public int getId(){
        return songId;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public String getPath() {
        return path;
    }

    // setters
    public void setName(String newName){
        this.songName = newName;
    }

    public void setId(int newId){
        this.songId  = newId;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author newAuthor){//to add a single author
        this.authors.add(newAuthor);
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public void addAlbum(Album newAlbum){//to add a single album
        this.albums.add(newAlbum);
    }
}