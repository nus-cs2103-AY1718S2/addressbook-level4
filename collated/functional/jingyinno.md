# jingyinno
###### \java\seedu\address\commons\events\ui\AliasListEvent.java
``` java
/**
 * Represents a list of aliases that have been set
 */
public class AliasListEvent extends BaseEvent {

    private final ObservableList<ArrayList<String>> aliases;

    public AliasListEvent(ObservableList<ArrayList<String>> aliases) {
        this.aliases = aliases;
    }

    public ObservableList<ArrayList<String>> getAliases() {
        return aliases;
    }

    @Override
    public String toString() {
        return "AliasListEvent";
    }
}
```
###### \java\seedu\address\commons\events\ui\GoogleMapsEvent.java
``` java
/**
 * Represents a Google Maps event in GoogleMapsDisplay
 */
public class GoogleMapsEvent extends BaseEvent {

    private static final String MAPS_URL = "https://www.google.com.sg/maps";
    private String locations;
    private boolean isOneLocationEvent;

    public GoogleMapsEvent(String locations, boolean isOneLocationEvent) throws IOException {
        this.locations = locations;
        this.isOneLocationEvent = isOneLocationEvent;
        checkInternetConnection();
    }

    public String getLocations() {
        return locations;
    }

    public boolean getIsOneLocationEvent() {
        return isOneLocationEvent;
    }

    /**
     * Checks if there is internet connection.
     *
     * @throws IOException if there is no internet connection
     */
    private boolean checkInternetConnection() throws IOException {
        URL url = new URL(MAPS_URL);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.connect();
        return HttpURLConnection.HTTP_OK == urlConn.getResponseCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\InfoPanelEvent.java
``` java
/**
 * Represents a hidden info panel
 */
public class InfoPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return "InfoPanelEvent";
    }
}
```
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
        return "VenueTableEvent";
    }
}
```
###### \java\seedu\address\logic\commands\AliasCommand.java
``` java
/**
 * Adds an alias-command pair to the address book.
 */
public class AliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "alias";
    public static final String LIST_ALIAS_COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows list of alias or creates new alias. "
            + "Parameters for creating new alias: [COMMAND] [NEW_ALIAS] \n"
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
            UndoCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, AliasCommand.COMMAND_WORD, ImportCommand.COMMAND_WORD,
            PasswordCommand.COMMAND_WORD, BirthdaysCommand.COMMAND_WORD, ExportCommand.COMMAND_WORD,
            MapCommand.COMMAND_WORD, RemovePasswordCommand.COMMAND_WORD, UnaliasCommand.COMMAND_WORD,
            VacantCommand.COMMAND_WORD, TimetableUnionCommand.COMMAND_WORD, UploadCommand.COMMAND_WORD);

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
        checkForValidCommandAndAlias();
        try {
            model.addAlias(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateAliasException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ALIAS);
        }
    }

    /**
     * Checks if the command specified is valid command and the alias specified is not a command word.
     */
    private void checkForValidCommandAndAlias() throws CommandException {
        if (!commands.contains(toAdd.getCommand())) {
            throw new CommandException(String.format(AliasCommand.MESSAGE_INVALID_COMMAND,
                            AliasCommand.MESSAGE_INVALID_COMMAND_DESCRIPTION));
        } else if (commands.contains(toAdd.getAlias())) {
            throw new CommandException(String.format(AliasCommand.MESSAGE_INVALID_ALIAS,
                            AliasCommand.MESSAGE_INVALID_ALIAS_DESCRIPTION));
        }
    }

    public static List<String> getCommands() {
        Collections.sort(commands);
        return commands;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AliasCommand // instanceof handles nulls
                && toAdd.equals(((AliasCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\MapCommand.java
``` java
/**
 * Launches Google Maps with the specified location(s)
 */
public class MapCommand extends Command {
    public static final String COMMAND_WORD = "map";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the location of the specified address(es). "
            + "Parameters: [ADDRESS] or [ADDRESS_START]/[ADDRESS_DESTINATION] \n"
            + "Example: " + COMMAND_WORD + " Tampines Mall/COM2 \n"
            + "Example: " + COMMAND_WORD + " 119077/117417 ";

    public static final String MESSAGE_SUCCESS = "Launching Google Maps ...";
    public static final String MESSAGE_NO_INTERNET = "Please check that you have internet connection";
    private static final String SPLIT_TOKEN = "/";
    private static final int TWO_LOCATIONS_WORD_LENGTH = 2;
    private static final int FIRST_LOCATION_INDEX = 0;

    private String locations;
    private boolean isOneLocation;

    /**
     * Creates a MapCommand to pass locations to Google Maps
     */
    public MapCommand(String locations) {
        requireNonNull(locations);
        this.locations = locations;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        String[] locationsArray = locations.split(SPLIT_TOKEN);
        checkForAndRetrieveNusBuildings(locationsArray);
        identifyNumberOfSpecifiedLocations(locationsArray);
        try {
            EventsCenter.getInstance().post(new GoogleMapsEvent(locations, isOneLocation));
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (IOException e) {
            throw new CommandException(MESSAGE_NO_INTERNET);
        }
    }

    /**
     * Identifies if one or more locations are specified in the user input
     */
    private void identifyNumberOfSpecifiedLocations(String[] locationsArray) {
        if (locationsArray.length >= TWO_LOCATIONS_WORD_LENGTH) {
            locations = String.join(SPLIT_TOKEN, locationsArray);
            isOneLocation = false;
        } else {
            locations = locationsArray[FIRST_LOCATION_INDEX];
            isOneLocation = true;
        }
    }

    /**
     * Creates a MapCommand to pass locations to Google Maps
     */
    private void checkForAndRetrieveNusBuildings(String[] locationsArray) {
        for (int i = 0; i < locationsArray.length; i++) {
            locationsArray[i] = locationsArray[i].trim();
            locationsArray[i] = retrieveNusBuildingIfExist(locationsArray[i]);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapCommand // instanceof handles nulls
                && locations.equals(((MapCommand) other).locations));
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
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the alias of a previously aliased command. "
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds vacant study rooms in a building. "
            + "Parameters: [BUILDING_NAME]\n"
            + "Example: " + COMMAND_WORD + " COM1";

    public static final String MESSAGE_SUCCESS = "List of rooms in building successfully retrieved.";
    public static final String MESSAGE_INVALID_BUILDING =
            "Building is not in the list of NUS Buildings given below: \n"
            + Arrays.toString(Building.NUS_BUILDINGS);
    public static final String MESSAGE_CORRUPTED_VENUE_INFORMATION_FILE =
            "Unable to read from venueinformation.json, file is corrupted. Please re-download the file.";
    public static final String MESSAGE_NO_ROOMS_IN_BUILDING = "Building has no rooms available";

    private final Building building;

    /**
     * Creates a VacantCommand to retrieve all the rooms in a given building
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
        } catch (NoRoomsInBuildingException e) {
            throw new CommandException(MESSAGE_NO_ROOMS_IN_BUILDING);
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
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return String[] consists of commandWord and arguments
     * @throws ParseException if the user input does not conform the expected format
     */
    public String[] extractCommandArgs(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        return new String[] {matcher.group("commandWord"), matcher.group("arguments")};
    }
```
###### \java\seedu\address\logic\parser\AliasCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AliasCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {
    private static final String SPLIT_TOKEN = "\\s+";
    private static final int CORRECT_ARGS_LENGTH = 2;
    private static final int COMMAND_INDEX = 0;
    private static final int ALIAS_INDEX = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns an AliasCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AliasCommand parse(String args) throws ParseException {
        String[] trimmedArgs = validateNumberOfArgs(args);
        try {
            Alias aliasCreated = ParserUtil.parseAlias(trimmedArgs[COMMAND_INDEX], trimmedArgs[ALIAS_INDEX]);
            return new AliasCommand(aliasCreated);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns a String Array of valid number of elements after slicing the user input.
     */
    private String[] validateNumberOfArgs(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] splitArgs = trimmedArgs.split(SPLIT_TOKEN);
        if (splitArgs.length != CORRECT_ARGS_LENGTH) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }
        return splitArgs;
    }
}
```
###### \java\seedu\address\logic\parser\MapCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MapCommand object
 */
public class MapCommandParser implements Parser<MapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MapCommand
     * and returns a MapCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MapCommand parse(String args) throws ParseException {
        return validateNumberOfArgs(args);
    }

    /**
     * Returns a Map Command of not empty location argument.
     */
    private MapCommand validateNumberOfArgs(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));
        } else {
            return new MapCommand(trimmedArgs);
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String command} and {@code String alias} into {@code Alias}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given command and alias are invalid.
     */
    public static Alias parseAlias(String command, String alias) throws IllegalValueException {
        requireNonNull(command, alias);
        if (!Alias.isValidAliasParameter(command) || !Alias.isValidAliasParameter(alias)) {
            throw new IllegalValueException(Alias.MESSAGE_ALIAS_CONSTRAINTS);

        }
        return new Alias(command, alias);
    }

    /**
     * Parses a {@code String unalias}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code unalias} is invalid.
     */
    public static String parseUnalias(String unalias) throws IllegalValueException {
        requireNonNull(unalias);
        if (!Alias.isValidAliasParameter(unalias)) {
            throw new IllegalValueException(Alias.MESSAGE_ALIAS_CONSTRAINTS);

        }
        return unalias;
    }

    /**
     * Parses a {@code String building} into a {@code Building}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code building} is invalid.
     */
    public static Building parseBuilding(String building) throws IllegalValueException {
        requireNonNull(building);
        String trimmedBuilding = building.trim();
        if (!Building.isValidBuilding(trimmedBuilding)) {
            throw new IllegalValueException(Building.MESSAGE_BUILDING_CONSTRAINTS);
        }
        return new Building(trimmedBuilding);
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
        String unalias = validateNumberOfArgs(args);
        try {
            String toBeRemoved = ParserUtil.parseUnalias(unalias);
            return new UnaliasCommand(toBeRemoved);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns a not empty String of unalias.
     */
    private String validateNumberOfArgs(String args) throws ParseException {
        String unalias = args.trim();
        if (unalias.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
        }
        return unalias;
    }
}
```
###### \java\seedu\address\logic\parser\VacantCommandParser.java
``` java
/**
 * Parses input arguments and creates a new VacantCommand object
 */
public class VacantCommandParser implements Parser<VacantCommand> {
    private static final String SPLIT_TOKEN = "\\s+";
    private static final String EMPTY_STRING = "";
    private static final int NO_ARGS_LENGTH = 0;
    private static final int CORRECT_ARGS_LENGTH = 1;
    private static final int BUILDING_NAME_INDEX = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the VacantCommand
     * and returns a VacantCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VacantCommand parse(String args) throws ParseException {
        String[] buildingName = validateNumberOfArgs(args);
        try {
            Building building = ParserUtil.parseBuilding(buildingName[BUILDING_NAME_INDEX]);
            return new VacantCommand(building);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns a String Array of valid number of elements after slicing the user input.
     */
    private String[] validateNumberOfArgs(String args) throws ParseException {
        int length;
        String trimmedArgs = args.trim();
        String[] buildingName = trimmedArgs.split(SPLIT_TOKEN);
        if (EMPTY_STRING.equals(trimmedArgs) || SPLIT_TOKEN.equals(trimmedArgs)) {
            length = NO_ARGS_LENGTH;
        } else {
            length = buildingName.length;
        }
        if (length != CORRECT_ARGS_LENGTH) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, VacantCommand.MESSAGE_USAGE));
        }
        return buildingName;
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setAliases(HashMap<String, String> aliases) {
        this.aliases.setAliases(aliases);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData} and {@code newList}.
     */
    public void resetData(ReadOnlyAddressBook newData, HashMap<String, String> newList) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        setAliases(new HashMap<>(newList));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        updatePassword(newData.getPassword());
        try {
            setPersons(syncedPersonList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        }
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds an alias to the address book.
     *
     * @throws DuplicateAliasException if an equivalent alias already exists.
     */
    public void addAlias(Alias alias) throws DuplicateAliasException {
        aliases.add(alias);
    }

    /**
     * Removes an alias from the address book.
     *
     * @throws AliasNotFoundException if alias to-be-removed does not exist.
     */
    public void removeAlias(String toRemove) throws AliasNotFoundException {
        aliases.remove(toRemove);
    }

    /**
     * Retrieve the associated commandWord from the address book.
     * @param aliasKey the alias keyword associated to command word
     * @return the associated command word if exists else the aliasKey
     *
     */
    public String getCommandFromAlias(String aliasKey) {
        return aliases.contains(aliasKey) ? aliases.getCommandFromAlias(aliasKey) : aliasKey;
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<Alias> getAliasList() {
        return aliases.getAliasObservableList();
    }

    @Override
    public ArrayList<ArrayList<String>> getUiFormattedAliasList() {
        return aliases.extractAliasMapping();
    }

    @Override
    public HashMap<String, String> getAliasMapping() {
        return aliases.getAliasCommandMappings();
    }


    @Override
    public void resetAliasList() {
        aliases.resetHashmap();
    }
```
###### \java\seedu\address\model\alias\Alias.java
``` java
/**
 * Represents an Alias in the address book.
 * Guarantees: immutable; alias and command are valid as declared in {@link #isValidAliasParameter(String)}
 */
public class Alias {

    public static final String MESSAGE_ALIAS_CONSTRAINTS = "Alias names should be alphanumeric";
    public static final String ALIAS_VALIDATION_REGEX = "\\p{Alnum}+";

    private final String command;
    private final String aliasName;

    /**
     * Constructs an {@code Alias}.
     *
     * @param aliasName A valid alias name.
     */
    public Alias(String command, String aliasName) {
        requireNonNull(aliasName);
        checkArgument(isValidAliasParameter(aliasName), MESSAGE_ALIAS_CONSTRAINTS);
        checkArgument(isValidAliasParameter(command), MESSAGE_ALIAS_CONSTRAINTS);
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
     * Returns true if a given string is a valid Alias parameter.
     */
    public static boolean isValidAliasParameter(String test) {
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
 * Signals that the operation will result in duplicate Alias objects.
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

    private static final String EMPTY_CELL = "";
    private HashMap<String, String> aliasCommandMap = new HashMap<String, String>();

    /**
     * Constructs an empty AliasList.
     */
    public UniqueAliasList() {}

    /**
     * Returns true if the list contains an equivalent alias as the given argument.
     */
    public boolean contains(String toCheck) {
        requireNonNull(toCheck);
        return aliasCommandMap.containsKey(toCheck);
    }

    /**
     * Returns the command of the alias.
     */
    public String getCommandFromAlias(String alias) {
        requireNonNull(alias);
        return aliasCommandMap.get(alias);
    }

    /**
     * Adds an Alias to the list.
     *
     * @throws DuplicateAliasException if the Alias to add is a duplicate of an existing Alias in the list.
     */
    public void add(Alias toAdd) throws DuplicateAliasException {
        requireNonNull(toAdd);
        if (contains(toAdd.getAlias())) {
            throw new DuplicateAliasException();
        }
        aliasCommandMap.put(toAdd.getAlias(), toAdd.getCommand());
    }

    /**
     * Removes an Alias from the list.
     *
     * @throws AliasNotFoundException if the Alias to remove is a does not exist in the list.
     */
    public void remove(String toRemove) throws AliasNotFoundException {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new AliasNotFoundException();
        }
        aliasCommandMap.remove(toRemove);
    }

    /**
     * Imports an Alias to the list if the Alias is not a duplicate of an existing Alias in the list.
     */
    public void importAlias(Alias toAdd) {
        requireNonNull(toAdd);
        if (!contains(toAdd.getAlias())) {
            aliasCommandMap.put(toAdd.getAlias(), toAdd.getCommand());
        }
    }

    /**
     * Converts the HashMap of alias and command pairings into an observable list of Alias objects
     * Add in all the aliases in the given {@code aliases}
     * if the Alias is not a duplicate of an existing Alias in the list.
     */
    public void setAliases(HashMap<String, String> aliases) {
        requireNonNull(aliases);
        this.aliasCommandMap.clear();
        this.aliasCommandMap.putAll(aliases);
    }

    /**
     * Converts HashMap of alias and command pairing into an observable list of Alias objects
     */
    public void convertToList(ObservableList<Alias> internalList) {
        for (String key : aliasCommandMap.keySet()) {
            Alias newAlias = new Alias(aliasCommandMap.get(key), key);
            internalList.add(newAlias);
        }
    }

    /**
     * Gets an Observable alias list
     */
    public ObservableList<Alias> getAliasObservableList() {
        ObservableList<Alias> internalList = FXCollections.observableArrayList();
        convertToList(internalList);
        return internalList;
    }

    /**
     * Gets aliasCommandMap
     */
    public HashMap<String, String> getAliasCommandMappings() {
        return aliasCommandMap;
    }

    /**
     * Replaces the aliases in this aliasCommandMap with the HashMap in the argument.
     */
    public void replaceHashmap(HashMap<String, String> aliases) {
        aliasCommandMap = aliases;
    }

    /**
     * Clears aliasCommandMap, for clear command.
     */
    public void resetHashmap() {
        this.aliasCommandMap = new HashMap<>();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Alias> asObservableList() {
        ObservableList<Alias> internalList = FXCollections.observableArrayList();
        convertToList(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAliasList // instanceof handles nulls
                && this.aliasCommandMap.equals(((UniqueAliasList) other).aliasCommandMap));
    }

    /**
     * Returns an ArrayList of ArrayList of string aliases grouped by command.
     */
    public ArrayList<ArrayList<String>> extractAliasMapping() {
        requireNonNull(aliasCommandMap);
        ArrayList<ArrayList<String>> aliases = new ArrayList<>();
        convertAliasHashmapToArrayList(aliases);

        int largest = findMaxCommandAliasSize(aliases);
        populateEmptyAliasCells(aliases, largest);

        ArrayList<ArrayList<String>> formattedAliases = formatArrayListForUi(aliases, largest);
        return formattedAliases;
    }

    /**
     * Returns an ArrayList of ArrayList of aliases organised by rows of commands.
     */
    private ArrayList<ArrayList<String>> formatArrayListForUi(ArrayList<ArrayList<String>> aliases, int largest) {
        ArrayList<ArrayList<String>> formattedAliases = new ArrayList<>();
        for (int j = 0; j < largest; j++) {
            generateAliasColumn(aliases, formattedAliases, j);
        }
        return formattedAliases;
    }

    /**
     * Generates an ArrayList of a row of aliases for all the commands.
     */
    private void generateAliasColumn(ArrayList<ArrayList<String>> aliases,
                                     ArrayList<ArrayList<String>> formattedAliases, int j) {
        formattedAliases.add(new ArrayList<>());
        for (int i = 0; i < AliasCommand.getCommands().size(); i++) {
            formattedAliases.get(j).add(aliases.get(i).get(j));
        }
    }

    /**
     * Groups alias mappings by command.
     */
    private void convertAliasHashmapToArrayList(ArrayList<ArrayList<String>> aliases) {
        for (String command : AliasCommand.getCommands()) {
            aliases.add(new ArrayList<>());
        }
        for (String key: aliasCommandMap.keySet()) {
            String command = aliasCommandMap.get(key);
            aliases.get(AliasCommand.getCommands().indexOf(command)).add(key);
        }
    }

    /**
     * Finds the largest alias group among all the commands.
     */
    private int findMaxCommandAliasSize(ArrayList<ArrayList<String>> aliases) {
        int largest = Integer.MIN_VALUE;
        for (ArrayList<String> list : aliases) {
            largest = Math.max(largest, list.size());
        }
        return largest;
    }

    /**
     * Generate empty cells in alias ArrayList.
     */
    private void populateEmptyAliasCells(ArrayList<ArrayList<String>> aliases, int largest) {
        for (ArrayList<String> list : aliases) {
            while (list.size() < largest) {
                list.add(EMPTY_CELL);
            }
        }
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Clears existing backing model and replaces with the provided new data and new list. */
    void resetData(ReadOnlyAddressBook newData, HashMap<String, String> newAliasList);
```
###### \java\seedu\address\model\Model.java
``` java
    /** Adds the given alias */
    void addAlias(Alias alias) throws DuplicateAliasException;

    /** Returns a HashMap of alias-command mappings */
    HashMap<String, String> getAliasList();

    /** Returns a the associated command word that is mapped to aliasKey */
    String getCommandFromAlias(String aliasKey);

    /** Returns an ArrayList of ArrayList of alias strings formatted for the UI */
    ArrayList<ArrayList<String>> getUiFormattedAliasList();

    /**
     * Replaces the alias mapping by the given {@code aliases}.
     */
    void updateAliasesMapping(HashMap<String, String> aliases);
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
    public void resetData(ReadOnlyAddressBook newData, HashMap<String, String> newAliasList) {
        addressBook.resetData(newData, newAliasList);
        addressBook.updatePassword(newData.getPassword());
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addAlias(Alias alias) throws DuplicateAliasException {
        addressBook.addAlias(alias);
        indicateAddressBookChanged();
    }

    @Override
    public void updateAliasesMapping(HashMap<String, String> aliases) {
        requireNonNull(aliases);
        addressBook.setAliases(aliases);
    }

    @Override
    public synchronized HashMap<String, String> getAliasList() {
        return addressBook.getAliasMapping();
    }

    @Override
    public synchronized String getCommandFromAlias(String aliasKey) {
        return addressBook.getCommandFromAlias(aliasKey);
    }

    @Override
    public synchronized ArrayList<ArrayList<String>> getUiFormattedAliasList() {
        return addressBook.getUiFormattedAliasList();
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
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the aliases list.
     * This list will not contain any duplicate aliases.
     */
    ObservableList<Alias> getAliasList();

    /**
     * Returns the HashMap of alias list.
     * This list will not contain any duplicate aliases.
     */
    HashMap<String, String> getAliasMapping();

    /**
     * Returns an ArrayList of ArrayList of alias strings.
     * This list will not contain any duplicate aliases.
     */
    ArrayList<ArrayList<String>> getUiFormattedAliasList();

    /**
     * Resets the alias list to an empty list
     */
    void resetAliasList();
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
     * Constructs a {@code XmlAdaptedAlias} with the given {@code command} and {@code aliasName}.
     */
    public XmlAdaptedAlias(String command, String aliasName) {
        this.command = command;
        this.aliasName = aliasName;
    }

    /**
     * Converts a given Alias into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAlias.
     */
    public XmlAdaptedAlias(Alias source) {
        command = source.getCommand();
        aliasName = source.getAlias();
    }

    /**
     * Converts this jaxb-friendly adapted alias object into the model's Alias object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted alias.
     */
    public Alias toModelType() throws IllegalValueException {
        if (this.command == null) {
            throw new IllegalValueException(Alias.MESSAGE_ALIAS_CONSTRAINTS);
        }
        if (!Alias.isValidAliasParameter(command)) {
            throw new IllegalValueException(Alias.MESSAGE_ALIAS_CONSTRAINTS);
        }
        final String command = this.command;

        if (this.aliasName == null) {
            throw new IllegalValueException(Alias.MESSAGE_ALIAS_CONSTRAINTS);
        }
        if (!Alias.isValidAliasParameter(aliasName)) {
            throw new IllegalValueException(Alias.MESSAGE_ALIAS_CONSTRAINTS);
        }
        final String aliasName = this.aliasName;

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

        XmlAdaptedAlias otherAlias = (XmlAdaptedAlias) other;
        return Objects.equals(aliasName, otherAlias.aliasName)
                && Objects.equals(command, otherAlias.command);
    }
}
```
###### \java\seedu\address\ui\AliasList.java
``` java
/**
 * A ui for the info panel that is displayed when the list command is called.
 */
public class AliasList extends UiPart<Region> {
    private static final String FXML = "AliasList.fxml";
    private static final String EMPTY_CELL = "";
    private static final String ODD_COLUMN_STYLE_CLASS = "alias-column-odd";
    private static final String EVEN_COLUMN_STYLE_CLASS = "alias-column-even";
    private static final int SHORT_COMMAND_WIDTH = 75;
    private static final int LONG_COMMAND_WIDTH = 100;
    private static final int LONG_COMMAND_INDEX = 2;

    private ArrayList<TableColumn<ArrayList<String>, String>> columns;
    @FXML
    private TableView aliasList;
    @FXML
    private TableColumn<ArrayList<String>, String> addCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> editCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> selectCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> deleteComamnd;
    @FXML
    private TableColumn<ArrayList<String>, String> clearCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> findCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> listCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> historyCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> exitCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> helpCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> undoCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> redoCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> aliasCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> importCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> passwordCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> birthdaysCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> exportCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> mapCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> removePasswordCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> unaliasCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> vacantCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> unionCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> uploadCommand;

    public AliasList()  {
        super(FXML);
    }

    /**
     * Initializes columns
     */
    private void initializeColumns() {
        columns = new ArrayList<>();
        columns.add(addCommand);
        columns.add(aliasCommand);
        columns.add(birthdaysCommand);
        columns.add(clearCommand);
        columns.add(removePasswordCommand);
        columns.add(deleteComamnd);
        columns.add(editCommand);
        columns.add(passwordCommand);
        columns.add(exitCommand);
        columns.add(exportCommand);
        columns.add(findCommand);
        columns.add(helpCommand);
        columns.add(historyCommand);
        columns.add(importCommand);
        columns.add(listCommand);
        columns.add(mapCommand);
        columns.add(redoCommand);
        columns.add(selectCommand);
        columns.add(unaliasCommand);
        columns.add(undoCommand);
        columns.add(unionCommand);
        columns.add(uploadCommand);
        columns.add(vacantCommand);
    }

    /**
     * Initializes alias list Ui
     */
    public void init(ObservableList<ArrayList<String>> aliases) {
        aliasList.setItems(aliases);
        initializeColumns();
        initializeTableColumns();
        aliasList.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Initializes table columns
     */
    private void initializeTableColumns() {
        for (int i = 0; i < columns.size(); i++) {
            final int index = i;
            columns.get(i).setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(index)));
            columns.get(i).impl_setReorderable(false);
            if (i == LONG_COMMAND_INDEX) {
                columns.get(i).setMinWidth(LONG_COMMAND_WIDTH);
            } else {
                columns.get(i).setMinWidth(SHORT_COMMAND_WIDTH);
            }
            columns.get(i).setSortable(false);

        }
    }
    /**
     * Sets the command box style to indicate an alias belonging to the command.
     */
    public void setStyle() {
        for (int i = 0; i < columns.size(); i++) {
            final int index = i;
            columns.get(i).setCellFactory(column -> {
                return new TableCell<ArrayList<String>, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setStyle(EMPTY_CELL);
                        } else {
                            removeAllStyle(this);
                            setText(item);
                            if (!EMPTY_CELL.equals(getItem())) {
                                fillOddAndEvenIndexedCells();
                            }
                        }
                    }

                    private void fillOddAndEvenIndexedCells() {
                        if (index % 2 == 0) {
                            getStyleClass().add(EVEN_COLUMN_STYLE_CLASS);
                        } else {
                            getStyleClass().add(ODD_COLUMN_STYLE_CLASS);
                        }
                    }
                };
            });
        }
    }

    /**
     * Removes all styles present in cell
     * @param tableCell Cell with its style to be removed
     */
    private static void removeAllStyle(TableCell<ArrayList<String>, String> tableCell) {
        tableCell.getStyleClass().remove(ODD_COLUMN_STYLE_CLASS);
        tableCell.getStyleClass().remove(EVEN_COLUMN_STYLE_CLASS);
    }
}
```
###### \java\seedu\address\ui\GoogleMapsDisplay.java
``` java
/**
 * A ui for the info panel that is displayed when the map command is called.
 */

public class GoogleMapsDisplay extends UiPart<Region> {
    public static final String DEFAULT_PAGE = "default.html";
    public static final String MAP_SEARCH_URL_PREFIX = "https://www.google.com/maps/search/";
    public static final String MAP_DIRECTIONS_URL_PREFIX = "https://www.google.com/maps/dir/";
    private static final String FXML = "GoogleMapsDisplay.fxml";

    @FXML
    private WebView maps;

    public GoogleMapsDisplay() {
        this(null);
    }

    public GoogleMapsDisplay(String locations) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        loadDefaultPage();
    }

    /**
     * Loads the Google Maps page with the location.
     */
    public void loadMapPage(String location) {
        String address = MAP_SEARCH_URL_PREFIX + location;
        loadPage(address);
    }

    /**
     * Loads the Google Maps page with the directions between locations.
     */
    public void loadMapDirections(String locations) {
        String address = MAP_DIRECTIONS_URL_PREFIX + locations;
        loadPage(address);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> maps.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        maps = null;
    }
}
```
###### \java\seedu\address\ui\InfoPanel.java
``` java
    @Subscribe
    private void handleVenueTableEvent(VenueTableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        venuePlaceholder.getChildren().removeAll();
        venueTable = new VenueTable(event.getSchedule());
        venuePlaceholder.getChildren().add(venueTable.getRoot());
        venuePlaceholder.toFront();
        venueTable.setStyle();
    }

    @Subscribe
    private void handleGoogleMapsDisplayEvent(GoogleMapsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getIsOneLocationEvent()) {
            mapsDisplay.loadMapPage(event.getLocations());
        } else {
            mapsDisplay.loadMapDirections(event.getLocations());
        }
        mapsPlaceholder.toFront();
    }

    @Subscribe
    private void handleAliasListEvent(AliasListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        aliasListPlaceholder.getChildren().removeAll();
        aliasList.init(event.getAliases());
        aliasListPlaceholder.toFront();
        aliasList.setStyle();
    }

    @Subscribe
    private void handleInfoPanelEvent(InfoPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        hideAllPanel();
    }
```
###### \java\seedu\address\ui\VenueTable.java
``` java
/**
 * A ui for the info panel that is displayed when the vacant command is called.
 */
public class VenueTable extends UiPart<Region> {
    private static final String FXML = "VenueTable.fxml";
    private static final String EMPTY_CELL = "";
    private static final String OCCUPIED_LABEL = "occupied";
    private static final String VACANT_LABEL = "vacant";
    private static final String OCCUPIED_STYLE_CLASS = "venueTable-cell-occupied";
    private static final String VACANT_STYLE_CLASS = "venueTable-cell-vacant";
    private static final int MIN_CELL_WIDTH = 75;
    private static final int MAX_CELL_WIDTH = 100;
    private static final int ROOM_COLUMN_INDEX = 0;

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

    public VenueTable() {
        this(null);
    }

    public VenueTable(ObservableList<ArrayList<String>> schedules) {
        super(FXML);
        venueTable.setItems(schedules);
        initializeColumns();
        initializeTableColumns();
        roomId.setMinWidth(MAX_CELL_WIDTH);
        roomId.setMaxWidth(MAX_CELL_WIDTH);
        venueTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
            columns.get(i).impl_setReorderable(false);
            if (j != ROOM_COLUMN_INDEX) {
                columns.get(i).setMinWidth(MIN_CELL_WIDTH);
                columns.get(i).setMaxWidth(MAX_CELL_WIDTH);
            }

        }
    }
    /**
     * Sets the command box style to indicate a vacant or occupied room.
     */
    public void setStyle() {
        for (int i = 0; i < columns.size(); i++) {
            TableColumn<ArrayList<String>, String> columnToBeSet = columns.get(i);
            setStyleForColumn(columnToBeSet);
        }
    }

    /**
     * Sets the style of each column.
     * @param columnToBeSet is the column that would be set
     */
    private void setStyleForColumn (TableColumn<ArrayList<String>, String> columnToBeSet) {
        columnToBeSet.setCellFactory(column -> {
            return setStyleForCell();
        });
    }

    /**
     * Sets the style of the cell given the data and return it
     * @return the tablecell with its style set.
     */
    private TableCell<ArrayList<String>, String> setStyleForCell () {
        return new TableCell<ArrayList<String>, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle(EMPTY_CELL);
                } else {
                    removeAllStyle(this);
                    setText(item);
                    if (getItem().equals(OCCUPIED_LABEL)) {
                        getStyleClass().add(OCCUPIED_STYLE_CLASS);
                    } else if (getItem().equals(VACANT_LABEL)) {
                        getStyleClass().add(VACANT_STYLE_CLASS);

                    }
                }
            }
        };
    }

    /**
     * Removes all styles present in cell
     * @param tableCell Cell with its style to be removed
     */
    private static void removeAllStyle(TableCell<ArrayList<String>, String> tableCell) {
        tableCell.getStyleClass().remove(OCCUPIED_STYLE_CLASS);
        tableCell.getStyleClass().remove(VACANT_STYLE_CLASS);
    }
}
```
###### \resources\view\AliasList.fxml
``` fxml
<StackPane fx:id="aliasListPlaceholder" styleClass="pane-with-border" xmlns="http://javafx.com/javafx/9.0.4" xmlns:fx="http://javafx.com/fxml/1">
    <children>
        <TableView fx:id="aliasList" stylesheets="@DarkTheme.css">
            <columns>
                <TableColumn fx:id="addCommand" prefWidth="100.0" text="add" />
                <TableColumn fx:id="aliasCommand" prefWidth="60.0" text="alias" />
                <TableColumn fx:id="birthdaysCommand" prefWidth="60.0" text="birthdays" />
                <TableColumn fx:id="clearCommand" prefWidth="60.0" text="clear" />
                <TableColumn fx:id="removePasswordCommand" prefWidth="60.0" text="decrypt" />
                <TableColumn fx:id="deleteComamnd" prefWidth="60.0" text="delete" />
                <TableColumn fx:id="editCommand" prefWidth="60.0" text="edit" />
                <TableColumn fx:id="passwordCommand" prefWidth="60.0" text="encrypt" />
                <TableColumn fx:id="exitCommand" prefWidth="60.0" text="exit" />
                <TableColumn fx:id="exportCommand" prefWidth="60.0" text="export" />
                <TableColumn fx:id="findCommand" prefWidth="60.0" text="find" />
                <TableColumn fx:id="helpCommand" prefWidth="60.0" text="help" />
                <TableColumn fx:id="historyCommand" prefWidth="60.0" text="history" />
                <TableColumn fx:id="importCommand" prefWidth="60.0" text="import" />
                <TableColumn fx:id="listCommand" prefWidth="60.0" text="list" />
                <TableColumn fx:id="mapCommand" prefWidth="60.0" text="map" />
                <TableColumn fx:id="redoCommand" prefWidth="60.0" text="redo" />
                <TableColumn fx:id="selectCommand" prefWidth="60.0" text="select" />
                <TableColumn fx:id="unaliasCommand" prefWidth="60.0" text="unalias" />
                <TableColumn fx:id="undoCommand" prefWidth="60.0" text="undo" />
                <TableColumn fx:id="unionCommand" prefWidth="60.0" text="union" />
                <TableColumn fx:id="uploadCommand" prefWidth="60.0" text="upload" />
                <TableColumn fx:id="vacantCommand" prefWidth="60.0" text="vacant" />
            </columns>
        </TableView>
    </children>
</StackPane>
```
###### \resources\view\DarkTheme.css
``` css
.venueTable-cell-occupied {
    -fx-text-fill: black;
    -fx-background-color: #F08080;
    -fx-text-alignment: CENTER;
}

.venueTable-cell-vacant {
    -fx-text-fill: black;
    -fx-background-color: #17A589;
    -fx-text-alignment: CENTER;
}

.alias-column-odd {
    -fx-text-fill: black;
    -fx-background-color: #E6B0AA;
}

.alias-column-even {
    -fx-text-fill: black;
    -fx-background-color: #5DADE2;
}
```
###### \resources\view\GoogleMapsDisplay.fxml
``` fxml
<StackPane xmlns:fx="http://javafx.com/fxml/1">
    <WebView fx:id="maps"/>
</StackPane>
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
         </columns>
        </TableView>
    </children>
</StackPane>
```
