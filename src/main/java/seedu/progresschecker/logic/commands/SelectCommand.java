package seedu.progresschecker.logic.commands;

import java.util.List;

import seedu.progresschecker.commons.core.EventsCenter;
import seedu.progresschecker.commons.core.Messages;
import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.commons.events.ui.JumpToListRequestEvent;
import seedu.progresschecker.commons.events.ui.TabLoadChangedEvent;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.person.Person;

/**
 * Selects a person identified using it's last displayed index from the ProgressChecker.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String COMMAND_ALIAS = "s";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " INDEX";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";
    public static final String tabType = "profile";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        EventsCenter.getInstance().post(new TabLoadChangedEvent(tabType));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
