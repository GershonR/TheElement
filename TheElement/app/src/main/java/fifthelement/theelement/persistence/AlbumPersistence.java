package fifthelement.theelement.persistence;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Album;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;

public interface AlbumPersistence {

    List<Album> getAllAlbums() throws PersistenceException; // some unordered list.

    Album getAlbumByUUID(UUID uuid) throws PersistenceException, IllegalArgumentException; // get a album by UUID

    // using boolean since its a stub. would make changes when implementing db anyway
    boolean storeAlbum(Album album) throws PersistenceException, IllegalArgumentException; // checks & ignores duplicates

    boolean updateAlbum(Album album) throws PersistenceException, IllegalArgumentException; // replaces old album with new one

    boolean deleteAlbum(Album album) throws PersistenceException, IllegalArgumentException; // delete's using UUID

    boolean deleteAlbum(UUID uuid) throws PersistenceException, IllegalArgumentException; // delete's using UUID

    boolean albumExists(Album album) throws PersistenceException, IllegalArgumentException; // sees if an album exists by UUID

    boolean albumExists(UUID uuid) throws PersistenceException, IllegalArgumentException; // sees if an album exists by UUID
}
