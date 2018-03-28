package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.InvalidSubjectCombinationException;

/**
 * Selects a person identified using it's last displayed index from the address book.
 * Calls L1R5 function in Model to calculate L1R5 score and outputs L1R5 score of selected person.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_ALIAS + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";
    public static final String MESSAGE_L1R5_SUCCESS = ". L1R5 Score: %1$s";

    private final Index targetIndex;
    private int score;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.score = 0;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person selectedPerson = lastShownList.get(targetIndex.getZeroBased());
        StringBuilder result = new StringBuilder();

        if (selectedPerson.getSubjects().size() < 6) {
            throw new CommandException(Messages.MESSAGE_INSUFFICIENT_SUBJECTS);
        }
        try {
            score = selectedPerson.calculateL1R5();
        } catch (InvalidSubjectCombinationException error) {
            return new CommandResult("Please check that you have at least 1 subject in each category.");
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(result.append(String.format(MESSAGE_SELECT_PERSON_SUCCESS, selectedPerson.getName()))
                .append(String.format(MESSAGE_L1R5_SUCCESS, score)).toString());

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
