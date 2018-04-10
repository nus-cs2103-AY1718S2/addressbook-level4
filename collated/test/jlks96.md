# jlks96
###### \java\guitests\guihandles\CalendarPanelHandle.java
``` java
/**
 * Provides a handle for {@code CalendarPanel}.
 */
public class CalendarPanelHandle extends NodeHandle<Node> {
    public static final String CALENDAR_ID = "#calendarView";
    private final CalendarView calendarView;

    public CalendarPanelHandle(Node calendarPanelNode) {
        super(calendarPanelNode);
        calendarView = getChildNode(CALENDAR_ID);
    }


}
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public String getDateAdded() {
        return dateAddedLabel.getText();
    }
```
###### \java\seedu\address\commons\event\AppointmentDeletedEventTest.java
``` java
public class AppointmentDeletedEventTest {

    @Test
    public void getAppointmentDeleted_validAppointment_success() throws AppointmentNotFoundException {
        AddressBook addressBook = getTypicalAddressBook();
        addressBook.removeAppointment(addressBook.getAppointmentList().get(INDEX_FIRST_APPT.getZeroBased()));
        ObservableList<Appointment> appointments = addressBook.getAppointmentList();
        AppointmentDeletedEvent event = new AppointmentDeletedEvent(appointments);
        assert(event.getUpdatedAppointments().equals(appointments));
    }
}
```
###### \java\seedu\address\commons\event\NewAppointmentAddedEventTest.java
``` java
public class NewAppointmentAddedEventTest {

    @Test
    public void getAppointmentAdded_validAppointment_success() {
        Appointment appointment = new AppointmentBuilder().build();
        NewAppointmentAddedEvent event = new NewAppointmentAddedEvent(appointment);
        assert(event.getAppointmentAdded().equals(appointment));
    }
}
```
###### \java\seedu\address\commons\event\ZoomInEventTest.java
``` java
public class ZoomInEventTest {
    @Test
    public void toString_comparedWithClassName_success() {
        ZoomInEvent event = new ZoomInEvent();
        assert(event.toString().equals("ZoomInEvent"));
    }
}
```
###### \java\seedu\address\commons\event\ZoomOutEventTest.java
``` java
public class ZoomOutEventTest {
    @Test
    public void toString_comparedWithClassName_success() {
        ZoomOutEvent event = new ZoomOutEvent();
        assert(event.toString().equals("ZoomOutEvent"));
    }
}
```
###### \java\seedu\address\logic\commands\AddAppointmentCommandTest.java
``` java
public class AddAppointmentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAppointmentCommand(null);
    }

    @Test
    public void execute_appointmentAcceptedByModel_addSuccessful() throws Exception {
        Model modelStub = new ModelManager();
        Appointment validAppointment =  new AppointmentBuilder().build();

        CommandResult commandResult = getAddAppointmentCommandForAppointment(validAppointment, modelStub).execute();

        assertEquals(
                String.format(AddAppointmentCommand.MESSAGE_SUCCESS, validAppointment), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAppointment), modelStub.getFilteredAppointmentList());
    }

    @Test
    public void execute_duplicateAppointment_throwsCommandException() throws Exception {
        Model modelStub = new ModelManager();
        Appointment validAppointment =  new AppointmentBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT);

        getAddAppointmentCommandForAppointment(validAppointment, modelStub).execute();
        getAddAppointmentCommandForAppointment(validAppointment, modelStub).execute();
    }

    @Test
    public void equals() {
        Appointment aliceAppointment = new AppointmentBuilder().withPersonName("Alice").build();
        Appointment bobAppointment = new AppointmentBuilder().withPersonName("Bob").build();
        AddAppointmentCommand addAliceAppointmentCommand = new AddAppointmentCommand(aliceAppointment);
        AddAppointmentCommand addBobAppointmentCommand = new AddAppointmentCommand(bobAppointment);

        // same object -> returns true
        assertTrue(addAliceAppointmentCommand.equals(addAliceAppointmentCommand));

        // same values -> returns true
        AddAppointmentCommand addAliceAppointmentCommandCopy = new AddAppointmentCommand(aliceAppointment);
        assertTrue(addAliceAppointmentCommand.equals(addAliceAppointmentCommandCopy));

        // different types -> returns false
        assertFalse(addAliceAppointmentCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceAppointmentCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceAppointmentCommand.equals(addBobAppointmentCommand));
    }

    /**
     * Generates a new AddAppointmentCommand with the details of the given appointment.
     */
    private AddAppointmentCommand getAddAppointmentCommandForAppointment(Appointment appointment, Model model) {
        AddAppointmentCommand command = new AddAppointmentCommand(appointment);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void deletePersons(List<Person> targets) throws PersonNotFoundException {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\DeleteAppointmentCommandTest.java
``` java
public class DeleteAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_existentAppointment_success() throws Exception {
        Appointment appointment = model.getFilteredAppointmentList().get(INDEX_FIRST_APPT.getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(appointment);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPT_SUCCESS, appointment);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteAppointment(appointment);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentAppointment_throwsCommandException() {
        Appointment appointment = new AppointmentBuilder().build();
        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(appointment);

        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_APPT_NOT_FOUND);
    }

    @Test
    public void executeUndoRedo_existentAppointment_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Appointment appointment = model.getFilteredAppointmentList().get(INDEX_FIRST_APPT.getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(appointment);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first appointment deleted
        deleteAppointmentCommand.execute();
        undoRedoStack.push(deleteAppointmentCommand);

        // undo -> reverts address book back to previous state and filtered appointment list to show all appointments
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first appointment deleted again
        expectedModel.deleteAppointment(appointment);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_nonExistentAppointment_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Appointment appointment = new AppointmentBuilder().build();
        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(appointment);

        // execution failed -> deleteAppointmentCommand not pushed into undoRedoStack
        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_APPT_NOT_FOUND);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        Appointment firstAppointment = model.getFilteredAppointmentList().get(INDEX_FIRST_APPT.getZeroBased());
        Appointment secondAppointment = model.getFilteredAppointmentList().get(INDEX_SECOND_APPT.getZeroBased());
        DeleteAppointmentCommand deleteAppointmentFirstCommand = prepareCommand(firstAppointment);
        DeleteAppointmentCommand deleteAppointmentSecondCommand = prepareCommand(secondAppointment);

        // same object -> returns true
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommand));

        // same values -> returns true
        DeleteAppointmentCommand deleteAppointmentFirstCommandCopy = prepareCommand(firstAppointment);
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(null));

        // different appointment -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(deleteAppointmentSecondCommand));
    }

    /**
     * Returns a {@code DeleteAppointmentCommand} with the parameter {@code appointmentToDelete}.
     */
    private DeleteAppointmentCommand prepareCommand(Appointment appointmentToDelete) {
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(appointmentToDelete);
        deleteAppointmentCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteAppointmentCommand;
    }
}
```
###### \java\seedu\address\logic\commands\DeleteBeforeCommandTest.java
``` java
public class DeleteBeforeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_personsExist_success() throws Exception {
        DeleteBeforeCommand deleteBeforeCommand = prepareCommand(DATE_SECOND_FEB, TAG_SET_FRIEND);
        List<Person> personsToDelete = Arrays.asList(ALICE, BENSON);
        String expectedMessage = String.format(
                DeleteBeforeCommand.MESSAGE_DELETE_PERSONS_SUCCESS, 2, TAG_SET_FRIEND, DATE_SECOND_FEB);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePersons(personsToDelete);

        assertCommandSuccess(deleteBeforeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noSuchPerson_throwsCommandException() {
        DeleteBeforeCommand deleteBeforeCommand = prepareCommand(DATE_FIRST_JAN, TAG_SET_OWES_MONEY_FRIEND);

        assertCommandFailure(deleteBeforeCommand, model, Messages.MESSAGE_PERSONS_NOT_FOUND);
    }

    @Test
    public void executeUndoRedo_personsExist_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteBeforeCommand deleteBeforeCommand = prepareCommand(DATE_SECOND_FEB, TAG_SET_FRIEND);
        List<Person> personsToDelete = Arrays.asList(ALICE, BENSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first person deleted
        deleteBeforeCommand.execute();
        undoRedoStack.push(deleteBeforeCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.deletePersons(personsToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_noSuchPersonUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteBeforeCommand deleteBeforeCommand = prepareCommand(DATE_FIRST_JAN, TAG_SET_OWES_MONEY_FRIEND);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(deleteBeforeCommand, model, Messages.MESSAGE_PERSONS_NOT_FOUND);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteBeforeCommand deleteBeforeFirstCommand = prepareCommand(DATE_FIRST_JAN, TAG_SET_OWES_MONEY_FRIEND);
        DeleteBeforeCommand deleteBeforeSecondCommand = prepareCommand(DATE_SECOND_FEB, TAG_SET_FRIEND);

        // same object -> returns true
        assertTrue(deleteBeforeFirstCommand.equals(deleteBeforeFirstCommand));

        // same values -> returns true
        DeleteBeforeCommand deleteBeforeFirstCommandCopy = prepareCommand(DATE_FIRST_JAN, TAG_SET_OWES_MONEY_FRIEND);
        assertTrue(deleteBeforeFirstCommand.equals(deleteBeforeFirstCommandCopy));

        // one command preprocessed when previously equal -> returns true
        deleteBeforeFirstCommandCopy.preprocessUndoableCommand();
        assertTrue(deleteBeforeFirstCommand.equals(deleteBeforeFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteBeforeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteBeforeFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(deleteBeforeFirstCommand.equals(deleteBeforeSecondCommand));
    }

    /**
     * Returns a {@code DeleteBeforeCommand} with the parameter {@code date} and {@code tags}.
     */
    private DeleteBeforeCommand prepareCommand(DateAdded dateAdded, Set<Tag> tags) {
        DeleteBeforeCommand deleteBeforeCommand = new DeleteBeforeCommand(dateAdded, tags);
        deleteBeforeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteBeforeCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
```
###### \java\seedu\address\logic\commands\ZoomInCommandTest.java
``` java
public class ZoomInCommandTest {
    private boolean eventRaised;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @Before
    public void setUp() {
        eventRaised = false;
        EventsCenter.getInstance().registerHandler(this);
    }

    @Test
    public void execute() {
        ZoomInCommand command = new ZoomInCommand();
        String expectedMessage = ZoomInCommand.MESSAGE_SUCCESS;
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(true, eventRaised);
    }

    /**
     * Handles the event where the user is trying to zoom in on the calendar
     */
    @Subscribe
    private void handleZoomInEvent(ZoomInEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        eventRaised = true;
    }
}
```
###### \java\seedu\address\logic\commands\ZoomOutCommandTest.java
``` java
public class ZoomOutCommandTest {
    private boolean eventRaised;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @Before
    public void setUp() {
        eventRaised = false;
        EventsCenter.getInstance().registerHandler(this);
    }

    @Test
    public void execute() {
        ZoomOutCommand command = new ZoomOutCommand();
        String expectedMessage = ZoomOutCommand.MESSAGE_SUCCESS;
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(true, eventRaised);
    }

    /**
     * Handles the event where the user is trying to zoom out on the calendar
     */
    @Subscribe
    private void handleZoomOutEvent(ZoomOutEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        eventRaised = true;
    }
}
```
###### \java\seedu\address\logic\parser\AddAppointmentCommandParserTest.java
``` java
public class AddAppointmentCommandParserTest {

    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Appointment expectedAppointment = new AppointmentBuilder().withPersonName(VALID_PERSONNAME)
                .withDate(VALID_DATE).withStartTime(VALID_STARTTIME).withEndTime(VALID_ENDTIME)
                .withLocation(VALID_LOCATION).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC
                + ENDTIME_DESC + LOCATION_DESC, new AddAppointmentCommand(expectedAppointment));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_PERSONNAME + DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + LOCATION_DESC,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, PERSONNAME_DESC + VALID_DATE + STARTTIME_DESC + ENDTIME_DESC + LOCATION_DESC,
                expectedMessage);

        // missing start time prefix
        assertParseFailure(parser, PERSONNAME_DESC + DATE_DESC + VALID_STARTTIME + ENDTIME_DESC + LOCATION_DESC,
                expectedMessage);

        // missing end time prefix
        assertParseFailure(parser, PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + VALID_ENDTIME + LOCATION_DESC,
                expectedMessage);

        // missing location prefix
        assertParseFailure(parser, PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + VALID_LOCATION,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_PERSONNAME + VALID_DATE + VALID_STARTTIME + VALID_ENDTIME + VALID_LOCATION,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid person name
        assertParseFailure(parser,
                INVALID_PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + LOCATION_DESC,
                PersonName.MESSAGE_NAME_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser,
                PERSONNAME_DESC + INVALID_DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + LOCATION_DESC,
                Date.MESSAGE_DATE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser,
                PERSONNAME_DESC + DATE_DESC + INVALID_STARTTIME_DESC + ENDTIME_DESC + LOCATION_DESC,
                StartTime.MESSAGE_TIME_CONSTRAINTS);

        // invalid end time
        assertParseFailure(parser,
                PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + INVALID_ENDTIME_DESC + LOCATION_DESC,
                EndTime.MESSAGE_TIME_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser,
                PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + INVALID_LOCATION_DESC,
                Location.MESSAGE_LOCATION_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
                INVALID_PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + INVALID_LOCATION_DESC,
                PersonName.MESSAGE_NAME_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_deleteBefore() throws Exception {
        DeleteBeforeCommand command = (DeleteBeforeCommand) parser.parseCommand(
                DeleteBeforeCommand.COMMAND_WORD + VALID_DATE_DESC + VALID_TAG_DESC_FRIEND);
        assertEquals(new DeleteBeforeCommand(DATE_FIRST_JAN, new HashSet<>(Arrays.asList(TAG_FRIEND))), command);
    }

    @Test
    public void parseCommand_deleteBeforeAlias() throws Exception {
        DeleteBeforeCommand command = (DeleteBeforeCommand) parser.parseCommand(
                DeleteBeforeCommand.COMMAND_ALIAS + VALID_DATE_DESC + VALID_TAG_DESC_FRIEND);
        assertEquals(new DeleteBeforeCommand(DATE_FIRST_JAN, new HashSet<>(Arrays.asList(TAG_FRIEND))), command);
    }

    @Test
    public void parseCommand_addAppointment() throws Exception {
        Appointment appointment = new AppointmentBuilder().build();
        AddAppointmentCommand command =
                (AddAppointmentCommand) parser.parseCommand(AppointmentUtil.getAddAppointmentCommand(appointment));
        assertEquals(new AddAppointmentCommand(appointment), command);
    }

    @Test
    public void parseCommand_addAppointmentAlias() throws Exception {
        Appointment appointment = new AppointmentBuilder().build();
        AddAppointmentCommand command =
                (AddAppointmentCommand) parser.parseCommand(AppointmentUtil.getAddAppointmentAlias(appointment));
        assertEquals(new AddAppointmentCommand(appointment), command);
    }

    @Test
    public void parseCommand_deleteAppointment() throws Exception {
        Appointment appointment = new AppointmentBuilder().build();
        DeleteAppointmentCommand command =
                (DeleteAppointmentCommand) parser.parseCommand(
                        AppointmentUtil.getDeleteAppointmentCommand(appointment));
        assertEquals(new DeleteAppointmentCommand(appointment), command);
    }

    @Test
    public void parseCommand_deleteAppointmentAlias() throws Exception {
        Appointment appointment = new AppointmentBuilder().build();
        DeleteAppointmentCommand command =
                (DeleteAppointmentCommand) parser.parseCommand(AppointmentUtil.getDeleteAppointmentAlias(appointment));
        assertEquals(new DeleteAppointmentCommand(appointment), command);
    }

    @Test
    public void parseCommand_zoomIn() throws Exception {
        assertTrue(parser.parseCommand(ZoomInCommand.COMMAND_WORD) instanceof ZoomInCommand);
        assertTrue(parser.parseCommand(ZoomInCommand.COMMAND_WORD + " 3") instanceof ZoomInCommand);
    }

    @Test
    public void parseCommand_zoomInAlias() throws Exception {
        assertTrue(parser.parseCommand(ZoomInCommand.COMMAND_ALIAS) instanceof ZoomInCommand);
        assertTrue(parser.parseCommand(ZoomInCommand.COMMAND_ALIAS + " 3") instanceof ZoomInCommand);
    }

    @Test
    public void parseCommand_zoomOut() throws Exception {
        assertTrue(parser.parseCommand(ZoomOutCommand.COMMAND_WORD) instanceof ZoomOutCommand);
        assertTrue(parser.parseCommand(ZoomOutCommand.COMMAND_WORD + " 3") instanceof ZoomOutCommand);
    }

    @Test
    public void parseCommand_zoomOutAlias() throws Exception {
        assertTrue(parser.parseCommand(ZoomOutCommand.COMMAND_ALIAS) instanceof ZoomOutCommand);
        assertTrue(parser.parseCommand(ZoomOutCommand.COMMAND_ALIAS + " 3") instanceof ZoomOutCommand);
    }
```
###### \java\seedu\address\logic\parser\CommandParserTestUtil.java
``` java
    /**
     * Creates and returns a {@code DateAdded} with the dateAdded attribute representing the current date
     * @return current date in the following format: dd/MM/yyyy
     */
    public static String createDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        return dateFormatter.format(calendar.getTime());
    }
}
```
###### \java\seedu\address\logic\parser\DeleteAppointmentCommandParserTest.java
``` java
public class DeleteAppointmentCommandParserTest {

    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Appointment appointmentToDelete = new AppointmentBuilder().withPersonName(VALID_PERSONNAME)
                .withDate(VALID_DATE).withStartTime(VALID_STARTTIME).withEndTime(VALID_ENDTIME)
                .withLocation(VALID_LOCATION).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC
                + ENDTIME_DESC + LOCATION_DESC, new DeleteAppointmentCommand(appointmentToDelete));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_PERSONNAME + DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + LOCATION_DESC,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, PERSONNAME_DESC + VALID_DATE + STARTTIME_DESC + ENDTIME_DESC + LOCATION_DESC,
                expectedMessage);

        // missing start time prefix
        assertParseFailure(parser, PERSONNAME_DESC + DATE_DESC + VALID_STARTTIME + ENDTIME_DESC + LOCATION_DESC,
                expectedMessage);

        // missing end time prefix
        assertParseFailure(parser, PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + VALID_ENDTIME + LOCATION_DESC,
                expectedMessage);

        // missing location prefix
        assertParseFailure(parser, PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + VALID_LOCATION,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_PERSONNAME + VALID_DATE + VALID_STARTTIME + VALID_ENDTIME + VALID_LOCATION,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid person name
        assertParseFailure(parser,
                INVALID_PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + LOCATION_DESC,
                PersonName.MESSAGE_NAME_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser,
                PERSONNAME_DESC + INVALID_DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + LOCATION_DESC,
                Date.MESSAGE_DATE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser,
                PERSONNAME_DESC + DATE_DESC + INVALID_STARTTIME_DESC + ENDTIME_DESC + LOCATION_DESC,
                StartTime.MESSAGE_TIME_CONSTRAINTS);

        // invalid end time
        assertParseFailure(parser,
                PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + INVALID_ENDTIME_DESC + LOCATION_DESC,
                EndTime.MESSAGE_TIME_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser,
                PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + INVALID_LOCATION_DESC,
                Location.MESSAGE_LOCATION_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
                INVALID_PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + INVALID_LOCATION_DESC,
                PersonName.MESSAGE_NAME_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\logic\parser\DeleteBeforeCommandParserTest.java
``` java
public class DeleteBeforeCommandParserTest {

    private DeleteBeforeCommandParser parser = new DeleteBeforeCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {

        // one tag - accepted
        assertParseSuccess(parser, VALID_DATE_DESC + VALID_TAG_DESC_FRIEND,
                new DeleteBeforeCommand(DATE_FIRST_JAN, TAG_SET_FRIEND));

        // multiple tags - all accepted
        assertParseSuccess(parser, VALID_DATE_DESC + VALID_TAG_DESC_OWES_MONEY + VALID_TAG_DESC_FRIEND,
                new DeleteBeforeCommand(DATE_FIRST_JAN, TAG_SET_OWES_MONEY_FRIEND));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        // invalid date - fail
        assertParseFailure(parser, INVALID_DATE_DESC + VALID_TAG_DESC_FRIEND,
                DateAdded.MESSAGE_DATE_CONSTRAINTS);

        // invalid tag - fail
        assertParseFailure(parser, VALID_DATE_DESC + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBeforeCommand.MESSAGE_USAGE);

        // missing date prefix
        assertParseFailure(parser, VALID_TAG_DESC_FRIEND, expectedMessage);

        // missing tags prefix
        assertParseFailure(parser, VALID_DATE_DESC, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, "", expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseDateAdded_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDateAdded((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDateAdded((Optional<String>) null));
    }

    @Test
    public void parseDateAdded_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseDate(INVALID_DATE));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseDateAdded(Optional.of(INVALID_DATE)));
    }

    @Test
    public void parseDateAdded_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDateAdded(Optional.empty()).isPresent());
    }

    @Test
    public void parseDateAdded_validValueWithoutWhitespace_returnsDate() throws Exception {
        DateAdded expectedDate = new DateAdded(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDateAdded(VALID_DATE));
        assertEquals(Optional.of(expectedDate), ParserUtil.parseDateAdded(Optional.of(VALID_DATE)));
    }

    @Test
    public void parseDateAdded_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        DateAdded expectedDate = new DateAdded(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDateAdded(dateWithWhitespace));
        assertEquals(Optional.of(expectedDate), ParserUtil.parseDateAdded(Optional.of(dateWithWhitespace)));
    }

    @Test
    public void parsePersonName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePersonName((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePersonName((Optional<String>) null));
    }

    @Test
    public void parsePersonName_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parsePersonName(INVALID_NAME));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parsePersonName(Optional.of(INVALID_NAME)));
    }

    @Test
    public void parsePersonName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePersonName(Optional.empty()).isPresent());
    }

    @Test
    public void parsePersonName_validValueWithoutWhitespace_returnsPersonName() throws Exception {
        PersonName expectedName = new PersonName(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parsePersonName(VALID_NAME));
        assertEquals(Optional.of(expectedName), ParserUtil.parsePersonName(Optional.of(VALID_NAME)));
    }

    @Test
    public void parsePersonName_validValueWithWhitespace_returnsTrimmedPersonName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        PersonName expectedName = new PersonName(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parsePersonName(nameWithWhitespace));
        assertEquals(Optional.of(expectedName), ParserUtil.parsePersonName(Optional.of(nameWithWhitespace)));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDate((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDate((Optional<String>) null));
    }

    @Test
    public void parseDate_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseDate(INVALID_DATE));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseDate(Optional.of(INVALID_DATE)));
    }

    @Test
    public void parseDate_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDate(Optional.empty()).isPresent());
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
        assertEquals(Optional.of(expectedDate), ParserUtil.parseDate(Optional.of(VALID_DATE)));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
        assertEquals(Optional.of(expectedDate), ParserUtil.parseDate(Optional.of(dateWithWhitespace)));
    }

    @Test
    public void parseStartTime_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseStartTime((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseStartTime((Optional<String>) null));
    }

    @Test
    public void parseStartTime_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseStartTime(INVALID_TIME));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseStartTime(Optional.of(INVALID_TIME)));
    }

    @Test
    public void parseStartTime_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseStartTime(Optional.empty()).isPresent());
    }

    @Test
    public void parseStartTime_validValueWithoutWhitespace_returnsStartTime() throws Exception {
        StartTime expectedTime = new StartTime(VALID_TIME);
        assertEquals(expectedTime, ParserUtil.parseStartTime(VALID_TIME));
        assertEquals(Optional.of(expectedTime), ParserUtil.parseStartTime(Optional.of(VALID_TIME)));
    }

    @Test
    public void parseStartTime_validValueWithWhitespace_returnsTrimmedStartTime() throws Exception {
        String timeWithWhitespace = WHITESPACE + VALID_TIME + WHITESPACE;
        StartTime expectedTime = new StartTime(VALID_TIME);
        assertEquals(expectedTime, ParserUtil.parseStartTime(timeWithWhitespace));
        assertEquals(Optional.of(expectedTime), ParserUtil.parseStartTime(Optional.of(timeWithWhitespace)));
    }

    @Test
    public void parseEndTime_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEndTime((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEndTime((Optional<String>) null));
    }

    @Test
    public void parseEndTime_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseEndTime(INVALID_TIME));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseEndTime(Optional.of(INVALID_TIME)));
    }

    @Test
    public void parseEndTime_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEndTime(Optional.empty()).isPresent());
    }

    @Test
    public void parseEndTime_validValueWithoutWhitespace_returnsEndTime() throws Exception {
        EndTime expectedTime = new EndTime(VALID_TIME);
        assertEquals(expectedTime, ParserUtil.parseEndTime(VALID_TIME));
        assertEquals(Optional.of(expectedTime), ParserUtil.parseEndTime(Optional.of(VALID_TIME)));
    }

    @Test
    public void parseEndTime_validValueWithWhitespace_returnsTrimmedEndTime() throws Exception {
        String timeWithWhitespace = WHITESPACE + VALID_TIME + WHITESPACE;
        EndTime expectedTime = new EndTime(VALID_TIME);
        assertEquals(expectedTime, ParserUtil.parseEndTime(timeWithWhitespace));
        assertEquals(Optional.of(expectedTime), ParserUtil.parseEndTime(Optional.of(timeWithWhitespace)));
    }

    @Test
    public void parseLocation_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseLocation((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseLocation((Optional<String>) null));
    }

    @Test
    public void parseLocation_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseLocation(INVALID_LOCATION));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseLocation(Optional.of(INVALID_LOCATION)));
    }

    @Test
    public void parseLocation_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseLocation(Optional.empty()).isPresent());
    }

    @Test
    public void parseLocation_validValueWithoutWhitespace_returnsLocation() throws Exception {
        Location expectedLocation = new Location(VALID_LOCATION);
        assertEquals(expectedLocation, ParserUtil.parseLocation(VALID_LOCATION));
        assertEquals(Optional.of(expectedLocation), ParserUtil.parseLocation(Optional.of(VALID_LOCATION)));
    }

    @Test
    public void parseLocation_validValueWithWhitespace_returnsTrimmedLocation() throws Exception {
        String locationWithWhitespace = WHITESPACE + VALID_LOCATION + WHITESPACE;
        Location expectedLocation = new Location(VALID_LOCATION);
        assertEquals(expectedLocation, ParserUtil.parseLocation(locationWithWhitespace));
        assertEquals(Optional.of(expectedLocation), ParserUtil.parseLocation(Optional.of(locationWithWhitespace)));
    }
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void resetData_withDuplicateAppointments_throwsAssertionError() {
        List<Person> newPersons = Arrays.asList(ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<Appointment> newAppointments = Arrays.asList(ALICE_APPT, ALICE_APPT); // Repeat ALICE_APPT twice
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newAppointments);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void getAppointmentList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getAppointmentList().remove(0);
    }
```
###### \java\seedu\address\model\appointment\AppointmentTest.java
``` java
public class AppointmentTest {

    private final PersonName name = new PersonName("ALICE");
    private final Date date = new Date("01/01/2017");
    private final StartTime startTime = new StartTime("12:30");
    private final EndTime endTime = new EndTime("13:30");
    private final Location location = new Location("Gold Park Mall");
    private final Appointment appointment = new Appointment(name, date, startTime, endTime, location);

    @Test
    public void isEqual_sameAppointment_success() {
        assertTrue(new Appointment(name, date, startTime, endTime, location).equals(appointment));
    }

    @Test
    public void isEqual_compareNull_failure() {
        assertFalse(new Appointment(name, date, startTime, endTime, location).equals(null));
    }

    @Test
    public void getters_validAppointment_success() {
        assertTrue(appointment.getName().equals(name));
        assertTrue(appointment.getStartTime().equals(startTime));
        assertTrue(appointment.getEndTime().equals(endTime));
        assertTrue(appointment.getDate().equals(date));
        assertTrue(appointment.getLocation().equals(location));
    }

    @Test
    public void toString_validAppointment_success() {
        assertTrue(appointment.toString().equals(name + " Date: " + date + " Start Time: " + startTime
                + " End Time: " + endTime + " Location: " + location));
    }

    @Test
    public void toStringList_validAppointment_success() {
        final List<String> expectedStringList = new ArrayList<>();
        expectedStringList.add(name.toString());
        expectedStringList.add(date.toString());
        expectedStringList.add(startTime.toString());
        expectedStringList.add(endTime.toString());
        expectedStringList.add(location.toString());
        assertTrue(appointment.toStringList().equals(expectedStringList));
    }

}
```
###### \java\seedu\address\model\appointment\DateTest.java
``` java
public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDateAdded = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDateAdded));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("12/34")); // invalid date
        assertFalse(Date.isValidDate("date")); // non-numeric
        assertFalse(Date.isValidDate("12 Feb 2018")); // alphabets within date
        assertFalse(Date.isValidDate("12 /12/2018")); // spaces within date
        assertFalse(Date.isValidDate("13/13/2018")); // invalid month

        // valid date
        assertTrue(Date.isValidDate("12/12/2018"));
        assertTrue(Date.isValidDate("01/04/2017"));
    }

    @Test
    public void isEqual_equalDates_success() {
        assertTrue(new Date("01/04/2017").equals(new Date("01/04/2017")));
    }
}
```
###### \java\seedu\address\model\appointment\EndTimeTest.java
``` java
public class EndTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new StartTime(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new StartTime(invalidTime));
    }
}
```
###### \java\seedu\address\model\appointment\LocationTest.java
``` java
public class LocationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    public void constructor_invalidLocation_throwsIllegalArgumentException() {
        String invalidLocation = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Location(invalidLocation));
    }

    @Test
    public void isValidLocation() {
        // null location
        Assert.assertThrows(NullPointerException.class, () -> Location.isValidLocation(null));

        // invalid locations
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only

        // valid locations
        assertTrue(Location.isValidLocation("Blk 456, Den Road, #01-355"));
        assertTrue(Location.isValidLocation("-")); // one character
        assertTrue(Location.isValidLocation("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA"));
    }
}
```
###### \java\seedu\address\model\appointment\PersonNameTest.java
``` java
public class PersonNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PersonName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new PersonName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> PersonName.isValidName(null));

        // invalid name
        assertFalse(PersonName.isValidName("")); // empty string
        assertFalse(PersonName.isValidName(" ")); // spaces only
        assertFalse(PersonName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(PersonName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(PersonName.isValidName("peter jack")); // alphabets only
        assertTrue(PersonName.isValidName("12345")); // numbers only
        assertTrue(PersonName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(PersonName.isValidName("Capital Tan")); // with capital letters
        assertTrue(PersonName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
```
###### \java\seedu\address\model\appointment\StartTimeTest.java
``` java
public class StartTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new StartTime(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new StartTime(invalidTime));
    }

}
```
###### \java\seedu\address\model\appointment\TimeTest.java
``` java
public class TimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime));
    }

    @Test
    public void isValidTime() {
        // null time
        Assert.assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid time
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("1234")); // invalid time
        assertFalse(Time.isValidTime("time")); // non-numeric
        assertFalse(Time.isValidTime("eleven 30")); // alphabets within time
        assertFalse(Time.isValidTime("12 /12/2018")); // spaces within time
        assertFalse(Time.isValidTime("25:00")); // invalid hour

        // valid time
        assertTrue(Time.isValidTime("11:00"));
        assertTrue(Time.isValidTime("00:00"));
    }

    @Test
    public void isEqual_equalTimes_success() {
        assertTrue(new Time("12:00").equals(new Time("12:00")));
    }
}
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void getFilteredAppointmentList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredAppointmentList().remove(0);
    }
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
        //different filteredAppointmentList -> returns false
        modelManager.addAppointment(CARL_APPT);
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));
```
###### \java\seedu\address\model\person\DateAddedIsBeforeDateInputPredicateTest.java
``` java
public class DateAddedIsBeforeDateInputPredicateTest {
    @Test
    public void equals() {

        DateAddedIsBeforeDateInputPredicate firstPredicate = new
                DateAddedIsBeforeDateInputPredicate("03/03/2018");
        DateAddedIsBeforeDateInputPredicate secondPredicate = new
                DateAddedIsBeforeDateInputPredicate("01/01/2018");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DateAddedIsBeforeDateInputPredicate firstPredicateCopy = new
                DateAddedIsBeforeDateInputPredicate("03/03/2018");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different input date -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateAddedIsBeforeDateInput_returnsTrue() {
        DateAddedIsBeforeDateInputPredicate predicate = new
                DateAddedIsBeforeDateInputPredicate("03/03/2018");
        assertTrue(predicate.test(AMY));
    }

    @Test
    public void test_dateAddedIsAfterDateInput_returnsFalse() {
        DateAddedIsBeforeDateInputPredicate predicate = new
                DateAddedIsBeforeDateInputPredicate("01/01/2018");
        assertFalse(predicate.test(AMY));
    }
}
```
###### \java\seedu\address\model\person\DateAddedTest.java
``` java
public class DateAddedTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateAdded(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDateAdded = "";
        Assert.assertThrows(AssertionError.class, () -> new DateAdded(invalidDateAdded));
    }

    @Test
    public void isValidDate() {
        // null date added
        Assert.assertThrows(NullPointerException.class, () -> DateAdded.isValidDate(null));

        // invalid date added
        assertFalse(DateAdded.isValidDate("")); // empty string
        assertFalse(DateAdded.isValidDate(" ")); // spaces only
        assertFalse(DateAdded.isValidDate("12/34")); // invalid date
        assertFalse(DateAdded.isValidDate("date")); // non-numeric
        assertFalse(DateAdded.isValidDate("12 Feb 2018")); // alphabets within date
        assertFalse(DateAdded.isValidDate("12 /12/2018")); // spaces within date

        // valid date added
        assertTrue(DateAdded.isValidDate("12/12/2018"));
        assertTrue(DateAdded.isValidDate("01/04/2017"));
    }
}
```
###### \java\seedu\address\model\PersonIsAddedBeforeDateInputAndContainsTagsPredicateTest.java
``` java
public class PersonIsAddedBeforeDateInputAndContainsTagsPredicateTest {
    @Test
    public void equals() {

        PersonIsAddedBeforeDateInputAndContainsTagsPredicate firstPredicate = new
                PersonIsAddedBeforeDateInputAndContainsTagsPredicate(TAG_SET_FRIEND, "01/01/2018");
        PersonIsAddedBeforeDateInputAndContainsTagsPredicate secondPredicate = new
                PersonIsAddedBeforeDateInputAndContainsTagsPredicate(TAG_SET_HUSBAND, "03/03/2018");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonIsAddedBeforeDateInputAndContainsTagsPredicate firstPredicateCopy = new
                PersonIsAddedBeforeDateInputAndContainsTagsPredicate(TAG_SET_FRIEND, "01/01/2018");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different input date -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personIsAddedBeforeDateInputAndContainsTags_returnsTrue() {
        PersonIsAddedBeforeDateInputAndContainsTagsPredicate predicate = new
                PersonIsAddedBeforeDateInputAndContainsTagsPredicate(TAG_SET_HUSBAND, "03/03/2018");
        assertTrue(predicate.test(BOB));
    }

    @Test
    public void test_personDoesNotContainTags_returnsFalse() {
        PersonIsAddedBeforeDateInputAndContainsTagsPredicate predicate = new
                PersonIsAddedBeforeDateInputAndContainsTagsPredicate(TAG_SET_FRIEND, "03/03/2018");
        assertFalse(predicate.test(BOB));
    }

    @Test
    public void test_personIsNotAddedBeforeDateInput_returnsFalse() {
        PersonIsAddedBeforeDateInputAndContainsTagsPredicate predicate = new
                PersonIsAddedBeforeDateInputAndContainsTagsPredicate(TAG_SET_HUSBAND, "01/01/2018");
        assertFalse(predicate.test(BOB));
    }
}
```
###### \java\seedu\address\model\tag\UniqueTagListContainsTagsPredicateTest.java
``` java
public class UniqueTagListContainsTagsPredicateTest {
    @Test
    public void equals() {

        UniqueTagListContainsTagsPredicate firstPredicate = new
                UniqueTagListContainsTagsPredicate(TAG_SET_FRIEND);
        UniqueTagListContainsTagsPredicate secondPredicate = new
                UniqueTagListContainsTagsPredicate(TAG_SET_OWES_MONEY_FRIEND);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UniqueTagListContainsTagsPredicate firstPredicateCopy = new
                UniqueTagListContainsTagsPredicate(TAG_SET_FRIEND);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different input date -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_uniqueTagListContainsTags_returnsTrue() {
        UniqueTagListContainsTagsPredicate predicate = new
                UniqueTagListContainsTagsPredicate(TAG_SET_HUSBAND);
        assertTrue(predicate.test(BOB));
    }

    @Test
    public void test_uniqueTagListDoesNotContainTags_returnsFalse() {
        UniqueTagListContainsTagsPredicate predicate = new
                UniqueTagListContainsTagsPredicate(TAG_SET_OWES_MONEY_FRIEND);
        assertFalse(predicate.test(AMY));
    }
}
```
###### \java\seedu\address\model\UniqueAppointmentListTest.java
``` java
public class UniqueAppointmentListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueAppointmentList uniqueAppointmentList = new UniqueAppointmentList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueAppointmentList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedAppointmentTest.java
``` java
public class XmlAdaptedAppointmentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DATE = "12/34";
    private static final String INVALID_START_TIME = "26:80";
    private static final String INVALID_END_TIME = "0000";
    private static final String INVALID_LOCATION = " ";

    private static final String VALID_NAME = BENSON_APPT.getName().toString();
    private static final String VALID_DATE = BENSON_APPT.getDate().toString();
    private static final String VALID_START_TIME = BENSON_APPT.getStartTime().toString();
    private static final String VALID_END_TIME = BENSON_APPT.getEndTime().toString();
    private static final String VALID_LOCATION = BENSON_APPT.getLocation().toString();


    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(BENSON_APPT);
        assertEquals(BENSON_APPT, appointment.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(INVALID_NAME, VALID_DATE, VALID_START_TIME, VALID_END_TIME, VALID_LOCATION);
        String expectedMessage = PersonName.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(null, VALID_DATE, VALID_START_TIME, VALID_END_TIME, VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PersonName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, INVALID_DATE, VALID_START_TIME, VALID_END_TIME, VALID_LOCATION);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, null, VALID_START_TIME, VALID_END_TIME, VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, VALID_DATE, INVALID_START_TIME, VALID_END_TIME, VALID_LOCATION);
        String expectedMessage = StartTime.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, VALID_DATE, null, VALID_END_TIME, VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, VALID_DATE, VALID_START_TIME, INVALID_END_TIME, VALID_LOCATION);
        String expectedMessage = EndTime.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, VALID_DATE, VALID_START_TIME, null, VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, VALID_DATE, VALID_START_TIME, VALID_END_TIME, INVALID_LOCATION);
        String expectedMessage = Location.MESSAGE_LOCATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, VALID_DATE, VALID_START_TIME, VALID_END_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedPersonTest.java
``` java
    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_DATE, VALID_TAGS);
        String expectedMessage = DateAdded.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateAdded.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
```
###### \java\seedu\address\testutil\AppointmentBuilder.java
``` java
/**
 * A utility class to help with building Person objects.
 */
public class AppointmentBuilder {
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_DATE = "03/03/2018";
    public static final String DEFAULT_START_TIME = "12:30";
    public static final String DEFAULT_END_TIME = "13:30";
    public static final String DEFAULT_LOCATION = "Parkway Parade";

    private PersonName name;
    private Date date;
    private StartTime startTime;
    private EndTime endTime;
    private Location location;

    public AppointmentBuilder() {
        name = new PersonName(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        startTime = new StartTime(DEFAULT_START_TIME);
        endTime = new EndTime(DEFAULT_END_TIME);
        location = new Location(DEFAULT_LOCATION);
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        name = appointmentToCopy.getName();
        date = appointmentToCopy.getDate();
        startTime = appointmentToCopy.getStartTime();
        endTime = appointmentToCopy.getEndTime();
        location = appointmentToCopy.getLocation();
    }

    /**
     * Sets the {@code PersonName} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withPersonName(String name) {
        this.name = new PersonName(name);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withStartTime (String startTime) {
        this.startTime = new StartTime(startTime);
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withEndTime (String endTime) {
        this.endTime = new EndTime(endTime);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    public Appointment build() {
        return new Appointment(name, date, startTime, endTime, location);
    }
}
```
###### \java\seedu\address\testutil\AppointmentUtil.java
``` java
/**
 * A utility class for Appointment
 */
public class AppointmentUtil {
    /**
     * Returns an add appointment command string for adding the {@code appointment}.
     */
    public static String getAddAppointmentCommand(Appointment appointment) {
        return AddAppointmentCommand.COMMAND_WORD + " " + getAppointmentDetails(appointment);
    }

    /**
     * Returns an add appointment alias string for adding the {@code appointment}.
     */
    public static String getAddAppointmentAlias(Appointment appointment) {
        return AddAppointmentCommand.COMMAND_ALIAS + " " + getAppointmentDetails(appointment);
    }

    /**
     * Returns a delete appointment command string for deleting the {@code appointment}.
     */
    public static String getDeleteAppointmentCommand(Appointment appointment) {
        return DeleteAppointmentCommand.COMMAND_WORD + " " + getAppointmentDetails(appointment);
    }

    /**
     * Returns a delete appointment alias string for deleting the {@code appointment}.
     */
    public static String getDeleteAppointmentAlias(Appointment appointment) {
        return DeleteAppointmentCommand.COMMAND_ALIAS + " " + getAppointmentDetails(appointment);
    }

    /**
     * Returns the part of command string for the given {@code appointment}'s details.
     */
    public static String getAppointmentDetails(Appointment appointment) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + appointment.getName().fullName + " ");
        sb.append(PREFIX_DATE + appointment.getDate().date + " ");
        sb.append(PREFIX_STARTTIME + appointment.getStartTime().time + " ");
        sb.append(PREFIX_ENDTIME + appointment.getEndTime().time + " ");
        sb.append(PREFIX_LOCATION + appointment.getLocation().value + " ");
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Creates and returns a {@code DateAdded} with the dateAdded attribute representing the current date
     * @return current date in the following format: dd/MM/yyyy
     */
    public PersonBuilder generateDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        this.dateAdded =  new DateAdded(dateFormatter.format(calendar.getTime()));
        return this;
    }
```
###### \java\seedu\address\testutil\TypicalDates.java
``` java
/**
 * A utility class containing a list of {@code DateAdded} objects to be used in tests.
 */
public class TypicalDates {
    public static final DateAdded DATE_FIRST_JAN = new DateAdded("01/01/2018");
    public static final DateAdded DATE_SECOND_FEB = new DateAdded("02/02/2018");
    public static final DateAdded DATE_THIRD_MAR = new DateAdded("03/03/2018");
    public static final String VALID_DATE_DESC = " " + PREFIX_DATE + "01/01/2018";
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "12/34";
}
```
###### \java\seedu\address\testutil\TypicalPersonsAndAppointments.java
``` java
    public static final Appointment ALICE_APPT = new AppointmentBuilder().withPersonName("Alice Pauline")
            .withDate("01/01/2018").withStartTime("10:30").withEndTime("11:30")
            .withLocation("123, Jurong West Ave 6, #08-111").build();
    public static final Appointment BENSON_APPT = new AppointmentBuilder().withPersonName("Benson Meier")
            .withDate("02/02/2018").withStartTime("10:30").withEndTime("11:30")
            .withLocation("311, Clementi Ave 2, #02-25").build();
    public static final Appointment CARL_APPT = new AppointmentBuilder().withPersonName("Carl Kurz")
            .withDate("03/03/2018").withStartTime("10:30").withEndTime("11:30").withLocation("wall street").build();
    public static final Appointment DANIEL_APPT = new AppointmentBuilder().withPersonName("Daniel Meier")
            .withDate("04/04/2018").withStartTime("10:30").withEndTime("11:30").withLocation("10th street").build();
    public static final Appointment ELLE_APPT = new AppointmentBuilder().withPersonName("Elle Meyer")
            .withDate("05/05/2018").withStartTime("10:30").withEndTime("11:30").withLocation("michegan ave").build();
    public static final Appointment FIONA_APPT = new AppointmentBuilder().withPersonName("Fiona Kunz")
            .withDate("06/06/2018").withStartTime("10:30").withEndTime("11:30").withLocation("little tokyo").build();
    public static final Appointment GEORGE_APPT = new AppointmentBuilder().withPersonName("George Best")
            .withDate("07/07/2018").withStartTime("10:30").withEndTime("11:30").withLocation("4th street").build();
```
###### \java\seedu\address\testutil\TypicalPersonsAndAppointments.java
``` java
    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(
                Arrays.asList(ALICE_APPT, BENSON_APPT, CARL_APPT, DANIEL_APPT, ELLE_APPT, FIONA_APPT, GEORGE_APPT));
    }
```
###### \java\seedu\address\testutil\TypicalPersonsAndAppointmentsWithAutoDateGeneration.java
``` java
/**
 * A utility class containing a list of {@code Person} objects to be used in tests where all the {@code DateAdded}s are
 * automatically generated by the system.
 */
public class TypicalPersonsAndAppointmentsWithAutoDateGeneration {
    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com").withPhone("85355255")
            .generateDate().withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").generateDate().withEmail("johnd@example.com")
            .withPhone("98765432").withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").generateDate().build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").generateDate().build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").generateDate().build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").generateDate().build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").generateDate().build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").generateDate().build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").generateDate().build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).generateDate()
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).generateDate()
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    public static final Appointment ALICE_APPT = new AppointmentBuilder().withPersonName("Alice Pauline")
            .withDate("01/01/2018").withStartTime("10:30").withEndTime("11:30")
            .withLocation("123, Jurong West Ave 6, #08-111").build();
    public static final Appointment BENSON_APPT = new AppointmentBuilder().withPersonName("Benson Meier")
            .withDate("02/02/2018").withStartTime("10:30").withEndTime("11:30")
            .withLocation("311, Clementi Ave 2, #02-25").build();
    public static final Appointment CARL_APPT = new AppointmentBuilder().withPersonName("Carl Kurz")
            .withDate("03/03/2018").withStartTime("10:30").withEndTime("11:30").withLocation("wall street").build();
    public static final Appointment DANIEL_APPT = new AppointmentBuilder().withPersonName("Daniel Meier")
            .withDate("04/04/2018").withStartTime("10:30").withEndTime("11:30").withLocation("10th street").build();
    public static final Appointment ELLE_APPT = new AppointmentBuilder().withPersonName("Elle Meyer")
            .withDate("05/05/2018").withStartTime("10:30").withEndTime("11:30").withLocation("michegan ave").build();
    public static final Appointment FIONA_APPT = new AppointmentBuilder().withPersonName("Fiona Kunz")
            .withDate("06/06/2018").withStartTime("10:30").withEndTime("11:30").withLocation("little tokyo").build();
    public static final Appointment GEORGE_APPT = new AppointmentBuilder().withPersonName("George Best")
            .withDate("07/07/2018").withStartTime("10:30").withEndTime("11:30").withLocation("4th street").build();

    private TypicalPersonsAndAppointmentsWithAutoDateGeneration() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons with auto date generation.
     */
    public static AddressBook getTypicalAddressBookWithAutoDateGeneration() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersonsWithAutoDateGeneration()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        for (Appointment appointment : getTypicalAppointments()) {
            try {
                ab.addAppointment(appointment);
            } catch (DuplicateAppointmentException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Person> getTypicalPersonsWithAutoDateGeneration() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(
                Arrays.asList(ALICE_APPT, BENSON_APPT, CARL_APPT, DANIEL_APPT, ELLE_APPT, FIONA_APPT, GEORGE_APPT));
    }
}
```
###### \java\seedu\address\testutil\TypicalTags.java
``` java
/**
 * A utility class containing a list of {@code Tag} objects to be used in tests.
 */
public class TypicalTags {
    public static final Tag TAG_HUSBAND = new Tag("husband");
    public static final Tag TAG_FRIEND = new Tag("friends");
    public static final Tag TAG_OWES_MONEY = new Tag("owesMoney");
    public static final Set<Tag> TAG_SET_OWES_MONEY_FRIEND = new HashSet<>(Arrays.asList(TAG_OWES_MONEY, TAG_FRIEND));
    public static final Set<Tag> TAG_SET_FRIEND = new HashSet<>(Arrays.asList(TAG_FRIEND));
    public static final Set<Tag> TAG_SET_HUSBAND = new HashSet<>(Arrays.asList(TAG_HUSBAND));
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*";
    public static final String VALID_TAG_DESC_FRIEND = " " + PREFIX_TAG + "friends";
    public static final String VALID_TAG_DESC_OWES_MONEY = " " + PREFIX_TAG + "owesMoney";

}
```
###### \java\systemtests\DeleteBeforeAliasSystemTest.java
``` java
public class DeleteBeforeAliasSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_BEFORE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteBeforeCommand.MESSAGE_USAGE);

    @Test
    public void deleteBefore() {
        /* --------------------------------- Performing delete before operation  ------------------------------------ */

        /* Case: delete persons in the list, command with leading spaces and trailing spaces -> deleted */
        Model modelBeforeDeleting = getModel();
        Model modelAfterDeleting = getModel();
        String command = "     " + DeleteBeforeCommand.COMMAND_ALIAS + "      " + VALID_DATE_DESC
                + "       " + VALID_TAG_DESC_FRIEND;
        List<Person> deleteTargets = Arrays.asList(ALICE);
        removePersons(modelAfterDeleting, deleteTargets);
        String expectedResultMessage = String.format(
                MESSAGE_DELETE_PERSONS_SUCCESS, deleteTargets.size(), TAG_SET_FRIEND, DATE_FIRST_JAN);
        assertCommandSuccess(command, modelAfterDeleting, expectedResultMessage);

        /* Case: undo deleting the persons in the list -> deleted persons restored */
        command = UndoCommand.COMMAND_ALIAS;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeleting, expectedResultMessage);

        /* Case: redo deleting the persons in the list -> restored persons deleted again */
        command = RedoCommand.COMMAND_ALIAS;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelAfterDeleting, expectedResultMessage);


        /* ------------------------------ Performing invalid delete before operation -------------------------------- */

        /* Case: invalid date -> rejected */
        command = DeleteBeforeCommand.COMMAND_ALIAS + INVALID_DATE_DESC + VALID_TAG_DESC_OWES_MONEY;
        assertCommandFailure(command, DateAdded.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = DeleteBeforeCommand.COMMAND_ALIAS + VALID_DATE_DESC + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: missing date -> rejected */
        command = DeleteBeforeCommand.COMMAND_ALIAS + VALID_TAG_DESC_OWES_MONEY;
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_BEFORE_COMMAND_FORMAT);

        /* Case: missing tag -> rejected */
        command = DeleteBeforeCommand.COMMAND_ALIAS + VALID_DATE_DESC;
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_BEFORE_COMMAND_FORMAT);

        /* Case: no such person exists -> rejected */
        command = DeleteBeforeCommand.COMMAND_ALIAS + VALID_DATE_DESC + VALID_TAG_DESC_OWES_MONEY;
        assertCommandFailure(command, MESSAGE_PERSONS_NOT_FOUND);
    }

    /**
     * Removes all {@code Person}s in {@code model}'s address book specified by the {@code targets} list.
     */
    private void removePersons(Model model, List<Person> targets) {
        try {
            model.deletePersons(targets);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        }
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
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
###### \java\systemtests\DeleteBeforeCommandSystemTest.java
``` java
public class DeleteBeforeCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_BEFORE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteBeforeCommand.MESSAGE_USAGE);

    @Test
    public void deleteBefore() {
        /* --------------------------------- Performing delete before operation  ------------------------------------ */

        /* Case: delete persons in the list, command with leading spaces and trailing spaces -> deleted */
        Model modelBeforeDeleting = getModel();
        Model modelAfterDeleting = getModel();
        String command = "     " + DeleteBeforeCommand.COMMAND_WORD + "      " + VALID_DATE_DESC
                + "       " + VALID_TAG_DESC_FRIEND;
        List<Person> deleteTargets = Arrays.asList(ALICE);
        removePersons(modelAfterDeleting, deleteTargets);
        String expectedResultMessage = String.format(
                MESSAGE_DELETE_PERSONS_SUCCESS, deleteTargets.size(), TAG_SET_FRIEND, DATE_FIRST_JAN);
        assertCommandSuccess(command, modelAfterDeleting, expectedResultMessage);

        /* Case: undo deleting the persons in the list -> deleted persons restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeleting, expectedResultMessage);

        /* Case: redo deleting the persons in the list -> restored persons deleted again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelAfterDeleting, expectedResultMessage);


        /* ------------------------------ Performing invalid delete before operation -------------------------------- */

        /* Case: invalid date -> rejected */
        command = DeleteBeforeCommand.COMMAND_WORD + INVALID_DATE_DESC + VALID_TAG_DESC_OWES_MONEY;
        assertCommandFailure(command, DateAdded.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = DeleteBeforeCommand.COMMAND_WORD + VALID_DATE_DESC + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: missing date -> rejected */
        command = DeleteBeforeCommand.COMMAND_WORD + VALID_TAG_DESC_OWES_MONEY;
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_BEFORE_COMMAND_FORMAT);

        /* Case: missing tag -> rejected */
        command = DeleteBeforeCommand.COMMAND_WORD + VALID_DATE_DESC;
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_BEFORE_COMMAND_FORMAT);

        /* Case: no such person exists -> rejected */
        command = DeleteBeforeCommand.COMMAND_WORD + VALID_DATE_DESC + VALID_TAG_DESC_OWES_MONEY;
        assertCommandFailure(command, MESSAGE_PERSONS_NOT_FOUND);
    }

    /**
     * Removes all {@code Person}s in {@code model}'s address book specified by the {@code targets} list.
     */
    private void removePersons(Model model, List<Person> targets) {
        try {
            model.deletePersons(targets);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Targets not found in model.");
        }
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
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
###### \java\systemtests\EditAliasSystemTest.java
``` java
        /* Case: edit a person with new values same as another person's values -> rejected */
        executeCommand(PersonUtil.getAddCommand(BOB));
        Person newBob = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).generateDate()
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        assertTrue(getModel().getAddressBook().getPersonList().contains(newBob));
        executeCommand(PersonUtil.getAddCommand(AMY));
        index = Index.fromOneBased(getModel().getFilteredPersonList().size());
        assertFalse(getModel().getFilteredPersonList().get(index.getZeroBased()).equals(BOB));
        command = EditCommand.COMMAND_ALIAS + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a person with new values same as another person's values but with different tags -> rejected */
        command = EditCommand.COMMAND_ALIAS + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* Case: edit a person with new values same as another person's values -> rejected */
        executeCommand(PersonUtil.getAddCommand(BOB));
        Person newBob = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).generateDate()
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        assertTrue(getModel().getAddressBook().getPersonList().contains(newBob));
        executeCommand(PersonUtil.getAddCommand(AMY));
        index = Index.fromOneBased(getModel().getFilteredPersonList().size());
        assertFalse(getModel().getFilteredPersonList().get(index.getZeroBased()).equals(BOB));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a person with new values same as another person's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
```
