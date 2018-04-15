package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
/**
 * Opens up the Calories Calculator window with specific index
 */
public class CaloriesCommand extends Command {

    public static final String COMMAND_WORD = "Calories";
    public static final String COMMAND_ALIAS = "cal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the calories of specified person. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FIND_CALORIES_SUCCESS = "Calories Required Of %1$s";

    private final Index index;

    public CaloriesCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personWhoseCaloriesIsToBeShown = lastShownList.get(index.getZeroBased());

        try {
            model.showCalories(personWhoseCaloriesIsToBeShown);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_FIND_CALORIES_SUCCESS,
                personWhoseCaloriesIsToBeShown.getName().fullName, personWhoseCaloriesIsToBeShown.getAddress()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CaloriesCommand // instanceof handles nulls
                && this.index.equals(((CaloriesCommand) other).index)); // state check
    }
}
