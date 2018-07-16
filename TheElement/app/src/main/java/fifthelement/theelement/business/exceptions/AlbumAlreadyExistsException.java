package fifthelement.theelement.business.exceptions;

public class AlbumAlreadyExistsException extends Exception {

    public AlbumAlreadyExistsException(String id) {
        super("Album with ID: " + id + " already exists");
    }

}
