package seedu.address.logic.commands;

//@@author CYX28
/**
 * Sorts alphabetically by name and lists all persons in the address book to the user.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "sn";
    public static final String MESSAGE_SUCCESS = "Listed all persons sorted alphabetically by name";

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortPersons();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
