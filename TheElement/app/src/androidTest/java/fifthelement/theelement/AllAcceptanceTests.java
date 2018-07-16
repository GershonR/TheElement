package fifthelement.theelement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fifthelement.theelement.presentation.activities.DeleteSongTest;
import fifthelement.theelement.presentation.activities.PlayPausingTest;
import fifthelement.theelement.presentation.activities.SeekTest;
import fifthelement.theelement.presentation.activities.SettingsTest;
import fifthelement.theelement.presentation.activities.ShuffleSongTest;
import fifthelement.theelement.presentation.activities.SkipSongsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PlayPausingTest.class,
        ShuffleSongTest.class,
        SkipSongsTest.class,
        SeekTest.class,
        DeleteSongTest.class,
        SettingsTest.class
})
public class AllAcceptanceTests {


}
