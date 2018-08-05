package fifthelement.theelement.business.util;

public class SongMetaUtil {

    public static String getExtension(String uri) {
        if (uri == null) {
            return null;
        }

        int dot = uri.lastIndexOf(".");
        if (dot >= 0) {
            return uri.substring(dot+1);
        } else {
            return "";
        }
    }

    public static boolean supportedAudioFileExtension(String extension){
        if(extension == null) {
            return false;
        }

        boolean toReturn = false;
        final String[] supportedFormats = {"mp3", "mkv", "wav", "ogg", "mid",
                "rtx", "mp4", "m4a", "aac", "3gp",
                "flac", "xmf", "mxmf", "rtttl", "ota",
                "imy", "wma"};

        for(String format : supportedFormats){
            toReturn = toReturn || extension.equals(format);
        }

        return toReturn;
    }

    public static boolean validName(String newName){
        boolean result = false;
        String normalChars = "^[a-zA-Z0-9 ]+$";
        if (newName.matches(normalChars))
            result = true;
        return result;
    }
}
