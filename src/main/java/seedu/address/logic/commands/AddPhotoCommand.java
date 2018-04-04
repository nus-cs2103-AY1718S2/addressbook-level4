package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;

/**
 * Adds a photo to an employee.
 */
public class AddPhotoCommand extends Command {

    public static final String COMMAND_WORD = "addPhoto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a photo to an employee. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Photo Path (the path to the photo)"
            + "Example: " + COMMAND_WORD + " 1 C:\\Users\\imac\\Desktop\\DefaultPerson.png";

    public static final String MESSAGE_SUCCESS = "New photo added!";

    private final Index targetIndex;
    private final String path;

    /**
     * Creates an AddPhotoCommand to add the specified {@code Photo}
     */
    public AddPhotoCommand(Index index, String path) {
        this.targetIndex = index;
        this.path = path;
    }

    @Override
    public CommandResult execute() throws CommandException {

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
