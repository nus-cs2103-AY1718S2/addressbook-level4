# Damienskt
###### \java\seedu\address\logic\commands\calendar\ViewAppointmentCommandTest.java
``` java
public class ViewAppointmentCommandTest {

    private Model model;

    @Test
    public void execute_validIndexListingAppointments_success() throws DuplicateAppointmentException {
        model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        model.addAppointmentToStorageCalendar(CONCERT);
        model.addAppointmentToStorageCalendar(DENTAL);
        model.setIsListingAppointments(true);
        model.setCurrentlyDisplayedAppointments(model.getStorageCalendar().getAllAppointments());
        ViewAppointmentCommand viewAppointmentCommand = prepareCommand(INDEX_FIRST_APPOINTMENT);

        String displayedLocation = (CONCERT.getLocation() == null)
                ? ViewAppointmentCommand.MESSAGE_NO_LOCATION
                : CONCERT.getLocation();

        String expectedMessage = "Selected appointment details:\n"
                + "Appointment Name: " + CONCERT.getTitle() + "\n"
                + "Start Date: " + CONCERT.getStartDate() + "\n"
                + "Start Time: " + CONCERT.getStartTime() + "\n"
                + "End Date: " + CONCERT.getEndDate() + "\n"
                + "End Time: " + CONCERT.getEndTime() + "\n"
                + "Location: " + displayedLocation + "\n"
                + "Celebrities attending: " + CONCERT.getCelebritiesAttending() + "\n"
                + "Points of Contact: " + CONCERT.getPointsOfContact();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(),
                new UserPrefs());
        expectedModel.addAppointmentToStorageCalendar((new AppointmentBuilder(CONCERT)).build());
        expectedModel.addAppointmentToStorageCalendar((new AppointmentBuilder(DENTAL)).build());
        //still have appointments in the list after deletion, should show appointment list
        expectedModel.setIsListingAppointments(true);

        assertCommandSuccess(viewAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexNotListingAppointments_throwsCommandException() throws DuplicateAppointmentException {
        model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        model.addAppointmentToStorageCalendar(CONCERT);
        model.addAppointmentToStorageCalendar(DENTAL);
        model.setCurrentlyDisplayedAppointments(model.getStorageCalendar().getAllAppointments());
        model.setIsListingAppointments(false);
        ViewAppointmentCommand viewAppointmentCommand = prepareCommand(INDEX_FIRST_APPOINTMENT);

        assertCommandFailure(viewAppointmentCommand,
                model,
                ViewAppointmentCommand.MESSAGE_MUST_SHOW_LIST_OF_APPOINTMENTS);
    }

    @Test
    public void execute_invalidOutOfBoundsIndexListingAppointments_throwsCommandException() {
        model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        model.setIsListingAppointments(true);
        model.setCurrentlyDisplayedAppointments(model.getStorageCalendar().getAllAppointments());
        Index outOfBoundIndex = Index.fromOneBased(model.getAppointmentList().size() + 1);
        ViewAppointmentCommand viewAppointmentCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(viewAppointmentCommand, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        ViewAppointmentCommand viewFirstAppointmentCommand = prepareCommand(INDEX_FIRST_APPOINTMENT);
        ViewAppointmentCommand viewSecondAppointmentCommand = prepareCommand(INDEX_SECOND_APPOINTMENT);

        // same object -> returns true
        assertTrue(viewFirstAppointmentCommand.equals(viewFirstAppointmentCommand));

        // same values -> returns true
        ViewAppointmentCommand viewFirstAppointmentCommandCopy = prepareCommand(INDEX_FIRST_PERSON);
        assertTrue(viewFirstAppointmentCommand.equals(viewFirstAppointmentCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstAppointmentCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstAppointmentCommand.equals(null));

        // different appointment -> returns false
        assertFalse(viewFirstAppointmentCommand.equals(viewSecondAppointmentCommand));
    }

    /**
     * Returns a {@code ViewCommand} with the parameter {@code index}.
     */
    private ViewAppointmentCommand prepareCommand(Index index) {
        ViewAppointmentCommand viewAppointmentCommand = new ViewAppointmentCommand(index.getZeroBased());
        viewAppointmentCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return viewAppointmentCommand;
    }
}
```
###### \java\seedu\address\logic\commands\map\EstimateRouteCommandTest.java
``` java
/**
 * Contains integration tests and unit tests for
 * {@code EstimateRouteCommand}.
 */
public class EstimateRouteCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullAddress_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EstimateRouteCommand(null, null);
    }

    @Test
    public void execute_initialisationOfCommand_success() {
        EstimateRouteCommand estimateRouteCommand = prepareCommand(new MapAddress(VALID_ADDRESS_MAP_BOB),
                new MapAddress(VALID_ADDRESS_MAP_AMY));
        DistanceEstimate estimate = new DistanceEstimate();
        Geocoding geocode = new Geocoding();
        geocode.initialiseLatLngFromAddress(VALID_ADDRESS_MAP_BOB);
        LatLng startLatLng = geocode.getLatLng();
        geocode.initialiseLatLngFromAddress(VALID_ADDRESS_MAP_AMY);
        LatLng endLatLng = geocode.getLatLng();
        estimate.calculateDistanceMatrix(startLatLng, endLatLng, TravelMode.DRIVING);

        assertEquals(estimateRouteCommand.getStartLocation(), new MapAddress(VALID_ADDRESS_MAP_BOB));
        assertEquals(estimateRouteCommand.getEndLocation(), new MapAddress(VALID_ADDRESS_MAP_AMY));
        assertEquals(estimateRouteCommand.getDistOfTravel(), estimate.getDistBetweenOriginDest());
        assertEquals(estimateRouteCommand.getTimeOfTravel(), estimate.getTravelTime());
    }

    @Test
    public void equals() {

        EstimateRouteCommand estimateRouteFirstCommand = prepareCommand(new MapAddress(VALID_ADDRESS_MAP_BOB),
                new MapAddress(VALID_ADDRESS_MAP_AMY));
        EstimateRouteCommand estimateRouteSecondCommand = prepareCommand(new MapAddress("Bedok"),
                new MapAddress("NUS"));

        // same object -> returns true
        assertTrue(estimateRouteFirstCommand.equals(estimateRouteFirstCommand));

        // same start and end address -> returns true
        EstimateRouteCommand estimateRouteFirstCommandCopy = prepareCommand(new MapAddress(VALID_ADDRESS_MAP_BOB),
                new MapAddress(VALID_ADDRESS_MAP_AMY));
        assertTrue(estimateRouteFirstCommand.equals(estimateRouteFirstCommandCopy));

        // different types -> returns false
        assertFalse(estimateRouteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(estimateRouteFirstCommand.equals(null));

        // different start and end address -> returns false
        assertFalse(estimateRouteFirstCommand.equals(estimateRouteSecondCommand));
    }

    /**
     * Returns a {@code estimateRouteCommand} with the parameter {@code start} and {@code end}.
     */
    private EstimateRouteCommand prepareCommand(MapAddress start, MapAddress end) {
        EstimateRouteCommand estimateRouteCommand = new EstimateRouteCommand(start, end);
        return estimateRouteCommand;
    }
}
```
###### \java\seedu\address\logic\commands\map\ShowLocationCommandTest.java
``` java
/**
 * Contains integration tests and unit tests for
 * {@code ShowLocationCommand}.
 */
public class ShowLocationCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullAddress_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ShowLocationCommand(null);
    }

    @Test
    public void execute_initialisationOfCommand_success() {
        ShowLocationCommand showLocationCommand = prepareCommand(new MapAddress(VALID_ADDRESS_MAP_BOB));
        assertEquals(showLocationCommand.getLocation(), new MapAddress(VALID_ADDRESS_MAP_BOB));
    }

    @Test
    public void equals() {
        ShowLocationCommand showLocationFirstCommand = prepareCommand(new MapAddress(VALID_ADDRESS_MAP_BOB));

        // same object -> returns true
        assertTrue(showLocationFirstCommand.equals(showLocationFirstCommand));

        // same map address -> returns true
        ShowLocationCommand showLocationFirstCommandCopy = prepareCommand(new MapAddress(VALID_ADDRESS_MAP_BOB));
        assertTrue(showLocationFirstCommand.equals(showLocationFirstCommandCopy));

        // different types -> returns false
        assertFalse(showLocationFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showLocationFirstCommand.equals(null));

        // different map address -> returns false
        ShowLocationCommand showLocationSecondCommand = prepareCommand(new MapAddress(VALID_ADDRESS_MAP_AMY));
        assertFalse(showLocationFirstCommand.equals(showLocationSecondCommand));
    }

    /**
     * Returns a {@code showLocationCommand} with the parameter {@code address}.
     */
    private ShowLocationCommand prepareCommand(MapAddress address) {
        ShowLocationCommand showLocationCommand = new ShowLocationCommand(address);
        return showLocationCommand;
    }
}
```
###### \java\seedu\address\logic\map\DistanceEstimateTest.java
``` java
public class DistanceEstimateTest {

    private DistanceEstimate test;
    private LatLng start;
    private LatLng end;

    @Test
    public void isValidStartAndEndAddress() {

        test = new DistanceEstimate();

        Geocoding convertToLatLng = new Geocoding();

        //Initialise start location
        convertToLatLng.initialiseLatLngFromAddress("Hollywood");
        start = convertToLatLng.getLatLng();

        //Initialise end location
        convertToLatLng.initialiseLatLngFromAddress("NUS");
        end = convertToLatLng.getLatLng();

        // null start, end addresses and mode of travel
        Assert.assertThrows(NullPointerException.class, () -> test.calculateDistanceMatrix
                (null, null, null));

        // Start and End cannot be reached by driving
        test.calculateDistanceMatrix(start, end, TravelMode.DRIVING);
        assertEquals(test.getTravelTime(), "null");

        // valid start and end addresses
        convertToLatLng.initialiseLatLngFromAddress("820297");
        start = convertToLatLng.getLatLng();
        test.calculateDistanceMatrix(start, end, TravelMode.DRIVING);
        assertNotEquals(test.getTravelTime(), "null"); // long address

        // Invalid start LatLng and valid end LatLng
        start = new LatLng(-1, -1);
        test.calculateDistanceMatrix(start, end, TravelMode.DRIVING);
        assertEquals(test.getTravelTime(), "null"); // long address

        // Valid start LatLng and Invalid end LatLng
        end = new LatLng(-1, -1);
        test.calculateDistanceMatrix(start, end, TravelMode.DRIVING);
        assertEquals(test.getTravelTime(), "null"); // long address

        // Invalid start LatLng and invalid end LatLng
        start = new LatLng(-10, -10);
        end = new LatLng(-1, -1);
        test.calculateDistanceMatrix(start, end, TravelMode.DRIVING);
        assertEquals(test.getTravelTime(), "null"); // long address
    }
}
```
###### \java\seedu\address\logic\map\GeocodingTest.java
``` java
public class GeocodingTest {

    private Geocoding test;

    @Test
    public void isValidGeocode() {
        test = new Geocoding();

        //Invalid address to geocode conversion
        Assert.assertThrows(NullPointerException.class, () -> test.checkIfAddressCanBeFound(null));

        // invalid addresses
        assertFalse(test.checkIfAddressCanBeFound("")); // empty string
        assertFalse(test.checkIfAddressCanBeFound(" ")); // spaces only
        assertFalse(test.checkIfAddressCanBeFound("!!!!!!!")); // location not found in google server

        // valid addresses
        assertTrue(test.checkIfAddressCanBeFound("Kent ridge road"));
        assertTrue(test.checkIfAddressCanBeFound("820297")); // postal code
        assertTrue(test.checkIfAddressCanBeFound("National University Of Singapore")); // long address
        assertTrue(test.checkIfAddressCanBeFound("NUS")); // alias of location
    }
}
```
###### \java\seedu\address\logic\map\GoogleWebServicesTest.java
``` java
public class GoogleWebServicesTest {

    private GoogleWebServices test;

    @Test
    public void isValidConnection() {

        test = new GoogleWebServices();

        //Check valid connection
        assertTrue(test.checkInitialisedConnection());
    }
}
```
###### \java\seedu\address\logic\parser\calendar\ViewAppointmentCommandParserTest.java
``` java
public class ViewAppointmentCommandParserTest {

    private ViewAppointmentCommandParser parser = new ViewAppointmentCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteAppointmentCommand() {
        assertParseSuccess(parser, "1", new ViewAppointmentCommand(INDEX_FIRST_APPOINTMENT.getZeroBased()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "b",
                String.format(ParserUtil.MESSAGE_INVALID_INDEX));
    }
}
```
###### \java\seedu\address\logic\parser\map\EstimateRouteCommandParserTest.java
``` java
public class EstimateRouteCommandParserTest {
    private EstimateRouteCommandParser parser = new EstimateRouteCommandParser();

    @Test
    public void parse_validArgs_returnsEstimateRouteCommand() {
        MapAddress startAddress = new MapAddress(VALID_ADDRESS_MAP_BOB);
        MapAddress endAddress = new MapAddress(VALID_ADDRESS_MAP_AMY);
        assertParseSuccess(parser, " " + PREFIX_START_MAP_ADDRESS + VALID_ADDRESS_MAP_BOB + " "
                + PREFIX_END_MAP_ADDRESS + VALID_ADDRESS_MAP_AMY, new EstimateRouteCommand(startAddress, endAddress));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EstimateRouteCommand.MESSAGE_USAGE));
    }

}
```
###### \java\seedu\address\logic\parser\map\ShowLocationCommandParserTest.java
``` java
public class ShowLocationCommandParserTest {

    private ShowLocationCommandParser parser = new ShowLocationCommandParser();

    @Test
    public void parse_validArgs_returnsShowLocationCommand() {
        MapAddress address = new MapAddress(VALID_ADDRESS_MAP_BOB);
        assertParseSuccess(parser, " " + PREFIX_MAP_ADDRESS + VALID_ADDRESS_MAP_BOB,
                new ShowLocationCommand(address));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowLocationCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\map\MapAddressTest.java
``` java
public class MapAddressTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MapAddress(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MapAddress(invalidAddress));
    }

    @Test
    public void isValidAddress() {

        // null address
        Assert.assertThrows(NullPointerException.class, () -> MapAddress.isValidAddress(null));

        // invalid addresses
        assertFalse(MapAddress.isValidAddress("")); // empty string
        assertFalse(MapAddress.isValidAddress(" ")); // spaces only
        assertFalse(MapAddress.isValidAddress("!!!!!!!")); // location not found in google server

        // valid addresses
        assertTrue(MapAddress.isValidAddress("Kent ridge road"));
        assertTrue(MapAddress.isValidAddress("820297")); // postal code
        assertTrue(MapAddress.isValidAddress("National University Of Singapore")); // long address
    }
}
```
###### \java\systemtests\calendar\ViewAppointmentCommandSystemTest.java
``` java
public class ViewAppointmentCommandSystemTest extends AddressBookSystemTest {

    private List<Celebrity> celebrityList = new ArrayList<>();
    private List<Index> celebrityIndices = new ArrayList<>();
    private List<Person> pointOfContactList = new ArrayList<>();
    private List<Index> pointOfContactIndices = new ArrayList<>();

    @Test
    public void viewAppointment() {
        /**
         * Pre-populate application with appointments
         * Appointment without location, celebrities and points of contact
         */
        String command = AddAppointmentCommand.COMMAND_WORD + APPT_NAME_DESC_GRAMMY + APPT_END_DATE_DESC_GRAMMY
                + APPT_END_TIME_DESC_GRAMMY + APPT_START_DATE_DESC_GRAMMY
                + APPT_START_TIME_DESC_GRAMMY;
        executeCommand(command);

        /* Appointment with location, celebrities and points of contact */
        pointOfContactList.add(BENSON);
        celebrityList.add(JAY);
        celebrityIndices.addAll(getCelebrityIndices(this.getModel(), celebrityList));
        pointOfContactIndices.addAll(getPersonIndices(this.getModel(), pointOfContactList));
        command = AddAppointmentCommand.COMMAND_WORD + APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_OSCAR
                + APPT_END_DATE_DESC_OSCAR + APPT_END_TIME_DESC_OSCAR + APPT_START_DATE_DESC_OSCAR
                + APPT_START_TIME_DESC_OSCAR + generatePointOfContactandCelebrityFields(celebrityIndices,
                pointOfContactIndices);
        executeCommand(command);

        /* ---------------------------- Perform invalid viewAppointment operations --------------------------------- */
        assertCommandFailure(ViewAppointmentCommand.COMMAND_WORD + " " + 0, ParserUtil.MESSAGE_INVALID_INDEX);
        assertCommandFailure(ViewAppointmentCommand.COMMAND_WORD + " ads" , ParserUtil.MESSAGE_INVALID_INDEX);
        assertCommandFailure(ViewAppointmentCommand.COMMAND_WORD + " ", ParserUtil.MESSAGE_INVALID_INDEX);
        assertCommandFailure(ViewAppointmentCommand.COMMAND_WORD + " "
                + 2 , ViewAppointmentCommand.MESSAGE_MUST_SHOW_LIST_OF_APPOINTMENTS);

        executeCommand("listAppointment"); // Executes listAppointment to fulfil pre-requisite
        assertCommandFailure(ViewAppointmentCommand.COMMAND_WORD + " "
                + 10, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);

        /* ------------------------------ Perform valid viewAppointment operations --------------------------------- */
        assertCommandSuccess(1); //viewing appointment without location, celebrities and points of contact
        assertCommandSuccess(2); //viewing appointment with location, celebrities and points of contact
    }

    /**
     * Executes the {@code ViewAppointmentCommand} that asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code ViewAppointmentCommand}.<br>
     * 4. Shows the location marker of appointment location in Maps GUI.<br>
     * 5. Calendar panel and selected card remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(int index) {
        assertCommandSuccess(ViewAppointmentCommand.COMMAND_WORD + " " + index, index);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(int)}. Executes {@code command}
     * instead.
     * @see ViewAppointmentCommandSystemTest#assertCommandSuccess(int)
     */
    private void assertCommandSuccess(String command, int index) {
        Model expectedModel = getModel();
        Appointment selected;
        String location;
        if (index == 2) {
            selected = GRAMMY;
            location = ViewAppointmentCommand.MESSAGE_NO_LOCATION;
        } else {
            selected = OSCAR;
            selected.updateEntries(celebrityList, pointOfContactList);
            location = selected.getMapAddress().toString();
        }
        String expectedResultMessage = "Selected appointment details:\n"
                + "Appointment Name: " + selected.getTitle() + "\n"
                + "Start Date: " + selected.getStartDate() + "\n"
                + "Start Time: " + selected.getStartTime() + "\n"
                + "End Date: " + selected.getEndDate() + "\n"
                + "End Time: " + selected.getEndTime() + "\n"
                + "Location: " + location + "\n"
                + "Celebrities attending: " + selected.getCelebritiesAttending() + "\n"
                + "Points of Contact: " + selected.getPointsOfContact();

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String,int)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see ViewAppointmentCommandSystemTest#assertCommandSuccess(String,int)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertResultDisplayAndCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Generates command string for a list of celebrities and POCs for use with add Appointment command
     */
    private String generatePointOfContactandCelebrityFields(List<Index> celebrityIndices,
                                                            List<Index> pointOfContactIndices) {
        return " " + generateCelebrityFields(celebrityIndices) + " "
                + generatePointOfContactFields(pointOfContactIndices);
    }

    /**
     * Generates a command string for a list of celebrity indices for add Appointment command
     */
    private String generateCelebrityFields(List<Index> celebrityIndices) {
        StringBuilder sb =  new StringBuilder();
        for (Index i : celebrityIndices) {
            sb.append(PREFIX_CELEBRITY).append(i.getOneBased() + " ");
        }
        return sb.toString();
    }

    /**
     * Generates a command string for a list of POC indices for add Appointment command
     */
    private String generatePointOfContactFields(List<Index> pointOfContactIndices) {
        StringBuilder sb =  new StringBuilder();
        for (Index i : pointOfContactIndices) {
            sb.append(PREFIX_POINT_OF_CONTACT).append(i.getOneBased() + " ");
        }
        return sb.toString();
    }
}
```
###### \java\systemtests\map\EstimateRouteCommandSystemTest.java
``` java
public class EstimateRouteCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void estimateRoute() {
        Model model = getModel();

        /* ------------------------------ Perform valid estimateRoute operations --------------------------------- */

        /* Case: shows best estimated time and distance of travel between two locations
         *  -> Information shown in result display
         */
        MapAddress startAddress = new MapAddress(VALID_ADDRESS_MAP_BOB);
        MapAddress endAddress = new MapAddress(VALID_ADDRESS_MAP_AMY);
        assertCommandSuccess(startAddress, endAddress);

        /* Case: shows best estimated time and distance of travel between two locations using postal code
         * -> Information shown in result display
         */
        startAddress = new MapAddress("820296");
        endAddress = new MapAddress("119077");
        assertCommandSuccess(startAddress, endAddress);

        /* Case: shows best estimated time and distance of travel between two locations using location name
         * -> Information shown in result display
         */
        startAddress = new MapAddress("National University of Singapore");
        endAddress = new MapAddress("Punggol");
        assertCommandSuccess(startAddress, endAddress);

        /* ------------------------------ Perform invalid estimateRoute operations --------------------------------- */

        /* Case: missing MapAddress and prefix-> rejected */
        String command = EstimateRouteCommand.COMMAND_WORD + "";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EstimateRouteCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "estimateroute " + PREFIX_START_MAP_ADDRESS + VALID_ADDRESS_MAP_BOB
                + PREFIX_START_MAP_ADDRESS + VALID_ADDRESS_MAP_AMY;
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: correct prefix but missing MapAddress -> rejected */
        command = EstimateRouteCommand.COMMAND_WORD + " " + PREFIX_START_MAP_ADDRESS + " " + PREFIX_END_MAP_ADDRESS;
        assertCommandFailure(command, MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS);

        /* Case: correct prefix but invalid MapAddress -> rejected */
        command = EstimateRouteCommand.COMMAND_WORD + " " + PREFIX_START_MAP_ADDRESS + "))))))" + " "
                + PREFIX_END_MAP_ADDRESS + "^^^^^^^";
        assertCommandFailure(command, MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS);

        /* Case: missing MapAddress prefix -> rejected */
        command = EstimateRouteCommand.COMMAND_WORD + " " + VALID_ADDRESS_MAP_BOB + " " + VALID_ADDRESS_MAP_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EstimateRouteCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the {@code EstimateRouteCommand} that asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code EstimateRouteCommand}.<br>
     * 4. Shows the distance and time of travel in result display.<br>
     * 5. Calendar panel and selected card remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(MapAddress startAddress, MapAddress endAddress) {
        assertCommandSuccess(EstimateRouteCommand.COMMAND_WORD + " " + PREFIX_START_MAP_ADDRESS
                + startAddress.toString() + " " + PREFIX_END_MAP_ADDRESS + endAddress.toString());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(MapAddress, MapAddress)}. Executes {@code command}
     * instead.
     * @see EstimateRouteCommandSystemTest#assertCommandSuccess(MapAddress, MapAddress)
     */
    private void assertCommandSuccess(String command) {
        Model expectedModel = getModel();
        String expectedResultMessage = EstimateRouteCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see EstimateRouteCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedResultMessage = expectedResultMessage + EstimateRouteCommand.getStringOfDistanceAndTime();
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertResultDisplayAndCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\map\ShowLocationCommandSystemTest.java
``` java
public class ShowLocationCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void showLocation() {

        /* ------------------------------ Perform valid showLocation operations --------------------------------- */

        /* Case: show location using address (block and street name) of a place
         * -> location marker shown in map
         */
        MapAddress newAddress = new MapAddress(VALID_ADDRESS_MAP_BOB);
        assertCommandSuccess(newAddress);

        /* Case: show location using postal code of a place
         * -> location marker shown in map
         */
        newAddress = new MapAddress("820296");
        assertCommandSuccess(newAddress);

        /* Case: show location using name of a place (e.g National University of Singapore)
         * -> location marker shown in map
         */
        newAddress = new MapAddress("National University of Singapore");
        assertCommandSuccess(newAddress);

        /* ------------------------------- Perform invalid showLocation operations --------------------------------- */

        /* Case: missing MapAddress and prefix-> rejected */
        String command = ShowLocationCommand.COMMAND_WORD + "";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowLocationCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "showslocation " + VALID_ADDRESS_MAP_BOB;
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: correct prefix but missing MapAddress -> rejected */
        command = ShowLocationCommand.COMMAND_WORD + INVALID_ADDRESS_MAP_DESC2;
        assertCommandFailure(command, MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS);

        /* Case: correct prefix but invalid MapAddress -> rejected */
        command = ShowLocationCommand.COMMAND_WORD + INVALID_ADDRESS_MAP_DESC1;
        assertCommandFailure(command, MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS);

        /* Case: missing MapAddress prefix -> rejected */
        command = ShowLocationCommand.COMMAND_WORD + " " + VALID_ADDRESS_MAP_BOB;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowLocationCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the {@code ShowLocationCommand} that asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code ShowLocationCommand}.<br>
     * 4. Shows the location marker of {@code address} in Maps GUI.<br>
     * 5. Calendar panel and selected card remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(MapAddress address) {
        assertCommandSuccess(ShowLocationCommand.COMMAND_WORD + " " + PREFIX_MAP_ADDRESS + address.toString());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(MapAddress)}. Executes {@code command}
     * instead.
     * @see ShowLocationCommandSystemTest#assertCommandSuccess(MapAddress)
     */
    private void assertCommandSuccess(String command) {
        Model expectedModel = getModel();
        String expectedResultMessage = ShowLocationCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see ShowLocationCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertResultDisplayAndCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
