package seedu.address.logic.commands.job;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_JOBS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Lists all jobs in the address book to the user.
 */
public class JobListCommand extends Command {

    public static final String COMMAND_WORD = "listjob";

    public static final String MESSAGE_SUCCESS = "Listed all jobs";


    @Override
    public CommandResult execute() {
        model.updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
