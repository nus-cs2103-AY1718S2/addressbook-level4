package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.NextOfKin;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a person to the address book.
 */
public class AddNextOfKinCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addnok";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a next of kin of the student to EduBuddy "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_NRIC + "NRIC "
            + "[" + PREFIX_REMARK + "REMARK]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NRIC + "S9876543H "
            + PREFIX_REMARK + "Father ";

    public static final String MESSAGE_SUCCESS = "New NextOfKin added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final NextOfKin toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddNextOfKinCommand(NextOfKin nextOfKin) {
        requireNonNull(nextOfKin);
        toAdd = nextOfKin;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException, IOException {
        requireNonNull(model);
        try {
            model.addNextOfKin(toAdd);
            //model.addPage(toAdd);

            //EventsCenter.getInstance().post(new JumpToListRequestEvent(model.getFilteredPersonList().size() - 1));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));


        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddNextOfKinCommand // instanceof handles nulls
                && toAdd.equals(((AddNextOfKinCommand) other).toAdd));
    }
}
