package fifthelement.theelement.persistence;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Album;

public interface AlbumPersistence {

    List<Album> getAllAlbums(); // some unordered list.

    Album getAlbumByUUID(UUID uuid); // get a album by UUID

    Album storeAlbum(Album album); // checks & ignores duplicates

    Album updateAlbum(Album album); // replaces old album with new one

    boolean deleteAlbum(Album album); // delete's using UUID

    boolean albumExists(UUID uuid); // sees if an album exists by UUID
}
