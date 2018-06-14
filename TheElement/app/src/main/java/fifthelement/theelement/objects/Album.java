package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.UUID;

public class Album{
    private UUID uuid;
    private String albumName;
    private List<Song> songList;
    private List<Author> authorList;

    public Album(String name){
        this.uuid = UUID.randomUUID();
        this.albumName = name;
        this.songList = new ArrayList<>();
        this.authorList = new ArrayList<>();
    }

    public Album(String name, List<Song> songList, List<Author> authorList){
        this.uuid = UUID.randomUUID();
        this.albumName = name;
        this.songList = songList;
        this.authorList = authorList;
    }

    // getters
    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return albumName;
    }

    public List<Author> getAuthors() {
        return authorList;
    }

    public List<Song> getSongs() {
        return songList;
    }

    // setters
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(String newName) {
        this.albumName = newName;
    }

    public void setAuthors(List<Author> authorList) {
        this.authorList =  authorList;
    }

    public void setSongs(List<Song>songList) {
        this.songList = songList;
    }

    public void addAuthor(Author newAuthor) {
        this.authorList.add(newAuthor);
    }

    public void addSong(Song newSong) {
        this.songList.add(newSong);
    }

    public void deleteSong(Song song) {
        this.songList.remove(song);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Album && ((Album) object).getUUID() == this.getUUID();
    }
}