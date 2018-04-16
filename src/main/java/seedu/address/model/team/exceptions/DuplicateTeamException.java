package seedu.address.model.team.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author jordancjq
/**
 * Signals that the operation will result in duplicate Team objects.
 */
public class DuplicateTeamException extends DuplicateDataException {
    public DuplicateTeamException() {
        super("Operation would result in duplicate teams");
    }
}
