# trafalgarandre
###### /java/seedu/address/logic/commands/DateCommandTest.java
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
###### /java/seedu/address/logic/commands/MonthCommandTest.java
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
###### /java/seedu/address/logic/commands/DeleteAppointmentCommandTest.java
``` java
/**
 * {@code DeleteAppointmentCommand}.
 */
public class DeleteAppointmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validAppointment_success() throws Exception {
        Appointment appointmentToDelete = model.getAppointmentList().get(0);
        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(appointmentToDelete);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_SUCCESS, appointmentToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteAppointment(appointmentToDelete);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notAddedAppointment_throwsCommandException() throws Exception {
        Appointment notAddedAppointment = new AppointmentBuilder()
                .withTitle("Interview").withStartDateTime("2018-04-26 17:00")
                .withEndDateTime("2018-04-26 18:00").build();

        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(notAddedAppointment);

        assertCommandFailure(deleteAppointmentCommand, model, DeleteAppointmentCommand.MESSAGE_NOT_FOUND_APPOINTMENT);
    }

    @Test
    public void equals() throws Exception {
        DeleteAppointmentCommand deleteAppointmentFirstCommand = prepareCommand(BIRTHDAY);
        DeleteAppointmentCommand deleteAppointmentSecondCommand = prepareCommand(MEETING);

        // same object -> returns true
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommand));

        // same values -> returns true
        DeleteAppointmentCommand deleteAppointmentFirstCommandCopy = prepareCommand(BIRTHDAY);
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(null));

        // different appointment -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(deleteAppointmentSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteAppointmentCommand prepareCommand(Appointment appointment) {
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(appointment);
        deleteAppointmentCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteAppointmentCommand;
    }
}
```
###### /java/seedu/address/logic/commands/WeekCommandTest.java
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
###### /java/seedu/address/logic/commands/YearCommandTest.java
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
###### /java/seedu/address/logic/commands/CalendarCommandTest.java
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
###### /java/seedu/address/logic/commands/AddAppointmentCommandTest.java
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
        public void login(String username, String password) throws InvalidUsernameException,
                InvalidPasswordException, MultipleLoginException {
            fail("This method should not be called.");
        };

        @Override
        public void logout() throws UserLogoutException {
            fail("This method should not be called.");
        };



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
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseProfilePicture_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseProfilePicture((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseProfilePicture((Optional<String>) null));
    }

    @Test
    public void parseProfilePicture_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseProfilePicture(INVALID_PROFILE_PICTURE));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseProfilePicture(Optional.of(INVALID_PROFILE_PICTURE)));
    }

    @Test
    public void parseProfilePicture_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseProfilePicture(Optional.empty()).isPresent());
    }

    @Test
    public void parseProfilePicture_validValueWithoutWhitespace_returnsProfilePicture() throws Exception {
        ProfilePicture expectedProfilePicture = new ProfilePicture(VALID_PROFILE_PICTURE);
        assertEquals(expectedProfilePicture, ParserUtil.parseProfilePicture(VALID_PROFILE_PICTURE));
        assertEquals(Optional.of(expectedProfilePicture),
            ParserUtil.parseProfilePicture(Optional.of(VALID_PROFILE_PICTURE)));
    }

    @Test
    public void parseProfilePictUre_validValueWithWhitespace_returnsTrimmedProfilePicture() throws Exception {
        String profilePictureWithWhitespace = WHITESPACE + VALID_PROFILE_PICTURE + WHITESPACE;
        ProfilePicture expectedProfilePicture = new ProfilePicture(VALID_PROFILE_PICTURE);
        assertEquals(expectedProfilePicture, ParserUtil.parseProfilePicture(profilePictureWithWhitespace));
        assertEquals(Optional.of(expectedProfilePicture),
            ParserUtil.parseProfilePicture(Optional.of(profilePictureWithWhitespace)));
    }

```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parsetTitle_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTitle((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTitle((Optional<String>) null));
    }

    @Test
    public void parseTitle_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseTitle(INVALID_TITLE));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseTitle(Optional.of(INVALID_TITLE)));
    }

    @Test
    public void parseTitle_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTitle(Optional.empty()).isPresent());
    }

    @Test
    public void parseTitle_validValueWithoutWhitespace_returnsTitle() throws Exception {
        Title expectedTitle = new Title(VALID_TITLE);
        assertEquals(expectedTitle, ParserUtil.parseTitle(VALID_TITLE));
        assertEquals(Optional.of(expectedTitle), ParserUtil.parseTitle(Optional.of(VALID_TITLE)));
    }

    @Test
    public void parseTitle_validValueWithWhitespace_returnsTrimmedTitle() throws Exception {
        String titleWithWhitespace = WHITESPACE + VALID_TITLE + WHITESPACE;
        Title expectedTitle = new Title(VALID_TITLE);
        assertEquals(expectedTitle, ParserUtil.parseTitle(titleWithWhitespace));
        assertEquals(Optional.of(expectedTitle), ParserUtil.parseTitle(Optional.of(titleWithWhitespace)));
    }

    @Test
    public void parsetStartDateTime_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseStartDateTime((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseStartDateTime((Optional<String>) null));
    }

    @Test
    public void parseStartDateTime_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseStartDateTime(INVALID_START_DATE_TIME));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseStartDateTime(Optional.of(INVALID_START_DATE_TIME)));
    }

    @Test
    public void parseStartDateTime_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseStartDateTime(Optional.empty()).isPresent());
    }

    @Test
    public void parseStartDateTime_validValueWithoutWhitespace_returnsStartDateTime() throws IllegalValueException {
        StartDateTime expectedStartDateTime = new StartDateTime(VALID_START_DATE_TIME);
        assertEquals(expectedStartDateTime, ParserUtil.parseStartDateTime(VALID_START_DATE_TIME));
        assertEquals(Optional.of(expectedStartDateTime),
            ParserUtil.parseStartDateTime(Optional.of(VALID_START_DATE_TIME)));
    }

    @Test
    public void parseStartDateTime_validValueWithWhitespace_returnsTrimmedStartDateTime() throws IllegalValueException {
        String startDateTimeWithWhitespace = WHITESPACE + VALID_START_DATE_TIME + WHITESPACE;
        StartDateTime expectedStartDateTime = new StartDateTime(VALID_START_DATE_TIME);
        assertEquals(expectedStartDateTime, ParserUtil.parseStartDateTime(startDateTimeWithWhitespace));
        assertEquals(Optional.of(expectedStartDateTime),
            ParserUtil.parseStartDateTime(Optional.of(startDateTimeWithWhitespace)));
    }

    @Test
    public void parsetEndDateTime_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEndDateTime((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEndDateTime((Optional<String>) null));
    }

    @Test
    public void parseEndDateTime_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseEndDateTime(INVALID_END_DATE_TIME));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseEndDateTime(Optional.of(INVALID_END_DATE_TIME)));
    }

    @Test
    public void parseEndDateTime_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEndDateTime(Optional.empty()).isPresent());
    }

    @Test
    public void parseEndDateTime_validValueWithoutWhitespace_returnsEndDateTime() throws IllegalValueException {
        EndDateTime expectedEndDateTime = new EndDateTime(VALID_END_DATE_TIME);
        assertEquals(expectedEndDateTime, ParserUtil.parseEndDateTime(VALID_END_DATE_TIME));
        assertEquals(Optional.of(expectedEndDateTime),
            ParserUtil.parseEndDateTime(Optional.of(VALID_END_DATE_TIME)));
    }

    @Test
    public void parseEndDateTime_validValueWithWhitespace_returnsTrimmedEndDateTime() throws IllegalValueException {
        String endDateTimeWithWhitespace = WHITESPACE + VALID_END_DATE_TIME + WHITESPACE;
        EndDateTime expectedEndDateTime = new EndDateTime(VALID_END_DATE_TIME);
        assertEquals(expectedEndDateTime, ParserUtil.parseEndDateTime(endDateTimeWithWhitespace));
        assertEquals(Optional.of(expectedEndDateTime),
            ParserUtil.parseEndDateTime(Optional.of(endDateTimeWithWhitespace)));
    }

    @Test
    public void parseYearMonth_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseYearMonth(INVALID_MONTH));
    }

    @Test
    public void parseYearMonth_null_returnsYearMonth() throws IllegalValueException {
        YearMonth expectedYearMonth = null;
        assertEquals(expectedYearMonth, ParserUtil.parseYearMonth(EMPTY_STRING));
        assertEquals(expectedYearMonth, ParserUtil.parseYearMonth(WHITESPACE));
    }

    @Test
    public void parseYearMonth_validValueWithoutWhitespace_returnsYearMonth() throws Exception {
        YearMonth expectedYearMonth = YearMonth.parse(VALID_MONTH);
        assertEquals(expectedYearMonth, ParserUtil.parseYearMonth(VALID_MONTH));
    }

    @Test
    public void parseYearMonth_validValueWithWhitespace_returnsTrimmedYearMonth() throws Exception {
        String yearMonthWithWhitespace = WHITESPACE + VALID_MONTH + WHITESPACE;
        YearMonth expectedYearMonth = YearMonth.parse(VALID_MONTH);
        assertEquals(expectedYearMonth, ParserUtil.parseYearMonth(yearMonthWithWhitespace));
    }

    @Test
    public void parseDate_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_null_returnsDate() throws IllegalValueException {
        LocalDate expectedDate = null;
        assertEquals(expectedDate, ParserUtil.parseDate(EMPTY_STRING));
        assertEquals(expectedDate, ParserUtil.parseDate(WHITESPACE));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws IllegalValueException {
        LocalDate expectedDate = LocalDate.parse(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        LocalDate expectedDate = LocalDate.parse(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseYear_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseYear(INVALID_YEAR));
    }

    @Test
    public void parseYear_null_returnsYear() throws IllegalValueException {
        Year expectedYear = null;
        assertEquals(expectedYear, ParserUtil.parseYear(EMPTY_STRING));
        assertEquals(expectedYear, ParserUtil.parseYear(WHITESPACE));
    }

    @Test
    public void parseYear_validValueWithoutWhitespace_returnsYear() throws IllegalValueException {
        Year expectedYear = Year.parse(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYear(VALID_YEAR));
    }

    @Test
    public void parseYear_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String yearWithWhitespace = WHITESPACE + VALID_YEAR + WHITESPACE;
        Year expectedYear = Year.parse(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYear(yearWithWhitespace));
    }

    @Test
    public void parseWeek_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseYearOfWeek(INVALID_WEEK));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseWeek(INVALID_WEEK));
    }

    @Test
    public void parseWeek_null_returnsWeek() throws IllegalValueException {
        Year expectedYear = null;
        assertEquals(expectedYear, ParserUtil.parseYearOfWeek(EMPTY_STRING));
        assertEquals(expectedYear, ParserUtil.parseYearOfWeek(WHITESPACE));
        int expectedWeek = 0;
        assertEquals(expectedWeek, ParserUtil.parseWeek(EMPTY_STRING));
        assertEquals(expectedWeek, ParserUtil.parseWeek(WHITESPACE));
    }

    @Test
    public void parseWeek_validValueWithoutWhitespace_returnsWeek() throws IllegalValueException {
        Year expectedYear = Year.parse(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYearOfWeek(VALID_WEEK));
        int expectedWeek = Integer.parseInt(VALID_WEEK.substring(5));
        assertEquals(expectedWeek, ParserUtil.parseWeek(VALID_WEEK));
    }

    @Test
    public void parseWeek_validValueWithWhitespace_returnsTrimmedWeek() throws Exception {
        String weekWithWhitespace = WHITESPACE + VALID_WEEK + WHITESPACE;
        Year expectedYear = Year.parse(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYearOfWeek(weekWithWhitespace));
        int expectedWeek = Integer.parseInt(VALID_WEEK.substring(5));
        assertEquals(expectedWeek, ParserUtil.parseWeek(weekWithWhitespace));
    }
}
```
###### /java/seedu/address/logic/parser/WeekCommandParserTest.java
``` java
public class WeekCommandParserTest {

    private WeekCommandParser parser = new WeekCommandParser();

    @Test
    public void parse_validArgs_returnsDateCommand() {
        assertParseSuccess(parser, "", new WeekCommand(null, 0));
        assertParseSuccess(parser, "2018 03", new WeekCommand(Year.parse("2018"), 3));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "2018",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, WeekCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "04",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, WeekCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "2018-04",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, WeekCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/AddAppointmentParserTest.java
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
###### /java/seedu/address/logic/parser/DateCommandParserTest.java
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
###### /java/seedu/address/logic/parser/MonthCommandParserTest.java
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
###### /java/seedu/address/logic/parser/DeleteAppointmentParserTest.java
``` java
public class DeleteAppointmentParserTest {
    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Appointment expectedAppointment = new AppointmentBuilder().withTitle(VALID_TITLE_INTERVIEW2)
                .withStartDateTime(VALID_START_DATE_TIME_INTERVIEW2).withEndDateTime(VALID_END_DATE_TIME_INTERVIEW2)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_INTERVIEW2
                        + START_DATE_TIME_DESC_INTERVIEW2 + END_DATE_TIME_DESC_INTERVIEW2,
                new DeleteAppointmentCommand(expectedAppointment));

        // multiple title - last title accepted
        assertParseSuccess(parser, TITLE_DESC_INTERVIEW1 + TITLE_DESC_INTERVIEW2
                        + START_DATE_TIME_DESC_INTERVIEW2 + END_DATE_TIME_DESC_INTERVIEW2,
                new DeleteAppointmentCommand(expectedAppointment));

        // multiple start date time - last start date time accepted
        assertParseSuccess(parser, TITLE_DESC_INTERVIEW2 + START_DATE_TIME_DESC_INTERVIEW1
                        + START_DATE_TIME_DESC_INTERVIEW2 + END_DATE_TIME_DESC_INTERVIEW2,
                new DeleteAppointmentCommand(expectedAppointment));

        // multiple end date time - last end date time accepted
        assertParseSuccess(parser, TITLE_DESC_INTERVIEW2 + START_DATE_TIME_DESC_INTERVIEW2
                        + END_DATE_TIME_DESC_INTERVIEW1 + END_DATE_TIME_DESC_INTERVIEW2,
                new DeleteAppointmentCommand(expectedAppointment));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE);

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
                + VALID_END_DATE_TIME_INTERVIEW2, expectedMessage);
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
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_calendar() throws Exception {
        assertTrue(parser.parseCommand(CalendarCommand.COMMAND_WORD) instanceof CalendarCommand);
    }

    @Test
    public void parseCommand_addapp() throws Exception {
        Appointment appointment = new AppointmentBuilder().build();
        AddAppointmentCommand command =
                (AddAppointmentCommand) parser.parseCommand(AppointmentUtil.getAddAppointmentCommand(appointment));
        assertEquals(new AddAppointmentCommand(appointment), command);
    }

    @Test
    public void parseCommand_dateCommand() throws Exception {
        DateCommand command = (DateCommand) parser.parseCommand(
                DateCommand.COMMAND_WORD + " " + FIRST_DATE);
        assertEquals(new DateCommand(FIRST_DATE), command);
    }

    @Test
    public void parseCommand_weekCommand() throws Exception {
        WeekCommand command = (WeekCommand) parser.parseCommand(
                WeekCommand.COMMAND_WORD + " " + FIRST_YEAR + " " + String.format("%02d", FIRST_WEEK));
        assertEquals(new WeekCommand(FIRST_YEAR, FIRST_WEEK), command);
    }

    @Test
    public void parseCommand_monthCommand() throws Exception {
        MonthCommand command = (MonthCommand) parser.parseCommand(
                MonthCommand.COMMAND_WORD + " " + FIRST_YEAR_MONTH);
        assertEquals(new MonthCommand(FIRST_YEAR_MONTH), command);
    }

    @Test
    public void parseCommand_yearCommand() throws Exception {
        YearCommand command = (YearCommand) parser.parseCommand(
                YearCommand.COMMAND_WORD + " " + FIRST_YEAR);
        assertEquals(new YearCommand(FIRST_YEAR), command);
    }
}
```
###### /java/seedu/address/logic/parser/YearCommandParserTest.java
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
###### /java/seedu/address/model/person/ProfilePictureTest.java
``` java
public class ProfilePictureTest {

    @Test
    public void constructor_invalidProfilePicture_throwsIllegalArgumentException() {
        String invalidProfilePicture = "andre.jjp";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ProfilePicture(invalidProfilePicture));
    }

    @Test
    public void isValidProfilePicture() {

        String temp;
        // invalid ProfilePicture
        assertFalse(ProfilePicture.isValidProfilePicture("91fae")); // random string
        assertFalse(ProfilePicture.isValidProfilePicture("phone.jpp")); // wrong ending
        temp = "abc.jpg";
        assertFalse(ProfilePicture.isValidProfilePicture(temp)
                && ProfilePicture.hasValidProfilePicture(temp)); // not exists

        // valid profile picture
        assertTrue(ProfilePicture.isValidProfilePicture("")); //no image => default image
        temp = "./src/test/data/images/alex.jpeg";
        assertTrue(ProfilePicture.isValidProfilePicture(temp)
                && ProfilePicture.hasValidProfilePicture(temp)); //absolute path
    }
}
```
###### /java/seedu/address/model/appointment/EndDateTimeTest.java
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
###### /java/seedu/address/model/appointment/StartDateTimeTest.java
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
###### /java/seedu/address/model/appointment/TitleTest.java
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
###### /java/seedu/address/storage/XmlAdaptedAppointmentTest.java
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
###### /java/seedu/address/testutil/EditAppointmentDescriptorBuilder.java
``` java
/**
 * A utility class to help with building EditAppointmentDescriptor objects.
 */
public class EditAppointmentDescriptorBuilder {
}
```
###### /java/seedu/address/testutil/AddressBookBuilder.java
``` java
    /**
     * Parses {@code Appointment} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withAppointment(Appointment appointment) {
        try {
            addressBook.addAppointment(appointment);
        } catch (DuplicateAppointmentException dae) {
            throw new IllegalArgumentException("Appointment is expected to be unique");
        }
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
```
###### /java/seedu/address/testutil/TypicalAppointments.java
``` java
/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {
    public static final Appointment BIRTHDAY = new AppointmentBuilder()
            .withTitle("Birthday").withStartDateTime("2018-03-26 00:00").withEndDateTime("2018-03-26 23:59").build();
    public static final Appointment MEETING = new AppointmentBuilder()
            .withTitle("Meeting").withStartDateTime("2018-04-20 10:00").withEndDateTime("2018-04-20 12:00").build();

    private TypicalAppointments() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical appointments.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Appointment appointment : getTypicalAppointments()) {
            try {
                ab.addAppointment(appointment);
            } catch (DuplicateAppointmentException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(BIRTHDAY, MEETING));
    }
}
```
###### /java/seedu/address/testutil/AppointmentBuilder.java
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
###### /java/seedu/address/testutil/AppointmentUtil.java
``` java
/**
 * A utility class for Appointment.
 */
public class AppointmentUtil {

    /**
     * Returns an addAppointment command string for adding the {@code appointment}.
     */
    public static String getAddAppointmentCommand(Appointment appointment) {
        return AddAppointmentCommand.COMMAND_WORD + " " + getAppointmentDetails(appointment);
    }

    /**
     * Returns the part of command string for the given {@code appointment}'s details.
     */
    public static String getAppointmentDetails(Appointment appointment) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + appointment.getTitle().title + " ");
        sb.append(PREFIX_START_DATE_TIME + appointment.getStartDateTime().startDateTime + " ");
        sb.append(PREFIX_END_DATE_TIME + appointment.getEndDateTime().endDateTime + " ");
        return sb.toString();
    }
}
```
