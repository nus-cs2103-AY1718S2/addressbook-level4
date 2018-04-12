//@@author ZhangYijiong
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;



import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * Add an order to the application's order queue
 */

public class AddOrderCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add an order to the processing queue. \n"
            + "Parameters: "
            + PREFIX_ORDER + "ORDER "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_PRICE + "PRICE (of the order) "
            + PREFIX_DISTANCE + "DISTANCE (to the address) "
            + PREFIX_COUNT + "COUNT (past order count) "
            + PREFIX_DESCRIPTION + "[" + "DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ORDER + "CHICKEN RICE "
            + PREFIX_ADDRESS + "NUS "
            + PREFIX_PRICE + "3 "
            + PREFIX_DISTANCE + "5 "
            + PREFIX_COUNT + "1 "
            + PREFIX_DESCRIPTION + "CHILI SAUCE REQUIRED";

    public static final String MESSAGE_SUCCESS = "New Order added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This order already exists in the address book";

    private final Task toAdd;

    public AddOrderCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddOrderCommand // instanceof handles nulls
                && toAdd.equals(((AddOrderCommand) other).toAdd));
    }

}
