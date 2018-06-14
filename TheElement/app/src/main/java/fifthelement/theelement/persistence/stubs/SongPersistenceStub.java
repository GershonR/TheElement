package fifthelement.theelement.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.SongPersistence;

public class SongPersistenceStub implements SongPersistence {

    private List<Song> songList;

    public SongPersistenceStub() {
        this.songList = new ArrayList<>();

        /*
            Tife just change the song path here, the SongService
            calls this constructor
         */
        Song song = new Song(10001, "Nice For What", "test");
        song.addAuthor(new Author(20001, "Drake"));
        this.storeSong(song);

        song = new Song(10002, "Girls Like You", "test");
        this.storeSong(song);

        song = new Song(10003, "This Is America", "test");
        song.addAuthor(new Author(20003, "Childish Gambino"));
        this.storeSong(song);

        song = new Song(10004, "All Mine", "test");
        song.addAuthor(new Author(20004, "Kanye West"));
        this.storeSong(song);
    }

    public SongPersistenceStub(List<Song> songList) {
        this.songList = songList;
    }

    @Override
    public List<Song> getAllSongs() {
        return songList;
    }

    @Override
    public Song getSongByID(final int ID) {
        for(Song s : this.songList)
            if(s.getId() == ID)
                return s;
        return null;
    }

    @Override
    public boolean storeSong(Song song) {
        if(songExists(song))
            throw new ArrayStoreException();
        this.songList.add(song);
        return true;
    }

    @Override
    public boolean updateSong(Song song) {
        boolean found = false;
        if(song == null)
            throw new IllegalArgumentException("Cannot update a null song");
        for(int index = 0; index < songList.size(); index++)
            if(songList.get(index).getId() == song.getId()) {
                this.songList.set(index, song);
                found = true;
            }
        return found;
    }

    @Override
    public boolean deleteSong(Song song) {
        boolean removed = false;
        if(song == null)
            throw new IllegalArgumentException("Cannot delete a null song");
        for(int index = 0; index < songList.size(); index++) {
            if(songList.get(index).getId() == song.getId()) {
                this.songList.remove(index);
                removed = true;
            }
        }
        return removed;
    }

    private boolean songExists(Song song) {
        boolean exists = false;
        for(Song s : this.songList)
            if(s.getId() == song.getId()) {
                exists = true;
                break;
            }
        return exists;
    }
}
