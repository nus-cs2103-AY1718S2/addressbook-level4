# Livian1107
###### \java\seedu\progresschecker\commons\events\ui\ChangeThemeEvent.java
``` java
/**
 * Represents the change of the theme of ProgressChecker.
 */
public class ChangeThemeEvent extends BaseEvent {
    public final String theme;

    public ChangeThemeEvent(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getTheme() {
        return theme;
    }
}
```
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
###### \java\seedu\progresschecker\logic\commands\ThemeCommand.java
``` java
/**
 * Changes the thmem of ProgressChecker.
 */
public class ThemeCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "t";

    public static final String COMMAND_FORMAT = COMMAND_WORD + " "
            + " THEME";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change theme of ProgressChecker.\n"
            + "Parameters: " + "Theme(either 'day' or 'night')\n"
            + "Example: " + COMMAND_WORD + "day";

    public static final String MESSAGE_SUCCESS = "Change to theme %1$s";

    public final String theme;

    public ThemeCommand(String theme) {
        this.theme = theme;
    }

    @Override
    protected CommandResult executeUndoableCommand() {
        EventsCenter.getInstance().post(new ChangeThemeEvent(theme));
        return new CommandResult(String.format(MESSAGE_SUCCESS, theme));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && this.theme.equals(((ThemeCommand) other).theme)); // state check
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
###### \java\seedu\progresschecker\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code type} into a {@code String} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified theme is invalid (not of string "day" or "night").
     */
    public static String parseTheme(String theme) throws IllegalValueException {
        String trimmedType = theme.trim();
        if (!trimmedType.equals("day") && !trimmedType.equals("night")) {
            throw new IllegalValueException(MESSAGE_INVALID_TAB_TYPE);
        }
        return trimmedType;
    }

```
###### \java\seedu\progresschecker\logic\parser\ThemeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ThemeCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ThemeCommand parse(String args) throws ParseException {
        try {
            String theme = ParserUtil.parseTheme(args);
            return new ThemeCommand(theme);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
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
        scene.getStylesheets().setAll(DARK_THEME);
        primaryStage.setScene(scene);
        show();
    }

    /**
     * Switches to the Day Theme.
     */
    @FXML
    public void handleDayTheme() {
        Scene scene = primaryStage.getScene();
        scene.getStylesheets().setAll(DAY_THEME);
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
###### \java\seedu\progresschecker\ui\ProfilePanel.java
``` java

/**
 * Panel contains the information of person
 */
public class ProfilePanel extends UiPart<Region>  {

    private static final String FXML = "ProfilePanel.fxml";
    private static String DEFAULT_PHOTO = "/images/profile_photo.jpg";

    private static final String[] TAG_COLORS = { "red", "orange", "yellow", "green", "blue", "purple" };
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private Person person;
    private Person currentlyViewedPerson;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label major;
    @FXML
    private Label year;
    @FXML
    private Label email;
    @FXML
    private Label username;
    @FXML
    private FlowPane tags;
    @FXML
    private Ellipse profile;

    public ProfilePanel() {
        super(FXML);
        this.person = null;
        loadDefaultPerson();
        registerAsAnEventHandler(this);

    }

```
###### \java\seedu\progresschecker\ui\ProfilePanel.java
``` java
    /**
     * Loads the default person
     */
    private void loadDefaultPerson() {
        name.setText("Person X");
        phone.setText("");
        username.setText("");
        email.setText("");
        year.setText("");
        major.setText("");
        tags.getChildren().clear();

        setDefaultInfoPhoto();
        currentlyViewedPerson = null;
        logger.info("Currently Viewing: Default Person");
    }

    /**
     * Loads the info of the selected person
     */
    private void loadPerson(Person person) {
        this.person = person;
        tags.getChildren().clear();
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        major.setText(person.getMajor().value);
        year.setText(person.getYear().value);
        email.setText(person.getEmail().value);
        username.setText(person.getUsername().username);
```
###### \java\seedu\progresschecker\ui\ProfilePanel.java
``` java
        loadPhoto();

        currentlyViewedPerson = person;
        logger.info("Currently Viewing: " + currentlyViewedPerson.getName());
    }

    /**
     * Sets the default info photo.
     */
    public void setDefaultInfoPhoto() {
        Image defaultImage = new Image(MainApp.class.getResourceAsStream(DEFAULT_PHOTO));
        profile.setFill(new ImagePattern(defaultImage));
    }

    /**
     * Loads profile photo
     */
    private void loadPhoto() {
        String photoPath = person.getPhotoPath();
        Image profilePhoto;
        if (photoPath.contains("contact")) {
            File photo = new File(photoPath);
            if (photo.exists() && !photo.isDirectory()) {
                String url = photo.toURI().toString();
                profilePhoto = new Image(url);
                profile.setFill(new ImagePattern(profilePhoto));
            }
        } else {
            profilePhoto = new Image(
                    MainApp.class.getResourceAsStream(person.getDefaultPath()));
            profile.setFill(new ImagePattern(profilePhoto));
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangeEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPerson(event.getNewSelection().person);
    }
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
         <TabPane fx:id="tabPlaceholder" prefHeight="200.0" prefWidth="200.0" tabClosingPolicy="UNAVAILABLE">
           <tabs>
             <Tab fx:id="profilePlaceholder" text="Profile">
                 <StackPane fx:id="profilePanelPlaceholder" VBox.vgrow="ALWAYS"/>
             </Tab>
             <Tab fx:id="taskPlaceholder" text="Task">
```
###### \resources\view\ProfilePanel.fxml
``` fxml

<Pane xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <ImageView fx:id="profileBackground" fitHeight="500.0" fitWidth="1000.0" pickOnBounds="true" preserveRatio="true">
         <image>
            <Image url="@../images/profile_background.png" />
         </image>
      </ImageView>
      <Ellipse fx:id="profile" centerX="300.0" centerY="100.0" fill="WHITE" layoutX="200.0" radiusX="100.0" radiusY="80.0" stroke="BLACK" strokeType="INSIDE" />
      <GridPane layoutX="400.0" layoutY="180.0">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="94.0" minWidth="10.0" prefWidth="65.0" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="146.0" minWidth="10.0" prefWidth="135.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Name:">
               <font>
                  <Font name="System Bold" size="12.0" />
               </font></Text>
            <Label fx:id="name" prefHeight="17.0" prefWidth="136.0" text="\$name" GridPane.columnIndex="1" />
         </children>
      </GridPane>
      <FlowPane fx:id="tags" layoutX="28.0" layoutY="20.0" prefHeight="28.0" prefWidth="135.0" />
      <GridPane layoutX="400.0" layoutY="250.0">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="95.0" minWidth="10.0" prefWidth="69.0" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="135.0" minWidth="10.0" prefWidth="131.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints />
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
            <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
            <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
            <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
            <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <Label fx:id="phone" prefHeight="17.0" prefWidth="128.0" text="\$phone" GridPane.columnIndex="1" GridPane.rowIndex="1" />
            <Label fx:id="email" prefHeight="17.0" prefWidth="134.0" text="\$email" GridPane.columnIndex="1" GridPane.rowIndex="2" />
            <Label fx:id="username" prefHeight="17.0" prefWidth="132.0" text="\$username" GridPane.columnIndex="1" GridPane.rowIndex="3" />
            <Label fx:id="major" prefHeight="17.0" prefWidth="135.0" text="\$major" GridPane.columnIndex="1" GridPane.rowIndex="4" />
            <Label fx:id="year" prefHeight="17.0" prefWidth="130.0" text="\$year" GridPane.columnIndex="1" GridPane.rowIndex="5" />
            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Phone:" GridPane.rowIndex="1" />
            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Email:" GridPane.rowIndex="2" />
            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="GitHub:" GridPane.rowIndex="3" />
            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Major:" GridPane.rowIndex="4" />
            <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Year:" GridPane.rowIndex="5" />
         </children>
      </GridPane>
   </children>
</Pane>
```
