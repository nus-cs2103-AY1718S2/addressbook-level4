package seedu.address.logic.commands;
//@@author crizyli
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.logic.FileChoosedEvent;
import seedu.address.commons.events.ui.PersonEditedEvent;
import seedu.address.commons.events.ui.ResetPersonCardsEvent;
import seedu.address.commons.events.ui.ShowFileChooserEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.photo.Photo;

/**
 * Adds a photo to an employee.
 */
public class AddPhotoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addPhoto";

    public static final String IMAGE_FOLDER_WINDOWS = "src\\main\\resources\\images\\personphoto\\";

    public static final String IMAGE_FOLDER_OTHER = "src/main/resources/images/personphoto/";

    public static final String IMAGE_FOLDER = "data/personphoto/";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a photo to an employee.\n"
            + "Choose a photo in the file chooser. Acceptable photo file type are jpg, jprg, png, bmp."
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "New photo added!";

    public static final String MESSAGE_INVALID_PHOTO_TYPE = "The photo type is unacceptable!";

    public static final String MESSAGE_PHOTO_NOT_CHOSEN = "You have not chosen one photo!";

    private final Index targetIndex;

    private Person targetPerson;

    private Person editedPerson;

    private String path;

    private String photoNameWithExtension;

    private boolean isTestMode;

    private int osType;

    /**
     * Creates an AddPhotoCommand to add the specified {@code Photo}
     */
    public AddPhotoCommand(Index index) {
        this.targetIndex = index;
        isTestMode = false;
        registerAsAnEventHandler(this);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        //check if it is test mode.
        if (!isTestMode) {
            EventsCenter.getInstance().post(new ShowFileChooserEvent());
        } else {
            String currentDir = System.getProperty("user.dir");
            path = currentDir + "/src/main/java/resources/images/personphoto/DefaultPerson.png";
        }

        //check if the photo is chosen.
        if (path.equals("NoFileChoosed")) {
            return new CommandResult(MESSAGE_PHOTO_NOT_CHOSEN);
        }

        //check if the photo is of right type.
        if (!Photo.isValidPhotoName(path)) {
            return new CommandResult(MESSAGE_INVALID_PHOTO_TYPE);
        }

        List<Person> lastShownList = model.getFilteredPersonList();
        targetPerson = lastShownList.get(targetIndex.getZeroBased());


        if (!path.contains("/"))  { //windows
            this.osType = 1;
            photoNameWithExtension = path.substring(path.lastIndexOf("\\") + 1);
        } else {
            this.osType = 0;
            photoNameWithExtension = path.substring(path.lastIndexOf("/") + 1);
        }

        if (!model.getPhotoList().contains(new Photo(photoNameWithExtension))) {
            copyPhotoFileToStorage(photoNameWithExtension);
        }

        editedPerson = createEditedPerson(targetPerson, photoNameWithExtension);

        try {
            model.updatePerson(targetPerson, editedPerson);
        } catch (DuplicatePersonException e) {
            e.printStackTrace();
        } catch (PersonNotFoundException e) {
            e.printStackTrace();
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        EventsCenter.getInstance().post(new ResetPersonCardsEvent());

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        targetPerson = lastShownList.get(targetIndex.getZeroBased());
        editedPerson = createEditedPerson(targetPerson, photoNameWithExtension);
        EventsCenter.getInstance().post(new PersonEditedEvent(editedPerson));
    }

    /**
     * create a person with photo updated.
     * @param targetPerson
     * @param photoNameWithExtension
     * @return editedPerson
     */
    private Person createEditedPerson(Person targetPerson, String photoNameWithExtension) {
        Photo newPhoto = new Photo(photoNameWithExtension);
        targetPerson.setPhotoName(newPhoto.getName());
        Person editedPerson = new Person(targetPerson.getName(), targetPerson.getPhone(), targetPerson.getEmail(),
                targetPerson.getAddress(), targetPerson.getTags(), targetPerson.getCalendarId());
        editedPerson.setRating(targetPerson.getRating());
        editedPerson.setId(targetPerson.getId());
        editedPerson.setReviews(targetPerson.getReviews());
        editedPerson.setPhotoName(newPhoto.getName());
        return editedPerson;
    }

    /**
     * copy the file chosen by user to application's storage.
     * @param photoNameWithExtension
     */
    private void copyPhotoFileToStorage(String photoNameWithExtension) {

        String src = path;
        String dest;
        if (osType == 1) {
            dest = IMAGE_FOLDER + photoNameWithExtension;
        } else {
            dest = IMAGE_FOLDER + photoNameWithExtension;
        }

        byte[] buffer = new byte[1024];
        try {
            FileInputStream fis = new FileInputStream(src);
            BufferedInputStream bis = new BufferedInputStream(fis);


            FileOutputStream fos = new FileOutputStream(dest);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int len;
            while ((len = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }

            bis.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPhotoCommand // instanceof handles nulls
                && targetIndex.equals(((AddPhotoCommand) other).targetIndex)
                && (path == null || path.equals(((AddPhotoCommand) other).path)));
    }

    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Subscribe
    private void handleFileChoosedEvent(FileChoosedEvent event) {
        this.path = event.getFilePath();
    }

    public void setTestMode() {
        this.isTestMode = true;
    }
}
