package seedu.address.logic.commands;

/**
 * Sorts all the people in the list by their names in alphabetical order (case insensitive)
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS_SORT_BY_NAME = "Sorted all persons by name in alphabetical order";

    @Override
    public CommandResult execute() {
        model.sortPersonListByName();
        return new CommandResult(MESSAGE_SUCCESS_SORT_BY_NAME);
    }
}
