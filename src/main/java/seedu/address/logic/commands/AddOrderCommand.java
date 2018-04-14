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
import seedu.address.model.task.Distance;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Add an order to the application's order queue
 */

public class AddOrderCommand extends Command {
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

    private final Index targetIndex;

    private Task toAdd;


    public AddOrderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        Date date = new Date();
        date.getTime();

        Calendar cal = Calendar.getInstance();
        String orderTime = cal.get(Calendar.HOUR_OF_DAY)+":"
                +cal.get(Calendar.MINUTE)+":"
                +cal.get(Calendar.SECOND);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Person personToAdd = lastShownList.get(targetIndex.getZeroBased());

        toAdd = new Task(personToAdd, orderTime);

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
