package seedu.address.model.tag.exceptions;

//@@author {clarissayong}

/**
 * Signals that the operation is unable to find the specified tag.
 */
public class TagNotFoundException extends Exception {
    public TagNotFoundException() {
        super("There are no contacts with this tag.");
    }
}
