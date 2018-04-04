package seedu.address.model.tag.exceptions;

//@@author TeyXinHui
/**
 * Signals that the operation is unable to find the specified tag.
 */
public class TagNotFoundException extends Exception {
    public TagNotFoundException(String message) {
        super(message);
    }

}
//@@author
