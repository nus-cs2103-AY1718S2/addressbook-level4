package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * By Yang Yuqing
 * Sorts the employees by any field
 */

public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String[] SORT_FIELD_LIST = {"name", "phone", "email", "address", "tag", "rate"};

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list of employees by a specific field  "
            + "Parameters: FIELD\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_SORT_EMPLOYEE_SUCCESS = "Employees has been sorted.";
    public static final String MESSAGE_SORT_INVALID_FIELD = "Your input field is invalid, please check again.";

    private final String sortField;

    public SortCommand(String field) {
        this.sortField = field;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        switch (sortField) {
        case "name":
        case "phone":
        case "email":
        case "address":
        case "tag":
        case "rate":
            model.sort(sortField);
            break;
        default:
            throw new CommandException(MESSAGE_SORT_INVALID_FIELD);
        }
        return new CommandResult(MESSAGE_SORT_EMPLOYEE_SUCCESS);
    }
}
