package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.FileUtil.REGEX_VALID_IMAGE;
import static seedu.progresschecker.commons.util.FileUtil.copyFile;
import static seedu.progresschecker.commons.util.FileUtil.createMissing;
import static seedu.progresschecker.commons.util.FileUtil.getFileExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import seedu.progresschecker.commons.core.Messages;
import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.person.exceptions.DuplicatePersonException;
import seedu.progresschecker.model.person.exceptions.PersonNotFoundException;
import seedu.progresschecker.model.photo.PhotoPath;
import seedu.progresschecker.model.photo.exceptions.DuplicatePhotoException;

//@@author Livian1107
/**
 * Uploads a photo to the profile.
 */
public class UploadCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "upload";
    public static final String COMMAND_ALIAS = "up";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " " + "INDEX "
            + "[PATH]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Uploads a photo to the specified profile.\n"
            + "The valid photo extensions are 'jpg', 'jpeg' or 'png'.\n"
            + "Parameter: INDEX(must be a positive integer) PATH...\n"
            + "Example: " + COMMAND_WORD + " 1 C:\\Users\\User\\Desktop\\photo.png\n";

    public static final String MESSAGE_SUCCESS = "New photo uploaded!";
    public static final String MESSAGE_COPY_FAIL = "Cannot copy file!";
    public static final String MESSAGE_IMAGE_NOT_FOUND = "The image cannot be found!";
    public static final String MESSAGE_IMAGE_DUPLICATE = "Upload the same image!";
    public static final String MESSAGE_LOCAL_PATH_CONSTRAINTS =
            "The photo path should be a valid path on your PC. "
            + "It should start with the name of your PC user name, "
            + "followed by several folders, e.g.\"C:\\Usres\\User\\Desktop\\photo.png\". \n"
            + "The file should exist. \n"
            + "The path of file cannot contain any whitespaces inside. \n"
                    + "The valid extensions of the file should be 'jpg', 'jpeg' or 'png'. \n";

    public static final String REGEX_VALID_LOCAL_PATH =
            "([a-zA-Z]:)?(\\\\\\w+)+\\\\" + REGEX_VALID_IMAGE;
    public static final String PATH_SAVED_FILE = "src/main/resources/images/contact/";

    private final Index targetIndex;

    private Person personToUpdate;
    private PhotoPath photoPath;
    private String localPath;
    private String savePath;

    /**
     * Creates an UploadCommand to upload the profile photo with specified {@code Path}
     */
    public UploadCommand(Index index, String path) throws IllegalValueException, IOException {
        requireNonNull(path);
        requireNonNull(index);
        if (isValidLocalPath(path)) {
            this.localPath = path;
            this.targetIndex = index;
            this.savePath = copyLocalPhoto(localPath);
            this.photoPath = new PhotoPath(savePath);
        } else {
            throw new IllegalValueException(MESSAGE_LOCAL_PATH_CONSTRAINTS);
        }
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(personToUpdate);
        try {
            model.addPhoto(photoPath);
            model.uploadPhoto(personToUpdate, savePath);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (DuplicatePhotoException e) {
            throw new CommandException(MESSAGE_IMAGE_DUPLICATE);
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_IMAGE_DUPLICATE);
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

    /**
     * Returns true when the String path provided is a valid local path
     */
    public static boolean isValidLocalPath(String path) {
        return path.matches(REGEX_VALID_LOCAL_PATH);
    }

    /**
     * Copies uploaded photo file to specified saved path
     * @param localPath is the path of uploaded photo
     * @return String of saved path of uploaded photo
     */
    public String copyLocalPhoto(String localPath) throws IOException {
        File localFile = new File(localPath);
        String newPath = createSavePath(localPath);

        if (!localFile.exists()) {
            throw new FileNotFoundException(MESSAGE_LOCAL_PATH_CONSTRAINTS);
        }

        createSavedPhoto(newPath);

        try {
            copyFile(localPath, newPath);
        } catch (IOException e) {
            throw new IOException(MESSAGE_COPY_FAIL);
        }
        return newPath;
    }

    /**
     * Create a new path for uploaded photo to save
     * @param localPath is the String of uploaded photo on user PC
     * @return savePath String of uploaded photo
     */
    public static String createSavePath(String localPath) {
        Date date = new Date();
        Long num = date.getTime();
        String createPath = PATH_SAVED_FILE + num.toString() + getFileExtension(localPath);
        return createPath;
    }

    /**
     * Creates a new file to save profile photo
     * @param path to save photo
     */
    public void createSavedPhoto(String path) {
        File savedPhoto = new File(path);
        try {
            createMissing(savedPhoto);
        } catch (IOException e) {
            assert false : "Fail to create the file!";
        }
    }
}
