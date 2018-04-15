package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
//@@author TeyXinHui
/**
 * Finds student at the specified index and returns a streaming score based on the streaming type provided.
 */
public class StreamCommand extends Command {

    public static final String COMMAND_WORD = "stream";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds student at the specified index given and "
            + "returns a streaming score based on the streaming type provided.\n"
            + "Parameters: [INDEX] [STREAMING_TYPE]\n"
            + "Example: " + COMMAND_WORD + " 1 1";

    public static final String MESSAGE_SELECT_STUDENT_SUCCESS = "Student: %1$s";
    public static final String MESSAGE_L1R5_SUCCESS = ". L1R5 Score: %1$s";
    public static final String MESSAGE_L1B4A_SUCCESS = ". L1B4A Score: %1$s";
    public static final String MESSAGE_L1B4B_SUCCESS = ". L1B4B Score: %1$s";
    public static final String MESSAGE_L1B4C_SUCCESS = ". L1B4C Score: %1$s";
    public static final String MESSAGE_L1B4D_SUCCESS = ". L1B4D Score: %1$s";

    private final Index targetIndex;
    private int type;

    public StreamCommand(Index index, int type) {
        this.targetIndex = index;
        this.type = type;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        String message;

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person selectedPerson = lastShownList.get(targetIndex.getZeroBased());
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));

        message = scoreCalculation(selectedPerson, type);
        return new CommandResult(message);
    }

    /**
     * Returns a {@String result} message according to the subject combination
     * of {@Person selectedPerson} and {@int type}.
     * @return message to user
     */
    public static String scoreCalculation(Person selectedPerson, int type) {
        StringBuilder result = new StringBuilder();
        String message = "";
        int score = 0;

        switch (type) {
        case(1):
            score = selectedPerson.calculateL1R5();
            message = MESSAGE_L1R5_SUCCESS;
            break;
        case(2):
            score = selectedPerson.calculateL1B4A();
            message = MESSAGE_L1B4A_SUCCESS;
            break;
        case(3):
            score = selectedPerson.calculateL1B4B();
            message = MESSAGE_L1B4B_SUCCESS;
            break;
        case(4):
            score = selectedPerson.calculateL1B4C();
            message = MESSAGE_L1B4C_SUCCESS;
            break;
        case(5):
            score = selectedPerson.calculateL1B4D();
            message = MESSAGE_L1B4D_SUCCESS;
            break;
        default:
            break;
        }
        return result.append(String.format(MESSAGE_SELECT_STUDENT_SUCCESS, selectedPerson.getName()))
                .append(String.format(message, score)).toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StreamCommand // instanceof handles nulls
                && this.targetIndex.equals(((StreamCommand) other).targetIndex)
                && (this.type == (((StreamCommand) other).type))); // state check
    }

}
//@@author
