package fifthelement.theelement.persistence;

import java.util.List;

import fifthelement.theelement.objects.Album;

public interface AlbumPersistence {

    List<Album> getAllAlbums(); // some unordered list.

    Album getAlbumById(int Id); // get a album by Id

    Album storeAlbum(Album album); // checks & ignores duplicates

    Album updateAlbum(Album album); // replaces old album with new one

    boolean deleteAlbum(Album album); // delete's using Id
}
