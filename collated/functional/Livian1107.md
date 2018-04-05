# Livian1107
###### \java\seedu\progresschecker\logic\commands\SortCommand.java
``` java
/**
 * Sorts all persons in the ProgressChecker in alphabetical order.
 */
public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "so";

    public static final String MESSAGE_SUCCESS = "Sorted all persons in alphabetical order";

    @Override
    protected CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
```
###### \java\seedu\progresschecker\logic\commands\UploadCommand.java
``` java
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
```
###### \java\seedu\progresschecker\logic\parser\UploadCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UploadCommand object
 */
public class UploadCommandParser  implements Parser<UploadCommand>  {
    /**
     * Parses the given {@code String} of arguments in the context of the UploadCommand
     * and returns an UploadCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UploadCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;
        String[] content = args.trim().split(" ");
        try {
            index = ParserUtil.parseIndex(content[0]);
            return new UploadCommand(index, content[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadCommand.MESSAGE_USAGE));
        } catch (IOException e) {
            throw new ParseException(
                    UploadCommand.MESSAGE_IMAGE_NOT_FOUND);
        }

    }
}
```
###### \java\seedu\progresschecker\model\ModelManager.java
``` java
    @Override
    public void uploadPhoto(Person target, String path)
            throws PersonNotFoundException, DuplicatePersonException {
        progressChecker.uploadPhoto(target, path);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateProgressCheckerChanged();
    }

    @Override
    public void addPhoto(PhotoPath photoPath) throws DuplicatePhotoException {
        progressChecker.addPhotoPath(photoPath);
        indicateProgressCheckerChanged();
    }
```
###### \java\seedu\progresschecker\model\photo\exceptions\DuplicatePhotoException.java
``` java
/**
 * Signals that the operation will result in duplicate PhotoPath objects.
 */
public class DuplicatePhotoException extends DuplicateDataException {
    public DuplicatePhotoException() {
        super("Operation would result in duplicate photos");
    }
}
```
###### \java\seedu\progresschecker\model\photo\exceptions\PhotoNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified photo.
 */
public class PhotoNotFoundException extends Exception {}
```
###### \java\seedu\progresschecker\model\photo\PhotoPath.java
``` java
/**
 * Represents a Path of Photo in ProgressChecker
 */
public class PhotoPath {

    public static final String PHOTO_SAVED_PATH = "src/main/resources/images/contact/";
    public static final String MESSAGE_PHOTOPATH_CONSTRAINTS =
            "The path of the profile photo should start with '" + PHOTO_SAVED_PATH
                    + "'. The extensions of the file to upload should be 'jpg', 'jpeg' or 'png'.";

    public final String value;

    /**
     * Builds the path of profile photo in the ProgressChecker
     * Validates the given String of path
     * @param path is the String of the profile photo path
     * @trhows IllegalValueException if the String violates the constraints of photo path
     */
    public PhotoPath(String path) throws IllegalValueException {
        requireNonNull(path);
        if (isValidPhotoPath(path)) {
            this.value = path;
        } else {
            throw new IllegalValueException(MESSAGE_PHOTOPATH_CONSTRAINTS);
        }
    }

    /**
     * Validates the given photo path
     */
    public boolean isValidPhotoPath (String path) {
        if (path.isEmpty()) { //empty path
            return true;
        }
        boolean isValidImage = isValidImageFile(path);
        boolean isUnderFolder = isUnderFolder(path, PHOTO_SAVED_PATH);
        return isValidImage && isUnderFolder;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhotoPath // instanceof handles nulls
                && this.value.equals(((PhotoPath) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\progresschecker\model\photo\UniquePhotoList.java
``` java
/**
 * A list of photo paths that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see PhotoPath#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniquePhotoList implements Iterable<PhotoPath> {

    private final ObservableList<PhotoPath> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent photo path as the given argument.
     */
    public boolean contains(PhotoPath toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a photo path to the list.
     *
     * @throws DuplicatePhotoException if the photo path to add is a duplicate of an existing photo path in the list.
     */
    public void add(PhotoPath toAdd) throws DuplicatePhotoException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePhotoException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the photo path {@code target} in the list with {@code editedPhoto}.
     *
     * @throws DuplicatePhotoException if the replacement is equivalent to another existing photo path in the list.
     * @throws PhotoNotFoundException if {@code target} could not be found in the list.
     */
    public void setPhoto(PhotoPath target, PhotoPath editedPhoto)
            throws DuplicatePhotoException, PhotoNotFoundException {
        requireNonNull(editedPhoto);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PhotoNotFoundException();
        }

        if (!target.equals(editedPhoto) && internalList.contains(editedPhoto)) {
            throw new DuplicatePhotoException();
        }

        internalList.set(index, editedPhoto);
    }

    /**
     * Removes the equivalent photo path from the list.
     *
     * @throws PhotoNotFoundException if no such person could be found in the list.
     */
    public boolean remove(PhotoPath toRemove) throws PhotoNotFoundException {
        requireNonNull(toRemove);
        final boolean photoFoundAndDeleted = internalList.remove(toRemove);
        if (!photoFoundAndDeleted) {
            throw new PhotoNotFoundException();
        }
        return photoFoundAndDeleted;
    }

    public void setPhotos(UniquePhotoList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPhotos(List<PhotoPath> photos) throws DuplicatePhotoException {
        requireAllNonNull(photos);
        final UniquePhotoList replacement = new UniquePhotoList();
        for (final PhotoPath photo : photos) {
            replacement.add(photo);
        }
        setPhotos(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<PhotoPath> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<PhotoPath> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePhotoList // instanceof handles nulls
                && this.internalList.equals(((UniquePhotoList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\progresschecker\model\ProgressChecker.java
``` java
    /**
     * Sorts the existing {@code UniquePersonList} of this {@code ProgressChecker}
     * with their names in alphabetical order.
     */
    public void sort() {
        requireNonNull(persons);
        persons.sort();
    }

    /**
     * Adds a new uploaded photo path to the the list of profile photos
     * @param photoPath of a new uploaded photo
     * @throws DuplicatePhotoException if there already exists the same photo path
     */
    public void addPhotoPath(PhotoPath photoPath) throws DuplicatePhotoException {
        photos.add(photoPath);
    }
```
###### \java\seedu\progresschecker\model\ProgressChecker.java
``` java
    /**
     * Uploads the profile photo path of target person
     * @param target
     * @param path
     * @throws PersonNotFoundException
     * @throws DuplicatePersonException
     */
    public void uploadPhoto(Person target, String path) throws PersonNotFoundException, DuplicatePersonException {
        Person tempPerson = target;
        target.updatePhoto(path);
        persons.setPerson(tempPerson, target);
    }
```
###### \java\seedu\progresschecker\ui\MainWindow.java
``` java
    /**
     * Switches to the Night Theme.
     */
    @FXML
    public void handleNightTheme() {
        Scene scene = primaryStage.getScene();
        scene.getStylesheets().setAll("view/DarkTheme.css");
        primaryStage.setScene(scene);
        show();
    }

    /**
     * Switches to the Day Theme.
     */
    @FXML
    public void handleDayTheme() {
        Scene scene = primaryStage.getScene();
        scene.getStylesheets().setAll("view/DayTheme.css");
        primaryStage.setScene(scene);
        show();
    }
```
###### \java\seedu\progresschecker\ui\MainWindow.java
``` java
    /**
     * Sets the icon of Main Window
     * @param icon with given path
     */
    private void setIcon(String icon) {
        primaryStage.getIcons().setAll(AppUtil.getImage(icon));
    }

    /**
     * Sets the minimum size of the main window
     */
    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }
```
###### \resources\view\MainWindow.fxml
``` fxml
            <Menu mnemonicParsing="false" text="Theme">
                <MenuItem fx:id="nightTheme" mnemonicParsing="false" onAction="#handleNightTheme" text="NightTheme" />
                <MenuItem fx:id="dayTheme" mnemonicParsing="false" onAction="#handleDayTheme" text="DayTheme" />
            </Menu>
```
###### \resources\view\MainWindow.fxml
``` fxml
         <TabPane prefHeight="200.0" prefWidth="200.0" tabClosingPolicy="UNAVAILABLE">
           <tabs>
             <Tab text="Profile" />
             <Tab text="Task">
                 <StackPane fx:id="browserPlaceholder"/>
             </Tab>
               <Tab text="Exercise">
                   <StackPane fx:id="exerciseListPanelPlaceholder" VBox.vgrow="ALWAYS"/>
               </Tab>
           </tabs>
         </TabPane>
```
