package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_GRADUATION_YEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE_POINT_AVERAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_APPLIED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RESUME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIVERSITY;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ResumeUtil;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to HR+. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_UNIVERSITY + "UNIVERSITY "
            + PREFIX_EXPECTED_GRADUATION_YEAR + "EXPECTED GRADUATION YEAR "
            + PREFIX_MAJOR + "MAJOR "
            + PREFIX_GRADE_POINT_AVERAGE + "GRADE POINT AVERAGE "
            + PREFIX_JOB_APPLIED + "JOB APPLIED "
            + "[" + PREFIX_RESUME + "RESUME] "
            + "[" + PREFIX_IMAGE + "IMAGE] "
            + "[" + PREFIX_COMMENT + "COMMENT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_UNIVERSITY + "NUS "
            + PREFIX_EXPECTED_GRADUATION_YEAR + "2020 "
            + PREFIX_MAJOR + "Computer Science "
            + PREFIX_GRADE_POINT_AVERAGE + "4.93 "
            + PREFIX_JOB_APPLIED + "Software Developer "
            + PREFIX_RESUME + "jdoe.pdf "
            + PREFIX_IMAGE + "photo.jpg "
            + PREFIX_COMMENT + "Extraordinary Regex skill "
            + PREFIX_TAG + "Java "
            + PREFIX_TAG + "Go\n"
            + "The status is initialised to be 'new'.";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in HR+";

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
            ResumeUtil.cleanUpDataFolder(toAdd.getResume());
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
