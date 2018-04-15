package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_JOBS;

//@@author ChengSashankh
/**
 * Lists all jobs in the address book to the user.
 */
public class ListJobsCommand extends Command {
    public static final String COMMAND_WORD = "listjobs";

    public static final String MESSAGE_SUCCESS = "Listed all jobs";

    @Override
    public CommandResult execute() {
        model.updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

//@@author
