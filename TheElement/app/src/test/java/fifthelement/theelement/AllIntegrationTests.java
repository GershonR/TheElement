package fifthelement.theelement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fifthelement.theelement.business.AlbumServiceIT;
import fifthelement.theelement.business.AuthorServiceIT;
import fifthelement.theelement.business.SongServiceIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SongServiceIT.class,
        AlbumServiceIT.class,
        AuthorServiceIT.class,
})

public class AllIntegrationTests {

}
