package fifthelement.theelement.persistence.stubs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.PlayList;
import fifthelement.theelement.persistence.PlayListPersistence;

public class PlayListPersistenceStub implements PlayListPersistence {

    private List<PlayList> playLists;

    public PlayListPersistenceStub() {
        this.playLists = new ArrayList<>();

        this.storePlayList(new PlayList("PlayList1"));
        this.storePlayList(new PlayList("PlayList2"));
        this.storePlayList(new PlayList("PlayList3"));
        this.storePlayList(new PlayList("PlayList4"));
    }

    @Override
    public List<PlayList> getAllPlayLists() {
        return playLists;
    }

    @Override
    public PlayList getPlayListByUUID(UUID uuid) {
        for( PlayList p : playLists ) {
            if( p.getUUID() == uuid ) {
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean storePlayList(PlayList playList) {
        if( playListExists(playList.getUUID()) ) {
            throw new ArrayStoreException();
        }
        playLists.add(playList);
        return true;
    }

    @Override
    public boolean updatePlayList(PlayList playList) {
        if( playList == null ) {
            throw new IllegalArgumentException("Cannot update a null playlist");
        }
        for( int i = 0; i < playLists.size(); i++ ) {
            if( playLists.get(i).getUUID() == playList.getUUID() ) {
                playLists.set(i, playList);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deletePlayList(PlayList playList) {
        if( playList == null ) {
            throw new IllegalArgumentException("Cannot delete a null song");
        }
        return playLists.remove(playList);
    }

    @Override
    public boolean playListExists(UUID uuid) {
        return getPlayListByUUID(uuid) != null;
    }

}
