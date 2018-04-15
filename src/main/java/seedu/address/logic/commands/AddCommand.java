package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HALAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VEGETARIAN;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.dish.exceptions.DishNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a order to Delivery. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_ORDER + "FOOD_ORDER "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_HALAL + "HALAL] "
            + "[" + PREFIX_VEGETARIAN + "VEGETARIAN] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_ORDER + "Chicken Rice "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_HALAL + "Halal "
            + PREFIX_VEGETARIAN + "Vegetarian ";


    public static final String MESSAGE_SUCCESS = "New order added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This order already exists in the address book";
    public static final String MESSAGE_UNAVAILABLE_DISH = "This dish is not available in our menu";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (DishNotFoundException e) {
            throw new CommandException(MESSAGE_UNAVAILABLE_DISH);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
