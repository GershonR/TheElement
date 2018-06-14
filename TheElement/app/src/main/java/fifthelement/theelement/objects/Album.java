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
        authors = new ArrayList<>();
        songs = new ArrayList<>();
    }

    // getters
    public String getName(){
        return albumName;
    }

    public UUID getUUID() {
        return uuid;
    }

    public ArrayList<Author> getAuthors(){
        return authors;
    }
    
    public ArrayList<Song> getsongs(){
        return songs;
    }

    // setters
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(String newName){
        this.albumName = newName;
    }

    public void setAuthors(ArrayList<Author>authorList){
        this.authors =  authorList;
    }

    public void addAuthor(Author newAuthor){//to add a single author
        this.authors.add(newAuthor);
    }

    public void setSongs(ArrayList<Song>songList){
        this.songs = songList;
    }

    public void addSong(Song newSong){
        this.songs.add(newSong);
    }

    public void deleteSong(Song song) {
        this.songs.remove(song);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Album && ((Album) object).getUUID() == this.getUUID();
    }
}