package fifthelement.theelement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fifthelement.theelement.presentation.activities.MusicLibraryTests;
import fifthelement.theelement.presentation.activities.PlayMusicTests;
import fifthelement.theelement.presentation.activities.PlayerStatsTest;
import fifthelement.theelement.presentation.activities.PlaylistTests;
import fifthelement.theelement.presentation.activities.SettingsTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        MusicLibraryTests.class,
        PlaylistTests.class,
        PlayMusicTests.class,
        SettingsTest.class,
        PlayerStatsTest.class
})
public class AllAcceptanceTests {


}
