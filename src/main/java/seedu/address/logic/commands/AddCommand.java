package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to EduBuddy "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_NRIC + "NRIC "
            + "[" + PREFIX_TAG + "TAG] "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_SUBJECT + "SUBJECT_NAME SUBJECT_GRADE...]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NRIC + "S9876543H "
            + PREFIX_TAG + "3A "
            + PREFIX_REMARK + "English Rep "
            + PREFIX_SUBJECT + "English A2 Tamil A2 AMath B3 Phy A1 EMath A2 Hist A2\n"
            + "Example: " + COMMAND_ALIAS + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NRIC + "S9876543H "
            + PREFIX_TAG + "3A "
            + PREFIX_REMARK + "English Rep "
            + PREFIX_SUBJECT + "English A2 Tamil A2 AMath B3 Phy A1 EMath A2 Hist A2";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException, IOException {
        requireNonNull(model);
        try {
            for (Person p : model.getFilteredPersonList()) {
                if (toAdd.getNric().equals(p.getNric())) {
                    throw new CommandException("This NRIC already exists in the address book");
                }
            }
            model.addPerson(toAdd);
            model.addPage(toAdd);

            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));


        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
