//@@author ZhangYijiong
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.queue.TaskList;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.Messages;
import seedu.address.model.person.Person;

/**
 * Add multiple orders to the application's processing queue
 */

public class ProcessMoreCommand extends ProcessNextCommand {
    public static final String COMMAND_WORD = "processMore";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds first n unprocessed order into the processing queue.\n"
            + "Parameters: Number (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 3";

    private int noOfTimes;

    public ProcessMoreCommand(int noOfTimes) {
        this.noOfTimes = noOfTimes;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        while (noOfTimes-- >0 ) {
            super.execute();
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProcessMoreCommand // instanceof handles nulls
                && toAdd.equals(((ProcessMoreCommand) other).toAdd));
    }
}

