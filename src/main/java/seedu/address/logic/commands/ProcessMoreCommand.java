//@@author ZhangYijiong
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Add the first multiple unprocessed order in the order queue to the application's
 * processing queue, label the corresponding orders in the
 * order queue as Processed
 */

public class ProcessMoreCommand extends ProcessNextCommand {
    public static final String COMMAND_WORD = "processMore";
    public static final String COMMAND_ALIAS = "pM";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds first n unprocessed order into the processing queue.\n"
            + "Parameters: Number (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 3";

    private int noOfTimes;

    /**
     *
     * @param noOfTimes number that processNext needed to perform
     */
    public ProcessMoreCommand(int noOfTimes) {
        this.noOfTimes = noOfTimes;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        while (noOfTimes-- > 0) {
            super.execute();
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProcessMoreCommand // instanceof handles nulls
                && noOfTimes == ((ProcessMoreCommand) other).noOfTimes);
    }
}

