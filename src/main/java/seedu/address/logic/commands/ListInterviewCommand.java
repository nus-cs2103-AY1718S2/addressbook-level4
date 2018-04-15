//@@author deeheenguyen
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERVIEWS;

/**
 * Lists all jobs in the address book to the user.
 */
public class ListInterviewCommand extends Command {
    public static final String COMMAND_WORD = "listInterview";

    public static final String MESSAGE_SUCCESS = "Listed all interviews";

    @Override
    public CommandResult execute() {
        model.updateFilteredInterviewList(PREDICATE_SHOW_ALL_INTERVIEWS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
