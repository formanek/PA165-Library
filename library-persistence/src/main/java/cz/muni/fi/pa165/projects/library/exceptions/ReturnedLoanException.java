package cz.muni.fi.pa165.projects.library.exceptions;

/**
 *
 * @author Milan Skipala
 */
public class ReturnedLoanException extends Exception {

    /**
     * Creates a new instance of <code>ReturnedLoanException</code> without
     * detail message.
     */
    public ReturnedLoanException() {
    }

    /**
     * Constructs an instance of <code>ReturnedLoanException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ReturnedLoanException(String msg) {
        super(msg);
    }
}
