# yuxiangSg
###### \java\seedu\address\logic\commands\AddAppointmentCommandTest.java
``` java
public class AddAppointmentCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_appointmentEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAppointmentCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        AddAppointmentCommandTest.ModelStubAcceptingAppointmentAdded modelStub =
                new AddAppointmentCommandTest.ModelStubAcceptingAppointmentAdded();

        AppointmentEntry validAppointment = new AppointmentBuilder().build();

        CommandResult commandResult = getAddCommandForAppointment(validAppointment, modelStub).execute();

        assertEquals(String.format(AddAppointmentCommand.MESSAGE_SUCCESS, validAppointment),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAppointment), modelStub.appointmentAdded);
    }

    @Test
    public void execute_duplicateAppointment_throwsCommandException() throws Exception {
        AddAppointmentCommandTest.ModelStub modelStub =
                new AddAppointmentCommandTest.ModelStubThrowingDuplicateAppointmentException();
        AppointmentEntry validAppointment = new AppointmentBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT);

        getAddCommandForAppointment(validAppointment, modelStub).execute();
    }

    @Test
    public void equals() {
        AppointmentEntry john = new AppointmentBuilder().withTitle("John").build();
        AppointmentEntry josh = new AppointmentBuilder().withTitle("Josh").build();

        AddAppointmentCommand addJohnCommand = new AddAppointmentCommand(john);
        AddAppointmentCommand addJoshCommand = new AddAppointmentCommand(josh);

        // same object -> returns true
        assertTrue(addJohnCommand.equals(addJohnCommand));

        // same values -> returns true
        AppointmentEntry johnCopy = new AppointmentBuilder().withTitle("John").build();
        AddAppointmentCommand addJohnCommandCopy = new AddAppointmentCommand(johnCopy);
        assertTrue(addJohnCommand.equals(addJohnCommandCopy));

        // different types -> returns false
        assertFalse(addJohnCommand.equals(1));

        // null -> returns false
        assertFalse(addJohnCommand.equals(null));

        // different person -> returns false
        assertFalse(addJohnCommand.equals(addJoshCommand));
    }

    /**
     * Generates a new AddAppointmentCommand with the details of the given appointment.
     */
    private AddAppointmentCommand getAddCommandForAppointment(AppointmentEntry entry, Model model) {
        AddAppointmentCommand command = new AddAppointmentCommand(entry);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addAppointment(AppointmentEntry appointmentEntry) throws DuplicateAppointmentException {
            fail("This method should not be called.");
        }

        @Override
        public void removeAppointment(String searchText) throws AppointmentNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void editAppointment(String searchText, AppointmentEntry reference) throws EditAppointmentFailException {
            fail("This method should not be called.");
        }

        @Override
        public AppointmentEntry findAppointment(String searchText) throws AppointmentNotFoundException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public CalendarSource getCalendar() {
            fail("This method should not be called.");
            return null;
        }

        public ArrayList<ArrayList<Double>> getPersonAttrMatrix() {
            return null;
        }
    }

    /**
     * A Model stub that always throw a DuplicateAppointmentException when trying to add an appointment.
     */
    private class ModelStubThrowingDuplicateAppointmentException extends AddAppointmentCommandTest.ModelStub {

        @Override
        public void addAppointment(AppointmentEntry appointmentEntry) throws DuplicateAppointmentException {
            throw new DuplicateAppointmentException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the appointment being added.
     */
    private class ModelStubAcceptingAppointmentAdded extends AddAppointmentCommandTest.ModelStub {
        final ArrayList<AppointmentEntry> appointmentAdded = new ArrayList<>();

        @Override
        public void addAppointment(AppointmentEntry appointmentEntry) throws DuplicateAppointmentException {
            requireNonNull(appointmentEntry);
            appointmentAdded.add(appointmentEntry);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### \java\seedu\address\logic\commands\EditAppointmentCommandTest.java
``` java
public class EditAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAppointmentAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() throws Exception {
        String validSearchText = CommandTestUtil.VALID_TITLE_JOHN;
        String newTitle = "meet YX";
        LocalDateTime newStartDateTime = LocalDateTime.of(2018, 04, 04, 13, 00);
        LocalDateTime newEndDateTime = LocalDateTime.of(2018, 04, 04, 14, 00);
        AppointmentEntry updatedEntry = new AppointmentEntry(newTitle, new Interval(newStartDateTime, newEndDateTime));

        EditAppointmentCommand.EditAppointmentDescriptor descriptor =
                new EditAppointmentCommand.EditAppointmentDescriptor();
        descriptor.setGivenTitle(newTitle);
        descriptor.setStartDateTime(newStartDateTime);
        descriptor.setEndDateTime(newEndDateTime);

        EditAppointmentCommand editAppointmentCommand = prepareCommand(validSearchText, descriptor);

        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_SUCCESS, updatedEntry);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.editAppointment(validSearchText, updatedEntry);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() throws Exception {
        String validSearchText = CommandTestUtil.VALID_TITLE_JOHN;
        String newTitle = "meet YX";
        AppointmentEntry updatedEntry = new AppointmentBuilder().withTitle(newTitle).build();

        EditAppointmentCommand.EditAppointmentDescriptor descriptor =
                new EditAppointmentCommand.EditAppointmentDescriptor();
        descriptor.setGivenTitle(newTitle);

        EditAppointmentCommand editAppointmentCommand = prepareCommand(validSearchText, descriptor);

        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_SUCCESS, updatedEntry);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.editAppointment(validSearchText, updatedEntry);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns an {@code EditAppointmentCommand} with parameters {@code SearchText} and {@code descriptor}
     */
    private EditAppointmentCommand prepareCommand(String searchText,
                                                  EditAppointmentCommand.EditAppointmentDescriptor descriptor) {
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(searchText, descriptor);
        editAppointmentCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editAppointmentCommand;
    }
}
```
###### \java\seedu\address\logic\commands\RemoveAppointmentCommandTest.java
``` java
public class RemoveAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAppointmentAddressBook(), new UserPrefs());

    @Test
    public void execute_validSearchText_success() throws Exception {
        String searchText = "meet john";
        RemoveAppointmentsCommand removeAppointmentsCommand = prepareCommand(searchText);

        String expectedMessage = String.format(RemoveAppointmentsCommand.MESSAGE_SUCCESS, searchText);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeAppointment(searchText);

        assertCommandSuccess(removeAppointmentsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSearchText_throwsCommandException() {
        String searchText = "meet YX";
        RemoveAppointmentsCommand removeAppointmentsCommand = prepareCommand(searchText);

        String expectedMessage = String.format(RemoveAppointmentsCommand.MESSAGE_NO_SUCH_APPOINTMENT);

        assertCommandFailure(removeAppointmentsCommand, model, expectedMessage);
    }

    /**
     * Returns a {@code RemoveAppointmentsCommand} with the parameter {@code searchText}.
     */
    private RemoveAppointmentsCommand prepareCommand(String searchText) {
        RemoveAppointmentsCommand removeAppointmentsCommand = new RemoveAppointmentsCommand(searchText);
        removeAppointmentsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeAppointmentsCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddAppointmentParserTest.java
``` java
public class AddAppointmentParserTest {
    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AppointmentEntry expectedEntry = TypicalAppointmentEntires.MEET_JOHN;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_MEET_JOHN + START_DATE_DESC
                + END_DATE_DESC, new AddAppointmentCommand(expectedEntry));
    }

    @Test
    public void parse_fieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, START_DATE_DESC + END_DATE_DESC, expectedMessage);

        // missing start interval prefix
        assertParseFailure(parser, TITLE_DESC_MEET_JOHN + END_DATE_DESC, expectedMessage);

        // missing end interval prefix
        assertParseFailure(parser, TITLE_DESC_MEET_JOHN + START_DATE_DESC,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_JOHN + VALID_DATE_TIME_START + VALID_DATE_TIME_END,
                expectedMessage);
    }

    @Test
    public void parse_invalidDateFormat_failure() {

        assertParseFailure(parser, TITLE_DESC_MEET_JOHN + INVALID_DATE_TIME_START_DESC
                + INVALID_DATE_TIME_END_DESC, AppointmentEntry.MESSAGE_DATE_TIME_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedAppointmentEntryTest.java
``` java
public class XmlAdaptedAppointmentEntryTest {

    @Test
    public void toModelType_validAppointDetails_returnsAppointment() throws Exception {
        XmlAdptedAppointmentEntry entry = new XmlAdptedAppointmentEntry(MEET_JAMES);
        assertEquals(MEET_JAMES, entry.toModelType());
    }
}
```
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    @Test
    public void readAddressBook_invalidAppointmentAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAppointmentAddressBook.fxml");
    }
```
###### \java\seedu\address\testutil\AppointmentBuilder.java
``` java
/**
 * A utility class to help with building AppointmentEntry objects.
 */
public class AppointmentBuilder {
    public static final String DEFAULT_TITLE = "Meet John";
    public static final LocalDateTime DEFAULT_START_DATE_TIME = LocalDateTime.of
            (2018, 04, 04, 12, 00);

    public static final LocalDateTime DEFAULT_END_DATE_TIME = LocalDateTime.of
            (2018, 04, 04, 13, 00);

    private Entry appointmentEntry;
    private Interval interval;
    private String givenTitle;

    public AppointmentBuilder() {
        givenTitle = DEFAULT_TITLE;
        interval = new Interval(DEFAULT_START_DATE_TIME, DEFAULT_END_DATE_TIME);
        appointmentEntry = new Entry(givenTitle, interval);
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */

    public AppointmentBuilder(AppointmentEntry appointmentToCopy) {
        givenTitle = appointmentToCopy.getGivenTitle();
        interval = new Interval(appointmentToCopy.getStartDateTime(), appointmentToCopy.getEndDateTime());
        appointmentEntry = new Entry(givenTitle, interval);
    }

    /**
     * Sets the {@code givenTitle} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withTitle(String title) {
        givenTitle = title;
        return this;
    }

    /**
     * Sets the {@code title} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withInterval(Interval interval) {
        this.interval = interval;
        return this;
    }

    public AppointmentEntry build() {
        return new AppointmentEntry(givenTitle, interval);
    }


}
```
###### \java\seedu\address\testutil\AppointmentUtil.java
``` java
/**
 * A utility class for AppointmentEntry.
 */
public class AppointmentUtil {
    /**
     * Returns an addAppointment command string for adding the {@code entry}.
     */
    public static String getAddAppointmentCommand(AppointmentEntry entry) {
        return AddAppointmentCommand.COMMAND_WORD + " " + geEntryDetails(entry);
    }

    /**
     * Returns the part of command string for the given {@code entry}'s details.
     */
    public static String geEntryDetails(AppointmentEntry entry) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppointmentEntry.DATE_VALIDATION);
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + entry.getGivenTitle() + " ");
        sb.append(PREFIX_START_INTERVAL + entry.getStartDateTime().format(formatter) + " ");
        sb.append(PREFIX_END_INTERVAL  + entry.getEndDateTime().format(formatter));

        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\TypicalAppointmentEntires.java
``` java
/**
 * A utility class containing a list of {@code AppointmentEntry} objects to be used in tests.
 */
public class TypicalAppointmentEntires {

    public static final AppointmentEntry MEET_JOHN = new AppointmentBuilder().withTitle("meet john").build();

    public static final AppointmentEntry MEET_JAMES = new AppointmentBuilder().withTitle("meet james").build();

    public static final AppointmentEntry MEET_JOSH = new AppointmentBuilder().withTitle("meet josh").build();

    public static final String KEYWORD_MATCHING_YX = "yx"; // A keyword that matches YX

    private TypicalAppointmentEntires(){

    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAppointmentAddressBook() {
        AddressBook ab = new AddressBook();
        for (AppointmentEntry entry : getTypicalAppointmentEntries()) {
            try {
                ab.addAppointment(entry);
            } catch (DuplicateAppointmentException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static ArrayList<AppointmentEntry> getTypicalAppointmentEntries() {
        return new ArrayList<>(Arrays.asList(MEET_JAMES, MEET_JOHN, MEET_JOSH));
    }




}
```
