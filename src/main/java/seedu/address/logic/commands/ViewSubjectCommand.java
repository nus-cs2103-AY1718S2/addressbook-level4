package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Finds specific person by the index and displays selected person's subject in the Command Box.
 */
public class ViewSubjectCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String COMMAND_ALIAS = "v";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds person of specified index and "
            + " and displays his or her subject list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_ALIAS + " 1";

    private final Index targetIndex;

    public ViewSubjectCommand(Index index) {
        requireNonNull(index);
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format("Student's subject(s): "));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewSubjectCommand // instanceof handles nulls
                && this.targetIndex.equals(((ViewSubjectCommand) other).targetIndex)); // state check
    }


}
