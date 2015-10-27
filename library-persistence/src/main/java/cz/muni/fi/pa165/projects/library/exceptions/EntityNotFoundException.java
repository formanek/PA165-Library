package cz.muni.fi.pa165.projects.library.exceptions;

/**
 *
 * @author Milan Skipala
 */
public class EntityNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>LoanNotFoundException</code> without
     * detail message.
     */
    public EntityNotFoundException() {
    }

    /**
     * Constructs an instance of <code>LoanNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EntityNotFoundException(String msg) {
        super(msg);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
