package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author x3tsunayh

/**
 * Sorts the event list according to the specified parameter
 */
public class SortEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sortevent";
    public static final String COMMAND_WORD_TWO = "sortevents";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Order the Event Book based on a specified parameter.\n"
            + "Parameters:  TITLE, LOCATION, DATETIME\n"
            + "Example: " + COMMAND_WORD + " TITLE";

    public static final String MESSAGE_SORT_SUCCESS = "Event Book has been sorted by ";
    public static final String MESSAGE_SORT_WRONG_PARAMETER =
            "The parameter can only contain Title, Location or Datetime";

    private String sortParameter;

    public SortEventCommand(String sortParameter) {
        this.sortParameter = sortParameter;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    public String getSortParameter() {
        return sortParameter;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        try {
            model.sortEventList(sortParameter);

        } catch (CommandException e) {
            return new CommandResult(MESSAGE_SORT_WRONG_PARAMETER);
        }
        return new CommandResult(String.format(MESSAGE_SORT_SUCCESS + sortParameter));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortEventCommand // instanceof handles nulls
                && this.sortParameter.equals(((SortEventCommand) other).sortParameter)); // state check
    }

}
