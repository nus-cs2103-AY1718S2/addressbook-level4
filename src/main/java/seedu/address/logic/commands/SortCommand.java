package seedu.address.logic.commands;

/**
 * Sorts all the people in the list by their names in alphabetical order (case insensitive)
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS_SORT_BY_NAME = "Sorted all persons by name in alphabetical order";
    public static final String MESSAGE_SUCCESS_SORT_BY_TAG = "Sorted all persons by tag in alphabetical order";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the student list by the parameter provided "
            + "by the user.\n"
            + "Parameters: KEYWORD (valid keyword: name, tag)\n"
            + "Example: " + COMMAND_WORD + " name\n";

    private final String parameter;

    public SortCommand(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public CommandResult execute() {
        model.sortPersonList(parameter);
        if (("name").equals(parameter)) {
            return new CommandResult(MESSAGE_SUCCESS_SORT_BY_NAME);
        }
        return new CommandResult(MESSAGE_SUCCESS_SORT_BY_TAG);
    }
}
