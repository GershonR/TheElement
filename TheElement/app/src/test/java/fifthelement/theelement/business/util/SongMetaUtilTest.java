package fifthelement.theelement.business.util;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SongMetaUtilTest {

    @Test
    public void getValidExtenstionTest() {
        String song = "MySong.mp3";
        String extension = SongMetaUtil.getExtension(song);

        Assert.assertNotNull("getValidExtenstionTest: Extension is null", extension);
        Assert.assertEquals("getValidExtenstionTest: Extension is not equal to mp3", "mp3", extension);
    }

    @Test
    public void getInValidExtensionTest() {
        String song = "MySong";
        String extension = SongMetaUtil.getExtension(song);

        Assert.assertEquals("getInValidExtensionTest: Extension is not empty", "", extension);
    }

    @Test
    public void nullExtensionTest() {
        String song = null;
        String extension = SongMetaUtil.getExtension(song);

        Assert.assertEquals("nullExtensionTest: Extension is not null", null, extension);
    }

    @Test
    public void getValidSupportedExtensionTest() {
        String extension = "mp3";

        boolean result = SongMetaUtil.supportedAudioFileExtension(extension);

        Assert.assertTrue("getValidSupportedExtensionTest: Result is not true", result);
    }

    @Test
    public void getInValidSupportedExtensionTest() {
        String extension = "jpeg";

        boolean result = SongMetaUtil.supportedAudioFileExtension(extension);

        Assert.assertFalse("getInValidSupportedExtensionTest: Result is not false", result);
    }

    @Test
    public void nullSupportedExtensionTest() {
        String extension = null;

        boolean result = SongMetaUtil.supportedAudioFileExtension(extension);

        Assert.assertFalse("nullSupportedExtensionTest: Result is not false", result);
    }

}
