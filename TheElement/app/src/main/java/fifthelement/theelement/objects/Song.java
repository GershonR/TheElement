package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;

// TODO: implement proper stuff here
public class Song {
    private int songId;
    private String songName;
    private List<Author> authors;//might not include this
    private List<Album> albums; //might not include this

    public Song(int id, String name){
        this.songId = id;
        this.songName = name;
        albums = new ArrayList<>();
        authors = new ArrayList<>();
    }

    public Song(int id, String name, List<Author> authorList) {
        this.songId = id;
        this.songName = name;
        albums = new ArrayList<>();
        authors = new ArrayList<>();
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

    public void setAuthors(List<Author> authorList) {
        this.authors =  authorList;
    }
    //OR

    public void addAuthor(Author newAuthor){//to add a single author
        this.authors.add(newAuthor);
    }

    public void setAlbums(List<Album> albumList) {
        this.albums =  albumList;
    }
    //OR

    public void addAlbum(Album newAlbum){//to add a single album
        this.albums.add(newAlbum);
    }
    

}