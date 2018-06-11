package fifthelement.theelement.persistence;

import java.util.List;

import fifthelement.theelement.objects.PlayList;

public interface PlayListPersistence {

    List<PlayList> getAllPlayLists();

    PlayList getPlayListById(int Id);

    PlayList storePlayList(PlayList playList);

    PlayList updatePlayList(PlayList playList);

    boolean deletePlayList(PlayList playList);

}
