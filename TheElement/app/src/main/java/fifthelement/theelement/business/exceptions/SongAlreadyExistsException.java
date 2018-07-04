package fifthelement.theelement.business.exceptions;

public class SongAlreadyExistsException extends Exception {

    public SongAlreadyExistsException(String path) {
        super("Song with path: " + path + " already exists");
    }


}
