package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.List;

import seedu.progresschecker.commons.core.Messages;
import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.person.exceptions.DuplicatePersonException;
import seedu.progresschecker.model.person.exceptions.PersonNotFoundException;

/**
 * Uploads a photo to the profile.
 */
public class UploadCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "upload";
    public static final String COMMAND_ALIAS = "up";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " " + "INDEX "
            + "[PATH]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Uploads a photo to the profile. "
            + "Parameter: INDEX(must be a positive integer) PATH...\n"
            + "Example: " + COMMAND_WORD + " 1 /images/contact/user.jpg";

    public static final String MESSAGE_SUCCESS = "New photo uploaded!";
    public static final String MESSAGE_IMAGE_NOT_FOUND = "The image cannot be found!";
    public static final String MESSAGE_IMAGE_DUPLICATE = "Upload the same image!";

    private final String toUpload;
    private final Index targetIndex;
    private Person personToUpdate;

    /**
     * Creates an UploadCommand to upload the profile photo with specified {@code Path}
     */
    public UploadCommand(Index index, String path) {
        requireNonNull(path);
        this.toUpload = path;
        this.targetIndex = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(personToUpdate);
        try {
            model.uploadPhoto(personToUpdate, toUpload);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_IMAGE_NOT_FOUND);
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_IMAGE_DUPLICATE);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToUpdate = lastShownList.get(targetIndex.getZeroBased());
    }

}
