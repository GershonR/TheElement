package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.UUID;

public class Album{
    private UUID uuid;
    private String albumName;
    private ArrayList<Author> authors;
    private ArrayList<Song> songs;

    public Album(String name){
        this.uuid = UUID.randomUUID();
        this.albumName = name;
        authors = new ArrayList<Author>();
        songs = new ArrayList<Song>();
    }

    public String getName(){
        return albumName;
    }

    public UUID getUUID() { return uuid; }

    public void setUUID(UUID uuid) { this.uuid = uuid; }

    public ArrayList<Author> getAuthors(){
        return authors;
    }
    
    public ArrayList<Song> getsongs(){
        return songs;
    }

    public void setName(String newName){
        this.albumName = newName;
    }

    public void setAuthors(ArrayList<Author>authorList){
        this.authors =  authorList;
    }
    //OR

    public void addAuthor(Author newAuthor){//to add a single author
        this.authors.add(newAuthor);
    }

    public void setSongs(ArrayList<Song>songList){
        this.songs = songList;
    }
    //OR

    public void addSong(Song newSong){
        this.songs.add(newSong);
    }

}