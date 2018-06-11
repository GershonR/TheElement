
package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;
// TODO: implement proper stuff here

public class Album {
    private int albumId;
    private String albumName;
    private List<Author> authors;
    private List<Song> songs;

    public Album(int id, String name) {
        this.albumId = id;
        this.albumName = name;
        authors = new ArrayList<>();
        songs = new ArrayList<>();
    }

    public String getName(){
        return albumName;
    }

    public int getId(){
        return albumId;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<Song> getsongs() {
        return songs;
    }

    public void setName(String newName) {
        this.albumName = newName;
    }

    public void setId(int newId) {
        this.albumId = newId;
    }

    public void setAuthors(List<Author> authorList) {
        this.authors =  authorList;
    }
    //OR

    public void addAuthor(Author newAuthor){//to add a single author
        this.authors.add(newAuthor);
    }

    public void setSongs(List<Song> songList) {
        this.songs = songList;
    }
    //OR

    public void addSong(Song newSong){
        this.songs.add(newSong);
    }


}