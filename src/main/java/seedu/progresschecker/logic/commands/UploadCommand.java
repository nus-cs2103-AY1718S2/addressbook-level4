package seedu.progresschecker.logic.commands;

import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.person.exceptions.DuplicatePersonException;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_YEAR;

/**
 * Uploads a photo to the profile.
 */
public class UploadCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "upload";
    public static final String COMMAND_ALIAS = "up";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Uploads a photo to the profile.\n"
            + "Parameter: PATH...\n"
            + "Example: " + COMMAND_WORD + " C:\\Users\\ProgressChecker";

    public static final String MESSAGE_SUCCESS = "New photo uploaded: %1$s";

    private final String pathOfPhoto;

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
        }

    }

}
