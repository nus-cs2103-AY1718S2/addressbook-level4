package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.SwitchTabRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Shows the linkedIn search of a person identified using it's last displayed index
 * from the address book.
 */
public class LinkedInCommand extends Command {
    public static final String COMMAND_WORD = "linkedIn";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the linkedIn search of the person identified by the index number used "
            + "in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final int TAB_ID_LINKEDIN = 1;

    public static final String MESSAGE_LINKEDIN_PERSON_SUCCESS = "Showing LinkedIn search of: %1$s";

    private final Index targetIndex;

    public LinkedInCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new SwitchTabRequestEvent(TAB_ID_LINKEDIN));
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_LINKEDIN_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LinkedInCommand // instanceof handles nulls
                && this.targetIndex.equals(((LinkedInCommand) other).targetIndex)); // state check
    }
}
