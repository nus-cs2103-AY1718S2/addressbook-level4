# Caijun7
###### \java\seedu\address\commons\util\JsonUtil.java
``` java
    /**
     * Returns the Json object from the given file or {@code Optional.empty()} object if the file is not found.
     * If any values are missing from the file, default values will be used, as long as the file is a valid json file.
     * @param filePath cannot be null.
     * @param classOfObjectToDeserialize Json file has to correspond to the structure in the class given here.
     * @throws DataConversionException if the file format is not as expected.
     */
    public static <T> Optional<T> readJsonFileFromResource(
            String filePath, Class<T> classOfObjectToDeserialize) throws DataConversionException {
        requireNonNull(filePath);
        InputStream inputStream;
        try {
            inputStream = JsonUtil.class.getResourceAsStream(filePath);

            if (inputStream == null) {
                logger.warning("Json file "  + filePath + " not found");
                return Optional.empty();
            }
            T jsonFile;

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            jsonFile = fromJsonString(responseStrBuilder.toString(), classOfObjectToDeserialize);
            return Optional.of(jsonFile);
        } catch (IOException e) {
            logger.warning("Error reading from jsonFile file " + filePath + ": " + e);
            throw new DataConversionException(e);
        }
    }
```
###### \java\seedu\address\logic\commands\ExportCommand.java
``` java
/**
 * Exports an address book to the existing address book.
 */
public class ExportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports an address book "
            + "from filepath to the existing address book. "
            + "Parameters: FILEPATH PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + "data/addressbookbackup.xml "
            + "testpassword";

    public static final String MESSAGE_SUCCESS = "Current list of Persons, tags, or aliases from "
            + "Addressbook are successfully exported.";
    public static final String MESSAGE_FILE_UNABLE_TO_SAVE = "Unable to save or overwrite to given filepath. "
            + "Please give another filepath.";
    public static final String MESSAGE_INVALID_PASSWORD = "Password is in invalid format for Addressbook file.";
    public static final String MESSAGE_IMPOSSIBLE_ERROR = "Unexpected error has occurred.";

    private final String filepath;
    private final Password password;

    /**
     * Creates an ExportCommand to export the current view of {@code AddressBook} to the filepath without a password
     */
    public ExportCommand(String filepath) {
        requireNonNull(filepath);

        this.filepath = filepath;
        password = null;
    }

    /**
     * Creates an ExportCommand to export the current view of {@code AddressBook} to the filepath with a password
     */
    public ExportCommand(String filepath, String password) {
        requireNonNull(filepath);
        requireNonNull(password);

        this.filepath = filepath;
        this.password = new Password(password);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.exportAddressBook(filepath, password);
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_FILE_UNABLE_TO_SAVE);
        } catch (WrongPasswordException e) {
            throw new CommandException(MESSAGE_INVALID_PASSWORD);
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_IMPOSSIBLE_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && filepath.equals(((ExportCommand) other).filepath));
    }
}
```
###### \java\seedu\address\logic\commands\ImportCommand.java
``` java
/**
 * Imports an address book to the existing address book.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports an address book "
            + "from filepath to the existing address book. "
            + "Parameters: FILEPATH PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + "data/addressbook.xml "
            + "testpassword";

    public static final String MESSAGE_SUCCESS = "Persons, tags, and aliases from "
            + "Addressbook file successfully imported.";
    public static final String MESSAGE_FILE_NOT_FOUND = "Addressbook file is not found.";
    public static final String MESSAGE_DATA_CONVERSION_ERROR = "Addressbook file found is not in correct "
            + "format or wrong password.";
    public static final String MESSAGE_PASSWORD_WRONG = "Password wrong for Addressbook file.";

    private final String filepath;
    private final byte[] password;

    /**
     * Creates an ImportCommand to import the specified {@code AddressBook} from filepath to
     * current {@code AddressBook} and decrypt without password
     */
    public ImportCommand(String filepath) {
        requireNonNull(filepath);

        this.filepath = filepath;
        password = null;
    }

    /**
     * Creates an ImportCommand to import the specified {@code AddressBook} from filepath to
     * current {@code AddressBook} and decrypt with password
     */
    public ImportCommand(String filepath, String password) {
        requireNonNull(filepath);
        requireNonNull(password);

        this.filepath = filepath;
        this.password = SecurityUtil.hashPassword(password);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.importAddressBook(filepath, password);
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (WrongPasswordException wpe) {
            throw new CommandException(MESSAGE_PASSWORD_WRONG);
        } catch (DataConversionException dce) {
            throw new CommandException(MESSAGE_DATA_CONVERSION_ERROR);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && filepath.equals(((ImportCommand) other).filepath));
    }
}
```
###### \java\seedu\address\logic\parser\ExportCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {
    private static final String SPLIT_TOKEN = " ";
    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        String[] splitArgs = trimmedArgs.split(SPLIT_TOKEN);
        if (splitArgs.length == 1) {
            return new ExportCommand(splitArgs[0]);
        } else if (splitArgs.length == 2) {
            return new ExportCommand(splitArgs[0], splitArgs[1]);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

    }
}
```
###### \java\seedu\address\logic\parser\ImportCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {
    private static final String SPLIT_TOKEN = " ";
    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }
        String[] splitArgs = trimmedArgs.split(SPLIT_TOKEN);
        if (splitArgs.length == 1) {
            return new ImportCommand(splitArgs[0]);
        } else if (splitArgs.length == 2) {
            return new ImportCommand(splitArgs[0], splitArgs[1]);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

    }
}
```
###### \java\seedu\address\MainApp.java
``` java
    /**
     * Initialize {@code nusVenues} and {@code nusBuildingsAndRooms} using the file at
     * {@code storage}'s venue information file path
     */
    private void initVenueInformation(ReadOnlyVenueInformation storage) {
        String venueInformationFilePath = storage.getVenueInformationFilePath();
        logger.info("Using venueInformation file : " + venueInformationFilePath);

        try {
            Optional<Building> buildingOptional = storage.readBuildingsAndRoomsInformation();
            Building building = buildingOptional.orElseThrow(() -> new DataConversionException(new IOException()));
            Building.setNusBuildingsAndRooms(building.getBuildingsAndRooms());
            Optional<Room> roomOptional = storage.readVenueInformation();
            Room room = roomOptional.orElseThrow(() -> new DataConversionException(new IOException()));
            Room.setNusVenues(room.getNusRooms());
        } catch (DataConversionException de) {
            logger.warning("VenueInformation file at " + venueInformationFilePath + " is not in the correct format.");
        } catch (IOException ioe) {
            logger.warning("Problem while reading from the file at " + venueInformationFilePath);
        }

    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Imports a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     */
    public void importPerson(Person p) {
        Person person = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.importPerson(person);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void importAlias(Alias alias) {
        aliases.importAlias(alias);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void importTag(Tag t) {
        tags.importTag(t);
    }
```
###### \java\seedu\address\model\building\Building.java
``` java
/**
 * Represents a Building in National University of Singapore.
 * Guarantees: immutable; is valid as declared in {@link #isValidBuilding(String)}
 */
public class Building {

    public static final String MESSAGE_BUILDING_CONSTRAINTS =
            "Building names should only contain alphanumeric characters and it should not be blank";

    public static final String BUILDING_VALIDATION_REGEX = "\\p{Alnum}+";

    /**
     * Represents an array of Buildings in National University of Singapore
     */
    public static final String[] NUS_BUILDINGS = {
        "AS1", "AS2", "AS3", "AS4", "AS5", "AS6", "AS7", "AS8", "COM1", "COM2", "I3", "BIZ1", "BIZ2",
        "SDE", "S1", "S1A", "S2", "S3", "S4", "S4A", "S5", "S8", "S10", "S11", "S12", "S13", "S14", "S15",
        "S16", "S17", "E1", "E1A", "E2", "E2A", "E3", "E3A", "E4", "E4A", "E5", "E6", "EA", "ERC", "UTSRC"
    };

    public static final String[] NUS_BUILDINGS_ADDRESSES = {
        "117570", "117570", "117570", "117570", "117570", "117416", "117570", "119260", "117417", "117417",
        "119613", "119245", "119245", "117592", "117546", "117546", "117546", "117558", "117543", "117543",
        "117543", "117548", "117546", "117553", "117550", "117550", "117542", "117541", "117546", "119076 S17",
        "117575", "117575", "117575", "117361", "117581", "117574", "117583", "117583", "117583", "117608",
        "117575", "139599", "138607"
    };

    private static final Logger logger = LogsCenter.getLogger(Building.class);

    private static HashMap<String, ArrayList<String>> nusBuildingsAndRooms = null;

    private final String buildingName;

    private HashMap<String, ArrayList<String>> buildingsAndRooms = null;

    /**
     * Uses a private {@code Building} constructor for Jackson JSON API to instantiate an object
     */
    private Building() {
        buildingName = "";
    }

    /**
     * Constructs a {@code Building}.
     *
     * @param buildingName A valid building name.
     */
    public Building(String buildingName) {
        requireNonNull(buildingName);
        checkArgument(isValidBuilding(buildingName), MESSAGE_BUILDING_CONSTRAINTS);
        this.buildingName = buildingName;
    }

    /**
     * Returns true if a given string is a valid building name.
     */
    public static boolean isValidBuilding(String test) {
        return test.matches(BUILDING_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid building name.
     */
    public static boolean isValidBuilding(Building test) {
        for (String building : NUS_BUILDINGS) {
            if (building.equals(test.buildingName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the postal code if building is an NUS building.
     */
    public static String retrieveNusBuildingIfExist(String test) {
        for (int i = 0; i < NUS_BUILDINGS.length; i++) {
            if (NUS_BUILDINGS[i].equalsIgnoreCase(test)) {
                return NUS_BUILDINGS_ADDRESSES[i];
            }
        }
        return test;
    }

    public static HashMap<String, ArrayList<String>> getNusBuildingsAndRooms() {
        return nusBuildingsAndRooms;
    }

    public static void setNusBuildingsAndRooms(HashMap<String, ArrayList<String>> nusBuildingsAndRooms) {
        Building.nusBuildingsAndRooms = nusBuildingsAndRooms;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public HashMap<String, ArrayList<String>> getBuildingsAndRooms() {
        return buildingsAndRooms;
    }

    public void setBuildingsAndRooms(HashMap<String, ArrayList<String>> buildingsAndRooms) {
        this.buildingsAndRooms = buildingsAndRooms;
    }

    /**
     * Retrieves weekday schedule of all {@code Room}s in the {@code Building} in an ArrayList of ArrayList
     *
     * @throws CorruptedVenueInformationException if the room schedule format is not as expected.
     */
    public ArrayList<ArrayList<String>> retrieveAllRoomsSchedule() throws CorruptedVenueInformationException {
        ArrayList<ArrayList<String>> allRoomsSchedule = new ArrayList<>();
        ArrayList<String> allRoomsInBuilding = retrieveAllRoomsInBuilding();
        for (String roomName : allRoomsInBuilding) {
            Room room = new Room(roomName);
            ArrayList<String> weekDayRoomSchedule = room.retrieveWeekDaySchedule();
            allRoomsSchedule.add(weekDayRoomSchedule);
        }
        return allRoomsSchedule;
    }

    /**
     * Retrieves all {@code Room}s in the {@code Building} in an ArrayList
     *
     * @throws CorruptedVenueInformationException if the NUS Buildings and Rooms format is not as expected.
     */
    public ArrayList<String> retrieveAllRoomsInBuilding() throws CorruptedVenueInformationException {
        checkArgument(isValidBuilding(this));
        if (nusBuildingsAndRooms == null) {
            logger.warning("NUS buildings and rooms is null, venueinformation.json file is corrupted.");
            throw new CorruptedVenueInformationException();
        }
        if (nusBuildingsAndRooms.get(buildingName) == null) {
            logger.warning("NUS buildings and rooms has some null data, venueinformation.json file is corrupted.");
            throw new CorruptedVenueInformationException();
        }
        return nusBuildingsAndRooms.get(buildingName);
    }

    @Override
    public String toString() {
        return buildingName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Building // instanceof handles nulls
                && buildingName.equals(((Building) other).buildingName)); // state check
    }

    @Override
    public int hashCode() {
        return buildingName.hashCode();
    }

}
```
###### \java\seedu\address\model\building\exceptions\BuildingNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified building.
 */
public class BuildingNotFoundException extends CommandException {
    public BuildingNotFoundException() {
        super("Building is not in the list of NUS Buildings given below: \n"
                + Arrays.toString(Building.NUS_BUILDINGS));
    }
}
```
###### \java\seedu\address\model\building\exceptions\CorruptedVenueInformationException.java
``` java
/**
 * Signals that some data in venueinformation.json file is corrupted
 */
public class CorruptedVenueInformationException extends Exception {
    public CorruptedVenueInformationException() {
        super("Unable to read from venueinformation.json, file is corrupted. Please re-download the file.");
    }
}
```
###### \java\seedu\address\model\building\exceptions\InvalidWeekDayScheduleException.java
``` java
/**
 * Signals that weekday schedule is in incorrect format
 */
public class InvalidWeekDayScheduleException extends Exception {
    public InvalidWeekDayScheduleException() {
        super("Weekday Schedule is in incorrect format, venueinformation.json file is corrupted.");
    }
}
```
###### \java\seedu\address\model\building\exceptions\InvalidWeekScheduleException.java
``` java
/**
 * Signals that week schedule is in incorrect format
 */
public class InvalidWeekScheduleException extends Exception {
    public InvalidWeekScheduleException() {
        super("Week Schedule is in incorrect format, venueinformation.json file is corrupted.");
    }
}
```
###### \java\seedu\address\model\building\Room.java
``` java
/**
 * Represents a Room in National University of Singapore.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoom(String)}
 */
public class Room {

    public static final String MESSAGE_ROOM_CONSTRAINTS =
            "Room names can take any values, and it should not be blank";

    public static final String ROOM_VALIDATION_REGEX = "[^\\s].*";

    private static final Logger logger = LogsCenter.getLogger(Room.class);

    /**
     * Represents all rooms in National University of Singapore
     */
    private static HashMap<String, Week> nusVenues = null;

    private final String roomName;

    private HashMap<String, Week> nusRooms = null;
    private Week week = null;

    /**
     * Uses a private {@code Room} constructor for Jackson JSON API to instantiate an object
     */
    private Room() {
        roomName = "";
    }

    /**
     * Constructs a {@code Room}.
     *
     * @param roomName A valid room name.
     */
    public Room(String roomName) {
        requireNonNull(roomName);
        checkArgument(isValidRoom(roomName), MESSAGE_ROOM_CONSTRAINTS);
        this.roomName = roomName;
    }

    /**
     * Returns true if a given string is a valid room name.
     */
    private static boolean isValidRoom(String test) {
        return test.matches(ROOM_VALIDATION_REGEX);
    }

    public static HashMap<String, Week> getNusVenues() {
        return nusVenues;
    }

    public static void setNusVenues(HashMap<String, Week> nusVenues) {
        Room.nusVenues = nusVenues;
    }

    public HashMap<String, Week> getNusRooms() {
        return nusRooms;
    }

    public void setNusRooms(HashMap<String, Week> nusRooms) {
        this.nusRooms = nusRooms;
    }

    public String getRoomName() {
        return roomName;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    /**
     * Retrieves the {@code Room}'s weekday schedule in an ArrayList
     *
     * @throws CorruptedVenueInformationException if the room schedule format is not as expected.
     */
    public ArrayList<String> retrieveWeekDaySchedule() throws CorruptedVenueInformationException {
        initializeWeek();
        ArrayList<String> schedules = week.retrieveWeekDaySchedule();
        schedules.add(0, roomName);
        return schedules;
    }

    /**
     * Initialize the {@code Week} schedule of the room
     *
     * @throws CorruptedVenueInformationException if the NUS Venues format is not as expected.
     */
    public void initializeWeek() throws CorruptedVenueInformationException {
        if (nusVenues == null) {
            logger.warning("NUS Venues is null, venueinformation.json file is corrupted.");
            throw new CorruptedVenueInformationException();
        }
        week = nusVenues.get(roomName);
        if (week == null) {
            logger.warning(roomName + " data is null, venueinformation.json file is corrupted.");
            throw new CorruptedVenueInformationException();
        }
        week.setRoomName(roomName);
    }

    @Override
    public String toString() {
        return roomName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Room // instanceof handles nulls
                && roomName.equals(((Room) other).roomName)); // state check
    }

    @Override
    public int hashCode() {
        return roomName.hashCode();
    }

}
```
###### \java\seedu\address\model\building\Week.java
``` java
/**
 * Represents a Week schedule of a Room in National University of Singapore.
 */
public class Week {

    public static final int NUMBER_OF_DAYS = 6;
    public static final int SUNDAY = -1;

    private static final Logger logger = LogsCenter.getLogger(Week.class);

    private static final int NUMBER_OF_CLASSES = 13;

    private ArrayList<WeekDay> weekSchedule = null;
    private String roomName;
    private int weekday;

    public ArrayList<WeekDay> getWeekSchedule() {
        return weekSchedule;
    }

    public void setWeekSchedule(ArrayList<WeekDay> weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    /**
     * Retrieves the {@code Room}'s weekday schedule in an ArrayList
     *
     * @throws CorruptedVenueInformationException if the room schedule format is not as expected.
     */
    public ArrayList<String> retrieveWeekDaySchedule() throws CorruptedVenueInformationException {
        try {
            isValidWeekSchedule();
            weekday = getDayOfWeek();
            if (weekday == SUNDAY) {
                return getNoClassSchedule();
            }
            WeekDay weekDay = initializeWeekDay();
            return weekDay.retrieveWeekDaySchedule();
        } catch (InvalidWeekScheduleException e) {
            throw new CorruptedVenueInformationException();
        }
    }

    /**
     * Checks for null instance in week schedule list
     *
     * @throws InvalidWeekScheduleException if the week schedule format is not as expected.
     */
    public boolean isValidWeekSchedule() throws InvalidWeekScheduleException {
        if (weekSchedule == null) {
            logger.warning("Week Schedule is null, venueinformation.json file is corrupted.");
            throw new InvalidWeekScheduleException();
        }
        if (weekSchedule.size() != NUMBER_OF_DAYS) {
            logger.warning("Week Schedule has incorrect data, venueinformation.json file is corrupted.");
            throw new InvalidWeekScheduleException();
        }
        return true;
    }

    /**
     * Gets the current weekday, weekday starts from Monday which is 0, which is offset by 2 from calendar API
     */
    private int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day - 2;
    }

    private WeekDay initializeWeekDay() {
        WeekDay weekDay = weekSchedule.get(weekday);
        weekDay.setRoomName(roomName);
        return weekDay;
    }

    private ArrayList<String> getNoClassSchedule() {
        ArrayList<String> noClassSchedule = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_CLASSES; i++) {
            noClassSchedule.add("vacant");
        }
        return noClassSchedule;
    }

    @Override
    public String toString() {
        return roomName + "Week Schedule";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Week // instanceof handles nulls
                && weekSchedule.equals(((Week) other).weekSchedule)); // state check
    }

    @Override
    public int hashCode() {
        return weekSchedule.hashCode();
    }

}
```
###### \java\seedu\address\model\building\WeekDay.java
``` java
/**
 * Represents a WeekDay schedule of a Room in National University of Singapore.
 */
public class WeekDay {

    public static final int NUMBER_OF_CLASSES = 13;

    private static final Logger logger = LogsCenter.getLogger(WeekDay.class);

    private static final int START_TIME = 800;
    private static final int END_TIME = 2000;
    private static final int FOUR_DIGIT_24_HOUR_FORMAT = 1000;
    private static final int ONE_HOUR_IN_24_HOUR_FORMAT = 100;

    private HashMap<String, String> weekDaySchedule = null;
    private String weekday;
    private String roomName;

    public HashMap<String, String> getWeekDaySchedule() {
        return weekDaySchedule;
    }

    public void setWeekDaySchedule(HashMap<String, String> weekDaySchedule) {
        this.weekDaySchedule = weekDaySchedule;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    /**
     * Retrieves the {@code Room}'s weekday schedule in an ArrayList
     *
     * @throws CorruptedVenueInformationException if the weekday schedule format is not as expected.
     */
    public ArrayList<String> retrieveWeekDaySchedule() throws CorruptedVenueInformationException {
        try {
            isValidWeekDaySchedule();
            ArrayList<String> weekDayScheduleList = new ArrayList<>();
            int time = START_TIME;
            while (time <= END_TIME) {
                String timeString = "" + time;
                if (time < FOUR_DIGIT_24_HOUR_FORMAT) {
                    timeString = "0" + time;
                }
                String roomStatus = this.weekDaySchedule.get(timeString);

                weekDayScheduleList.add(roomStatus);
                time = incrementOneHour(time);
            }
            return weekDayScheduleList;
        } catch (InvalidWeekDayScheduleException e) {
            throw new CorruptedVenueInformationException();
        }
    }

    /**
     * Checks for null instance in week day schedule hash map
     *
     * @throws InvalidWeekDayScheduleException if the weekday schedule format is not as expected.
     */
    public boolean isValidWeekDaySchedule() throws InvalidWeekDayScheduleException {
        if (weekDaySchedule == null) {
            logger.warning("Weekday Schedule is null, venueinformation.json file is corrupted.");
            throw new InvalidWeekDayScheduleException();
        }
        int time = START_TIME;
        while (time <= END_TIME) {
            String timeString = "" + time;
            if (time < FOUR_DIGIT_24_HOUR_FORMAT) {
                timeString = "0" + time;
            }
            String roomStatus = this.weekDaySchedule.get(timeString);
            if (roomStatus == null) {
                logger.warning("Weekday Schedule contains some null data, venueinformation.json file is corrupted.");
                throw new InvalidWeekDayScheduleException();
            }
            if (!"vacant".equals(roomStatus) && !"occupied".equals(roomStatus)) {
                logger.warning("Weekday Schedule contains some incorrect data, "
                        + "venueinformation.json file is corrupted.");
                throw new InvalidWeekDayScheduleException();
            }
            time = incrementOneHour(time);
        }
        return true;
    }

    /**
     * Increments the time in 24 hour format by one hour
     */
    private int incrementOneHour(int time) {
        int timeAfterHalfHour = time + ONE_HOUR_IN_24_HOUR_FORMAT;
        return timeAfterHalfHour;
    }

    @Override
    public String toString() {
        return roomName + " " + weekday + " Schedule";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WeekDay // instanceof handles nulls
                && weekDaySchedule.equals(((WeekDay) other).weekDaySchedule)); // state check
    }

    @Override
    public int hashCode() {
        return weekDaySchedule.hashCode();
    }

}
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Imports specified {@code AddressBook} from filepath to current {@code AddressBook}
     */
    void importAddressBook(String filepath, byte[] password) throws DataConversionException, IOException,
            WrongPasswordException;

    /**
     * Exports the current view of {@code AddressBook} to the filepath.
     * @param filepath
     */
    void exportAddressBook(String filepath, Password password) throws IOException, WrongPasswordException,
                                                                        DuplicatePersonException;
```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns rooms for the given building */
    ArrayList<ArrayList<String>> retrieveAllRoomsSchedule(Building building) throws BuildingNotFoundException,
                                                                                    CorruptedVenueInformationException;
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Imports the specified {@code AddressBook} from the filepath to the current {@code AddressBook}.
     * @param filepath
     */
    @Override
    public void importAddressBook(String filepath, byte[] password) throws DataConversionException, IOException,
                                                                            WrongPasswordException {
        requireNonNull(filepath);

        XmlAddressBookStorage xmlAddressBook = new XmlAddressBookStorage(filepath);
        xmlAddressBook.importAddressBook(filepath, this.addressBook, password);
        indicateAddressBookChanged();
    }

    /**
     * Exports the current view of {@code AddressBook} to the filepath.
     * @param filepath
     */
    @Override
    public void exportAddressBook(String filepath, Password password) throws IOException, WrongPasswordException,
                                                                                DuplicatePersonException {
        requireNonNull(filepath);

        XmlAddressBookStorage xmlAddressBook = new XmlAddressBookStorage(filepath);
        xmlAddressBook.exportAddressBook(filepath, password, filteredPersons);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public ArrayList<ArrayList<String>> retrieveAllRoomsSchedule(Building building) throws BuildingNotFoundException,
            CorruptedVenueInformationException {
        if (!Building.isValidBuilding(building)) {
            throw new BuildingNotFoundException();
        }
        return building.retrieveAllRoomsSchedule();
    }
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Adds a person to the list if the person is not a duplicate of an existing person in the list
     */
    public void importPerson(Person toAdd) {
        requireNonNull(toAdd);
        if (!contains(toAdd)) {
            internalList.add(toAdd);
        }
    }
```
###### \java\seedu\address\model\tag\UniqueTagList.java
``` java
    /**
     * Adds a Tag to the list if the Tag is not a duplicate of an existing Tag in the list
     */
    public void importTag(Tag toAdd) {
        requireNonNull(toAdd);
        if (!contains(toAdd)) {
            internalList.add(toAdd);
        }

        assert CollectionUtil.elementsAreUnique(internalList);
    }
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public static String getUserAddressBookFilePath() {
        return UserPrefs.userAddressBookFilePath;
    }

    public static void setUserAddressBookFilePath(String userAddressBookFilePath) {
        UserPrefs.userAddressBookFilePath = userAddressBookFilePath;
    }
```
###### \java\seedu\address\storage\ReadOnlyJsonVenueInformation.java
``` java
/**
 * A class to access VenueInformation stored in the hard disk as a json file
 */
public class ReadOnlyJsonVenueInformation implements ReadOnlyVenueInformation {

    private String filePath;

    public ReadOnlyJsonVenueInformation(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getVenueInformationFilePath() {
        return filePath;
    }

    @Override
    public Optional<Room> readVenueInformation() throws DataConversionException, IOException {
        return readVenueInformation(filePath);
    }

    /**
     * Converts Json file into HashMap of NUS Rooms
     * @param venueInformationFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<Room> readVenueInformation(String venueInformationFilePath) throws DataConversionException {
        return JsonUtil.readJsonFileFromResource(venueInformationFilePath, Room.class);
    }

    @Override
    public Optional<Building> readBuildingsAndRoomsInformation() throws DataConversionException, IOException {
        return readBuildingsAndRoomsInformation(filePath);
    }

    /**
     * Converts Json file into HashMap of NUS Buildings and Rooms
     * @param buildingsAndRoomsInformationFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<Building> readBuildingsAndRoomsInformation(String buildingsAndRoomsInformationFilePath)
            throws DataConversionException {
        return JsonUtil.readJsonFileFromResource(buildingsAndRoomsInformationFilePath, Building.class);
    }

}
```
###### \java\seedu\address\storage\ReadOnlyVenueInformation.java
``` java
/**
 * Represents a storage for {@link seedu.address.model.building.Room}.
 */
public interface ReadOnlyVenueInformation {

    /**
     * Returns the file path of the VenueInformation data file.
     */
    String getVenueInformationFilePath();

    /**
     * Returns VenueInformation data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Room> readVenueInformation() throws DataConversionException, IOException;

    Optional<Building> readBuildingsAndRoomsInformation() throws DataConversionException, IOException;
}
```
###### \java\seedu\address\storage\Storage.java
``` java
    @Override
    Optional<Room> readVenueInformation() throws DataConversionException, IOException;

    @Override
    Optional<Building> readBuildingsAndRoomsInformation() throws DataConversionException, IOException;
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public String getVenueInformationFilePath() {
        return venueInformationStorage.getVenueInformationFilePath();
    }

    @Override
    public Optional<Room> readVenueInformation() throws DataConversionException, IOException {
        return venueInformationStorage.readVenueInformation();
    }

    @Override
    public Optional<Building> readBuildingsAndRoomsInformation() throws DataConversionException, IOException {
        return venueInformationStorage.readBuildingsAndRoomsInformation();
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    /**
     * Imports the specified {@code AddressBook} from the filepath to the current {@code AddressBook}.
     *
     * @param filePath      location of the specified AddressBook. Cannot be null
     * @param addressBook   current existing AddressBook
     * @return              modified AddressBook
     * @throws DataConversionException if the file is not in the correct format.
     */
    public AddressBook importAddressBook(String filePath, AddressBook addressBook, byte[] password)
            throws DataConversionException, IOException, WrongPasswordException {
        requireNonNull(filePath);

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            throw new FileNotFoundException();
        }
        if (password != null) {
            SecurityUtil.decrypt(new File(filePath), password);
        }
        XmlSerializableAddressBook xmlAddressBook = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        try {
            return xmlAddressBook.addToAddressBook(addressBook);
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + addressBookFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Exports the current view of {@code AddressBook} to the given filepath.
     *
     * @param filePath                  location of the exported data. Cannot be null
     * @throws IOException              If the file cannot be overwritten or opened.
     * @throws WrongPasswordException   If password is in wrong format
     * @throws DuplicatePersonException Impossible since AddressBook is newly created
     */
    public void exportAddressBook(String filePath, Password password, ObservableList<Person> filteredPersons)
            throws IOException, WrongPasswordException, DuplicatePersonException {
        requireNonNull(filePath);

        if (UserPrefs.getUserAddressBookFilePath().equals(filePath)) {
            logger.warning("Filepath is same as AddressBook storage filepath, storage file should not be overwritten!");
            throw new IOException();
        }
        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        AddressBook addressBook = new AddressBook();
        addressBook.setPersons(filteredPersons);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
        SecurityUtil.encryptFile(file, password);
    }
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    /**
     * Adds {@code person}s and {@code tag}s from this addressbook into the existing {@code AddressBook}.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} or {@code XmlAdaptedTag}.
     */
    public AddressBook addToAddressBook(AddressBook addressBook) throws IllegalValueException {
        for (XmlAdaptedTag t : tags) {
            addressBook.importTag(t.toModelType());
        }
        for (XmlAdaptedPerson p : persons) {
            addressBook.importPerson(p.toModelType());
        }
        for (XmlAdaptedAlias a : aliases) {
            addressBook.importAlias(a.toModelType());
        }
        return addressBook;
    }
```
