package fifthelement.theelement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fifthelement.theelement.presentation.activities.MainActivityTest;
import fifthelement.theelement.presentation.activities.PlayPausingTest;
import fifthelement.theelement.presentation.activities.SeekTest;
import fifthelement.theelement.presentation.activities.ShuffleSongTest;
import fifthelement.theelement.presentation.activities.SkipSongsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MainActivityTest.class,
        PlayPausingTest.class,
        ShuffleSongTest.class,
        SkipSongsTest.class,
        SeekTest.class
})
public class AllSystemTests {


}
