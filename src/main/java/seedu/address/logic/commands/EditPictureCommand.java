package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PICTURE_URL;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.student.MiscellaneousInfo.ProfilePictureUrl;

public class EditPictureCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editPicture";
    public static final String PRE = "editPicture";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the current profile picture of a student "
            + "identified by the index number used in the last student listing. "
            + "Pictures have to have file extensions ending in '.jpg' or '.png'.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_PICTURE_URL + "URL of new picture to use.\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PICTURE_URL + "C:\\example.jpg";

    public static final String MESSAGE_EDIT_STUDENT_SUCCESS = "Edited profile picture of Student: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Error in editing profile picture.";
    private final Index index;
    private final ProfilePictureUrl newProfilePictureUrl;

    public EditPictureCommand(Index index, ProfilePictureUrl profilePictureUrl) {
        requireNonNull(index);
        requireNonNull(profilePictureUrl);

        this.index = index;
        this.newProfilePictureUrl = profilePictureUrl;
    }




    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        return null;
    }
}
