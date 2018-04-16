# dezhanglee
###### \java\seedu\address\logic\commands\AddPictureCommand.java
``` java
/**
 * Adds a profile picture to a person in the address book
 */
public class AddPictureCommand extends Command {

    public static final String COMMAND_WORD = "addpicture";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Picture for Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "A valid file must be provided.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the picture of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) " + PREFIX_FILEPATH
            + "FILEPATH (path to a valid image file)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_FILEPATH + "C://Pictures/johnPicture.jpg";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final String path;

    private Person personToEdit;
    private Person editedPerson;

    public AddPictureCommand(Index index, String path) {

        requireNonNull(index);
        requireNonNull(path);

        this.index = index;
        this.path = path;

    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        int personIndex = index.getZeroBased();

        if (personIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(personIndex);
        editedPerson = new Person(personToEdit);

        try {
            editedPerson.setPicture(path);
        } catch (IllegalValueException e) {
            throw new CommandException(Picture.MESSAGE_PICTURE_CONSTRAINTS);
        }

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, index.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPictureCommand // instanceof handles nulls
                && this.index.equals(((AddPictureCommand) other).index)
                && this.path.equals(((AddPictureCommand) other).path)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\AddTagCommand.java
``` java
/**
 * Add tags to an existing person in the Address Book
 */
public class AddTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add tag(s) to the person identified "
            + "by the index number used in the last person listing. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG] " + "[" + PREFIX_TAG + "TAG] ...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "NUS " + PREFIX_TAG + "CS2103 " + PREFIX_TAG + "Tutor";

    public static final String MESSAGE_ADD_TAG_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_TAGS_MUST_NOT_EXIST = "All input tags must be new.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Set<Tag> tags;


    /**
     * @param index of the person in the filtered person list to edit
     * @param tags to be added to person refered to by index
     */
    public AddTagCommand(Index index, Set<Tag> tags) {
        requireNonNull(index);
        requireNonNull(tags);

        this.index = index;
        this.tags = tags;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit);

        try {
            editedPerson.addTags(tags);
        } catch (DuplicateTagException dte) {
            throw new CommandException(MESSAGE_TAGS_MUST_NOT_EXIST);
        }

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_TAG_PERSON_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTagCommand // instanceof handles nulls
                && this.index.equals(((AddTagCommand) other).index)
                && this.tags.equals(((AddTagCommand) other).tags)); // state check
    }

}
```
###### \java\seedu\address\logic\commands\DeleteByNameCommand.java
``` java
/**
 * Deletes a person by name. Case insensitive
 * Will not work if there are multiple people with same names, use DeleteCommand instead
 */

public class DeleteByNameCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletebyname";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the input NAME. Non case sensitive.\n"
            + "NAME must be the full name of the person.\n"
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " John Doe";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public static final String MESSAGE_NAME_NOT_FOUND = "Person with input name not found. "
            + "Please ensure that you typed the full name of the person, and "
            + "check if the person exists using the list command - \n"
            + ListCommand.MESSAGE_USAGE;

    public static final String MESSAGE_MULTIPLE_SAME_NAME = "There are several people with the same name."
            + "Please use the delete command instead -  \n"
            + DeleteCommand.MESSAGE_USAGE;


    private Name inputName;
    private List<Person> allPersons;
    private List <Person> personsWithMatchingName;
    private Person personToBeDeleted;



    public DeleteByNameCommand(Name inputName) {

        this.inputName = inputName;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        searchForPersonByName();

        if (personsWithMatchingName.size() == 0) {
            throw new CommandException(MESSAGE_NAME_NOT_FOUND);
        } else if (personsWithMatchingName.size() > 1) {
            throw new CommandException(MESSAGE_MULTIPLE_SAME_NAME);
        }

        try {
            personToBeDeleted = personsWithMatchingName.get(0);
            model.deletePerson(personToBeDeleted);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToBeDeleted));
    }

    /**
     * Helper function: Extract and search PersonList for the name
     */

    public void searchForPersonByName() {

        this.allPersons = model.getAddressBook().getPersonList();

        Stream<Person> searchPerson = allPersons.stream()
                .filter(person -> person.getName().toString().toLowerCase()
                        .equals(inputName.toString().toLowerCase())); //

        this.personsWithMatchingName = searchPerson.collect(Collectors.toList());

    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        } else if (other instanceof DeleteByNameCommand) {
            Name otherName = ((DeleteByNameCommand) other).inputName;
            return otherName.equals(this.inputName);
        }
        return false;
    }
}
```
###### \java\seedu\address\logic\commands\ResetPictureCommand.java
``` java
/**
 * Resets the picture of a contact to the default picture
 */
public class ResetPictureCommand extends Command {

    public static final String COMMAND_WORD = "resetpicture";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Reset Picture for Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "A valid index must be specified.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Resets the picture of the person identified by the index number used in the last person listing "
            + "to the default picture.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;

    private Person personToEdit;
    private Person editedPerson;

    public ResetPictureCommand(Index index) {

        requireNonNull(index);

        this.index = index;

    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        int personIndex = index.getZeroBased();

        if (personIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(personIndex);
        editedPerson = new Person(personToEdit);

        editedPerson.resetPicture();

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, index.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ResetPictureCommand // instanceof handles nulls
                && this.index.equals(((ResetPictureCommand) other).index)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddPictureCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddPictureCommand object
 */
public class AddPictureCommandParser implements Parser<AddPictureCommand> {

    private String path;
    private Index index;
    /**
     * Parses the given {@code String} of arguments in the context of the AddPictureCommand
     * and returns an AddPictureCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPictureCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILEPATH);

        if (!arePrefixesPresent(argMultimap, PREFIX_FILEPATH)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPictureCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (Exception e) {
            throw new ParseException(Picture.MESSAGE_PICTURE_CONSTRAINTS);
        }

        try {
            path = ParserUtil.parseImageFilename(argMultimap.getValue(PREFIX_FILEPATH).get());
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPictureCommand.MESSAGE_USAGE));
        }


        return new AddPictureCommand(index, path);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\AddTagCommandParser.java
``` java
/**
 * Parses input arguments to extract index and tags, and creates a new AddTagCommand object
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns an AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        Index index;
        List<String> tags;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }
;

        Set<Tag> tagSet;

        try {
            tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        } catch (IllegalValueException ive) {
            System.out.println(ive.getMessage());
            throw new ParseException(ive.getMessage(), ive);
        }

        return new AddTagCommand(index, tagSet);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\DeleteByNameCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteByNameCommandParser implements Parser<DeleteByNameCommand> {

    private Name inputName;
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteByNameCommand
     * and returns an DeleteByNameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteByNameCommand parse(String args) throws ParseException {

        try {
            inputName = ParserUtil.parseName(args);
            return new DeleteByNameCommand(inputName);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\ResetPictureCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ResetPictureCommand object
 */
public class ResetPictureCommandParser implements Parser<ResetPictureCommand> {

    private String path;
    private Index index;
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteByNameCommand
     * and returns an DeleteByNameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ResetPictureCommand parse(String args) throws ParseException {

        try {
            Index index = ParserUtil.parseIndex(args);
            return new ResetPictureCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResetPictureCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Picture pic, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.picture = pic;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public Picture getPicture() {
        return picture;
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Update contact picture to that located in path
     * @param path
     */
    public void updatePicture(String path) {
        int hash = this.hashCode();
        String filename = String.valueOf(hash);
        this.picture = new Picture(path, filename);
    }

    /**
     * Resets the current picture to a default picture.
     */
    public void resetPicture() {
        this.picture = new Picture();
    }

    /**
     * Set profile picture to that in path
     */
    public void setPicture(String path) throws IllegalValueException {

        Picture oldPic = this.picture;
        try {
            int fileName = this.hashCode();
            this.picture = new Picture(path, String.valueOf(fileName));
        } catch (Exception e) {
            this.picture = oldPic; //reset picture back to default
            throw new IllegalValueException(Picture.MESSAGE_PICTURE_CONSTRAINTS);
        }
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Adds tags from {@code toAdd} to existing Person tag list, throws exception if there are duplicate tags
     * @param toAdd
     * @throws DuplicateTagException
     */
    public void addTags(Set<Tag> toAdd) throws DuplicateTagException {

        for (Tag t : toAdd) {
            this.tags.add(t);
        }
    }
```
###### \java\seedu\address\model\person\Picture.java
``` java
/**
 * Represents a Picture in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Picture {

    public static final String DEFAULT_PATH = "images/default.png";
    public static final String MESSAGE_PICTURE_CONSTRAINTS =
            "Filepath must be valid, point to an image file, and is less than 10MB in size";
    public static final String PICTURE_VALIDATION_REGEX_EXT = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9._-]+)+\\\\?";
    public static final String PICTURE_VALIDATION_REGEX_INT = "[^\\s].*";
    public static final String APPDATA_DIR = getDefaultDirectory();
    public static final String FOLDER = APPDATA_DIR + "/AddressBook";
    private static final String URL_PREFIX = "file:/";

    private static final int FIVE_MB_IN_BYTES = 5242880;

    private String path;

    /**
     * Default initializer, uses default picture
     */
    public Picture() {

        this.path = DEFAULT_PATH;
    }

    /**
     * initializer if path pointing to pic is specified. For now, only called by XmlAdaptedPerson class
     * Since its only called by XmlAdaptedPerson (which saves the picture filepaths based on last successful state),
     * arguments are correct and hence no need to call checkArgument on {@code path}
     * @param path
     */
    public Picture(String path) {

        requireNonNull(path);
        String p = truncateFilePrefix(path);
        //if invalid/outdated copy of picture in XMLAdaptedPerson, reset to default pic.
        if (isValidPath(p)) {
            this.path = path;
        } else {
            this.path = DEFAULT_PATH;
        }
    }

    /**
     * initializer if path, and image name of new picture is specified
     *
     * @param path
     * @param newPictureName
     */
    public Picture(String path, String newPictureName) {

        requireNonNull(path);
        checkArgument(isValidPath(path), MESSAGE_PICTURE_CONSTRAINTS);
        String extension = "";

        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i + 1);
        }
        this.path = FOLDER + "//"  + newPictureName + "." + extension;

        createNewPicture(path, newPictureName + "." + extension);

    }

    /**
     * Check if path is valid
     *
     * @param path
     * @return
     */
    public static boolean isValidPath(String path) {

        if (path.equals(DEFAULT_PATH)) {
            return true;
        }

        boolean isValidExternalPath = path.matches(PICTURE_VALIDATION_REGEX_EXT);
        boolean isValidInternalPath = path.matches(PICTURE_VALIDATION_REGEX_INT);

        if (isValidExternalPath || isValidInternalPath) {
            return isValidPicture(path);
        }
        return false;
    }

    /**
     * Checks if the image pointed to by the path is indeed a valid image
     *
     * @param path
     * @return
     */
    public static boolean isValidPicture(String path) {

        File f = new File(path);

        try {
            if (ImageIO.read(f) == null) {
                return false;
            }
        } catch (Exception e) { //cannot read image properly
            return false;
        }

        return isValidSize(path);
    }
    /**
     * Check if image is greater than allowed size
     *
     * @param path
     * @return
     */
    public static boolean isValidSize(String path) {

        File f = new File(path);

        return (f.length() <= FIVE_MB_IN_BYTES);
    }

    /**
     * copies the file from (@code source) to the profile pic folder
     * If unable to copy, then just directly use file at source.
     * This will work since we have already passed {@code source} into isValidPath to ensure it can be read
     * TODO: display some kind of commandresult to notify user of this ^
     * @param source
     * @param dstFilename
     */
    public void createNewPicture(String source, String dstFilename) {

        File folder = new File(FOLDER);

        if (!folder.exists()) {

            if (!folder.mkdir()) {
                this.path = URL_PREFIX + source;
                return;
            }
        }

        File src = null;

        try {
            src = new File(source);
        } catch (Exception e) {
            this.path = URL_PREFIX + source;
            return;
        }


        File dest = new File(FOLDER + "//" + dstFilename);

        if (!dest.exists()) {
            try {
                dest.createNewFile();
            } catch (Exception e) {
                this.path = URL_PREFIX + source;
                return;
            }
        }

        try {
            Files.copy(src.toPath(), dest.toPath() , REPLACE_EXISTING);
            this.path = URL_PREFIX + dest.toPath().toString();
        } catch (Exception e) {
            this.path = URL_PREFIX + source;
        }
    }

    public String getPath() {
        return this.path;
    }

    /**
     * Determines the User OS and output the appropriate folder to store profile pic
     *
     * @return
     */
    private static String getDefaultDirectory() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("WIN")) {
            return System.getenv("APPDATA");
        } else if (os.contains("MAC")) {
            return System.getProperty("user.home") + "/Library/Application "
                    + "Support";
        } else if (os.contains("NUX")) {
            return System.getProperty("user.home");
        }
        return System.getProperty("user.dir");
    }

    /**
     * Removes the "file:/" prefix from a filepath, so that can use isValidPath on p
     * @param p
     * @return
     */
    private String truncateFilePrefix(String p) {

        if (p.substring(0, 6).equals("file:/")) {
            return p.substring(6);
        }
        return p;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Picture // instanceof handles nulls
                && this.path.equals(((Picture) other).path)); // state check
    }


}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    /**
     * Constructs an {@code XmlAdaptedPerson} with an additional Picture parameter
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address, String picture,
                            List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        this.picture = picture;
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * initialize javafx imageview to profile pic of (@code person)
     * @param person
     */
    public void initProfilePic(Person person) {


        try {
            String url = person.getPicture().getPath();
            imageView.setImage(new Image(url, 128, 128, true, false));
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImage(new Image(BROKEN_IMAGE_URL, 128, 128, true, false));
        }
    }
```
###### \resources\view\PersonListCard.fxml
``` fxml
      </VBox>
      <ImageView fx:id="imageView" fitHeight="64" fitWidth="64">
      </ImageView>
    </HBox>
  </GridPane>
</HBox>
```
