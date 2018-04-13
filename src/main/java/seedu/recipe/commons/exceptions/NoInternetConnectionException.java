//@@author kokonguyen191
package seedu.recipe.commons.exceptions;

/**
 * Signals that there is no internet connection for a procedure that requires one
 */
public class NoInternetConnectionException extends Exception {
    public NoInternetConnectionException(String message) {
        super(message);
    }

}
