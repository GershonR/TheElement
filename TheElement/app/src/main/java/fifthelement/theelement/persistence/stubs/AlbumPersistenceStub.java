package fifthelement.theelement.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import fifthelement.theelement.objects.Album;
import fifthelement.theelement.persistence.AlbumPersistence;

public class AlbumPersistenceStub implements AlbumPersistence {

    private List<Album> albumList;

    public AlbumPersistenceStub() {
        this.albumList = new ArrayList<>();

        this.albumList.add(new Album(01001, "Album1"));
        this.albumList.add(new Album(01002, "Album2"));
        this.albumList.add(new Album(01003, "Album3"));
        this.albumList.add(new Album(01004, "Album4"));

    }

    public AlbumPersistenceStub(List<Album> albumList) {
        this.albumList = albumList;
    }

    @Override
    public List<Album> getAllAlbums() {
        return albumList;
    }

    @Override
    public Album getAlbumByID(int ID) {
        for(Album a : this.albumList)
            if(a.getId() == ID)
                return a;
        return null;
    }

    @Override
    public Album storeAlbum(Album album) {
        if(albumExists(album))
            throw new ArrayStoreException();
        this.albumList.add(album);
        return album;
    }

    @Override
    public Album updateAlbum(Album album) {
        if(album == null)
            throw new IllegalArgumentException("Cannot update a null song");
        for(int index = 0; index < albumList.size(); index++) {
            if(albumList.get(index).getId() == album.getId()) {
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
            if(albumList.get(index).getId() == album.getId()) {
                this.albumList.remove(index);
                removed = true;
            }
        }
        return removed;
    }

    private boolean albumExists(Album album) {
        boolean exists = false;
        for(Album a : this.albumList)
            if(a.getId() == album.getId()) {
                exists = true;
                break;
            }
        return exists;
    }
}
