package fifthelement.theelement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fifthelement.theelement.presentation.activities.CustomizeTests;
import fifthelement.theelement.presentation.activities.MusicLibraryTests;
import fifthelement.theelement.presentation.activities.PlayMusicTests;
import fifthelement.theelement.presentation.activities.PlaylistTests;
import fifthelement.theelement.presentation.activities.SettingsTest;
import fifthelement.theelement.presentation.activities.SongInformationTests;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        CustomizeTests.class,
        MusicLibraryTests.class,
        PlaylistTests.class,
        PlayMusicTests.class,
        SettingsTest.class,
})
public class AllAcceptanceTests {
}
