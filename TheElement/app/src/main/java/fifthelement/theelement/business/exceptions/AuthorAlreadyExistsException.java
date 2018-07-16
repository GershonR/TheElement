package fifthelement.theelement.business.exceptions;

public class AuthorAlreadyExistsException extends Exception {

    public AuthorAlreadyExistsException(String id) {
        super("Author with ID: " + id + " already exists");
    }


}
