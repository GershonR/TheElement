package fifthelement.theelement.persistence;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Album;

public interface AlbumPersistence {

    List<Album> getAllAlbums(); // some unordered list.

    Album getAlbumByUUID(UUID uuid); // get a album by UUID

    // using boolean since its a stub. would make changes when implementing db anyway
    boolean storeAlbum(Album album); // checks & ignores duplicates

    boolean updateAlbum(Album album); // replaces old album with new one

    boolean deleteAlbum(UUID uuid); // delete's using UUID

    boolean albumExists(UUID uuid); // sees if an album exists by UUID
}
