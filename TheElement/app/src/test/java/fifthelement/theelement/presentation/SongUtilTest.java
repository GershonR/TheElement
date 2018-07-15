package fifthelement.theelement.presentation;

import org.junit.Assert;
import org.junit.Test;

import fifthelement.theelement.presentation.util.SongUtil;

public class SongUtilTest {

    @Test
    public void validNameTest() {
        String invalidName = "*&&$@()&*)";
        String validBlankName = "";
        String validName = "Valid Name";

        Assert.assertTrue("The Song name is valid", SongUtil.validName(validName));
        Assert.assertTrue("The Song name is invalid: "+invalidName, !SongUtil.validName(invalidName));
        Assert.assertTrue("The Song name is blank and invalid", !SongUtil.validName(validBlankName));

    }
}
