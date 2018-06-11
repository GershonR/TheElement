package fifthelement.theelement.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.SongPersistence;

public class SongPersistenceStub implements SongPersistence {

    private List<Song> songList;

    public SongPersistenceStub() {
        this.songList = new ArrayList<>();

        this.storeSong(new Song(10001, "Song1", "test"));
        this.storeSong(new Song(10002, "Song2", "test"));
        this.storeSong(new Song(10003, "Song3", "test"));
        this.storeSong(new Song(10004, "Song4", "test"));

    }

    public SongPersistenceStub(List<Song> songList) {
        this.songList = songList;
    }

    @Override
    public List<Song> getAllSongs() {
        return songList;
    }

    @Override
    public Song getSongById(final int Id) {
        for(Song s : this.songList)
            if( s.getId() == Id )
                return s;
        return null;
    }

    @Override
    public Song storeSong(Song song) {
        if(songExists(song))
            throw new ArrayStoreException();
        this.songList.add(song);
        return song;
    }

    @Override
    public Song updateSong(Song song) {
        if(song == null)
            throw new IllegalArgumentException("Cannot update a null song");
        for( int index = 0; index < songList.size(); index++ ) {
            if( songList.get(index).getId() == song.getId() ) {
                this.songList.set(index, song);
                return song;
            }
        }
        return null;
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
                break;
            }
        }
        return removed;
    }

    private boolean songExists(Song song) {
        return this.getSongById(song.getId()) != null;
    }
}
