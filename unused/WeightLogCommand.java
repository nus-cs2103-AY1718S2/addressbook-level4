package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author wenhao53-unused
// Feature is working but was unable to solve the many tests that were failing.
/**
 * Opens up the Weight Log of a specified person in the Personal Trainer Pro
 */
public class WeightLogCommand extends Command {

    public static final String COMMAND_WORD = "log";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays weight log of a person.\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_WEIGHT_LOG_SUCCESS = "Weight log for %1$s displayed! Displaying all weight "
            + "changes since application startup.";

    private final Index index;

    public WeightLogCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToShowWeightLog = lastShownList.get(index.getZeroBased());

        try {
            model.showWeightLog(personToShowWeightLog);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_WEIGHT_LOG_SUCCESS,
                personToShowWeightLog.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WeightLogCommand // instanceof handles nulls
                && this.index.equals(((WeightLogCommand) other).index)); // state check
    }
}
