package fifthelement.theelement.persistence;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.PlayList;

public interface PlayListPersistence {

    List<PlayList> getAllPlayLists();

    PlayList getPlayListByUUID(UUID uuid);

    PlayList storePlayList(PlayList playList);

    PlayList updatePlayList(PlayList playList);

    boolean deletePlayList(PlayList playList);

    boolean playListExists(UUID uuid);
}
