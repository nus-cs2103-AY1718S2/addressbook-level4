# trafalgarandre
###### /test/java/seedu/address/model/appointment/StartDateTimeTest.java
``` java
public class StartDateTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new StartDateTime(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidStartDateTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new StartDateTime(invalidStartDateTime));
    }

    @Test
    public void isValidStartDateTime() {
        // null startDateTime
        Assert.assertThrows(NullPointerException.class, () -> StartDateTime.isValidStartDateTime(null));

        // invalid startDateTime
        assertFalse(StartDateTime.isValidStartDateTime("")); // empty string
        assertFalse(StartDateTime.isValidStartDateTime(" ")); // spaces only
        assertFalse(StartDateTime.isValidStartDateTime("abc")); // random string
        assertFalse(StartDateTime.isValidStartDateTime("23-09-2018 12:00")); // wrong order
        assertFalse(StartDateTime.isValidStartDateTime("2018-23-09")); // missing time

        // valid startDateTime
        assertTrue(StartDateTime.isValidStartDateTime("2018-26-03 12:00"));
    }
}
```
###### /test/java/seedu/address/model/appointment/TitleTest.java
``` java
public class TitleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_invalidTitle_throwsIllegalArgumentException() {
        String invalidTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Title(invalidTitle));
    }

    @Test
    public void isValidTitle() {
        // null title
        Assert.assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid title
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only

        // valid title
        assertTrue(Title.isValidTitle("meeting")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("meeting the 2nd")); // alphanumeric characters
        assertTrue(Title.isValidTitle("Meeting")); // with capital letters
        assertTrue(Title.isValidTitle("Meeting with Christiano Ronaldo")); // long titles
    }
}
```
###### /test/java/seedu/address/model/appointment/EndDateTimeTest.java
``` java
public class EndDateTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EndDateTime(null));
    }

    @Test
    public void constructor_invalidEndDateTime_throwsIllegalArgumentException() {
        String invalidEndDateTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EndDateTime(invalidEndDateTime));
    }

    @Test
    public void isValidEndDateTime() {
        // null endDateTime
        Assert.assertThrows(NullPointerException.class, () -> EndDateTime.isValidEndDateTime(null));

        // invalid endDateTime
        assertFalse(EndDateTime.isValidEndDateTime("")); // empty string
        assertFalse(EndDateTime.isValidEndDateTime(" ")); // spaces only
        assertFalse(EndDateTime.isValidEndDateTime("abc")); // random string
        assertFalse(EndDateTime.isValidEndDateTime("23-09-2018 12:00")); // wrong order
        assertFalse(EndDateTime.isValidEndDateTime("2018-23-09")); // missing time

        // valid endDateTime
        assertTrue(EndDateTime.isValidEndDateTime("2018-26-03 12:00"));
    }
}
```
###### /test/java/seedu/address/testutil/AppointmentBuilder.java
``` java
/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {
    public static final String DEFAULT_TITLE = "Meeting";
    public static final String DEFAULT_START_DATE_TIME = "2018-03-26 14:00";
    public static final String DEFAULT_END_DATE_TIME = "2018-03-26 16:00";

    private Title title;
    private StartDateTime startDateTime;
    private EndDateTime endDateTime;

    public AppointmentBuilder() {
        title = new Title(DEFAULT_TITLE);
        startDateTime = new StartDateTime(DEFAULT_START_DATE_TIME);
        endDateTime = new EndDateTime(DEFAULT_END_DATE_TIME);
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        title = appointmentToCopy.getTitle();
        startDateTime = appointmentToCopy.getStartDateTime();
        endDateTime = appointmentToCopy.getEndDateTime();
    }

    /**
     * Sets the {@code Title} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code StartDateTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withStartDateTime(String startDateTime) {
        this.startDateTime = new StartDateTime(startDateTime);
        return this;
    }

    /**
     * Sets the {@code EndDateTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withEndDateTime(String endDateTime) {
        this.endDateTime = new EndDateTime(endDateTime);
        return this;
    }

    public Appointment build() {
        return new Appointment(title, startDateTime, endDateTime);
    }
}
```
###### /test/java/seedu/address/testutil/EditAppointmentDescriptorBuilder.java
``` java
/**
 * A utility class to help with building EditAppointmentDescriptor objects.
 */
public class EditAppointmentDescriptorBuilder {
}
```
###### /test/java/seedu/address/storage/XmlAdaptedAppointmentTest.java
``` java
public class XmlAdaptedAppointmentTest {
    private static final String INVALID_TITLE = "";
    private static final String INVALID_START_DATE_TIME = "2018-20-03";
    private static final String INVALID_END_DATE_TIME = "20-03-2018 08:00";

    private static final String VALID_TITLE = BIRTHDAY.getTitle().toString();
    private static final String VALID_START_DATE_TIME = BIRTHDAY.getStartDateTime().toString();
    private static final String VALID_END_DATE_TIME = BIRTHDAY.getEndDateTime().toString();

    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(BIRTHDAY);
        assertEquals(BIRTHDAY, appointment.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(INVALID_TITLE, VALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String expectedMessage = Title.MESSAGE_TITLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(null, VALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidStartDateTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_TITLE, INVALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String expectedMessage = StartDateTime.MESSAGE_START_DATE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullStartDateTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appoinment =
                new XmlAdaptedAppointment(VALID_TITLE, null, VALID_END_DATE_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartDateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appoinment::toModelType);
    }

    @Test
    public void toModelType_invalidEndDateTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_TITLE, VALID_START_DATE_TIME, INVALID_END_DATE_TIME);
        String expectedMessage = EndDateTime.MESSAGE_END_DATE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullEndDateTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_TITLE, VALID_START_DATE_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndDateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }
}
```
###### /test/java/seedu/address/logic/parser/AddAppointmentParserTest.java
``` java
public class AddAppointmentParserTest {
    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Appointment expectedAppointment = new AppointmentBuilder().withTitle(VALID_TITLE_INTERVIEW2)
                .withStartDateTime(VALID_START_DATE_TIME_INTERVIEW2).withEndDateTime(VALID_END_DATE_TIME_INTERVIEW2)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_INTERVIEW2
                + START_DATE_TIME_DESC_INTERVIEW2 + END_DATE_TIME_DESC_INTERVIEW2,
                new AddAppointmentCommand(expectedAppointment));

        // multiple title - last title accepted
        assertParseSuccess(parser, TITLE_DESC_INTERVIEW1 + TITLE_DESC_INTERVIEW2
                + START_DATE_TIME_DESC_INTERVIEW2 + END_DATE_TIME_DESC_INTERVIEW2,
                new AddAppointmentCommand(expectedAppointment));

        // multiple start date time - last start date time accepted
        assertParseSuccess(parser, TITLE_DESC_INTERVIEW2 + START_DATE_TIME_DESC_INTERVIEW1
                + START_DATE_TIME_DESC_INTERVIEW2 + END_DATE_TIME_DESC_INTERVIEW2,
                new AddAppointmentCommand(expectedAppointment));

        // multiple end date time - last end date time accepted
        assertParseSuccess(parser, TITLE_DESC_INTERVIEW2 + START_DATE_TIME_DESC_INTERVIEW2
                + END_DATE_TIME_DESC_INTERVIEW1 + END_DATE_TIME_DESC_INTERVIEW2,
                new AddAppointmentCommand(expectedAppointment));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_TITLE_INTERVIEW2 + START_DATE_TIME_DESC_INTERVIEW2
                + END_DATE_TIME_DESC_INTERVIEW2, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, TITLE_DESC_INTERVIEW2 + VALID_START_DATE_TIME_INTERVIEW2
                + END_DATE_TIME_DESC_INTERVIEW2, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, TITLE_DESC_INTERVIEW2 + START_DATE_TIME_DESC_INTERVIEW2
                + VALID_END_DATE_TIME_INTERVIEW2, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_INTERVIEW2 + VALID_START_DATE_TIME_INTERVIEW2
                + VALID_END_DATE_TIME_INTERVIEW2 , expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC + START_DATE_TIME_DESC_INTERVIEW2
                + END_DATE_TIME_DESC_INTERVIEW2, Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid start date time
        assertParseFailure(parser, TITLE_DESC_INTERVIEW2 + INVALID_START_DATE_TIME
                + END_DATE_TIME_DESC_INTERVIEW2, StartDateTime.MESSAGE_START_DATE_TIME_CONSTRAINTS);

        // invalid end date time
        assertParseFailure(parser, TITLE_DESC_INTERVIEW2 + START_DATE_TIME_DESC_INTERVIEW2
                + INVALID_END_DATE_TIME, EndDateTime.MESSAGE_END_DATE_TIME_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TITLE_DESC + START_DATE_TIME_DESC_INTERVIEW2
                + END_DATE_TIME_DESC_INTERVIEW2, Title.MESSAGE_TITLE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TITLE_DESC_INTERVIEW2
                        + START_DATE_TIME_DESC_INTERVIEW2 + END_DATE_TIME_DESC_INTERVIEW2,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE));
    }
}
```
###### /test/java/seedu/address/logic/parser/MonthCommandParserTest.java
``` java
public class MonthCommandParserTest {

    private MonthCommandParser parser = new MonthCommandParser();

    @Test
    public void parse_validArgs_returnsMonthCommand() {
        assertParseSuccess(parser, "", new MonthCommand(null));
        assertParseSuccess(parser, "2018-04", new MonthCommand(YearMonth.parse("2018-04")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "2018-4",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MonthCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "04-2018",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MonthCommand.MESSAGE_USAGE));
    }
}
```
###### /test/java/seedu/address/logic/parser/YearCommandParserTest.java
``` java
public class YearCommandParserTest {

    private YearCommandParser parser = new YearCommandParser();

    @Test
    public void parse_validArgs_returnsDateCommand() {
        assertParseSuccess(parser, "", new YearCommand(null));
        assertParseSuccess(parser, "2018", new YearCommand(Year.parse("2018")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "2018-04-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, YearCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, YearCommand.MESSAGE_USAGE));
    }
}
```
###### /test/java/seedu/address/logic/parser/DateCommandParserTest.java
``` java
public class DateCommandParserTest {

    private DateCommandParser parser = new DateCommandParser();

    @Test
    public void parse_validArgs_returnsDateCommand() {
        assertParseSuccess(parser, "", new DateCommand(null));
        assertParseSuccess(parser, "2018-04-01", new DateCommand(LocalDate.parse("2018-04-01")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "2018-04-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "01-04-2018",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE));
    }
}
```
###### /test/java/seedu/address/logic/commands/CalendarCommandTest.java
``` java
public class CalendarCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();


    @Test
    public void execute() {
        CalendarCommand calendarCommand = new CalendarCommand();

        // write success Message
        CommandResult commandResult = calendarCommand.execute();
        assertEquals(String.format(CalendarCommand.MESSAGE_SUCCESS), commandResult.feedbackToUser);

        // change to right tab
        SwitchTabRequestEvent lastEvent = (SwitchTabRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(CalendarCommand.TAB_ID, lastEvent.tabId);
    }
}
```
###### /test/java/seedu/address/logic/commands/AddAppointmentCommandTest.java
``` java
public class AddAppointmentCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAppointmentCommand(null);
    }

    @Test
    public void execute_appointmentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();
        Appointment validAppointment = new AppointmentBuilder().build();

        CommandResult commandResult = getAddAppointmentCommandForAppointment(validAppointment, modelStub).execute();

        assertEquals(String.format(AddAppointmentCommand.MESSAGE_SUCCESS, validAppointment),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAppointment), modelStub.appointmentsAdded);
    }

    @Test
    public void execute_duplicateAppointment_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateAppointmentException();
        Appointment validAppointment = new AppointmentBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT);

        getAddAppointmentCommandForAppointment(validAppointment, modelStub).execute();
    }

    @Test
    public void equals() {
        Appointment birthday = new AppointmentBuilder().withTitle("Birthday").build();
        Appointment meeting = new AppointmentBuilder().withTitle("Meeting").build();
        AddAppointmentCommand addBirthdayCommand = new AddAppointmentCommand(birthday);
        AddAppointmentCommand addMeetingCommand = new AddAppointmentCommand(meeting);

        // same object -> returns true
        assertTrue(addBirthdayCommand.equals(addBirthdayCommand));

        // same values -> returns true
        AddAppointmentCommand addBirthdayCommandCopy = new AddAppointmentCommand(birthday);
        assertTrue(addBirthdayCommand.equals(addBirthdayCommandCopy));

        // different types -> returns false
        assertFalse(addBirthdayCommand.equals(1));

        // null -> returns false
        assertFalse(addBirthdayCommand.equals(null));

        // different appointment -> returns false
        assertFalse(addBirthdayCommand.equals(addMeetingCommand));
    }

    /**
     * Generates a new AddAppointmentCommand with the details of the given appointments.
     */
    private AddAppointmentCommand getAddAppointmentCommandForAppointment(Appointment appointment, Model model) {
        AddAppointmentCommand command = new AddAppointmentCommand(appointment);
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
        public void deleteTag(Tag t)
                throws PersonNotFoundException, DuplicatePersonException, UniqueTagList.DuplicateTagException {
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
        public AccountsManager getAccountsManager() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void register(String username, String password) throws DuplicateUsernameException {
            fail("This method should not be called.");
        }

        @Override
        public void addJob(Job job) throws DuplicateJobException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Job> getFilteredJobList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredJobList(Predicate<Job> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteAppointment(Appointment target) throws AppointmentNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public void addAppointment(Appointment appointment) throws DuplicateAppointmentException {
            fail("This method should not be called");
        }

        @Override
        public void updateAppointment(Appointment target, Appointment editedPerson)
                throws DuplicateAppointmentException, AppointmentNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public List<Appointment> getAppointmentList() {
            fail("This method should not be called");
            return null;
        }

    }

    /**
     * A Model stub that always throw a DuplicateAppointmentException when trying to add a appointment.
     */
    private class ModelStubThrowingDuplicateAppointmentException extends ModelStub {
        @Override
        public void addAppointment(Appointment appointment) throws DuplicateAppointmentException {
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
    private class ModelStubAcceptingAppointmentAdded extends ModelStub {
        final ArrayList<Appointment> appointmentsAdded = new ArrayList<>();

        @Override
        public void addAppointment(Appointment person) throws DuplicateAppointmentException {
            requireNonNull(person);
            appointmentsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### /test/java/seedu/address/logic/commands/MonthCommandTest.java
``` java
public class MonthCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validMonth_success() {
        assertExecutionSuccess("2018-02");
    }

    @Test
    public void execute_invalidMonth_failure() {
        assertExecutionFailure("a", MonthCommand.MESSAGE_YEAR_MONTH_CONSTRAINTS);
    }

    @Test
    public void equals() {
        MonthCommand monthFirstCommand = new MonthCommand(FIRST_YEAR_MONTH);
        MonthCommand monthSecondCommand = new MonthCommand(SECOND_YEAR_MONTH);
        MonthCommand nullMonthCommand = new MonthCommand(null);

        // same object -> returns true
        assertTrue(monthFirstCommand.equals(monthFirstCommand));

        // same values -> returns true
        MonthCommand monthFirstCommandCopy = new MonthCommand(FIRST_YEAR_MONTH);
        assertTrue(monthFirstCommand.equals(monthFirstCommandCopy));

        // both null
        MonthCommand nullMonthCommandCopy = new MonthCommand(null);
        assertTrue(nullMonthCommand.equals(nullMonthCommandCopy));

        // different types -> returns false
        assertFalse(monthFirstCommand.equals(1));

        // null -> returns false
        assertFalse(monthFirstCommand.equals(null));

        // different date -> returns false
        assertFalse(monthFirstCommand.equals(monthSecondCommand));
    }

    /**
     * Executes a {@code monthCommand} with the given {@code month}, and checks that {@code handleShowMonthRequestEvent}
     * is raised with the correct month.
     */
    private void assertExecutionSuccess(String yearMonth) {

        try {
            MonthCommand monthCommand = prepareCommand(yearMonth);
            CommandResult commandResult = monthCommand.execute();
            assertEquals(String.format(MonthCommand.MESSAGE_SUCCESS, yearMonth),
                    commandResult.feedbackToUser);

            ShowMonthRequestEvent lastEvent =
                    (ShowMonthRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(ParserUtil.parseYearMonth(yearMonth), lastEvent.targetYearMonth);
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
    }

    /**
     * Executes a {@code MonthCommand} with the given {@code yearMonth}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String yearMonth, String expectedMessage) {

        try {
            MonthCommand monthCommand = prepareCommand(yearMonth);
            monthCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (IllegalValueException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code MonthCommand} with parameters {@code yearMonth}.
     */
    private MonthCommand prepareCommand(String yearMonth) throws IllegalValueException {
        MonthCommand monthCommand = new MonthCommand(ParserUtil.parseYearMonth(yearMonth));
        return monthCommand;
    }
}
```
###### /test/java/seedu/address/logic/commands/WeekCommandTest.java
``` java
public class WeekCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validWeek_success() {
        assertExecutionSuccess("2018 18");
    }

    @Test
    public void execute_invalidDate_failure() {
        assertExecutionFailure("2018", WeekCommand.MESSAGE_WEEK_CONSTRAINTS);
    }

    @Test
    public void equals() {
        WeekCommand weekFirstCommand = new WeekCommand(FIRST_YEAR, FIRST_WEEK);
        WeekCommand weekSecondCommand = new WeekCommand(SECOND_YEAR, SECOND_WEEK);
        WeekCommand nullWeekCommand = new WeekCommand(null, 0);

        // same object -> returns true
        assertTrue(weekFirstCommand.equals(weekFirstCommand));

        // same values -> returns true
        WeekCommand weekFirstCommandCopy = new WeekCommand(FIRST_YEAR, FIRST_WEEK);
        assertTrue(weekFirstCommand.equals(weekFirstCommandCopy));

        // both null
        WeekCommand nullWeekCommandCopy = new WeekCommand(null, 0);
        assertTrue(nullWeekCommand.equals(nullWeekCommandCopy));

        // different types -> returns false
        assertFalse(weekFirstCommand.equals(1));

        // null -> returns false
        assertFalse(weekFirstCommand.equals(null));

        // different week -> returns false
        assertFalse(weekFirstCommand.equals(weekSecondCommand));
    }

    /**
     * Executes a {@code weekCommand} with the given {@code week}, and checks that {@code handleShowWeekRequestEvent}
     * is raised with the correct date.
     */
    private void assertExecutionSuccess(String str) {

        try {
            WeekCommand weekCommand = prepareCommand(str);
            CommandResult commandResult = weekCommand.execute();
            assertEquals(String.format(WeekCommand.MESSAGE_SUCCESS, str.substring(5) + " " + str.substring(0, 4)),
                    commandResult.feedbackToUser);

            ShowWeekRequestEvent lastEvent = (ShowWeekRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(ParserUtil.parseWeek(str), lastEvent.targetWeek);
            assertEquals(ParserUtil.parseYearOfWeek(str), lastEvent.targetYear);
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
    }

    /**
     * Executes a {@code WeekCommand} with the given {@code week}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String str, String expectedMessage) {

        try {
            WeekCommand weekCommand = prepareCommand(str);
            weekCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (IllegalValueException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code WeekCommand} with parameters {@code week}.
     */
    private WeekCommand prepareCommand(String str) throws IllegalValueException {
        WeekCommand weekCommand = new WeekCommand(ParserUtil.parseYearOfWeek(str), ParserUtil.parseWeek(str));
        return weekCommand;
    }
}
```
###### /test/java/seedu/address/logic/commands/DateCommandTest.java
``` java
public class DateCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validDate_success() {
        assertExecutionSuccess("2018-02-27");
    }

    @Test
    public void execute_invalidDate_failure() {
        assertExecutionFailure("a", DateCommand.MESSAGE_DATE_CONSTRAINTS);
    }

    @Test
    public void equals() {
        DateCommand dateFirstCommand = new DateCommand(FIRST_DATE);
        DateCommand dateSecondCommand = new DateCommand(SECOND_DATE);
        DateCommand nullDateCommand = new DateCommand(null);

        // same object -> returns true
        assertTrue(dateFirstCommand.equals(dateFirstCommand));

        // same values -> returns true
        DateCommand dateFirstCommandCopy = new DateCommand(FIRST_DATE);
        assertTrue(dateFirstCommand.equals(dateFirstCommandCopy));

        // both null
        DateCommand nullDateCommandCopy = new DateCommand(null);
        assertTrue(nullDateCommand.equals(nullDateCommandCopy));

        // different types -> returns false
        assertFalse(dateFirstCommand.equals(1));

        // null -> returns false
        assertFalse(dateFirstCommand.equals(null));

        // different date -> returns false
        assertFalse(dateFirstCommand.equals(dateSecondCommand));
    }

    /**
     * Executes a {@code dateCommand} with the given {@code date}, and checks that {@code handleShowDateRequestEvent}
     * is raised with the correct date.
     */
    private void assertExecutionSuccess(String date) {

        try {
            DateCommand dateCommand = prepareCommand(date);
            CommandResult commandResult = dateCommand.execute();
            assertEquals(String.format(DateCommand.MESSAGE_SUCCESS, date),
                    commandResult.feedbackToUser);

            ShowDateRequestEvent lastEvent = (ShowDateRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(ParserUtil.parseDate(date), lastEvent.targetDate);
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
    }

    /**
     * Executes a {@code DateCommand} with the given {@code date}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String date, String expectedMessage) {

        try {
            DateCommand dateCommand = prepareCommand(date);
            dateCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (IllegalValueException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code DateCommand} with parameters {@code date}.
     */
    private DateCommand prepareCommand(String date) throws IllegalValueException {
        DateCommand dateCommand = new DateCommand(ParserUtil.parseDate(date));
        return dateCommand;
    }
}
```
###### /test/java/seedu/address/logic/commands/YearCommandTest.java
``` java
public class YearCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validYear_success() {
        assertExecutionSuccess("2018");
    }

    @Test
    public void execute_invalidYear_failure() {
        assertExecutionFailure("a", YearCommand.MESSAGE_YEAR_CONSTRAINTS);
    }

    @Test
    public void equals() {
        YearCommand yearFirstCommand = new YearCommand(FIRST_YEAR);
        YearCommand yearSecondCommand = new YearCommand(SECOND_YEAR);
        YearCommand nullYearCommand = new YearCommand(null);

        // same object -> returns true
        assertTrue(yearFirstCommand.equals(yearFirstCommand));

        // same values -> returns true
        YearCommand yearFirstCommandCopy = new YearCommand(FIRST_YEAR);
        assertTrue(yearFirstCommand.equals(yearFirstCommandCopy));

        // both null
        YearCommand nullYearCommandCopy = new YearCommand(null);
        assertTrue(nullYearCommand.equals(nullYearCommandCopy));

        // different types -> returns false
        assertFalse(yearFirstCommand.equals(1));

        // null -> returns false
        assertFalse(yearFirstCommand.equals(null));

        // different date -> returns false
        assertFalse(yearFirstCommand.equals(yearSecondCommand));
    }

    /**
     * Executes a {@code yearCommand} with the given {@code year}, and checks that {@code handleShowYearRequestEvent}
     * is raised with the correct month.
     */
    private void assertExecutionSuccess(String year) {

        try {
            YearCommand yearCommand = prepareCommand(year);
            CommandResult commandResult = yearCommand.execute();
            assertEquals(String.format(YearCommand.MESSAGE_SUCCESS, year),
                    commandResult.feedbackToUser);

            ShowYearRequestEvent lastEvent = (ShowYearRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(ParserUtil.parseYear(year), lastEvent.targetYear);
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
    }

    /**
     * Executes a {@code YearCommand} with the given {@code year}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String year, String expectedMessage) {

        try {
            YearCommand yearCommand = prepareCommand(year);
            yearCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (IllegalValueException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code YearCommand} with parameters {@code year}.
     */
    private YearCommand prepareCommand(String year) throws IllegalValueException {
        YearCommand yearCommand = new YearCommand(ParserUtil.parseYear(year));
        return yearCommand;
    }
}
```
###### /main/java/seedu/address/model/person/ProfilePicture.java
``` java
/**
 * Represents a ProfilePicture in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ProfilePicture {
    public static final String MESSAGE_PROFILEPICTURE_CONSTRAINTS =
            "Profile picture name should be a valid image name,"
                    + " and it should end with either jpeg, jpg, png, gif or bmp";
    public static final String MESSAGE_PROFILEPICTURE_NOT_EXISTS =
            "Profile picture does not exist. Please give another profile picture";

    // alphanumeric and special characters
    public static final String PROFILE_PICTURE_VALIDATION_REGEX = "^$|([^\\s]+(\\.(?i)(jpeg|jpg|png|gif|bmp))$)";
    public static final String DEFAULT_IMG_URL = "file:src/test/data/images/default.png";
    public static final String PROFILE_PICTURE_FOLDER =
            "./src/main/resources/ProfilePictures/";

    public final String filePath;
    public final String url;

    /**
     * Constructs an {@code Email}.
     *
     * @param profilePicture A valid image path.
     */
    public ProfilePicture(String... profilePicture) {
        if (profilePicture.length != 0 && profilePicture[0] != null) {
            checkArgument(isValidProfilePicture(profilePicture[0]), MESSAGE_PROFILEPICTURE_CONSTRAINTS);
            checkArgument(hasValidProfilePicture(profilePicture[0]), MESSAGE_PROFILEPICTURE_NOT_EXISTS);
            if (profilePicture[0].length() > 37
                    && profilePicture[0].substring(0, 37).equals("./src/main/resources/ProfilePictures/")) {
                this.filePath = profilePicture[0];
            } else {
                this.filePath = copyImageToProfilePictureFolder(profilePicture[0]);
            }
            this.url = "file:".concat(this.filePath.substring(2));
        } else {
            this.url = DEFAULT_IMG_URL;
            this.filePath = DEFAULT_IMG_URL.replace("file:", "./");
        }
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidProfilePicture(String test) {
        return test.matches(PROFILE_PICTURE_VALIDATION_REGEX);
    }

    /**
     * Returns if there exists profile picture.
     * @param profilePicture
     * @return
     */
    public static boolean hasValidProfilePicture(String profilePicture) {
        File file = new File(profilePicture);
        return file.exists() && !file.isDirectory();
    }

    public Image getImage() {
        return new Image(url);
    }

    @Override
    public String toString() {
        return filePath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePicture // instanceof handles nulls
                && this.filePath.equals(((ProfilePicture) other).filePath)); // state check
    }

    @Override
    public int hashCode() {
        return filePath.hashCode();
    }

    /**
     * copy the image from the absolute path to the Profile Picture Folder
     * @param profilePicture
     * @return destination path
     */
    private String copyImageToProfilePictureFolder(String profilePicture) {
        String destPath = "";
        try {
            File source = new File(profilePicture);
            String fileExtension = extractFileExtension(profilePicture);
            Date date = new Date();
            destPath = PROFILE_PICTURE_FOLDER.concat(
                    date.toString().replace(":", "").replace(" ", "").concat(
                            ".").concat(fileExtension));
            File dest = new File(destPath);
            Files.copy(source.toPath(), dest.toPath());
        } catch (IOException e) {
            // Exception will not happen as the profile picture path has been check through hasValidProfilePicture
        }
        return destPath;
    }

    /**
     * extract FileExtension from fileName
     * @param fileName
     * @return fileExtension
     */
    private String extractFileExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }
}
```
###### /main/java/seedu/address/logic/parser/appointment/YearCommandParser.java
``` java
/**
 * Parses input arguments and creates a new YearCommand object
 */
public class YearCommandParser implements Parser<YearCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the YearCommand
     * and returns an YearCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public YearCommand parse(String args) throws ParseException {
        try {
            Year year = ParserUtil.parseYear(args);
            return new YearCommand(year);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, YearCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /main/java/seedu/address/logic/parser/appointment/WeekCommandParser.java
``` java
/**
 * Parses input arguments and creates a new WeekCommand object
 */
public class WeekCommandParser implements Parser<WeekCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the WeekCommand
     * and returns an WeekCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WeekCommand parse(String args) throws ParseException {
        try {
            Year year = ParserUtil.parseYearOfWeek(args);
            int week = ParserUtil.parseWeek(args);
            return new WeekCommand(year, week);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, WeekCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /main/java/seedu/address/logic/parser/appointment/AddAppointmentCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_TITLE, PREFIX_START_DATE_TIME, PREFIX_END_DATE_TIME);
        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_START_DATE_TIME, PREFIX_END_DATE_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }
        try {
            Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE)).get();
            StartDateTime startDateTime =
                    ParserUtil.parseStartDateTime(argMultimap.getValue(PREFIX_START_DATE_TIME)).get();
            EndDateTime endDateTime = ParserUtil.parseEndDateTime(argMultimap.getValue(PREFIX_END_DATE_TIME)).get();

            Appointment appointment = new Appointment(title, startDateTime, endDateTime);

            return new AddAppointmentCommand(appointment);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### /main/java/seedu/address/logic/parser/appointment/DateCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DateCommand object
 */
public class DateCommandParser implements Parser<DateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DateCommand
     * and returns an DateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DateCommand parse(String args) throws ParseException {
        try {
            LocalDate date = ParserUtil.parseDate(args);
            return new DateCommand(date);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /main/java/seedu/address/logic/parser/appointment/MonthCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MonthCommand object
 */
public class MonthCommandParser implements Parser<MonthCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MonthCommand
     * and returns an MonthCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MonthCommand parse(String args) throws ParseException {
        try {
            YearMonth yearMonth = ParserUtil.parseYearMonth(args);
            return new MonthCommand(yearMonth);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MonthCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /main/java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> numberOfPositions} into an {@code Optional<String>}
     * if {@code numberOfPositions} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<NumberOfPositions> parseNumberOfPositions(Optional<String> numberOfPositions)
            throws IllegalValueException {
        requireNonNull(numberOfPositions);
        return numberOfPositions.isPresent() ? Optional.of(parseNumberOfPositions(numberOfPositions.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String yearMonth} into a {@code yearMonth}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code yearMonth} is invalid.
     */
    public static YearMonth parseYearMonth(String yearMonth) throws IllegalValueException {
        String trimmedYearMonth = yearMonth.trim();
        if (!trimmedYearMonth.matches(YEAR_MONTH_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_YEAR_MONTH_CONSTRAINTS);
        }
        if (trimmedYearMonth.length() == 0) {
            return null;
        } else {
            return YearMonth.parse(trimmedYearMonth);
        }
    }

    /**
     * Parses a {@code String date} into a {@code date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws IllegalValueException {
        String trimmedDate = date.trim();
        if (!trimmedDate.matches(DATE_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        if (trimmedDate.length() == 0) {
            return null;
        } else {
            return LocalDate.parse(trimmedDate);
        }
    }

    /**
     * Parses a {@code String date} into a {@code year}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code year} is invalid.
     */
    public static Year parseYear(String year) throws IllegalValueException {
        String trimmedYear = year.trim();
        if (!trimmedYear.matches(YEAR_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_YEAR_CONSTRAINTS);
        }
        if (trimmedYear.length() == 0) {
            return null;
        } else {
            return Year.parse(trimmedYear);
        }
    }

    /**
     * Parses a {@code String args} into a {@code year}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code args} is invalid.
     */
    public static Year parseYearOfWeek(String args) throws IllegalValueException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.matches(WEEK_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_WEEK_CONSTRAINTS);
        }
        if (trimmedArgs.length() == 0) {
            return null;
        } else {
            return Year.parse(trimmedArgs.substring(0, 4));
        }
    }

    /**
     * Parses a {@code String args} into a {@code week}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code args} is invalid.
     */
    public static int parseWeek(String args) throws IllegalValueException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.matches(WEEK_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_YEAR_CONSTRAINTS);
        }
        if (trimmedArgs.length() == 0) {
            return 0;
        } else {
            return Integer.parseInt(trimmedArgs.substring(5));
        }
    }
}
```
###### /main/java/seedu/address/logic/commands/appointment/AddAppointmentCommand.java
``` java
/**
 * Add appointment to calendar of addressbook
 */
public class AddAppointmentCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addapp";

    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_TITLE + " "
            + PREFIX_START_DATE_TIME + " "
            + PREFIX_END_DATE_TIME;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to calendar. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_START_DATE_TIME + "START DATE TIME "
            + PREFIX_END_DATE_TIME + "END DATE TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Birthday "
            + PREFIX_START_DATE_TIME + "2018-03-26 12:00 "
            + PREFIX_END_DATE_TIME + "2018-03-26 12:30 ";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the calendar";

    private final Appointment toAdd;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(Appointment appointment) {
        requireNonNull(appointment);
        toAdd = appointment;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addAppointment(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateAppointmentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && toAdd.equals(((AddAppointmentCommand) other).toAdd));
    }
}
```
###### /main/java/seedu/address/logic/commands/appointment/CalendarCommand.java
``` java
/**
 * Switch tab to Calendar
 */
public class CalendarCommand extends Command {
    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_SUCCESS = "Opened your calendar";

    public static final int TAB_ID = 2;

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new SwitchTabRequestEvent(TAB_ID));
        return new CommandResult(String.format(MESSAGE_SUCCESS));

    }

}
```
###### /main/java/seedu/address/logic/commands/appointment/MonthCommand.java
``` java
/**
 * Change view of calendar to specific month.
 */
public class MonthCommand extends Command {
    public static final String COMMAND_WORD = "month";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View month. "
            + "Parameters: MONTH (optional, but must be in format YYYY-MM if have)\n"
            + "Example: " + COMMAND_WORD + " 2018-03";

    public static final String MESSAGE_SUCCESS = "View month: %1$s";
    public static final String YEAR_MONTH_VALIDATION_REGEX = "^$|^\\d{4}-\\d{2}";
    public static final String MESSAGE_YEAR_MONTH_CONSTRAINTS = "Month needs to be null or in format YYYY-MM";

    private final YearMonth yearMonth;

    /**
     * Creates an MonthCommand to view the specified {@code yearMonth} or current if null
     */
    public MonthCommand(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowMonthRequestEvent(yearMonth));

        return new CommandResult(String.format(MESSAGE_SUCCESS, yearMonth));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MonthCommand // instanceof handles nulls
                && ((yearMonth == null && ((MonthCommand) other).yearMonth == null)
                || (yearMonth != null && ((MonthCommand) other).yearMonth != null
                && yearMonth.equals(((MonthCommand) other).yearMonth))));
    }
}
```
###### /main/java/seedu/address/logic/commands/appointment/YearCommand.java
``` java
/**
 * Change view of calendar to specific year.
 */
public class YearCommand extends Command {
    public static final String COMMAND_WORD = "year";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View year. "
            + "Parameters: year (optional, but must be in format YYYY if have)\n"
            + "Example: " + COMMAND_WORD + " 2018";

    public static final String MESSAGE_SUCCESS = "View year: %1$s";
    public static final String YEAR_VALIDATION_REGEX = "^$|^\\d{4}";
    public static final String MESSAGE_YEAR_CONSTRAINTS = "Year needs to be null or in format YYYY";

    private final Year year;

    /**
     * Creates an YearCommand to view the specified {@code yearMonth} or current if null
     */
    public YearCommand(Year year) {
        this.year = year;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowYearRequestEvent(year));

        return new CommandResult(String.format(MESSAGE_SUCCESS, year));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof YearCommand // instanceof handles nulls
                && ((year == null && ((YearCommand) other).year == null)
                || (year != null && ((YearCommand) other).year != null && year.equals(((YearCommand) other).year))));
    }
}
```
###### /main/java/seedu/address/logic/commands/appointment/WeekCommand.java
``` java
/**
 * Change view of calendar to specific week.
 */
public class WeekCommand extends Command {
    public static final String COMMAND_WORD = "week";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View week. "
            + "Parameters: YEAR WEEK (optional, but must be in format YYYY WW if have)\n"
            + "Example: " + COMMAND_WORD + " 2018";

    public static final String MESSAGE_SUCCESS = "View week: %1$s";
    public static final String WEEK_VALIDATION_REGEX = "^$|^\\d{4}\\s\\d{2}";
    public static final String MESSAGE_WEEK_CONSTRAINTS = "Week needs to be null or in format YYYY DD";

    private final Year year;
    private final int week;

    /**
     * Creates an WeekCommand to view the specified {@code week, year} or current if null
     */
    public WeekCommand(Year year, int week) {
        this.year = year;
        this.week = week;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowWeekRequestEvent(year, week));
        return new CommandResult(String.format(MESSAGE_SUCCESS, week + " " + year));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WeekCommand // instanceof handles nulls
                && ((year == null && ((WeekCommand) other).year == null)
                || (year != null && ((WeekCommand) other).year != null && year.equals(((WeekCommand) other).year))
                && week == (((WeekCommand) other).week)));
    }
}
```
###### /main/java/seedu/address/logic/commands/appointment/DateCommand.java
``` java
/**
 * Change view of calendar to specific date.
 */
public class DateCommand extends Command {
    public static final String COMMAND_WORD = "date";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View date. "
            + "Parameters: DATE (optional, but must be in format YYYY-MM-DD if have)\n"
            + "Example: " + COMMAND_WORD + " 2018-03-26";

    public static final String MESSAGE_SUCCESS = "View date: %1$s";
    public static final String DATE_VALIDATION_REGEX = "^$|^\\d{4}-\\d{2}-\\d{2}";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Date needs to be null or in format YYYY-MM-DD";

    private final LocalDate date;

    /**
     * Creates an DateCommand to view the specified {@code Date} or current if null
     */
    public DateCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowDateRequestEvent(date));

        return new CommandResult(String.format(MESSAGE_SUCCESS, date));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateCommand // instanceof handles nulls
                && ((date == null && ((DateCommand) other).date == null)
                || (date != null && ((DateCommand) other).date != null && date.equals(((DateCommand) other).date))));

    }
}
```
###### /main/java/seedu/address/ui/DetailsPanel.java
``` java
    @Subscribe
    private void handleReloadCalendarEvent(ReloadCalendarEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        addCalendarPanel(event.appointments);
    }
}
```
###### /main/java/seedu/address/ui/CalendarPanel.java
``` java
/**
 * The CalendarPanel of the App
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private CalendarSource source = new CalendarSource("My Calendars");
    private Calendar calendar = new Calendar("My Calendar");
    private ArrayList<Entry> entries = new ArrayList<Entry>();

    @FXML
    private CalendarView calendarView;



    public CalendarPanel(List<Appointment> appointmentsList) {
        super(FXML);
        System.out.println(appointmentsList.size());
        calendar.setStyle(Calendar.Style.STYLE1);
        addAppointments(appointmentsList);
        source.getCalendars().add(calendar);
        setUpCalendarView();
        startTimeThread();
        registerAsAnEventHandler(this);
    }

    /**
     * Add appointment to Calendar
     * @param appointments
     */
    private void addAppointments(List<Appointment> appointments) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Appointment appointment: appointments) {
            Entry entry = new Entry();
            entry.setCalendar(calendar);
            System.out.println(appointment.getTitle().title);
            LocalDateTime start = LocalDateTime.parse(appointment.getStartDateTime().startDateTime, formatter);
            LocalDateTime end = LocalDateTime.parse(appointment.getEndDateTime().endDateTime, formatter);
            entry.setInterval(start, end);
            entry.setTitle(appointment.getTitle().title);
            calendar.addEntry(entry);
        }
    }

    /**
     * Set up calendar View
     */
    public void setUpCalendarView() {
        calendarView.getCalendarSources().addAll(source);
        calendarView.setRequestedTime(LocalTime.now());
    }

    /**
     * Start time thread
     */
    private void startTimeThread() {
        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }

    @Subscribe
    private void handleShowDateRequestEvent(ShowDateRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.targetDate == null) {
            calendarView.showDayPage();
        } else {
            calendarView.showDate(event.targetDate);
        }
    }

    @Subscribe
    private void handleShowWeekRequestEvent(ShowWeekRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.targetYear == null) {
            calendarView.showWeekPage();
        } else {
            calendarView.showWeek(event.targetYear, event.targetWeek);
        }
    }

    @Subscribe
    private void handleShowMonthRequestEvent(ShowMonthRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.targetYearMonth == null) {
            calendarView.showMonthPage();
        } else {
            calendarView.showYearMonth(event.targetYearMonth);
        }
    }

    @Subscribe
    private void handleShowYearRequestEvent(ShowYearRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.targetYear == null) {
            calendarView.showYearPage();
        } else {
            calendarView.showYear(event.targetYear);
        }
    }
}
```
###### /main/java/seedu/address/commons/events/ui/ShowYearRequestEvent.java
``` java
/**
 * An event requesting to change view of Calendar to YearPage.
 */
public class ShowYearRequestEvent extends BaseEvent {
    public final Year targetYear;

    public ShowYearRequestEvent(Year year) {
        this.targetYear = year;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /main/java/seedu/address/commons/events/ui/ShowMonthRequestEvent.java
``` java
/**
 * An event requesting to change view of Calendar to MonthPage.
 */
public class ShowMonthRequestEvent extends BaseEvent {
    public final YearMonth targetYearMonth;

    public ShowMonthRequestEvent(YearMonth yearMonth) {
        this.targetYearMonth = yearMonth;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /main/java/seedu/address/commons/events/ui/ShowDateRequestEvent.java
``` java
/**
 * An event requesting to change view of Calendar to DatePage.
 */
public class ShowDateRequestEvent extends BaseEvent {
    public final LocalDate targetDate;

    public ShowDateRequestEvent(LocalDate date) {
        this.targetDate = date;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /main/java/seedu/address/commons/events/ui/ShowWeekRequestEvent.java
``` java
/**
 * An event requesting to change view of Calendar to WeekPage.
 */
public class ShowWeekRequestEvent extends BaseEvent {
    public final Year targetYear;
    public final int targetWeek;

    public ShowWeekRequestEvent(Year year, int week) {
        this.targetYear = year;
        this.targetWeek = week;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /main/resources/view/CalendarPanel.fxml
``` fxml
<StackPane xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/9">
    <CalendarView fx:id="calendarView" />
</StackPane>
```
