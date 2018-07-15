package fifthelement.theelement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fifthelement.theelement.business.SongServiceIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SongServiceIT.class,
})

public class AllIntegrationTests {

}
