package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.GoogleContactNameEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

//@@author x3tsunayh

/**
 * Selects and google searches a person's name identified using the last displayed index from the address book.
 */
public class GoogleCommand extends Command {

    public static final String COMMAND_WORD = "google";
    public static final String COMMAND_ALIAS = "g";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Googles the person's name identified by the index number used in the latest person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_GOOGLE_PERSON_SUCCESS = "Google Searched Person: %1$s";

    private final Index targetIndex;

    public GoogleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new GoogleContactNameEvent(lastShownList.get(targetIndex.getZeroBased())));
        return new CommandResult(String.format(MESSAGE_GOOGLE_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoogleCommand // instanceof handles nulls
                && this.targetIndex.equals(((GoogleCommand) other).targetIndex)); // state check
    }
}
