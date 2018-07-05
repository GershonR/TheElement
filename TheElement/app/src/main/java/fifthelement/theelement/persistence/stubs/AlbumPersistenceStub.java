package fifthelement.theelement.persistence.stubs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Album;
import fifthelement.theelement.persistence.AlbumPersistence;

public class AlbumPersistenceStub implements AlbumPersistence {

    private List<Album> albumList;

    public AlbumPersistenceStub() {
        this.albumList = new ArrayList<>();

        this.albumList.add(new Album("Album1"));
        this.albumList.add(new Album("Album2"));
        this.albumList.add(new Album("Album3"));
        this.albumList.add(new Album("Album4"));

    }

    @Override
    public List<Album> getAllAlbums() {
        return albumList;
    }

    @Override
    public Album getAlbumByUUID(UUID uuid) throws IllegalArgumentException {
        if(uuid == null)
            throw new IllegalArgumentException("Cannot get album with a null UUID");
        for(Album a : this.albumList)
            if(a.getUUID().compareTo(uuid) == 0)
                return a;
        return null;
    }

    @Override
    public boolean storeAlbum(Album album) throws IllegalArgumentException {
        if(album == null)
            throw new IllegalArgumentException("Cant store an album with null Album");
        if(albumExists(album.getUUID()))
            throw new IllegalArgumentException("Cant store an album with existing UUID");
        this.albumList.add(album);
        return true;
    }

    @Override
    public boolean updateAlbum(Album album) throws IllegalArgumentException {
        boolean found = false;
        if(album == null)
            throw new IllegalArgumentException("Cannot update a null album");
        for(int index = 0; index < albumList.size(); index++) {
            if(albumList.get(index).getUUID().compareTo(album.getUUID()) == 0) {
                this.albumList.set(index, album);
                found = true;
                break;
            }
        }
        return found;
    }

    @Override
    public boolean deleteAlbum(Album album) throws IllegalArgumentException {
        if (album == null)
            throw new IllegalArgumentException("Cannot delete album with a null album");
        return this.deleteAlbum(album.getUUID());
    }

    @Override
    public boolean deleteAlbum(UUID uuid) throws IllegalArgumentException  {
        boolean removed = false;
        if(uuid == null)
            throw new IllegalArgumentException("Cannot delete album with a null UUID");
        for(int index = 0; index < albumList.size(); index++) {
            if(albumList.get(index).getUUID().compareTo(uuid) == 0) {
                this.albumList.remove(index);
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean albumExists(Album album) {
        if(album == null)
            throw new IllegalArgumentException("Cannot check exists with a null Album");
        return albumExists(album.getUUID());
    }

    @Override
    public boolean albumExists(UUID uuid) {
        if(uuid == null)
            throw new IllegalArgumentException("Cannot check exists with a null UUID");
        boolean exists = false;
        for(Album a : this.albumList)
            if(a.getUUID().compareTo(uuid) == 0) {
                exists = true;
                break;
            }
        return exists;
    }
}
