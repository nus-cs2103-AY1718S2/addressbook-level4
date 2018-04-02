# Caijun7
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ImportCommand}.
 */
public class ImportCommandTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/ImportCommandTest/");
    private static final String TEST_DATA_FILE_ALICE = TEST_DATA_FOLDER + "aliceAddressBook.xml";
    private static final String TEST_DATA_FILE_ALICE_BENSON = TEST_DATA_FOLDER + "aliceBensonAddressBook.xml";
    private static final String TEST_PASSWORD = "test";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBookWithAliceAndBenson = new AddressBookBuilder().withPerson(ALICE)
            .withPerson(BENSON).build();

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());

    private final ImportCommand standardCommand = prepareCommand(TEST_DATA_FILE_ALICE_BENSON, model);

    @Test
    public void execute_validFileImportIntoEmptyAddressBook_success() throws Exception {
        String filepath = TEST_DATA_FOLDER + "aliceBensonAddressBook.xml";
        ImportCommand importCommand = prepareCommand(filepath, model);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.importAddressBook(filepath, SecurityUtil.hashPassword(""));
        assertCommandSuccess(importCommand, model, ImportCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_validFileImportIntoNonEmptyAddressBook_success() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String filepath = TEST_DATA_FOLDER + "aliceBensonAddressBook.xml";
        ImportCommand importCommand = prepareCommand(filepath, model);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.importAddressBook(filepath, SecurityUtil.hashPassword(""));
        assertCommandSuccess(importCommand, model, ImportCommand.MESSAGE_SUCCESS, expectedModel);
    }
```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
    @Test
    public void execute_nonExistentFileImportIntoAddressBook_throwsCommandException() throws Exception {
        String nonExistentFile = TEST_DATA_FOLDER + "nonExistentFile.xml";
        ImportCommand importCommand = prepareCommand(nonExistentFile, model);

        assertCommandFailure(importCommand, model, ImportCommand.MESSAGE_FILE_NOT_FOUND);
    }

    @Test
    public void execute_invalidFileFormatImportIntoAddressBook_throwsCommandException() throws Exception {
        String invalidFileFormat = TEST_DATA_FOLDER + "invalidFileFormatAddressBook.xml";
        ImportCommand importCommand = prepareCommand(invalidFileFormat, model);

        assertCommandFailure(importCommand, model, ImportCommand.MESSAGE_DATA_CONVERSION_ERROR);
    }

    @Test
    public void execute_importDuplicateAddressBook_noChange() throws Exception {
        Model model = new ModelManager(addressBookWithAliceAndBenson, new UserPrefs());
        String filepath = TEST_DATA_FOLDER + "aliceBensonAddressBook.xml";
        ImportCommand importCommand = prepareCommand(filepath, model);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.importAddressBook(filepath, SecurityUtil.hashPassword(""));
        assertCommandSuccess(importCommand, model, ImportCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_importDuplicatePerson_noChange() throws Exception {
        Model model = new ModelManager(addressBookWithAliceAndBenson, new UserPrefs());
        String filepath = TEST_DATA_FOLDER + "aliceAddressBook.xml";
        ImportCommand importCommand = prepareCommand(filepath, model);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.importAddressBook(filepath, SecurityUtil.hashPassword(""));
        assertCommandSuccess(importCommand, model, ImportCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_validAddressBookFile_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        String filepath = TEST_DATA_FOLDER + "aliceBensonAddressBook.xml";
        ImportCommand importCommand = prepareCommand(filepath, model);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // import -> address book imported
        importCommand.execute();
        undoRedoStack.push(importCommand);

        // undo -> reverts address book back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> address book imported again
        expectedModel.importAddressBook(filepath, SecurityUtil.hashPassword(""));
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidAddressBookFile_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        String filepath = TEST_DATA_FOLDER + "invalidFileFormatAddressBook.xml";
        ImportCommand importCommand = prepareCommand(filepath, model);

        // execution failed -> importCommand not pushed into undoRedoStack
        assertCommandFailure(importCommand, model, importCommand.MESSAGE_DATA_CONVERSION_ERROR);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_nonExistentAddressBookFile_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        String filepath = TEST_DATA_FOLDER + "nonExistentAddressBook.xml";
        ImportCommand importCommand = prepareCommand(filepath, model);

        // execution failed -> importCommand not pushed into undoRedoStack
        assertCommandFailure(importCommand, model, importCommand.MESSAGE_FILE_NOT_FOUND);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals_sameValues_true() {
        ImportCommand commandWithSameValues = prepareCommand(TEST_DATA_FILE_ALICE_BENSON, model);
        assertTrue(standardCommand.equals(commandWithSameValues));
    }

    @Test
    public void equals_sameObject_true() {
        assertTrue(standardCommand.equals(standardCommand));
    }

    @Test
    public void equals_sameType_true() {
        assertTrue(standardCommand.equals(new ImportCommand(TEST_DATA_FILE_ALICE_BENSON, TEST_PASSWORD)));
    }

    @Test
    public void equals_nullInstance_false() {
        assertFalse(standardCommand.equals(null));
    }

    @Test
    public void equals_differentTypes_false() {
        assertFalse(standardCommand.equals(new ClearCommand()));
    }

    @Test
    public void equals_differentAddressBook_false() {
        assertFalse(standardCommand.equals(prepareCommand(TEST_DATA_FILE_ALICE, model)));
    }

    /**
     * Returns a {@code ImportCommand} with the parameter {@code filepath} with password as TEST_PASSWORD.
     */
    private ImportCommand prepareCommand(String filepath, Model model) {
        return prepareCommand(filepath, model, TEST_PASSWORD);
    }

    /**
     * Returns a {@code ImportCommand} with the parameter {@code filepath} and {@code password}.
     */
    private ImportCommand prepareCommand(String filepath, Model model, String password) {
        ImportCommand importCommand = new ImportCommand(filepath, password);
        importCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return importCommand;
    }

}
```
###### \java\seedu\address\logic\parser\ImportCommandParserTest.java
``` java
public class ImportCommandParserTest {
    private static final String TEST_PASSWORD = "test";

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validArgs_returnsImportCommand() {
        assertParseSuccess(parser, "validString test", new ImportCommand("validString", TEST_PASSWORD));
    }

    @Test
    public void parse_oneArg_throwsParseException() {
        assertParseFailure(parser, "validString", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\building\BuildingTest.java
``` java
public class BuildingTest {

    private Building building = new Building("COM2");
    private final String validBuildingName = "COM2";
    private final Building validBuilding = new BuildingBuilder().build();
    private final Building standardBuilding = new BuildingBuilder().withBuildingName("COM2").build();

    @Test
    public void isValidBuilding_validString_true() {
        assertTrue(Building.isValidBuilding(validBuildingName));
    }

    @Test
    public void isValidBuilding_invalidString_false() {
        String invalidString = "COM2!";
        assertFalse(Building.isValidBuilding(invalidString));
    }

    @Test
    public void isValidBuilding_buildingFoundInListOfNusBuildings_true() {
        assertTrue(Building.isValidBuilding(validBuilding));
    }

    @Test
    public void isValidBuilding_buildingNotFoundInListOfNusBuildings_false() {
        Building invalidBuilding = new Building("COM3");
        assertFalse(Building.isValidBuilding(invalidBuilding));
    }

    @Test
    public void retrieveAllRoomsSchedule_nullNusBuildingsAndRooms_throwsCorruptedVenueInformationException() {
        Building.setNusBuildingsAndRooms(null);
        assertThrows(CorruptedVenueInformationException.class, () -> building.retrieveAllRoomsSchedule());
    }

    @Test
    public void retrieveAllRoomsSchedule_invalidNusBuildingsAndRooms_throwsCorruptedVenueInformationException() {
        HashMap<String, ArrayList<String>> invalidNusBuildingsAndRooms = new HashMap<>();
        invalidNusBuildingsAndRooms.put("COM2", null);
        Building.setNusBuildingsAndRooms(invalidNusBuildingsAndRooms);
        assertThrows(CorruptedVenueInformationException.class, () -> building.retrieveAllRoomsSchedule());
    }

    @Test
    public void retrieveAllRoomsSchedule_validNusBuildingsAndRooms_success() throws Exception {
        ArrayList<ArrayList<String>> expectedList = new ArrayList<>();
        ArrayList<String> expectedSchedule = new ArrayList<>();
        Room room = new RoomBuilder().build();
        expectedSchedule.add(room.getRoomName());
        for (int i = 0; i < WeekDay.NUMBER_OF_CLASSES; i++) {
            expectedSchedule.add("vacant");
        }
        expectedList.add(expectedSchedule);
        assertEquals(expectedList, validBuilding.retrieveAllRoomsSchedule());
    }

    @Test
    public void retrieveAllRoomsInBuilding_nullNusBuildingsAndRooms_throwsCorruptedVenueInformationException() {
        Building.setNusBuildingsAndRooms(null);
        assertThrows(CorruptedVenueInformationException.class, () -> building.retrieveAllRoomsInBuilding());
    }

    @Test
    public void retrieveAllRoomsInBuilding_invalidNusBuildingsAndRooms_throwsCorruptedVenueInformationException() {
        HashMap<String, ArrayList<String>> invalidNusBuildingsAndRooms = new HashMap<>();
        invalidNusBuildingsAndRooms.put("COM2", null);
        Building.setNusBuildingsAndRooms(invalidNusBuildingsAndRooms);
        assertThrows(CorruptedVenueInformationException.class, () -> building.retrieveAllRoomsInBuilding());
    }

    @Test
    public void retrieveAllRoomsInBuilding_invalidBuilding_throwsIllegalArgumentException() throws Exception {
        Building invalidBuilding = new Building("COM3");
        assertThrows(IllegalArgumentException.class, () -> invalidBuilding.retrieveAllRoomsInBuilding());
    }

    @Test
    public void retrieveAllRoomsInBuilding_validNusBuildingsAndRooms_success() throws Exception {
        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add(RoomBuilder.DEFAULT_ROOMNAME);
        assertEquals(expectedList, validBuilding.retrieveAllRoomsInBuilding());
    }

    @Test
    public void equals_sameValues_true() {
        Building buildingWithSameValues = new BuildingBuilder(standardBuilding).build();
        assertTrue(standardBuilding.equals(buildingWithSameValues));
    }

    @Test
    public void equals_sameObject_true() {
        assertTrue(standardBuilding.equals(standardBuilding));
    }

    @Test
    public void equals_sameType_true() {
        Building building = new Building("COM2");
        Building sameTypeBuilding = new Building("COM2");
        assertTrue(building.equals(sameTypeBuilding));
    }

    @Test
    public void equals_nullInstance_false() {
        assertFalse(standardBuilding.equals(null));
    }

    @Test
    public void equals_differentTypes_false() {
        assertFalse(standardBuilding.equals(new Week()));
    }

}
```
###### \java\seedu\address\model\building\RoomTest.java
``` java
public class RoomTest {

    private Room room = new Room("COM2-0108");
    private final Room validRoom = new RoomBuilder().build();
    private final Room standardRoom = new RoomBuilder().withRoomName("COM2-0108").build();

    @Test
    public void retrieveWeekDaySchedule_nullNusVenues_throwsCorruptedVenueInformationException() {
        Room.setNusVenues(null);
        assertThrows(CorruptedVenueInformationException.class, () -> room.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_nonNullInvalidNusVenues_throwsCorruptedVenueInformationException() {
        HashMap<String, Week> invalidNusVenues = new HashMap<>();
        invalidNusVenues.put("COM2-0108", null);
        Room.setNusVenues(invalidNusVenues);
        assertThrows(CorruptedVenueInformationException.class, () -> room.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_validNusVenues_success() throws Exception {
        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add(room.getRoomName());
        for (int i = 0; i < WeekDay.NUMBER_OF_CLASSES; i++) {
            expectedList.add("vacant");
        }
        assertEquals(expectedList, validRoom.retrieveWeekDaySchedule());
    }

    @Test
    public void initializeWeek_nullNusVenues_throwsCorruptedVenueInformationException() {
        Room.setNusVenues(null);
        assertThrows(CorruptedVenueInformationException.class, () -> room.initializeWeek());
    }

    @Test
    public void initializeWeek_nonNullInvalidNusVenues_throwsCorruptedVenueInformationException() {
        HashMap<String, Week> invalidNusVenues = new HashMap<>();
        invalidNusVenues.put("COM2-0108", null);
        Room.setNusVenues(invalidNusVenues);
        assertThrows(CorruptedVenueInformationException.class, () -> room.initializeWeek());
    }

    @Test
    public void initializeWeek_validNusVenues_success() throws Exception {
        validRoom.initializeWeek();

        Week expectedWeek = new WeekBuilder().build();
        assertEquals(expectedWeek, validRoom.getWeek());
    }

    @Test
    public void equals_sameValues_true() {
        Room roomWithSameValues = new RoomBuilder(standardRoom).build();
        assertTrue(standardRoom.equals(roomWithSameValues));
    }

    @Test
    public void equals_sameObject_true() {
        assertTrue(standardRoom.equals(standardRoom));
    }

    @Test
    public void equals_sameType_true() {
        Room room = new Room("COM2-0108");
        Room sameTypeRoom = new Room("COM2-0108");
        assertTrue(room.equals(sameTypeRoom));
    }

    @Test
    public void equals_nullInstance_false() {
        assertFalse(standardRoom.equals(null));
    }

    @Test
    public void equals_differentTypes_false() {
        assertFalse(standardRoom.equals(new Week()));
    }

}
```
###### \java\seedu\address\model\building\WeekDayTest.java
``` java
public class WeekDayTest {

    private WeekDay weekDay = new WeekDay();
    private final WeekDay validWeekDay = new WeekDayBuilder().build();
    private final WeekDay standardWeekDay =
            new WeekDayBuilder().withWeekDay("Monday").withRoomName("COM2-0108").build();

    @Test
    public void retrieveWeekDaySchedule_nullWeekDaySchedule_throwsCorruptedVenueInformationException() {
        weekDay.setWeekDaySchedule(null);
        assertThrows(CorruptedVenueInformationException.class, () -> weekDay.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_invalidWeekDayScheduleFormat_throwsCorruptedVenueInformationException() {
        HashMap<String, String> invalidWeekDaySchedule = new HashMap<>();
        invalidWeekDaySchedule.put("800", "vacant");
        weekDay.setWeekDaySchedule(invalidWeekDaySchedule);
        assertThrows(CorruptedVenueInformationException.class, () -> weekDay.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_invalidWeekDayScheduleData_throwsCorruptedVenueInformationException() {
        HashMap<String, String> invalidWeekDaySchedule = createInvalidWeekDaySchedule();
        weekDay.setWeekDaySchedule(invalidWeekDaySchedule);
        assertThrows(CorruptedVenueInformationException.class, () -> weekDay.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_validWeekDaySchedule_success() throws Exception {
        HashMap<String, String> validWeekDaySchedule = validWeekDay.getWeekDaySchedule();
        weekDay.setWeekDaySchedule(validWeekDaySchedule);

        ArrayList<String> expectedList = new ArrayList<>();
        for (int i = 0; i < WeekDay.NUMBER_OF_CLASSES; i++) {
            expectedList.add("vacant");
        }
        assertEquals(expectedList, weekDay.retrieveWeekDaySchedule());
    }

    @Test
    public void isValidWeekDaySchedule_nullWeekDaySchedule_throwsInvalidWeekDayScheduleException() {
        weekDay.setWeekDaySchedule(null);
        assertThrows(InvalidWeekDayScheduleException.class, () -> weekDay.isValidWeekDaySchedule());
    }

    @Test
    public void isValidWeekDaySchedule_invalidWeekDayScheduleFormat_throwsInvalidWeekDayScheduleException() {
        HashMap<String, String> invalidWeekDaySchedule = new HashMap<>();
        invalidWeekDaySchedule.put("800", "vacant");
        weekDay.setWeekDaySchedule(invalidWeekDaySchedule);
        assertThrows(InvalidWeekDayScheduleException.class, () -> weekDay.isValidWeekDaySchedule());
    }

    @Test
    public void isValidWeekDaySchedule_invalidWeekDayScheduleData_throwsInvalidWeekDayScheduleException() {
        HashMap<String, String> invalidWeekDaySchedule = createInvalidWeekDaySchedule();
        weekDay.setWeekDaySchedule(invalidWeekDaySchedule);
        assertThrows(InvalidWeekDayScheduleException.class, () -> weekDay.isValidWeekDaySchedule());
    }

    @Test
    public void isValidWeekDaySchedule_validWeekDaySchedule_success() throws Exception {
        HashMap<String, String> validWeekDaySchedule = validWeekDay.getWeekDaySchedule();
        weekDay.setWeekDaySchedule(validWeekDaySchedule);

        assertTrue(weekDay.isValidWeekDaySchedule());
    }

    @Test
    public void equals_sameValues_true() {
        WeekDay weekDayWithSameValues = new WeekDayBuilder().build();
        assertTrue(standardWeekDay.equals(weekDayWithSameValues));
    }

    @Test
    public void equals_sameObject_true() {
        assertTrue(standardWeekDay.equals(standardWeekDay));
    }

    @Test
    public void equals_sameType_true() {
        WeekDay weekDay = new WeekDay();
        HashMap<String, String> testWeekDaySchedule = new HashMap<>();
        weekDay.setWeekDaySchedule(testWeekDaySchedule);
        WeekDay sameTypeWeekDay = new WeekDay();
        sameTypeWeekDay.setWeekDaySchedule(testWeekDaySchedule);
        assertTrue(weekDay.equals(sameTypeWeekDay));
    }

    @Test
    public void equals_nullInstance_false() {
        assertFalse(standardWeekDay.equals(null));
    }

    @Test
    public void equals_differentTypes_false() {
        assertFalse(standardWeekDay.equals(new Week()));
    }

    /**
     * Creates an invalid {@code weekDaySchedule} HashMap with one incorrect data
     */
    private HashMap<String, String> createInvalidWeekDaySchedule() {
        HashMap<String, String> invalidWeekDaySchedule = new HashMap<>();
        invalidWeekDaySchedule.put("0800", "vacant");
        invalidWeekDaySchedule.put("0900", "vacant");
        invalidWeekDaySchedule.put("1000", "vacant");
        invalidWeekDaySchedule.put("1100", "vacant");
        invalidWeekDaySchedule.put("1200", "vacant");
        invalidWeekDaySchedule.put("1300", "vacant");
        invalidWeekDaySchedule.put("1400", "vacant");
        invalidWeekDaySchedule.put("1500", "vacant");
        invalidWeekDaySchedule.put("1600", "vacant");
        invalidWeekDaySchedule.put("1700", "vacant");
        invalidWeekDaySchedule.put("1800", "vacant");
        invalidWeekDaySchedule.put("1900", "vacant");
        invalidWeekDaySchedule.put("2000", "vacan");
        return invalidWeekDaySchedule;
    }

}
```
###### \java\seedu\address\model\building\WeekTest.java
``` java
public class WeekTest {

    private Week week = new Week();
    private final Week validWeek = new WeekBuilder().build();
    private final Week standardWeek = new WeekBuilder().withDay(0).withRoomName("COM2-0108").build();

    @Test
    public void retrieveWeekDaySchedule_nullWeekSchedule_throwsCorruptedVenueInformationException() {
        week.setWeekSchedule(null);
        assertThrows(CorruptedVenueInformationException.class, () -> week.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_nonNullInvalidWeekDaySchedule_throwsCorruptedVenueInformationException() {
        ArrayList<WeekDay> invalidWeekSchedule = new ArrayList<>();
        invalidWeekSchedule.add(new WeekDayBuilder().build());
        week.setWeekSchedule(invalidWeekSchedule);
        assertThrows(CorruptedVenueInformationException.class, () -> week.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_validWeekDaySchedule_success() throws Exception {
        ArrayList<String> expectedList = new ArrayList<>();
        for (int i = 0; i < WeekDay.NUMBER_OF_CLASSES; i++) {
            expectedList.add("vacant");
        }
        assertEquals(expectedList, validWeek.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_sundayWeekDaySchedule_success() throws Exception {
        validWeek.setWeekday(Week.SUNDAY);

        ArrayList<String> expectedList = new ArrayList<>();
        for (int i = 0; i < WeekDay.NUMBER_OF_CLASSES; i++) {
            expectedList.add("vacant");
        }
        assertEquals(expectedList, validWeek.retrieveWeekDaySchedule());
    }

    @Test
    public void isValidWeekSchedule_nullWeekSchedule_throwsInvalidWeekScheduleException() {
        week.setWeekSchedule(null);
        assertThrows(InvalidWeekScheduleException.class, () -> week.isValidWeekSchedule());
    }

    @Test
    public void isValidWeekSchedule_nonNullInvalidWeekDaySchedule_throwsInvalidWeekScheduleException() {
        ArrayList<WeekDay> invalidWeekSchedule = new ArrayList<>();
        invalidWeekSchedule.add(new WeekDayBuilder().build());
        week.setWeekSchedule(invalidWeekSchedule);
        assertThrows(InvalidWeekScheduleException.class, () -> week.isValidWeekSchedule());
    }

    @Test
    public void isValidWeekSchedule_validWeekDaySchedule_success() throws Exception {
        assertTrue(validWeek.isValidWeekSchedule());
    }

    @Test
    public void equals_sameValues_true() {
        Week weekWithSameValues = new WeekBuilder(standardWeek).build();
        assertTrue(standardWeek.equals(weekWithSameValues));
    }

    @Test
    public void equals_sameObject_true() {
        assertTrue(standardWeek.equals(standardWeek));
    }

    @Test
    public void equals_sameType_true() {
        Week week = new Week();
        ArrayList<WeekDay> testWeekSchedule = new ArrayList<>();
        week.setWeekSchedule(testWeekSchedule);
        Week sameTypeWeek = new Week();
        sameTypeWeek.setWeekSchedule(testWeekSchedule);
        assertTrue(week.equals(sameTypeWeek));
    }

    @Test
    public void equals_nullInstance_false() {
        assertFalse(standardWeek.equals(null));
    }

    @Test
    public void equals_differentTypes_false() {
        assertFalse(standardWeek.equals(new WeekDay()));
    }

}
```
###### \java\seedu\address\storage\ReadOnlyJsonVenueInformationTest.java
``` java
public class ReadOnlyJsonVenueInformationTest {

    private static final String TEST_DATA_FOLDER = "/ReadOnlyJsonVenueInformationTest/";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void readVenueInformation_nullFilePath_throwsNullPointerException() throws DataConversionException {
        thrown.expect(NullPointerException.class);
        readVenueInformation(null);
    }

    @Test
    public void readVenueInformation_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readVenueInformation("NonExistentFile.json").isPresent());
    }

    @Test
    public void readVenueInformation_notJsonFormat_exceptionThrown() throws DataConversionException {
        thrown.expect(DataConversionException.class);
        readVenueInformation("NotJsonFormatVenueInformation.json");
    }

    @Test
    public void readVenueInformation_fileInOrder_successfullyRead() throws DataConversionException {
        HashMap<String, Week> expected = getTypicalVenueInformation().getNusRooms();
        HashMap<String, Week> actual = readVenueInformation("TypicalVenueInformation.json").get().getNusRooms();
        assertEquals(expected, actual);
    }

    @Test
    public void readVenueInformation_valuesMissingFromFile_defaultValueUsed() throws DataConversionException {
        HashMap<String, Week> actual = readVenueInformation("EmptyVenueInformation.json").get().getNusRooms();
        assertEquals(null, actual);
    }

    @Test
    public void readVenueInformation_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        HashMap<String, Week> expected = getTypicalVenueInformation().getNusRooms();
        HashMap<String, Week> actual = readVenueInformation("ExtraValuesVenueInformation.json").get().getNusRooms();
        assertEquals(expected, actual);
    }

    @Test
    public void readBuildingsAndRoomsInformation_nullFilePath_throwsNullPointerException()
            throws DataConversionException {
        thrown.expect(NullPointerException.class);
        readBuildingsAndRoomsInformation(null);
    }

    @Test
    public void readBuildingsAndRoomsInformation_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readBuildingsAndRoomsInformation("NonExistentFile.json").isPresent());
    }

    @Test
    public void readBuildingsAndRoomsInformation_notJsonFormat_exceptionThrown() throws DataConversionException {
        thrown.expect(DataConversionException.class);
        readBuildingsAndRoomsInformation("NotJsonFormatVenueInformation.json");
    }

    @Test
    public void readBuildingsAndRoomsInformation_fileInOrder_successfullyRead() throws DataConversionException {
        HashMap<String, ArrayList<String>> expected = getTypicalBuildingsAndRoomsInformation().getBuildingsAndRooms();
        HashMap<String, ArrayList<String>> actual =
                readBuildingsAndRoomsInformation("TypicalVenueInformation.json").get().getBuildingsAndRooms();
        assertEquals(expected, actual);
    }

    @Test
    public void readBuildingsAndRoomsInformation_valuesMissingFromFile_defaultValueUsed()
            throws DataConversionException {
        HashMap<String, ArrayList<String>> actual =
                readBuildingsAndRoomsInformation("EmptyVenueInformation.json").get().getBuildingsAndRooms();
        assertEquals(null, actual);
    }

    @Test
    public void readBuildingsAndRoomsInformation_extraValuesInFile_extraValuesIgnored()
            throws DataConversionException {
        HashMap<String, ArrayList<String>> expected = getTypicalBuildingsAndRoomsInformation().getBuildingsAndRooms();
        HashMap<String, ArrayList<String>> actual =
                readBuildingsAndRoomsInformation("ExtraValuesVenueInformation.json").get().getBuildingsAndRooms();
        assertEquals(expected, actual);
    }

    private Optional<Room> readVenueInformation(String venueInformationFileInTestDataFolder)
            throws DataConversionException {
        String venueInformationFilePath = addToTestDataPathIfNotNull(venueInformationFileInTestDataFolder);
        return new ReadOnlyJsonVenueInformation(venueInformationFilePath)
                .readVenueInformation(venueInformationFilePath);
    }

    private Optional<Building> readBuildingsAndRoomsInformation(String venueInformationFileInTestDataFolder)
            throws DataConversionException {
        String venueInformationFilePath = addToTestDataPathIfNotNull(venueInformationFileInTestDataFolder);
        return new ReadOnlyJsonVenueInformation(venueInformationFilePath)
                .readBuildingsAndRoomsInformation(venueInformationFilePath);
    }

    private String addToTestDataPathIfNotNull(String venueInformationFileInTestDataFolder) {
        return venueInformationFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + venueInformationFileInTestDataFolder
                : null;
    }

    private Room getTypicalVenueInformation() {
        Room venueInformation = new RoomBuilder().build();
        return venueInformation;
    }

    private Building getTypicalBuildingsAndRoomsInformation() {
        Building buildingsAndRoomsInformation = new BuildingBuilder().build();
        return buildingsAndRoomsInformation;
    }

}
```
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    @Test
    public void importAddressBook_invalidFileFormat_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        String filePath = TEST_DATA_FOLDER + "invalidFileFormatAddressBook.xml";
        AddressBook original = new AddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        xmlAddressBookStorage.importAddressBook(filePath, original, SecurityUtil.hashPassword(TEST_PASSWORD));
    }

    @Test
    public void importAddressBook_nonExistentFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        String filePath = TEST_DATA_FOLDER + "nonExistentAddressBook.xml";
        AddressBook original = new AddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        xmlAddressBookStorage.importAddressBook(filePath, original, SecurityUtil.hashPassword(TEST_PASSWORD));
    }

    @Test
    public void importAddressBook_validFile_success() throws Exception {
        String filePath = TEST_DATA_FOLDER + "validAddressBook.xml";
        AddressBook original = new AddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);

        // Import file into existing address book
        xmlAddressBookStorage.importAddressBook(filePath, original, SecurityUtil.hashPassword(TEST_PASSWORD));
        AddressBook expected = original;
        expected.importPerson(ALICE);
        expected.importPerson(BENSON);
        assertEquals(original, expected);
    }
```
###### \java\seedu\address\testutil\BuildingBuilder.java
``` java
/**
 * A utility class to help with building {@code Building} objects
 */
public class BuildingBuilder {

    public static final String DEFAULT_BUILDINGNAME = "COM2";

    private static HashMap<String, ArrayList<String>> nusBuildingsAndRooms;

    private HashMap<String, ArrayList<String>> buildingsAndRooms;
    private String buildingName;

    public BuildingBuilder() {
        buildingName = DEFAULT_BUILDINGNAME;
        buildingsAndRooms = createValidNusBuildingsAndRooms();
        nusBuildingsAndRooms = buildingsAndRooms;
    }

    /**
     * Initializes the BuildingBuilder with the data of {@code buildingToCopy}.
     */
    public BuildingBuilder(Building buildingToCopy) {
        buildingName = buildingToCopy.getBuildingName();
        buildingsAndRooms = buildingToCopy.getBuildingsAndRooms();
        nusBuildingsAndRooms = Building.getNusBuildingsAndRooms();
    }

    /**
     * Creates a valid {@code nusBuildingsAndBuildings}
     */
    private HashMap<String, ArrayList<String>> createValidNusBuildingsAndRooms() {
        HashMap<String, ArrayList<String>> validNusBuildingsAndRooms = new HashMap<>();
        ArrayList<String> validRoomsInBuildings = new ArrayList<>();
        validRoomsInBuildings.add(RoomBuilder.DEFAULT_ROOMNAME);
        validNusBuildingsAndRooms.put(DEFAULT_BUILDINGNAME, validRoomsInBuildings);
        return validNusBuildingsAndRooms;
    }

    /**
     * Sets the {@code buildingName} into a {@code Building} that we are building.
     */
    public BuildingBuilder withBuildingName(String buildingName) {
        this.buildingName = buildingName;
        return this;
    }

    /**
     * Sets the {@code nusBuildingsAndRooms} into a {@code Building} that we are building.
     */
    public BuildingBuilder withNusBuildingsAndRooms(HashMap<String, ArrayList<String>> nusBuildingsAndRooms) {
        BuildingBuilder.nusBuildingsAndRooms = nusBuildingsAndRooms;
        return this;
    }

    /**
     * Sets the {@code buildingsAndRooms} into a {@code Building} that we are building.
     */
    public BuildingBuilder withBuildingsAndRooms(HashMap<String, ArrayList<String>> buildingsAndRooms) {
        this.buildingsAndRooms = buildingsAndRooms;
        return this;
    }

    /**
     * Builds a {@code Building} object
     */
    public Building build() {
        Building building = new Building(buildingName);
        Building.setNusBuildingsAndRooms(nusBuildingsAndRooms);
        building.setBuildingsAndRooms(buildingsAndRooms);
        return building;
    }
}
```
###### \java\seedu\address\testutil\RoomBuilder.java
``` java
/**
 * A utility class to help with building {@code Room} objects
 */
public class RoomBuilder {

    public static final String DEFAULT_ROOMNAME = "COM2-0108";

    private static HashMap<String, Week> nusVenues;

    private String roomName;
    private HashMap<String, Week> nusRooms;
    private Week week = null;

    public RoomBuilder() {
        roomName = DEFAULT_ROOMNAME;
        nusRooms = createValidNusRooms();
        nusVenues = nusRooms;
        week = new WeekBuilder().build();
    }

    /**
     * Initializes the RoomBuilder with the data of {@code roomToCopy}.
     */
    public RoomBuilder(Room roomToCopy) {
        roomName = roomToCopy.getRoomName();
        nusRooms = roomToCopy.getNusRooms();
        nusVenues = Room.getNusVenues();
        week = roomToCopy.getWeek();
    }

    /**
     * Creates a valid {@code nusRooms}
     */
    private HashMap<String, Week> createValidNusRooms() {
        HashMap<String, Week> validNusRooms = new HashMap<>();
        Week validWeek = new WeekBuilder().build();
        validNusRooms.put(roomName, validWeek);
        return validNusRooms;
    }

    /**
     * Sets the {@code roomName} into a {@code Room} that we are building.
     */
    public RoomBuilder withRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    /**
     * Sets the {@code nusVenues} into a {@code Room} that we are building.
     */
    public RoomBuilder withNusVenues(HashMap<String, Week> nusVenues) {
        RoomBuilder.nusVenues = nusVenues;
        return this;
    }

    /**
     * Sets the {@code nusRooms} into a {@code Room} that we are building.
     */
    public RoomBuilder withNusRooms(HashMap<String, Week> nusRooms) {
        this.nusRooms = nusRooms;
        return this;
    }

    /**
     * Sets the {@code week} into a {@code Room} that we are building.
     */
    public RoomBuilder withWeek(Week week) {
        this.week = week;
        return this;
    }

    /**
     * Builds a {@code Room} object
     */
    public Room build() {
        Room room = new Room(roomName);
        room.setNusRooms(nusRooms);
        Room.setNusVenues(nusVenues);
        room.setWeek(week);
        return room;
    }
}
```
###### \java\seedu\address\testutil\WeekBuilder.java
``` java
/**
 * A utility class to help with building {@code Week} objects
 */
public class WeekBuilder {

    public static final String DEFAULT_ROOMNAME = "COM2-0108";
    public static final int DEFAULT_WEEKDAY = 0;

    private String roomName;
    private int weekday;
    private ArrayList<WeekDay> weekSchedule;

    public WeekBuilder() {
        roomName = DEFAULT_ROOMNAME;
        weekday = DEFAULT_WEEKDAY;
        weekSchedule = createValidWeekSchedule();
    }

    /**
     * Initializes the WeekBuilder with the data of {@code weekToCopy}.
     */
    public WeekBuilder(Week weekToCopy) {
        roomName = weekToCopy.getRoomName();
        weekSchedule = weekToCopy.getWeekSchedule();
    }

    /**
     * Creates a valid {@code weekSchedule}
     */
    private ArrayList<WeekDay> createValidWeekSchedule() {
        ArrayList<WeekDay> validWeekSchedule = new ArrayList<>();
        WeekDay validWeekDay = new WeekDayBuilder().build();
        for (int i = 0; i < Week.NUMBER_OF_DAYS; i++) {
            validWeekSchedule.add(validWeekDay);
        }
        return validWeekSchedule;
    }

    /**
     * Sets the {@code weekday} into a {@code Week} that we are building.
     */
    public WeekBuilder withDay(int day) {
        this.weekday = day;
        return this;
    }

    /**
     * Sets the {@code roomName} into a {@code Week} that we are building.
     */
    public WeekBuilder withRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    /**
     * Sets the {@code weekSchedule} into a {@code Week} that we are building.
     */
    public WeekBuilder withWeekSchedule(ArrayList<WeekDay> weekSchedule) {
        this.weekSchedule = weekSchedule;
        return this;
    }

    /**
     * Builds a {@code Week} object
     */
    public Week build() {
        Week week = new Week();
        week.setWeekday(weekday);
        week.setRoomName(roomName);
        week.setWeekSchedule(weekSchedule);
        return week;
    }
}
```
###### \java\seedu\address\testutil\WeekDayBuilder.java
``` java
/**
 * A utility class to help with building {@code WeekDay} objects
 */
public class WeekDayBuilder {

    public static final String DEFAULT_WEEKDAY = "Monday";
    public static final String DEFAULT_ROOMNAME = "COM2-0108";

    private HashMap<String, String> weekDaySchedule;
    private String weekday;
    private String roomName;

    public WeekDayBuilder() {
        weekday = DEFAULT_WEEKDAY;
        roomName = DEFAULT_ROOMNAME;
        weekDaySchedule = createValidWeekDaySchedule();
    }

    /**
     * Initializes the WeekDayBuilder with the data of {@code weekDayToCopy}.
     */
    public WeekDayBuilder(WeekDay weekDayToCopy) {
        weekday = weekDayToCopy.getWeekday();
        roomName = weekDayToCopy.getRoomName();
        weekDaySchedule = weekDayToCopy.getWeekDaySchedule();
    }

    /**
     * Creates a valid {@code weekDaySchedule}
     */
    private HashMap<String, String> createValidWeekDaySchedule() {
        HashMap<String, String> validWeekDaySchedule = new HashMap<>();
        validWeekDaySchedule.put("0800", "vacant");
        validWeekDaySchedule.put("0900", "vacant");
        validWeekDaySchedule.put("1000", "vacant");
        validWeekDaySchedule.put("1100", "vacant");
        validWeekDaySchedule.put("1200", "vacant");
        validWeekDaySchedule.put("1300", "vacant");
        validWeekDaySchedule.put("1400", "vacant");
        validWeekDaySchedule.put("1500", "vacant");
        validWeekDaySchedule.put("1600", "vacant");
        validWeekDaySchedule.put("1700", "vacant");
        validWeekDaySchedule.put("1800", "vacant");
        validWeekDaySchedule.put("1900", "vacant");
        validWeekDaySchedule.put("2000", "vacant");
        return validWeekDaySchedule;
    }

    /**
     * Sets the {@code weekday} of the {@code WeekDay} that we are building.
     */
    public WeekDayBuilder withWeekDay(String weekday) {
        this.weekday = weekday;
        return this;
    }

    /**
     * Sets the {@code roomName} into a {@code WeekDay} that we are building.
     */
    public WeekDayBuilder withRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    /**
     * Sets the {@code weekDaySchedule} into a {@code WeekDay} that we are building.
     */
    public WeekDayBuilder withWeekDaySchedule(HashMap<String, String> weekDaySchedule) {
        this.weekDaySchedule = weekDaySchedule;
        return this;
    }

    /**
     * Builds a {@code WeekDay} object
     */
    public WeekDay build() {
        WeekDay weekDay = new WeekDay();
        weekDay.setWeekday(weekday);
        weekDay.setRoomName(roomName);
        weekDay.setWeekDaySchedule(weekDaySchedule);
        return weekDay;
    }
}
```
