package fifthelement.theelement.persistence;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.PlayList;

public interface PlayListPersistence {

    List<PlayList> getAllPlayLists();

    PlayList getPlayListByUUID(UUID uuid);

    // using boolean since its a stub. would make changes when implementing db anyway
    boolean storePlayList(PlayList playList);

    boolean updatePlayList(PlayList playList);

    boolean deletePlayList(PlayList playList);

    boolean deletePlayList(UUID uuid);

    boolean playListExists(PlayList playList);

    boolean playListExists(UUID uuid);
}
