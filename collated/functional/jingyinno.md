# jingyinno
###### \java\seedu\address\commons\events\ui\VenueTableEvent.java
``` java
/**
 * Represents a schedule list in VenueTable
 */
public class VenueTableEvent extends BaseEvent {

    private final ObservableList<ArrayList<String>> schedule;

    public VenueTableEvent(ObservableList<ArrayList<String>> schedule) {
        this.schedule = schedule;
    }

    public ObservableList<ArrayList<String>> getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return null;
    }
}
```
###### \java\seedu\address\logic\commands\AliasCommand.java
``` java
/**
 * Adds an alias pair to the address book.
 */
public class AliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "alias";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows list of alias or creates new alias.\n"
            + "Parameters: [COMMAND] [NEW_ALIAS]\n"
            + "Example: " + COMMAND_WORD + " add a";

    public static final String MESSAGE_SUCCESS = "New alias added";
    public static final String MESSAGE_DUPLICATE_ALIAS = "This alias is already used";
    public static final String MESSAGE_INVALID_ALIAS = "Invalid alias word! \n%1$s";
    public static final String MESSAGE_INVALID_ALIAS_DESCRIPTION = "Alias word is a command word. \n"
            + "Please choose another alias";
    public static final String MESSAGE_INVALID_COMMAND = "Invalid command word! \n%1$s";
    public static final String MESSAGE_INVALID_COMMAND_DESCRIPTION = "There is no such command to alias to.";
    private static final List<String> commands = Arrays.asList(AddCommand.COMMAND_WORD, EditCommand.COMMAND_WORD,
            SelectCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD,
            UndoCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, AliasCommand.COMMAND_WORD, ImportCommand.COMMAND_WORD);

    private final Alias toAdd;

    /**
     * Creates an AliasCommand to add the specified {@code Alias}
     */
    public AliasCommand(Alias alias) {
        requireNonNull(alias);
        toAdd = alias;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        if (!commands.contains(toAdd.getCommand())) {
            throw new CommandException(
                    String.format(AliasCommand.MESSAGE_INVALID_COMMAND,
                            AliasCommand.MESSAGE_INVALID_COMMAND_DESCRIPTION));
        } else if (commands.contains(toAdd.getAlias())) {
            throw new CommandException(
                    String.format(AliasCommand.MESSAGE_INVALID_ALIAS,
                            AliasCommand.MESSAGE_INVALID_ALIAS_DESCRIPTION));
        }
        try {
            model.addAlias(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateAliasException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ALIAS);
        }

    }

    public static List<String> getCommands() {
        return commands;
    }

    @Override
    public boolean equals(Object other) {
        return other == (this)
                || (other instanceof AliasCommand // instanceof handles nulls
                && toAdd.equals(((AliasCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\UnaliasCommand.java
``` java
/**
 * Removes an alias pair from the address book.
 */
public class UnaliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unalias";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes alias for previously aliased command.\n"
            + "Parameters: [CURRENT_ALIAS]\n"
            + "Example: " + COMMAND_WORD + " a";

    public static final String MESSAGE_SUCCESS = "Alias has been removed!";
    public static final String MESSAGE_UNKNOWN_UNALIAS = "This alias does not exist.";

    private final String toRemove;

    /**
     * Creates an UnaliasCommand to add the specified {@code Alias}
     */
    public UnaliasCommand(String unalias) {
        requireNonNull(unalias);
        toRemove = unalias;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.removeAlias(toRemove);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove));
        } catch (AliasNotFoundException e) {
            throw new CommandException(MESSAGE_UNKNOWN_UNALIAS);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == (this)
                || (other instanceof UnaliasCommand // instanceof handles nulls
                && toRemove.equals(((UnaliasCommand) other).toRemove));
    }
}
```
###### \java\seedu\address\logic\commands\VacantCommand.java
``` java
/**
 * Retrieves all vacant rooms in a given building
 */
public class VacantCommand extends Command {
    public static final String COMMAND_WORD = "vacant";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds vacant study rooms in a building \n"
            + "Parameters: [BUILDING_NAME]\n"
            + "Example: " + COMMAND_WORD + " COM1";

    public static final String MESSAGE_SUCCESS = "List of rooms in building successfully retrieved.";
    public static final String MESSAGE_INVALID_BUILDING =
            "Building is not in the list of NUS Buildings given below: \n"
            + Arrays.toString(Building.NUS_BUILDINGS);
    public static final String MESSAGE_CORRUPTED_VENUE_INFORMATION_FILE =
            "Unable to read from venueinformation.json, file is corrupted. Please re-download the file.";

    private final Building building;

    /**
     * Creates a VacantCommand to retrieve all vacant rooms in a given building
     */
    public VacantCommand(Building building) {
        requireNonNull(building);
        this.building = building;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            ArrayList<ArrayList<String>> allRoomsSchedule = model.retrieveAllRoomsSchedule(building);
            ObservableList<ArrayList<String>> schedule = FXCollections.observableArrayList(allRoomsSchedule);
            EventsCenter.getInstance().post(new VenueTableEvent(schedule));
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (BuildingNotFoundException e) {
            throw new CommandException(MESSAGE_INVALID_BUILDING);
        } catch (CorruptedVenueInformationException e) {
            throw new CommandException(MESSAGE_CORRUPTED_VENUE_INFORMATION_FILE);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VacantCommand // instanceof handles nulls
                && building.equals(((VacantCommand) other).building));
    }
}
```
###### \java\seedu\address\logic\parser\AliasCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns an AliasCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AliasCommand parse(String args) throws ParseException {
        args = args.trim();
        String[] trimmedArgs = args.split("\\s+");
        if (trimmedArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }
        try {
            Alias aliasCreated = ParserUtil.parseAlias(trimmedArgs[0], trimmedArgs[1]);
            return new AliasCommand(aliasCreated);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \java\seedu\address\logic\parser\UnaliasCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UnaliasCommand object
 */
public class UnaliasCommandParser implements Parser<UnaliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnaliasCommand
     * and returns an UnaliasCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnaliasCommand parse(String args) throws ParseException {
        String unalias = args.trim();
        if (unalias.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
        }

        try {
            String toBeRemoved = ParserUtil.parseUnalias(unalias);
            return new UnaliasCommand(toBeRemoved);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \java\seedu\address\logic\parser\VacantCommandParser.java
``` java
/**
 * Parses input arguments and creates a new VacantCommand object
 */
public class VacantCommandParser implements Parser<VacantCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the VacantCommand
     * and returns a VacantCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VacantCommand parse(String args) throws ParseException {
        args = args.trim();
        int length;
        String[] buildingName = args.split("\\s+");
        if ("".equals(args) || "\\s+".equals(args)) {
            length = 0;
        } else {
            length = buildingName.length;
        }
        if (length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, VacantCommand.MESSAGE_USAGE));
        }

        try {
            Building building = ParserUtil.parseBuilding(buildingName[0]);
            return new VacantCommand(building);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \java\seedu\address\model\alias\Alias.java
``` java
/**
 * Represents a Alias in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAliasName(String)}
 */
public class Alias {

    public static final String MESSAGE_ALIAS_CONSTRAINTS = "Alias names should be alphanumeric";
    public static final String ALIAS_VALIDATION_REGEX = "\\p{Alnum}+";

    private final String command;
    private final String aliasName;

    /**
     * Constructs a {@code Alias}.
     *
     * @param aliasName A valid alias name.
     */
    public Alias(String command, String aliasName) {
        requireNonNull(aliasName);
        checkArgument(isValidAliasName(aliasName), MESSAGE_ALIAS_CONSTRAINTS);
        this.aliasName = aliasName;
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public String getAlias() {
        return aliasName;
    }

    /**
     * Returns true if a given string is a valid alias name.
     */
    public static boolean isValidAliasName(String test) {
        return test.matches(ALIAS_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid unalias name.
     */
    public static boolean isValidUnaliasName(String test) {
        return test.matches(ALIAS_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Alias // instanceof handles nulls
                && this.aliasName.equals(((Alias) other).aliasName)
                && this.command.equals(((Alias) other).command)); // state check
    }

    @Override
    public int hashCode() {
        return aliasName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + aliasName + ']';
    }

}
```
###### \java\seedu\address\model\alias\exceptions\AliasNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified alias.
 */
public class AliasNotFoundException extends CommandException {
    public AliasNotFoundException() {
        super("Alias does not exist.");
    }
}
```
###### \java\seedu\address\model\alias\exceptions\DuplicateAliasException.java
``` java
/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class DuplicateAliasException extends DuplicateDataException {

    public static final String MESSAGE = "Operation would result in duplicate aliases";
    public DuplicateAliasException() {
        super(MESSAGE);
    }
}
```
###### \java\seedu\address\model\alias\UniqueAliasList.java
``` java
/**
 * A list of aliases that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Alias#equals(Object)
 */
public class UniqueAliasList {

    private static HashMap<String, String> hashList = new HashMap<String, String>();
    private ObservableList<Alias> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty AliasList.
     */
    public UniqueAliasList() {}

    /**
     * Returns true if the list contains an equivalent Alias as the given argument.
     */
    public static boolean contains(String toCheck) {
        requireNonNull(toCheck);
        return hashList.containsKey(toCheck);
    }

    /**
     * Returns the command of the alias.
     */
    public static String getCommandFromAlias(String alias) {
        requireNonNull(alias);
        return hashList.get(alias);
    }

    /**
     * Adds an Alias to the list.
     *
     * @throws DuplicateAliasException if the Alias to add is a duplicate of an existing Alias in the list.
     */
    public static void add(Alias toAdd) throws DuplicateAliasException {
        requireNonNull(toAdd);
        if (contains(toAdd.getAlias())) {
            throw new DuplicateAliasException();
        }
        hashList.put(toAdd.getAlias(), toAdd.getCommand());
    }

    /**
     * Removes an Alias from the list.
     *
     * @throws AliasNotFoundException if the Alias to remove is a does not exist in the list.
     */
    public static void remove(String toRemove) throws AliasNotFoundException {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new AliasNotFoundException();
        }
        hashList.remove(toRemove);
    }

    /**
     * Imports an Alias to the list if the Alias is not a duplicate of an existing Alias in the list.
     */
    public void importAlias(Alias toAdd) {
        requireNonNull(toAdd);
        if (!contains(toAdd.getAlias())) {
            hashList.put(toAdd.getAlias(), toAdd.getCommand());
        }
    }

    /**
     * Converts HashMap into an observable list
     */
    public void convertToList() {
        for (String key : hashList.keySet()) {
            Alias newAlias = new Alias(hashList.get(key), key);
            internalList.add(newAlias);
        }
    }

    /**
     * Getter for Observable list
     */
    public ObservableList<Alias> getAliasObservableList() {
        internalList = FXCollections.observableArrayList();
        convertToList();
        return internalList;
    }


    /**
     * Replaces the Aliases in this list with those in the argument alias list.
     */
    public void setAliases(Set<Alias> aliases) {
        requireAllNonNull(aliases);
        internalList.setAll(aliases);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Clears hashList, for clear command.
     */
    public void resetHashmap() {
        hashList.clear();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Alias> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Adds the given alias */
    void addAlias(Alias alias) throws DuplicateAliasException;
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Removes alias given the alias string to remove.
     */
    void removeAlias(String toRemove) throws AliasNotFoundException;
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addAlias(Alias alias) throws DuplicateAliasException {
        addressBook.addAlias(alias);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void removeAlias(String toRemove) throws AliasNotFoundException {
        addressBook.removeAlias(toRemove);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\storage\XmlAdaptedAlias.java
``` java
/**
 * JAXB-friendly adapted version of the Alias.
 */
public class XmlAdaptedAlias {

    @XmlElement(required = true)
    private String command;
    @XmlElement(required = true)
    private String aliasName;

    /**
     * Constructs an XmlAdaptedAlias.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAlias() {}

    /**
     * Constructs a {@code XmlAdaptedAlias} with the given {@code aliasName}.
     */
    public XmlAdaptedAlias(String command, String aliasName) {
        this.command = command;
        this.aliasName = aliasName;
    }

    /**
     * Converts a given Alias into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedAlias(Alias source) {
        command = source.getCommand();
        aliasName = source.getAlias();
    }

    /**
     * Converts this jaxb-friendly adapted alias object into the model's Alias object.
     *
     */
    public Alias toModelType() throws IllegalValueException {
        if (!Alias.isValidAliasName(aliasName)) {
            throw new IllegalValueException(Alias.MESSAGE_ALIAS_CONSTRAINTS);
        }
        return new Alias(command, aliasName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAlias)) {
            return false;
        }

        return aliasName.equals(((XmlAdaptedAlias) other).aliasName)
                && command.equals(((XmlAdaptedAlias) other).command);
    }
}
```
###### \java\seedu\address\ui\InfoPanel.java
``` java
    @Subscribe
    private void handleVenueTableEvent(VenueTableEvent event) {
        venuePlaceholder.getChildren().removeAll();
        venueTable = new VenueTable(event.getSchedule());
        venuePlaceholder.getChildren().add(venueTable.getRoot());
        venuePlaceholder.toFront();
        venueTable.setStyle();
    }
```
###### \java\seedu\address\ui\VenueTable.java
``` java
/**
 * A ui for the info panal that is displayed when the vacant command is called.
 */
public class VenueTable extends UiPart<Region> {
    private static final String OCCUPIED_STYLE_CLASS = "occupied";
    private static final String VACANT_STYLE_CLASS = "vacant";
    private static final String FXML = "VenueTable.fxml";

    private ArrayList<TableColumn<ArrayList<String>, String>> columns;
    @FXML
    private TableView venueTable;
    @FXML
    private TableColumn<ArrayList<String>, String> roomId;
    @FXML
    private TableColumn<ArrayList<String>, String> eightAm;
    @FXML
    private TableColumn<ArrayList<String>, String> nineAm;
    @FXML
    private TableColumn<ArrayList<String>, String> tenAm;
    @FXML
    private TableColumn<ArrayList<String>, String> elevenAm;
    @FXML
    private TableColumn<ArrayList<String>, String> twelvePm;
    @FXML
    private TableColumn<ArrayList<String>, String> onePm;
    @FXML
    private TableColumn<ArrayList<String>, String> twoPm;
    @FXML
    private TableColumn<ArrayList<String>, String> threePm;
    @FXML
    private TableColumn<ArrayList<String>, String> fourPm;
    @FXML
    private TableColumn<ArrayList<String>, String> fivePm;
    @FXML
    private TableColumn<ArrayList<String>, String> sixPm;
    @FXML
    private TableColumn<ArrayList<String>, String> sevenPm;
    @FXML
    private TableColumn<ArrayList<String>, String> eightPm;

    public VenueTable(ObservableList<ArrayList<String>> schedules) {
        super(FXML);
        venueTable.setItems(schedules);
        initializeColumns();
        initializeTableColumns();
    }

    /**
     * Initializes columns
     */
    private void initializeColumns() {
        columns = new ArrayList<>();
        columns.add(roomId);
        columns.add(eightAm);
        columns.add(nineAm);
        columns.add(tenAm);
        columns.add(elevenAm);
        columns.add(twelvePm);
        columns.add(onePm);
        columns.add(twoPm);
        columns.add(threePm);
        columns.add(fourPm);
        columns.add(fivePm);
        columns.add(sixPm);
        columns.add(sevenPm);
        columns.add(eightPm);
    }

    /**
     * Initializes table columns
     */
    private void initializeTableColumns() {
        for (int i = 0; i < columns.size(); i++) {
            final int j = i;
            columns.get(i).setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(j)));
        }
    }

    /**
     * Sets the command box style to indicate a vacant or occupied room.
     */
    public void setStyle() {
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setCellFactory(column -> {
                return new TableCell<ArrayList<String>, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);
                            if (getItem().equals(OCCUPIED_STYLE_CLASS)) {
                                setTextFill(Color.BLACK);
                                setStyle("-fx-background-color: #F08080");
                            } else if (getItem().equals(VACANT_STYLE_CLASS)) {
                                setTextFill(Color.BLACK);
                                setStyle("-fx-background-color: #17A589");
                            }
                        }
                    }
                };
            });
        }
    }
}
```
###### \resources\view\VenueTable.fxml
``` fxml
<StackPane fx:id="venuePlaceholder" styleClass="pane-with-border" xmlns="http://javafx.com/javafx/9.0.4" xmlns:fx="http://javafx.com/fxml/1">
    <children>
        <TableView fx:id="venueTable" stylesheets="@DarkTheme.css">
         <columns>
             <TableColumn fx:id="roomId" prefWidth="100.0" text="Room" />
            <TableColumn fx:id="eightAm" prefWidth="60.0" text="0800" />
            <TableColumn fx:id="nineAm" prefWidth="60.0" text="0900" />
             <TableColumn fx:id="tenAm" prefWidth="60.0" text="1000" />
             <TableColumn fx:id="elevenAm" prefWidth="60.0" text="1100" />
             <TableColumn fx:id="twelvePm" prefWidth="60.0" text="1200" />
             <TableColumn fx:id="onePm" prefWidth="60.0" text="1300" />
             <TableColumn fx:id="twoPm" prefWidth="60.0" text="1400" />
             <TableColumn fx:id="threePm" prefWidth="60.0" text="1500" />
            <TableColumn fx:id="fourPm" prefWidth="60.0" text="1600" />
            <TableColumn fx:id="fivePm" prefWidth="60.0" text="1700" />
            <TableColumn fx:id="sixPm" prefWidth="60.0" text="1800" />
            <TableColumn fx:id="sevenPm" prefWidth="60.0" text="1900" />
            <TableColumn fx:id="eightPm" prefWidth="60.0" text="2000" />
         </columns></TableView>
    </children>
</StackPane>
```
