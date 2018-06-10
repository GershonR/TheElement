package fifthelement.theelement.presentation;

/**
 * Allows {@link SongListFragment} to control media playback of {@link MusicService}.
 */
public interface PlayerAdapter {

    void loadMedia(int resourceId);

    void release();

    boolean isPlaying();

    void play();

    void reset();

    void pause();

    void initializeProgressCallback();

    void seekTo(int position);
}