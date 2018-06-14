package fifthelement.theelement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fifthelement.theelement.business.AlbumServiceTest;
import fifthelement.theelement.business.AuthorServiceTest;
import fifthelement.theelement.business.SongServiceTest;
import fifthelement.theelement.persistence.stubs.SongPersistenceStubTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SongPersistenceStubTest.class,
        SongServiceTest.class,
        AuthorServiceTest.class,
        AlbumServiceTest.class
})

public class AllTests {

}
