package fifthelement.theelement.persistence.stubs;
/* SongStorage (a stub)
 * extends BaseStorage
 * acts as a database. stores Songs
 */

import java.util.List;

import fifthelement.theelement.objects.Song;

public class SongStorage extends BaseStorage<Song> {

    public SongStorage() {
        super();
        super.storeItem(new Song("10001", "Song1"));
        super.storeItem(new Song("10002", "Song1"));
        super.storeItem(new Song("10003", "Song1"));
        super.storeItem(new Song("10004", "Song1"));
    }

    public SongStorage(List<Song> songs) {
        super(songs);
    }

}

