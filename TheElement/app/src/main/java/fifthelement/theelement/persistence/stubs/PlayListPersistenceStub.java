package fifthelement.theelement.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import fifthelement.theelement.objects.PlayList;
import fifthelement.theelement.persistence.PlayListPersistence;

public class PlayListPersistenceStub implements PlayListPersistence {

    // could be a SongPersistence object but nah
    private List<PlayList> playLists;

    public PlayListPersistenceStub() {
        this.playLists = new ArrayList<>();

        this.storePlayList(new PlayList(11101, "PlayList1"));
        this.storePlayList(new PlayList(11102, "PlayList2"));
        this.storePlayList(new PlayList(11103, "PlayList3"));
        this.storePlayList(new PlayList(11104, "PlayList4"));
    }

    @Override
    public List<PlayList> getAllPlayLists() {
        return playLists;
    }

    @Override
    public PlayList getPlayListById(final int Id) {
        for( PlayList p : playLists ) {
            if( p.getId() == Id ) {
                return p;
            }
        }
        return null;
    }

    @Override
    public PlayList storePlayList(PlayList playList) {
        if( playListExists(playList) ) {
            throw new ArrayStoreException();
        }
        playLists.add(playList);
        return playList;
    }

    @Override
    public PlayList updatePlayList(PlayList playList) {
        if( playList == null ) {
            throw new IllegalArgumentException("Cannot update a null playlist");
        }
        for( int i = 0; i < playLists.size(); i++ ) {
            if( playLists.get(i).getId() == playList.getId() ) {
                playLists.set(i, playList);
                return playList;
            }
        }
        return null;
    }

    @Override
    public boolean deletePlayList(PlayList playList) {
        boolean removed = false;
        if( playList == null ) {
            throw new IllegalArgumentException("Cannot delete a null song");
        }
        for( int i = 0; i < playLists.size(); i++ ) {
            if( playLists.get(i).getId() == playList.getId() ) {
                playLists.set(i, playList);
                removed = true;
                break;
            }
        }
        return removed;
    }

    private boolean playListExists(PlayList playList) {
        return getPlayListById(playList.getId()) != null;
    }
}
