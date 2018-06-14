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

    public AlbumPersistenceStub(List<Album> albumList) {
        this.albumList = albumList;
    }

    @Override
    public List<Album> getAllAlbums() {
        return albumList;
    }

    @Override
    public Album getAlbumByUUID(UUID uuid) {
        for(Album a : this.albumList)
            if(a.getUUID().compareTo(uuid) == 0)
                return a;
        return null;
    }

    @Override
    public Album storeAlbum(Album album) {
        if(albumExists(album.getUUID()))
            throw new ArrayStoreException();
        this.albumList.add(album);
        return album;
    }

    @Override
    public Album updateAlbum(Album album) {
        if(album == null)
            throw new IllegalArgumentException("Cannot update a null album");
        for(int index = 0; index < albumList.size(); index++) {
            if(albumList.get(index).getUUID().compareTo(album.getUUID()) == 0) {
                this.albumList.set(index, album);
            }
        }
        return album;
    }

    @Override
    public boolean deleteAlbum(Album album) {
        boolean removed = false;
        if(album == null)
            throw new IllegalArgumentException("Cannot delete a null song");
        for(int index = 0; index < albumList.size(); index++) {
            if(albumList.get(index).getUUID().compareTo(album.getUUID()) == 0) {
                this.albumList.remove(index);
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean albumExists(UUID uuid) {
        boolean exists = false;
        for(Album a : this.albumList)
            if(a.getUUID().compareTo(uuid) == 0) {
                exists = true;
                break;
            }
        return exists;
    }
}
