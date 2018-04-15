//@@author ZhangYijiong
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.PersonPanelPathChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.ui.PersonCard;

/**
 * Selects a person identified using it's last displayed index from the address book
 * and show the path to the address of the person identified
 */
public class PathCommand extends Command {

    public static final String COMMAND_WORD = "path";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Show the path to the address of the person identified "
            + "by the index number used in the last person listing\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 3";

    public static final String MESSAGE_PATH_PERSON_SUCCESS = "Path to Person: %1$s";

    private final Index targetIndex;

    public PathCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        PersonCard personToFindPath = new PersonCard(
                lastShownList.get(targetIndex.getZeroBased()), targetIndex.getOneBased());
        EventsCenter.getInstance().post(new PersonPanelPathChangedEvent(personToFindPath));
        return new CommandResult(String.format(MESSAGE_PATH_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PathCommand // instanceof handles nulls
                && this.targetIndex.equals(((PathCommand) other).targetIndex)); // state check
    }
}

