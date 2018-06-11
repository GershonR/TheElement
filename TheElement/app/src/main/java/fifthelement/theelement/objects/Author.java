package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;

public class Author {

    private int id;
    private String name;
    private List<Album> albums;
    private List<Song> songs;


    public Author(int id, String name) {
        this.id = id;
        this.name = name;
        this.albums = new ArrayList<>();
        this.songs = new ArrayList<>();
    }

    public Author(int id, String name, List<Album> albums, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.albums = albums;
        this.songs = songs;
    }

    // getters
    public String getName(){
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setId(int newId) {
        this.id  = newId;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public void addAlbum(Album album) {
        this.albums.add(album);
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public void deleteSong(Song song) {
        for( int i = 0; i < songs.size(); i++ ) {
            if( songs.get(i).getId() == song.getId() ) {
                songs.remove(i);
            }
        }
    }
}