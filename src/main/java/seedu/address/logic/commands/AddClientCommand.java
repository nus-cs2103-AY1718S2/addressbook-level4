package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.CommandNotAvailableInClosedViewException;
import seedu.address.model.person.Client;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.ui.util.ListPanelController;

//@@author shookshire
/**
 * Adds a tutor to TuitionCor.
 */
public class AddClientCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addclient";
    public static final String COMMAND_ALIAS = "ac";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a client to TuitionCor. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]... "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_GRADE + "GRADE "
            + PREFIX_SUBJECT + "SUBJECT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_CATEGORY + "t "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney "
            + PREFIX_LOCATION + "east "
            + PREFIX_GRADE + "p6 "
            + PREFIX_SUBJECT + "physics";

    public static final String MESSAGE_SUCCESS_STUDENT = "New student added: %1$s";
    public static final String MESSAGE_SUCCESS_TUTOR = "New tutor added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This student/tutor already exists in the address book";

    private final Client toAdd;

    /**
     * Creates an AddClientCommand to add the specified {@code Client}
     */
    public AddClientCommand(Client client) {
        requireNonNull(client);
        toAdd = client;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        if (!ListPanelController.isCurrentDisplayActiveList()) {
            throw new CommandNotAvailableInClosedViewException();
        }

        try {
            if (toAdd.getCategory().isStudent()) {
                model.addStudent(toAdd);
                return new CommandResult(String.format(MESSAGE_SUCCESS_STUDENT, toAdd));
            } else {
                model.addTutor(toAdd);
                return new CommandResult(String.format(MESSAGE_SUCCESS_TUTOR, toAdd));
            }

        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddClientCommand // instanceof handles nulls
                && toAdd.equals(((AddClientCommand) other).toAdd));
    }
}
