package fifthelement.theelement.objects;

import java.util.ArrayList;

public class Song implements Comparable<Song>{
    private int songId;
    private String songName;
    private String path;
    private ArrayList<Author>authors;//might not include this 
    private ArrayList<Album> albums; //might not include this

    public Song(int id, String name, String path){
        this.songId = id;
        this.songName = name;
        this.path = path;
        albums = new ArrayList<Album>();
        authors = new ArrayList<Author>();
    }

    public String getName(){
        return songName;
    }

    public int getId(){
        return songId;
    }

    public ArrayList<Author> getAuthors(){
        return authors;
    }
    
    public ArrayList<Album> getAlbums(){
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

    public void setAuthors(ArrayList<Author>authorList){
        this.authors =  authorList;
    }

    public void addAuthor(Author newAuthor){//to add a single author
        this.authors.add(newAuthor);
    }

    public void setAlbums(ArrayList<Album>albumList){
        this.albums =  albumList;
    }

    public void addAlbum(Album newAlbum){//to add a single album
        this.albums.add(newAlbum);
    }

    @Override
    public int compareTo(Song song){
        return songName.compareTo(song.getName());
    }
}
