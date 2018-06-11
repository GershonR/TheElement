package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;

public class Song{
    private int songId;
    private String songName;
    private List<Author> authors;//might not include this
    private List<Album> albums; //might not include this
    private String path;

    public Song(int id, String name, String path){
        this.songId = id;
        this.songName = name;
        this.path = path;
        this.albums = new ArrayList<>();
        this.authors = new ArrayList<>();
    }

    public Song(int id, String name, String path, List<Album> albums, List<Author> authors){
        this.songId = id;
        this.songName = name;
        this.path = path;
        this.albums = albums;
        this.authors = authors;
    }

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

    public void setName(String newName){
        this.songName = newName;
    }

    public void setId(int newId){
        this.songId  = newId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAuthors(List<Author> authorList) {
        this.authors =  authorList;
    }

    public void addAuthor(Author newAuthor){//to add a single author
        this.authors.add(newAuthor);
    }

    public void setAlbums(List<Album> albumList) {
        this.albums =  albumList;
    }

    public void addAlbum(Album newAlbum){//to add a single album
        this.albums.add(newAlbum);
    }
}