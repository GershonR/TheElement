package fifthelement.theelement.persistence.stubs;
/* AlbumStorage (a stub)
 * extends BaseStorage
 * acts as a database. stores Albums
 */

import java.util.List;

import fifthelement.theelement.objects.Album;

public class AlbumStorage extends BaseStorage<Album> {

    public AlbumStorage() {
        super();
        super.storeItem(new Album("01001", "Album1"));
        super.storeItem(new Album("01002", "Album2"));
        super.storeItem(new Album("01003", "Album3"));
        super.storeItem(new Album("01004", "Album4"));
    }

    public AlbumStorage(List<Album> albums) {
        super(albums);
    }

}
