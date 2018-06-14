package fifthelement.theelement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fifthelement.theelement.business.AlbumServiceTest;
import fifthelement.theelement.business.AuthorServiceTest;
import fifthelement.theelement.business.SongServiceTest;
import fifthelement.theelement.persistence.stubs.AlbumPersistenceTest;
import fifthelement.theelement.persistence.stubs.AuthorPersistenceTest;
import fifthelement.theelement.persistence.stubs.SongPersistenceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SongPersistenceTest.class,
        SongServiceTest.class,
        AuthorServiceTest.class,
        AlbumServiceTest.class,
        AuthorPersistenceTest.class,
        AlbumPersistenceTest.class
})

public class AllTests {

}
