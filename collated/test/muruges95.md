# muruges95
###### \java\seedu\address\logic\commands\calendar\AddAppointmentCommandTest.java
``` java

public class AddAppointmentCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Set<Index> emptyCelebrityIndices = new HashSet<>();
    private Set<Index> emptyPointOfContactIndices = new HashSet<>();


    @Test
    public void constructor_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAppointmentCommand(null, emptyCelebrityIndices, emptyPointOfContactIndices);
    }

    @Test
    public void execute_appointmentAcceptedByModel_addSuccessful() throws CommandException {
        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();
        Appointment validAppointment = new AppointmentBuilder().build();

        CommandResult commandResult = getAddAppointmentCommandForAppointment(validAppointment, modelStub).execute();
        assertEquals(String.format(AddAppointmentCommand.MESSAGE_SUCCESS, validAppointment.getTitle()),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAppointment), modelStub.appointmentsAdded);
    }

    @Test
    public void execute_addAppointmentWhileNotInCombinedCalendarView_throwsCommandException() throws CommandException {
        ModelStub modelStub = new ModelStubThrowingNotInCombinedCalendarViewException();
        Appointment validAppointment = new AppointmentBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(AddAppointmentCommand.MESSAGE_NOT_IN_COMBINED_CALENDAR,
                modelStub.getCurrentCelebCalendarOwner().getName().toString()));

        getAddAppointmentCommandForAppointment(validAppointment, modelStub).execute();
    }

    @Test
    public void equals() {
        Appointment dentistAppointment = new AppointmentBuilder().withName("Dentist Appointment").build();
        Appointment doctorAppointment = new AppointmentBuilder().withName("Doctor Appointment").build();
        AddAppointmentCommand addDentistApptCommand = new AddAppointmentCommand(dentistAppointment,
                emptyCelebrityIndices, emptyPointOfContactIndices);
        AddAppointmentCommand addDoctorApptCommand = new AddAppointmentCommand(doctorAppointment,
                emptyCelebrityIndices, emptyPointOfContactIndices);

        // same object -> return true
        assertTrue(addDentistApptCommand.equals(addDentistApptCommand));

        // same values -> return true
        AddAppointmentCommand addDentistApptCommandCopy = new AddAppointmentCommand(dentistAppointment,
                emptyCelebrityIndices, emptyPointOfContactIndices);
        assertTrue(addDentistApptCommand.equals(addDentistApptCommandCopy));

        // different types -> returns false
        assertFalse(addDentistApptCommand.equals(1));

        // null -> returns false
        assertFalse(addDentistApptCommand.equals(null));

        // different appointment -> returns false
        assertFalse(addDentistApptCommand.equals(addDoctorApptCommand));
    }

    /**
     * Generates a new AddAppointmentCommand with the details of the given appointment.
     */
    private AddAppointmentCommand getAddAppointmentCommandForAppointment(Appointment appointment, Model model) {
        AddAppointmentCommand command = new AddAppointmentCommand(appointment, emptyCelebrityIndices,
                emptyPointOfContactIndices);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    private class ModelStubThrowingNotInCombinedCalendarViewException extends ModelStub {

        @Override
        public Celebrity getCurrentCelebCalendarOwner() {
            return TypicalCelebrities.AYANE;
        }
    }

    /**
     * A Model stub that always accept the appointment being added.
     */
    private class ModelStubAcceptingAppointmentAdded extends ModelStub {
        final ArrayList<Appointment> appointmentsAdded = new ArrayList<>();

        @Override
        public void addAppointmentToStorageCalendar(Appointment appt) {
            requireNonNull(appt);
            appointmentsAdded.add(appt);
        }

        @Override
        public Celebrity getCurrentCelebCalendarOwner() {
            return null;
        }

        @Override
        public List<Celebrity> getCelebritiesChosen(Set<Index> celebrityIndices) {
            return new ArrayList<>();
        }

        @Override
        public List<Person> getPointsOfContactChosen(Set<Index> pocIndices) {
            return new ArrayList<>();
        }

        @Override
        public void setBaseDate(LocalDate startDate) {

        }

        @Override
        public void setCelebCalendarViewPage(String page) {

        }

        @Override
        public void setIsListingAppointments(boolean isListing) {

        }

        @Override
        public boolean getIsListingAppointments() {
            return false;
        }
    }
}
```
###### \java\seedu\address\logic\commands\calendar\EditAppointmentCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditAppointmentCommand.
 */
public class EditAppointmentCommandTest {

    private Model model;

    @Test
    public void execute_allFieldsSpecifiedListingAppointments_success() throws DuplicateAppointmentException {
        prepareModel(CONCERT);

        Appointment editedAppointment = new AppointmentBuilder().withLocation("Clementi Road").build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = prepareCommand(INDEX_FIRST_APPOINTMENT, descriptor);
        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_SUCCESS, editedAppointment.getTitle());
        Model expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        expectedModel.addAppointmentToStorageCalendar(editedAppointment);
        expectedModel.setIsListingAppointments(false);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_someFieldsSpecifiedListingAppointments_success() throws DuplicateAppointmentException {
        prepareModel(CONCERT);

        Appointment editedAppointment = new AppointmentBuilder(CONCERT)
                .withName("New Concert").withStartTime("15:00").withEndTime("16:00").build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withName("New Concert").withStartTime("15:00").withEndTime("16:00").build();
        EditAppointmentCommand editAppointmentCommand = prepareCommand(INDEX_FIRST_APPOINTMENT, descriptor);
        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_SUCCESS, editedAppointment.getTitle());
        Model expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        expectedModel.addAppointmentToStorageCalendar(editedAppointment);
        expectedModel.setIsListingAppointments(false);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldsSpecifiedListingAppointments_throwsCommandException()
            throws DuplicateAppointmentException {
        prepareModel(CONCERT);

        EditAppointmentCommand editAppointmentCommand = prepareCommand(INDEX_FIRST_APPOINTMENT,
                new EditAppointmentDescriptor());
        assertCommandFailure(editAppointmentCommand, model, EditAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT);
    }

    @Test
    public void execute_validIndexNotListingAppointments_throwsCommandException() throws DuplicateAppointmentException {
        prepareModel(CONCERT);
        model.setIsListingAppointments(false);

        Appointment editedAppointment = new AppointmentBuilder().build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = prepareCommand(INDEX_FIRST_APPOINTMENT, descriptor);

        assertCommandFailure(editAppointmentCommand,
                model, Messages.MESSAGE_MUST_SHOW_LIST_OF_APPOINTMENTS);
    }

    @Test
    public void execute_invalidIndexListingAppointments_throwsCommandException() throws DuplicateAppointmentException {
        prepareModel(CONCERT);

        Index outOfBoundIndex = Index.fromOneBased(model.getAppointmentList().size() + 1);
        Appointment editedAppointment = new AppointmentBuilder().build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editAppointmentCommand, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditAppointmentCommand standardCommand = prepareCommand(INDEX_FIRST_APPOINTMENT, DESC_OSCAR);

        // same values -> returns true
        EditAppointmentDescriptor copyDescriptor = new EditAppointmentDescriptor(DESC_OSCAR);
        EditAppointmentCommand commandWithSameValues = prepareCommand(INDEX_FIRST_APPOINTMENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditAppointmentCommand(INDEX_SECOND_APPOINTMENT, DESC_OSCAR)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, DESC_GRAMMY)));
    }

    /**
     * Returns an {@code EditAppointmentCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditAppointmentCommand prepareCommand(Index index, EditAppointmentDescriptor descriptor) {
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(index, descriptor);
        editAppointmentCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editAppointmentCommand;
    }

    /**
     * Add appointment to storageCalendar Model and change isListingAppointment attribute of model
     */
    private void prepareModel(Appointment appt) throws DuplicateAppointmentException {
        model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        model.addAppointmentToStorageCalendar(appt);
        model.setIsListingAppointments(true);
        model.setCurrentlyDisplayedAppointments(model.getStoredAppointmentList());
    }
}
```
###### \java\seedu\address\logic\parser\calendar\AddAppointmentCommandParserTest.java
``` java
public class AddAppointmentCommandParserTest {
    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();
    private Set<Index> emptyIndexSet = new HashSet<>();

    @Test
    public void parse_allFieldsPresent_success() throws IllegalValueException {
        Appointment expectedAppointment = new AppointmentBuilder().withName(VALID_APPOINTMENT_NAME_OSCAR)
                .withLocation(VALID_APPOINTMENT_LOCATION_OSCAR).withStartTime(VALID_START_TIME_OSCAR)
                .withStartDate(VALID_START_DATE_OSCAR).withEndTime(VALID_END_TIME_OSCAR)
                .withEndDate(VALID_END_DATE_OSCAR).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_OSCAR
                        + APPT_START_TIME_DESC_OSCAR + APPT_START_DATE_DESC_OSCAR
                        + APPT_END_TIME_DESC_OSCAR + APPT_END_DATE_DESC_OSCAR,
                new AddAppointmentCommand(expectedAppointment, emptyIndexSet, emptyIndexSet));

        // multiple names - last name accepted
        assertParseSuccess(parser, APPT_NAME_DESC_GRAMMY + APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_OSCAR
                        + APPT_START_TIME_DESC_OSCAR + APPT_START_DATE_DESC_OSCAR
                        + APPT_END_TIME_DESC_OSCAR + APPT_END_DATE_DESC_OSCAR,
                new AddAppointmentCommand(expectedAppointment, emptyIndexSet, emptyIndexSet));

        // multiple locations - last location accepted
        assertParseSuccess(parser, APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_GRAMMY + APPT_LOCATION_DESC_OSCAR
                        + APPT_START_TIME_DESC_OSCAR + APPT_START_DATE_DESC_OSCAR
                        + APPT_END_TIME_DESC_OSCAR + APPT_END_DATE_DESC_OSCAR,
                new AddAppointmentCommand(expectedAppointment, emptyIndexSet, emptyIndexSet));

        // multiple start times - last start times accepted
        assertParseSuccess(parser, APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_OSCAR
                        + APPT_START_TIME_DESC_GRAMMY + APPT_START_TIME_DESC_OSCAR + APPT_START_DATE_DESC_OSCAR
                        + APPT_END_TIME_DESC_OSCAR + APPT_END_DATE_DESC_OSCAR,
                new AddAppointmentCommand(expectedAppointment, emptyIndexSet, emptyIndexSet));

        // multiple start dates - last start date accepted
        assertParseSuccess(parser, APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_OSCAR + APPT_START_TIME_DESC_OSCAR
                        + APPT_START_DATE_DESC_GRAMMY + APPT_START_DATE_DESC_OSCAR
                        + APPT_END_TIME_DESC_OSCAR + APPT_END_DATE_DESC_OSCAR,
                new AddAppointmentCommand(expectedAppointment, emptyIndexSet, emptyIndexSet));

        // multiple end times - last end time accepted
        assertParseSuccess(parser, APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_OSCAR
                        + APPT_END_TIME_DESC_GRAMMY + APPT_START_TIME_DESC_OSCAR + APPT_START_DATE_DESC_OSCAR
                        + APPT_END_TIME_DESC_OSCAR + APPT_END_DATE_DESC_OSCAR,
                new AddAppointmentCommand(expectedAppointment, emptyIndexSet, emptyIndexSet));

        // multiple end dates - last end date accepted
        assertParseSuccess(parser, APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_OSCAR + APPT_START_TIME_DESC_OSCAR
                        + APPT_END_DATE_DESC_GRAMMY + APPT_START_DATE_DESC_OSCAR
                        + APPT_END_TIME_DESC_OSCAR + APPT_END_DATE_DESC_OSCAR,
                new AddAppointmentCommand(expectedAppointment, emptyIndexSet, emptyIndexSet));
    }

    @Test
    public void parse_locationFieldMissing_success() {
        Appointment expectedAppointment = new AppointmentBuilder().withName(VALID_APPOINTMENT_NAME_OSCAR)
                .withStartTime(VALID_START_TIME_OSCAR).withStartDate(VALID_START_DATE_OSCAR)
                .withEndTime(VALID_END_TIME_OSCAR).withEndDate(VALID_END_DATE_OSCAR).build();
        assertParseSuccess(parser, APPT_NAME_DESC_OSCAR + APPT_START_TIME_DESC_OSCAR
                        + APPT_START_DATE_DESC_OSCAR + APPT_END_TIME_DESC_OSCAR + APPT_END_DATE_DESC_OSCAR,
                new AddAppointmentCommand(expectedAppointment, emptyIndexSet, emptyIndexSet));
    }

    @Test
    public void parse_startDateFieldMissing_success() {
        Appointment expectedAppointment = new AppointmentBuilder().withName(VALID_APPOINTMENT_NAME_OSCAR)
                .withStartTime(VALID_START_TIME_OSCAR).withEndTime(VALID_END_TIME_OSCAR)
                .withEndDate(VALID_END_DATE_OSCAR).build();
        assertParseSuccess(parser, APPT_NAME_DESC_OSCAR + APPT_START_TIME_DESC_OSCAR + APPT_END_TIME_DESC_OSCAR
                        + APPT_END_DATE_DESC_OSCAR,
                new AddAppointmentCommand(expectedAppointment, emptyIndexSet, emptyIndexSet));
    }

    @Test
    public void parse_endDateFieldMissing_success() {
        Appointment expectedAppointment = new AppointmentBuilder().withName(VALID_APPOINTMENT_NAME_OSCAR)
                .withStartTime(VALID_START_TIME_OSCAR).withEndTime(VALID_END_TIME_OSCAR)
                .withStartDate(VALID_START_DATE_OSCAR).build();
        assertParseSuccess(parser, APPT_NAME_DESC_OSCAR + APPT_END_TIME_DESC_OSCAR + APPT_START_TIME_DESC_OSCAR
                        + APPT_START_DATE_DESC_OSCAR,
                new AddAppointmentCommand(expectedAppointment, emptyIndexSet, emptyIndexSet));
    }

    @Test
    public void parseEndTimeAndEndDateFieldMissing_success() {
        Appointment expectedAppointment = new AppointmentBuilder().withName(VALID_APPOINTMENT_NAME_OSCAR)
                .withStartTime(VALID_START_TIME_OSCAR).withStartDate(VALID_START_DATE_OSCAR).build();
        assertParseSuccess(parser, APPT_NAME_DESC_OSCAR + APPT_START_TIME_DESC_OSCAR
                + APPT_START_DATE_DESC_OSCAR, new AddAppointmentCommand(expectedAppointment, emptyIndexSet,
                emptyIndexSet));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);
        // missing name prefix
        assertParseFailure(parser, VALID_APPOINTMENT_NAME_OSCAR + APPT_LOCATION_DESC_OSCAR
                + APPT_START_TIME_DESC_OSCAR + APPT_START_DATE_DESC_OSCAR, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_APPT_NAME_DESC + APPT_LOCATION_DESC_OSCAR
                + APPT_START_TIME_DESC_OSCAR + APPT_START_DATE_DESC_OSCAR, Appointment.MESSAGE_NAME_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, APPT_NAME_DESC_OSCAR + INVALID_APPT_LOCATION_DESC
                + APPT_START_DATE_DESC_OSCAR + APPT_START_DATE_DESC_OSCAR, MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS);


        // invalid start time
        assertParseFailure(parser, APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_OSCAR
                + INVALID_START_TIME + APPT_START_DATE_DESC_OSCAR, Appointment.MESSAGE_TIME_CONSTRAINTS);

        // invalid start date
        assertParseFailure(parser, APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_OSCAR
                + APPT_START_TIME_DESC_OSCAR + INVALID_START_DATE, Appointment.MESSAGE_DATE_CONSTRAINTS);


        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_APPT_NAME_DESC + APPT_LOCATION_DESC_OSCAR
                + APPT_START_TIME_DESC_OSCAR + INVALID_START_DATE, Appointment.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_OSCAR
                + APPT_START_TIME_DESC_OSCAR + APPT_START_DATE_DESC_OSCAR,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\testutil\AppointmentBuilder.java
``` java

/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {

    public static final String DEFAULT_NAME = "Oscars 2018";
    public static final MapAddress DEFAULT_LOCATION = null;
    public static final LocalTime DEFAULT_START_TIME = LocalTime.now();
    public static final LocalDate DEFAULT_START_DATE = LocalDate.now();
    public static final LocalTime DEFAULT_END_TIME = LocalTime.now().plusMinutes(15);
    public static final LocalDate DEFAULT_END_DATE = LocalDate.now();

    private String name;
    private MapAddress location;
    private LocalTime startTime;
    private LocalDate startDate;
    private LocalTime endTime;
    private LocalDate endDate;

    public AppointmentBuilder() {
        name = DEFAULT_NAME;
        location = DEFAULT_LOCATION;
        startTime = DEFAULT_START_TIME;
        startDate = DEFAULT_START_DATE;
        endDate = DEFAULT_END_DATE;
        endTime = DEFAULT_END_TIME;
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code apptToCopy}
     */
    public AppointmentBuilder(Appointment apptToCopy) {
        name = apptToCopy.getTitle();
        location = apptToCopy.getMapAddress();
        startTime = apptToCopy.getStartTime();
        startDate = apptToCopy.getStartDate();
        endTime = apptToCopy.getEndTime();
        endDate = apptToCopy.getEndDate();
    }

    /**
     * Sets the {@code name} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code location} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withLocation(String location) {
        try {
            this.location = ParserUtil.parseMapAddress(location);
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("map address not valid.");
        }
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withStartTime(String startTime) {
        this.startTime = LocalTime.parse(startTime, Appointment.TIME_FORMAT);
        this.endTime = this.startTime.plusMinutes(15);
        return this;
    }

    /**
     * Sets the {@code startDate} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate, Appointment.DATE_FORMAT);
        this.endDate = this.startDate;
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withEndTime(String endTime) {
        this.endTime = LocalTime.parse(endTime, Appointment.TIME_FORMAT);
        return this;
    }

    /**
     * Sets the {@code endDate} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate, Appointment.DATE_FORMAT);
        return this;
    }

    public Appointment build() {
        return new Appointment(name, startTime, startDate, location, endTime, endDate);
    }
}
```
###### \java\seedu\address\testutil\AppointmentUtil.java
``` java

/**
 * A util class for Appointments
 */
public class AppointmentUtil {

    /**
     * Returns an add command string for adding the {@code appointment}.
     */
    public static String getAddAppointmentCommand(Appointment appointment) {
        return AddAppointmentCommand.COMMAND_WORD + " " + getAppointmentDetails(appointment);
    }

    /**
     * Returns the part of command string for the given {@code appointment}'s details.
     */
    public static String getAppointmentDetails(Appointment appointment) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + appointment.getTitle() + " ");
        sb.append(PREFIX_LOCATION + appointment.getMapAddress().value + " ");
        sb.append(PREFIX_START_TIME + appointment.getStartTime().format(Appointment.TIME_FORMAT) + " ");
        sb.append(PREFIX_START_DATE + appointment.getStartDate().format(Appointment.DATE_FORMAT) + " ");
        sb.append(PREFIX_END_TIME + appointment.getEndTime().format(Appointment.TIME_FORMAT) + " ");
        sb.append(PREFIX_END_DATE + appointment.getEndDate().format(Appointment.DATE_FORMAT) + " ");
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\EditAppointmentDescriptorBuilder.java
``` java

/**
 * A utility class to help with building EditAppointmentDescriptor objects.
 */
public class EditAppointmentDescriptorBuilder {
    private EditAppointmentCommand.EditAppointmentDescriptor descriptor;

    public EditAppointmentDescriptorBuilder() {
        descriptor = new EditAppointmentDescriptor();
    }

    public EditAppointmentDescriptorBuilder(EditAppointmentDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * Returns an {@code EditAppointmentDescriptor} with fields containing {@code appointment}'s details
     */
    public EditAppointmentDescriptorBuilder(Appointment appointment) {
        descriptor = new EditAppointmentDescriptor();
        descriptor.setAppointmentName(appointment.getTitle());
        descriptor.setStartDate(appointment.getStartDate());
        descriptor.setEndDate(appointment.getEndDate());
        descriptor.setStartTime(appointment.getStartTime());
        descriptor.setEndTime(appointment.getEndTime());
        descriptor.setLocation(appointment.getMapAddress());
        descriptor.setCelebIds(appointment.getCelebIds());
        descriptor.setPointOfContactIds(appointment.getPointOfContactIds());
    }

    /**
     * Sets the {@code Name} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withName(String name) {
        descriptor.setAppointmentName(name);
        return this;
    }

    /**
     * Sets the {@code MapAddress} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withLocation(String location) {
        try {
            descriptor.setLocation(ParserUtil.parseMapAddress(location));
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("map address not valid.");
        }
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withStartTime(String startTime) {
        descriptor.setStartTime(LocalTime.parse(startTime, Appointment.TIME_FORMAT));
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withEndTime(String endTime) {
        descriptor.setEndTime(LocalTime.parse(endTime, Appointment.TIME_FORMAT));
        return this;
    }

    /**
     * Sets the {@code startDate} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withStartDate(String startDate) {
        descriptor.setStartDate(LocalDate.parse(startDate, Appointment.DATE_FORMAT));
        return this;
    }

    /**
     * Sets the {@code endDate} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withEndDate(String endDate) {
        descriptor.setEndDate(LocalDate.parse(endDate, Appointment.DATE_FORMAT));
        return this;
    }

    public EditAppointmentDescriptor build() {
        return descriptor;
    }
}
```
###### \java\systemtests\calendar\AddAppointmentCommandSystemTest.java
``` java

public class AddAppointmentCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void addAppointment() {
        /* ------------------------ Perform add appointment operations on the shown unfiltered list ----------------- */

        /* Case: add an appointment, command with leading spaces and trailing spaces
         * -> added
         */
        Appointment toAdd = OSCAR;
        List<Celebrity> celebrityList = new ArrayList<>();
        List<Person> pointOfContactList = new ArrayList<>();
        List<Index> celebrityIndices = new ArrayList<>();
        List<Index> pointOfContactIndices = new ArrayList<>();

        String command = "   " + AddAppointmentCommand.COMMAND_WORD + "  " + APPT_NAME_DESC_OSCAR + "  "
                + APPT_LOCATION_DESC_OSCAR + "  " + APPT_START_DATE_DESC_OSCAR + "  " + APPT_END_DATE_DESC_OSCAR + "  "
                + APPT_START_TIME_DESC_OSCAR + "  " + APPT_END_TIME_DESC_OSCAR + " ";
        assertCommandSuccess(command, toAdd);


        /* Case: add an appointment with all fields and a celebrity -> added */

        celebrityList.add(JAY);
        celebrityIndices.addAll(getCelebrityIndices(this.getModel(), celebrityList));
        toAdd = new AppointmentBuilder().withName(VALID_APPOINTMENT_NAME_GRAMMY)
                .withLocation(VALID_APPOINTMENT_LOCATION_OSCAR).withStartDate(VALID_START_DATE_OSCAR)
                .withStartTime(VALID_START_TIME_OSCAR).withEndDate(VALID_END_DATE_OSCAR)
                .withEndTime(VALID_END_TIME_OSCAR).build();
        toAdd.updateEntries(celebrityList, pointOfContactList);

        command = AddAppointmentCommand.COMMAND_WORD + APPT_NAME_DESC_GRAMMY + APPT_LOCATION_DESC_OSCAR
                + APPT_END_DATE_DESC_OSCAR + APPT_END_TIME_DESC_OSCAR + APPT_START_DATE_DESC_OSCAR
                + APPT_START_TIME_DESC_OSCAR + generatePointOfContactandCelebrityFields(celebrityIndices,
                pointOfContactIndices);

        assertCommandSuccess(command, toAdd);


        /* Case: add an appointment with all fields and 2 celebrities -> added */

        celebrityList.add(AYANE);
        celebrityIndices.clear();
        celebrityIndices.addAll(getCelebrityIndices(this.getModel(), celebrityList));
        toAdd = new AppointmentBuilder().withName(VALID_APPOINTMENT_NAME_OSCAR)
                .withLocation(VALID_APPOINTMENT_LOCATION_GRAMMY).withStartDate(VALID_START_DATE_OSCAR)
                .withStartTime(VALID_START_TIME_OSCAR).withEndDate(VALID_END_DATE_OSCAR)
                .withEndTime(VALID_END_TIME_OSCAR).build();
        toAdd.updateEntries(celebrityList, pointOfContactList);

        command = AddAppointmentCommand.COMMAND_WORD + APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_GRAMMY
                + APPT_END_DATE_DESC_OSCAR + APPT_END_TIME_DESC_OSCAR + APPT_START_DATE_DESC_OSCAR
                + APPT_START_TIME_DESC_OSCAR + generatePointOfContactandCelebrityFields(celebrityIndices,
                pointOfContactIndices);

        assertCommandSuccess(command, toAdd);

        /* Case: add an appointment with all fields and 2 celebrities and 1 point of contact -> added */

        pointOfContactList.add(BENSON);
        celebrityIndices.clear();
        celebrityIndices.addAll(getCelebrityIndices(this.getModel(), celebrityList));
        pointOfContactIndices.addAll(getPersonIndices(this.getModel(), pointOfContactList));
        toAdd = new AppointmentBuilder().withName(VALID_APPOINTMENT_NAME_OSCAR)
                .withLocation(VALID_APPOINTMENT_LOCATION_GRAMMY).withStartDate(VALID_START_DATE_OSCAR)
                .withStartTime(VALID_START_TIME_GRAMMY).withEndDate(VALID_END_DATE_OSCAR)
                .withEndTime(VALID_END_TIME_GRAMMY).build();
        toAdd.updateEntries(celebrityList, pointOfContactList);

        command = AddAppointmentCommand.COMMAND_WORD + APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_GRAMMY
                + APPT_END_DATE_DESC_OSCAR + APPT_END_TIME_DESC_GRAMMY + APPT_START_DATE_DESC_OSCAR
                + APPT_START_TIME_DESC_GRAMMY + generatePointOfContactandCelebrityFields(celebrityIndices,
                pointOfContactIndices);

        assertCommandSuccess(command, toAdd);

        /* Case: add an appointment with all fields and 2 celebrities and 1 point of contact -> added */

        pointOfContactList.add(CARL);
        celebrityIndices.clear();
        celebrityIndices.addAll(getCelebrityIndices(this.getModel(), celebrityList));
        pointOfContactIndices.clear();
        pointOfContactIndices.addAll(getPersonIndices(this.getModel(), pointOfContactList));
        toAdd = new AppointmentBuilder().withName(VALID_APPOINTMENT_NAME_GRAMMY)
                .withLocation(VALID_APPOINTMENT_LOCATION_GRAMMY).withStartDate(VALID_START_DATE_GRAMMY)
                .withStartTime(VALID_START_TIME_GRAMMY).withEndDate(VALID_END_DATE_GRAMMY)
                .withEndTime(VALID_END_TIME_GRAMMY).build();
        toAdd.updateEntries(celebrityList, pointOfContactList);

        command = AddAppointmentCommand.COMMAND_WORD + APPT_NAME_DESC_GRAMMY + APPT_LOCATION_DESC_GRAMMY
                + APPT_END_DATE_DESC_GRAMMY + APPT_END_TIME_DESC_GRAMMY + APPT_START_DATE_DESC_GRAMMY
                + APPT_START_TIME_DESC_GRAMMY + generatePointOfContactandCelebrityFields(celebrityIndices,
                pointOfContactIndices);

        assertCommandSuccess(command, toAdd);

        /* ----------------------------------- Perform invalid add appointment operations --------------------------- */

        /* Case: add a duplicate appointment -> rejected */
        command = AddAppointmentCommand.COMMAND_WORD + APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_OSCAR
                + APPT_START_DATE_DESC_OSCAR + APPT_END_DATE_DESC_OSCAR + APPT_START_TIME_DESC_OSCAR
                + APPT_END_TIME_DESC_OSCAR;
        assertCommandFailure(command, MESSAGE_DUPLICATE_APPOINTMENT);

        /* Case: add an appointment with invalid map address -> rejected */
        command = AddAppointmentCommand.COMMAND_WORD + APPT_NAME_DESC_OSCAR + INVALID_APPT_LOCATION_DESC
                + APPT_START_DATE_DESC_OSCAR + APPT_END_DATE_DESC_OSCAR + APPT_START_TIME_DESC_OSCAR
                + APPT_END_TIME_DESC_OSCAR;
        assertCommandFailure(command, MESSAGE_ADDRESS_MAP_CONSTRAINTS);

        /* Case: add an appointment with invalid celebrity indices -> rejected */
        celebrityIndices.clear();
        celebrityIndices.addAll(pointOfContactIndices);
        command = AddAppointmentCommand.COMMAND_WORD + APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_OSCAR
                + APPT_START_DATE_DESC_OSCAR + APPT_END_DATE_DESC_OSCAR + APPT_START_TIME_DESC_OSCAR
                + APPT_END_TIME_DESC_OSCAR + generatePointOfContactandCelebrityFields(celebrityIndices,
                pointOfContactIndices);
        assertCommandFailure(command, MESSAGE_NOT_CELEBRITY_INDEX);

        /* Case: add an appointment with invalid point of contact indices -> rejected */
        celebrityIndices.clear();
        celebrityIndices.addAll(getCelebrityIndices(this.getModel(), celebrityList));
        pointOfContactIndices.clear();
        pointOfContactIndices.addAll(celebrityIndices);
        command = AddAppointmentCommand.COMMAND_WORD + APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_OSCAR
                + APPT_START_DATE_DESC_OSCAR + APPT_END_DATE_DESC_OSCAR + APPT_START_TIME_DESC_OSCAR
                + APPT_END_TIME_DESC_OSCAR + generatePointOfContactandCelebrityFields(celebrityIndices,
                pointOfContactIndices);
        assertCommandFailure(command, MESSAGE_NOT_POINT_OF_CONTACT_INDEX);

        /* Case: add a appointment with start time not 15 minutes before end time -> rejected */
        command = AddAppointmentCommand.COMMAND_WORD + APPT_NAME_DESC_OSCAR + APPT_LOCATION_DESC_OSCAR
                + APPT_START_DATE_DESC_OSCAR + APPT_END_DATE_DESC_OSCAR + APPT_START_TIME_DESC_GRAMMY
                + " " + PREFIX_END_TIME + VALID_START_TIME_OSCAR;
        assertCommandFailure(command, MESSAGE_START_DATE_TIME_NOT_BEFORE_END_DATE_TIME);

    }

    /**
     * Executes the {@code AddAppointmentCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddAppointmentCommand} with the name of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Selected card remains unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     */
    private void assertCommandSuccess(String command, Appointment toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addAppointmentToStorageCalendar(toAdd);
        } catch (DuplicateAppointmentException e) {
            throw new IllegalArgumentException("toAdd already exists in th model.");
        }


        String expectedResultMessage = String.format(AddAppointmentCommand.MESSAGE_SUCCESS, toAdd.getTitle());

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Appointment)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddAppointmentCommandSystemTest#assertCommandSuccess(String, Appointment)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertResultDisplayAndCommandBoxShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertResultDisplayAndCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarChangedExceptSaveLocation();
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
