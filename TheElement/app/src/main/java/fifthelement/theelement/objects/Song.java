package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Song implements Comparable<Song>{
    private UUID uuid;
    private String songName;
    private String path;
    private List<Author> authorList;//might not include this
    private List<Album> albumList; //might not include this

    public Song(String name, String path){
        this.uuid = UUID.randomUUID();
        this.songName = name;
        this.path = path;
        albumList = new ArrayList<>();
        authorList = new ArrayList<>();
    }

    // getters
    public UUID getUUID(){
        return uuid;
    }

    public String getName(){
        return songName;
    }

    public String getPath() {
        return path;
    }

    public List<Author> getAuthors(){
        return authorList;
    }

    public List<Album> getAlbums(){
        return albumList;
    }

    // setters
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(String newName){
        this.songName = newName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAuthors(List<Author> authorList){
        this.authorList =  authorList;
    }

    public void setAlbums(List<Album> albumList){
        this.albumList =  albumList;
    }

    public void addAuthor(Author newAuthor){//to add a single author
        this.authorList.add(newAuthor);
    }

    public void addAlbum(Album newAlbum){//to add a single album
        this.albumList.add(newAlbum);
    }

    @Override
    public int compareTo(Song song){
        return songName.compareTo(song.getName());
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Song && ((Song) object).getUUID() == this.getUUID();
    }
}
