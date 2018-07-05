package fifthelement.theelement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fifthelement.theelement.business.AlbumServiceTest;
import fifthelement.theelement.business.AuthorServiceTest;
import fifthelement.theelement.business.PlaylistServiceTest;
import fifthelement.theelement.business.SongListServiceTest;
import fifthelement.theelement.business.SongServiceTest;
import fifthelement.theelement.business.services.PlaylistService;
import fifthelement.theelement.persistence.stubs.AlbumPersistenceTest;
import fifthelement.theelement.persistence.stubs.AuthorPersistenceTest;
import fifthelement.theelement.persistence.stubs.PlaylistPersistenceTest;
import fifthelement.theelement.persistence.stubs.SongPersistenceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SongPersistenceTest.class,
        SongServiceTest.class,
        SongListServiceTest.class,
        AuthorServiceTest.class,
        AlbumServiceTest.class,
        PlaylistServiceTest.class,
        AuthorPersistenceTest.class,
        AlbumPersistenceTest.class,
        PlaylistPersistenceTest.class
})

public class AllTests {

}
